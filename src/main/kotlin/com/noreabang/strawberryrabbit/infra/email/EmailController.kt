package com.noreabang.strawberryrabbit.infra.email

import com.noreabang.strawberryrabbit.infra.email.service.MailSendService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    fun mailSend(@RequestBody @Valid emailRequest: EmailRequest) : ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(emailService.joinEmail(emailRequest.email))
    }
    // TODO : 인증을 여러 번 연속 요청하는 경우에 대한 처리 필요
}