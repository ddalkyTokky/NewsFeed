package com.noreabang.strawberryrabbit.infra.secutiry.filter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.noreabang.strawberryrabbit.domain.member.service.MemberService
import com.noreabang.strawberryrabbit.infra.secutiry.CustomMemberDetails
import com.noreabang.strawberryrabbit.infra.secutiry.util.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtCheckFilter(
    private val jwtUtil: JwtUtil,
    private val memberService: MemberService
) : OncePerRequestFilter() { // OncePerRequestFilter : 모든 요청에 대해 체크할 때 사용

    override fun shouldNotFilter(request: HttpServletRequest): Boolean { // 필터로 체크하지 않을 경로 or 메서드 지정
        val path = request.requestURI
        val urls = listOf("/members/signin", "/members/signup", "/members/refresh", "/email")

        // // GET 요청은 필터링 X || path의 접두사와 일치하는 URI가 있으면 필터 체크 X
        return "GET".equals(request.method) || urls.any { path.startsWith(it) }
    }

    override fun doFilterInternal(
        // 모든 요청에 대해 체크하려고 할 때 사용
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val authHeader = request.getHeader("Authorization")

        try {
            val accessToken = authHeader
            val claims = jwtUtil.validateToken(accessToken)

            // token의 ID가 DB에 존재하는지 확인
            val member = memberService.getMemberById(claims["id"].toString().toLong())

            val user = CustomMemberDetails(member)

            val authenticationToken = UsernamePasswordAuthenticationToken(user, user.password, user.authorities)

            SecurityContextHolder.getContext().authentication = authenticationToken

            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            logger.error(e.message)

            var errorMessage = e.message

            if (e.message == null) { // 헤더에 토큰을 넣지 않은 경우: NullPointException
                errorMessage = "No token"
            }

            if (e.message?.startsWith("Range [7") == true) { // 헤더의 값의 길이가 짧은 경우(ex: Bear)
                errorMessage = "Check authentication type"
            }

            response.status = HttpStatus.BAD_REQUEST.value()
            response.contentType = MediaType.APPLICATION_JSON_VALUE

            jacksonObjectMapper().writeValue(response.writer, errorMessage)
        }
    }
}