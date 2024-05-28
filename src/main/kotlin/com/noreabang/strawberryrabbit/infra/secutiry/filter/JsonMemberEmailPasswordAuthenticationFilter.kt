package com.noreabang.strawberryrabbit.infra.secutiry.filter

import com.fasterxml.jackson.module.kotlin.jsonMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.util.StreamUtils
import java.nio.charset.StandardCharsets

class JsonMemberEmailPasswordAuthenticationFilter :
    AbstractAuthenticationProcessingFilter(AntPathRequestMatcher("/members/signin", "POST")) {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {

        // contentType이 null이거나 application/json이 아니면 예외 발생
        if (request.contentType == null || request.contentType != "application/json") {
            throw AuthenticationServiceException("Authentication Content-Type not supported: " + request.contentType)
        }

        // request의 응답 본문을 읽어서 DTO로 변환
        val loginDto: LoginDto = jsonMapper().readValue(
            StreamUtils.copyToString(request.inputStream, StandardCharsets.UTF_8),
            LoginDto::class.java
        )

        // UsernamePasswordAuthenticationToken : 사용자의 인증 정보(사용자 ID = Principal, 비밀번호 = Credential)를 담는 역할
        val authRequest = UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password)
        setDetails(request, authRequest)

        return authenticationManager.authenticate(authRequest) // 검증: 요청으로 받은 사용자 정보와 DB에 저장된 사용자 정보를 비교 인증 수행
    }

    // 사용자 요청(request)에서 추출한 세부 정보를 사용자의 인증 정보에 세부 정보를 추가
    private fun setDetails(request: HttpServletRequest?, authRequest: UsernamePasswordAuthenticationToken) {
        authRequest.details = authenticationDetailsSource.buildDetails(request)
    }

    private data class LoginDto(
        var email: String,
        var password: String
    ) {
        // 기본 생성자가 없으면, InvalidDefinitionException: Cannot construct instance
        constructor() : this("", "")
    }
}