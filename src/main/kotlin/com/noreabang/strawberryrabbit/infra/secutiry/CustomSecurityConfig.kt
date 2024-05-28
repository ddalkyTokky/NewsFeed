package com.noreabang.strawberryrabbit.infra.secutiry


import com.noreabang.strawberryrabbit.infra.secutiry.exception.CustomAccessDeniedException
import com.noreabang.strawberryrabbit.infra.secutiry.filter.JsonMemberEmailPasswordAuthenticationFilter
import com.noreabang.strawberryrabbit.infra.secutiry.filter.JwtCheckFilter
import com.noreabang.strawberryrabbit.infra.secutiry.handler.CustomAuthenticationFailureHandler
import com.noreabang.strawberryrabbit.infra.secutiry.handler.CustomAuthenticationSuccessHandler
import com.noreabang.strawberryrabbit.infra.secutiry.util.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class CustomSecurityConfig(
    private val userDetailsService: CustomMemberDetailsService,
    private val customAuthenticationSuccessHandler: CustomAuthenticationSuccessHandler,
    private val customAuthenticationFailureHandler: CustomAuthenticationFailureHandler
) {
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder() // 비밀번호 암호화

    @Bean
    fun securityFilterChain(http: HttpSecurity, jwtUtil: JwtUtil): SecurityFilterChain {
        return http
            .addFilterBefore( // JWT 필터
                JwtCheckFilter(jwtUtil), UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilterBefore( // JSON 데이터 처리
                jsonUsernamePasswordAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .csrf { it.disable() } // API 서버로 사용하므로 비활성화
            .cors { it.configurationSource(corsConfigurationSource()) } // cors 설정
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // 서버 내부에서 세션 생성 X
            .formLogin { it.disable() } // 기본 로그인 폼 비활성화
            .exceptionHandling { it.accessDeniedHandler(CustomAccessDeniedException("ERROR_ACCESSIONED")) } // 접근 거부 오류가 발생 시
            .build()
    }

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

    @Bean
    fun authenticationManager(): AuthenticationManager {
        val authProvider = DaoAuthenticationProvider() // 사용자 이름과 비밀번호를 기반으로 인증을 수행하는 구성 요소
        authProvider.setUserDetailsService(userDetailsService) // 사용자의 세부 정보를 불러오기 위한 설정
        authProvider.setPasswordEncoder(bCryptPasswordEncoder()) // 비밀번호 검증을 위한 설정
        return ProviderManager(authProvider)
    }

    @Bean
    fun jsonUsernamePasswordAuthenticationFilter(): JsonMemberEmailPasswordAuthenticationFilter {
        val jsonUsernamePasswordAuthenticationFilter = JsonMemberEmailPasswordAuthenticationFilter()
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager())
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler)
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler)

        return jsonUsernamePasswordAuthenticationFilter
    }
}