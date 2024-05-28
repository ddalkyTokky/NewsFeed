package com.noreabang.strawberryrabbit.infra.secutiry.handler

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.noreabang.strawberryrabbit.infra.secutiry.CustomMemberDetails
import com.noreabang.strawberryrabbit.infra.secutiry.util.JwtUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component


@Component
class CustomAuthenticationSuccessHandler(
    private val jwtUtil: JwtUtil
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val member: CustomMemberDetails = authentication.principal as CustomMemberDetails
        val claims: MutableMap<String, Any> = member.getClaims().toMutableMap()

        val accessToken = jwtUtil.generateToken(claims, 60) // 10분
        val refreshToken = jwtUtil.generateToken(claims, 60 * 24) // 24시간

        claims["accessToken"] = accessToken
        claims["refreshToken"] = refreshToken

        response.status = HttpStatus.OK.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        jacksonObjectMapper().writeValue(response.writer, claims)
    }
}