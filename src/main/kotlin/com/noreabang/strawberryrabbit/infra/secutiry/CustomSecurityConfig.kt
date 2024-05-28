package com.noreabang.strawberryrabbit.infra.secutiry

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class CustomSecurityConfig {
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder() // 비밀번호 암호화

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        configuration.setAllowedOriginPatterns(listOf("*")) // 모든 출처에서 요청 허용
        configuration.allowedMethods = listOf("HEAD", "GET", "POST", "PUT", "DELETE") // 허용 HTTP 메서드 지정
        configuration.allowedHeaders = listOf("Authorization", "Cache-Control", "Content-Type") // 허용 HTTP 헤더 지정
        configuration.allowCredentials = true // 자격 증명(쿠키, HTTP 인증, Client SSL 인증 등)

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration) // 모든 URL 패턴에 대해 설정한 configuration 적용

        return source
    }
}