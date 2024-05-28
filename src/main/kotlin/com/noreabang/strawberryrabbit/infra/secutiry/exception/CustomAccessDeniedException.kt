package com.noreabang.strawberryrabbit.infra.secutiry.exception

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

class CustomAccessDeniedException(val message: String?) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        response?.status = HttpStatus.FORBIDDEN.value()
        response?.contentType = MediaType.APPLICATION_JSON_VALUE

        response?.let { jacksonObjectMapper().writeValueAsString(message) }
    }
}