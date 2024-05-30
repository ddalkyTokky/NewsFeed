package com.noreabang.strawberryrabbit.infra.email

import com.noreabang.strawberryrabbit.infra.email.service.MailSendService
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

    // 이메일 전송 후 인증 로직은 회원가입 서비스에서 진행
    @PostMapping("/check")
    fun mailSend(@RequestBody @Valid emailRequest: EmailRequest): String {
        return emailService.joinEmail(emailRequest.email)
    }
}