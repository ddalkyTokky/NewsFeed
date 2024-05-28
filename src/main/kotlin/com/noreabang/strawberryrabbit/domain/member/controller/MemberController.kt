package com.noreabang.strawberryrabbit.domain.member.controller

import com.noreabang.strawberryrabbit.domain.member.dto.MemberCreateRequest
import com.noreabang.strawberryrabbit.domain.member.dto.MemberResponse
import com.noreabang.strawberryrabbit.domain.member.dto.SigninRequest
import com.noreabang.strawberryrabbit.domain.member.service.MemberService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/members")
class MemberController (
    private  val memberService: MemberService
){
    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/signup")
    fun createUser(@Valid @RequestBody memberCreateRequest: MemberCreateRequest): ResponseEntity<MemberResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(memberService.createUser(memberCreateRequest))
    }

    // Swagger-ui에 보여주기 위함, 실제 login 처리는 Spring Security로 처리
    @PostMapping("/signin")
    fun singin (@RequestBody signinRequest: SigninRequest) {
        log.info("signin ${signinRequest}")
    }
}