package com.noreabang.strawberryrabbit.domain.member.controller

import com.noreabang.strawberryrabbit.domain.member.dto.SigninRequest
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/members")
class MemberController {
    private val log = LoggerFactory.getLogger(this::class.java)

    // TODO : 회원가입 - 순용님 담당


    // Swagger-ui에 보여주기 위함, 실제 login 처리는 Spring Security로 처리
    @PostMapping("/signin")
    fun singin (@RequestBody signinRequest: SigninRequest) {
        log.info("signin ${signinRequest}")
    }
}