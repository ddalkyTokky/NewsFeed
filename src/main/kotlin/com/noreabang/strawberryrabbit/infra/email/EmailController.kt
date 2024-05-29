package com.noreabang.strawberryrabbit.infra.email

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/email")
class EmailController(
    private val emailService: MailSendService
) {

    @PostMapping("/check")
    fun mailSend(@RequestBody @Valid emailRequest: EmailRequest): String {
        return emailService.joinEmail(emailRequest.email);
    }

    // TODO: 이메일 인증 관련 컨트롤 필요
}