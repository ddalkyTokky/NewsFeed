package com.noreabang.strawberryrabbit.domain.member.service

import com.noreabang.strawberryrabbit.domain.member.dto.MemberCreateRequest
import com.noreabang.strawberryrabbit.domain.member.dto.MemberResponse
import com.noreabang.strawberryrabbit.domain.member.dto.MemberUpdateRequest
import com.noreabang.strawberryrabbit.domain.member.model.Member
import com.noreabang.strawberryrabbit.domain.member.model.SignUpType
import com.noreabang.strawberryrabbit.domain.member.repository.MemberRepository
import com.noreabang.strawberryrabbit.infra.email.service.RedisService
import com.noreabang.strawberryrabbit.infra.exception.ModelNotFoundException
import com.noreabang.strawberryrabbit.infra.secutiry.CustomMemberDetails
import com.noreabang.strawberryrabbit.infra.secutiry.exception.CustomJwtException
import com.noreabang.strawberryrabbit.infra.secutiry.util.JwtUtil
import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import java.util.*

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val jwtUtil: JwtUtil,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val redisService: RedisService
) {
    private val log = LoggerFactory.getLogger(MemberService::class.java)

    @Transactional
    fun emailSignupMember(memberCreateRequest: MemberCreateRequest, image: String?): MemberResponse {
        val authNumber = redisService.getAuthNumber(memberCreateRequest.email)
            ?: throw IllegalArgumentException("Authentication timeout or no authentication request") // 인증 DB에 메일(key)이 없는 경우(인증 시간 초과 또는 인증 요청을 하지 않음 등)

        if (authNumber != memberCreateRequest.authNumber) { // 인증 번호가 일치하지 않는 경우
            throw IllegalArgumentException("The authentication number does not match.")
        }

        return memberRepository.save(
            Member.createMember(
                memberCreateRequest,
                bCryptPasswordEncoder.encode(memberCreateRequest.password),
                image,
                SignUpType.EMAIL
            )
        ).toResponse()
    }

    fun getMemberById(id: Long): Member {
        return memberRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("Member", id)
    }

    fun getMemberByEmail(email: String): Member? {
        return memberRepository.findByEmail(email)
    }

    fun getMemberDetails(): CustomMemberDetails? {
        val principal = SecurityContextHolder.getContext().authentication.principal
        return if (principal is CustomMemberDetails) principal else null
    }

    @Transactional
    fun updateMember(memberUpdateRequest: MemberUpdateRequest, memberId: Long, image: String?): MemberResponse {
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId)

        return member.update(
            memberUpdateRequest,
            bCryptPasswordEncoder.encode(memberUpdateRequest.password),
            image
        ).toResponse()
    }

    @Transactional
    fun deleteMember(memberId: Long) {
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId)

        memberRepository.delete(member)
    }

    fun refresh(authHeader: String, refreshToken: String): Map<String, Any> {
        if (authHeader.length < 7) {
            throw CustomJwtException("INVALID_STRING")
        }

        val accessToken = authHeader.substring(7)

        if (!checkExpiredToken(accessToken)) { // AccessToken이 만료되지 않았다면 그대로 반환
            return mapOf("accessToken" to accessToken, "refreshToken" to refreshToken)
        }

        val claims = jwtUtil.validateToken(refreshToken)
        val newAccessToken = jwtUtil.generateToken(claims, 60) // 60분
        val newRefreshToken =
            if (checkTime(claims["exp"] as Int)) jwtUtil.generateToken(claims, 60 * 24) else refreshToken

        return mapOf("accessToken" to newAccessToken, "refreshToken" to newRefreshToken)
    }

    fun checkTime(exp: Int): Boolean { // 1시간 미만 여부
        val expDate = Date(exp.toLong() * 1000) // JWT exp를 날짜로 변환
        val gap = expDate.time - System.currentTimeMillis() // 현재 시간과 차이 계산(ms)
        val leftMin = gap / (1000 * 60)
        println("checkTime - expDate: $expDate, gap: $gap, leftMin: $leftMin")

        return leftMin < exp
    }

    fun checkExpiredToken(token: String): Boolean {
        try {
            jwtUtil.validateToken(token)
        } catch (e: CustomJwtException) {
            if (e.message == "Expired") {
                return true
            }
        }
        return false
    }

    fun getMemberInfoFormKakao(accessToken: String, refreshToken: String): MutableMap<String, Any> { // 엑세스 토큰으로 사용자 정보 가져오기
        // 토큰 값이 null인 경우, 카카오 서버측에서는 우리가 클라이언트이기 때문에 400을 우리에게 던짐
        // 그러면 우리의 클라이언트 쪽에는 500에러가 전달되므로 여기서 예외를 잡아서 프런트 쪽으로 전달해야 함
        if (accessToken == "undefined") {
            throw IllegalArgumentException("Access token cannot be empty")
        }
        val getMemberInfoURL = "https://kapi.kakao.com/v2/user/me"

        val restTemplate = RestTemplate()

        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer $accessToken")
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")

        val entity = HttpEntity<String>(headers)

        val responseBody = restTemplate.exchange(getMemberInfoURL, HttpMethod.GET, entity, Map::class.java).body
        val kakaoAccount = responseBody?.get("kakao_account") as Map<*, *>

        val email = kakaoAccount["email"]
        val profile = kakaoAccount["profile"] as Map<*, *>
        val nickname = profile["nickname"]
        val profileImageUrl = profile["profile_image_url"]

        log.info("********** email: {}", email)
        log.info("********** nickname: {}", nickname)
        log.info("********** profileImageUrl: {}", profileImageUrl)

        try { // 일반 회원으로 가입되어 있다면 소셜 로그인 X
            val isExistEmail = memberRepository.findByEmail(email.toString())

            throw IllegalArgumentException("This email already exists.")

            return mutableMapOf(email.toString() to "already exist")
        } catch (e: EmptyResultDataAccessException) { // DB에 존재하지 않는 이메일이면 가입
            val member = Member.createMember(
                MemberCreateRequest(
                    email = email.toString(),
                    nickname = nickname.toString(),
                    password = "",
                    authNumber = ""
                ),
                refreshToken, // 소셜은 token이 password
                profileImageUrl.toString(),
                SignUpType.KAKAO
            )
            memberRepository.save(
                member
            )

            val claims = CustomMemberDetails(member).getClaims().toMutableMap()

            val accessToken = jwtUtil.generateToken(claims, 60) // 60분
            val refreshToken = jwtUtil.generateToken(claims, 60 * 24) // 24시간

            claims["accessToken"] = accessToken
            claims["refreshToken"] = refreshToken

            return claims
        }
    }
}