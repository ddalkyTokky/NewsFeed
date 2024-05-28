package com.noreabang.strawberryrabbit.domain.member.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members")
class MemberController {

    // TODO : 회원가입 - 순용님 담당

    // TODO : 로그인 구현 - 은혜 담당
    @GetMapping
    fun `swagger-ui test`() {
        println("Hello world!")
    }

}