package com.noreabang.strawberryrabbit.domain.member.service

import com.noreabang.strawberryrabbit.domain.member.dto.MemberCreateRequest
import com.noreabang.strawberryrabbit.domain.member.dto.MemberResponse
import com.noreabang.strawberryrabbit.domain.member.dto.MemberUpdateRequest
import com.noreabang.strawberryrabbit.domain.member.model.Member
import com.noreabang.strawberryrabbit.domain.member.repository.MemberRepository
import com.noreabang.strawberryrabbit.infra.email.service.RedisService
import com.noreabang.strawberryrabbit.infra.exception.ModelNotFoundException
import com.noreabang.strawberryrabbit.infra.secutiry.CustomMemberDetails
import com.noreabang.strawberryrabbit.infra.secutiry.exception.CustomJwtException
import com.noreabang.strawberryrabbit.infra.secutiry.util.JwtUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val jwtUtil: JwtUtil,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val redisService: RedisService
) {
    @Transactional
    fun createMember(memberCreateRequest: MemberCreateRequest): MemberResponse {
        val authNumber = redisService.getAuthNumber(memberCreateRequest.email)

        if (authNumber == null) { // 인증 DB에 메일(key)이 없는 경우(인증 시간 초과 또는 인증 요청을 하지 않음 등)
            throw IllegalArgumentException("Authentication timeout or no authentication request")
        }

        if (authNumber != memberCreateRequest.authNumber) { // 인증 번호가 일치하지 않는 경우
            throw IllegalArgumentException("The authentication number does not match.")
        }

        return memberRepository.save(
            Member.createMember(
                memberCreateRequest,
                bCryptPasswordEncoder.encode(memberCreateRequest.password),
            )
        ).toResponse()
    }

    fun getMemberById(id: Long): Member {
        return memberRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("Member", id)
    }

    fun getMemberByEmail(email: String): String? {
        return memberRepository.findByEmail(email).email ?: null
    }

    fun getMemberDetails(): CustomMemberDetails? {
        val principal = SecurityContextHolder.getContext().authentication.principal
        return if (principal is CustomMemberDetails) principal else null
    }

    @Transactional
    fun updateMember(memberUpdateRequest: MemberUpdateRequest, memberId: Long): MemberResponse {
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId)

        return member.update(
            memberUpdateRequest,
            bCryptPasswordEncoder.encode(memberUpdateRequest.password)
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
}