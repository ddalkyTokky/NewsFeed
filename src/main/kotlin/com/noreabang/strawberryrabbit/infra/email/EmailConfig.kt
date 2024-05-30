package com.noreabang.strawberryrabbit.infra.email

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*


@Configuration
class EmailConfig {
    @Value("\${EMAIL_PASSWORD}")
    lateinit var emailPassword: String

    @Bean
    fun mailSender(): JavaMailSender {

        val mailSender = JavaMailSenderImpl()
        mailSender.host = "smtp.gmail.com" // 이메일 전송에 사용할 SMTP 서버 호스트 설정
        mailSender.port = 587 // 포트 지정
        mailSender.username = "hellou2312@gmail.com" // 발신자 구글 계정
        mailSender.password = emailPassword // 구글 앱 비밀번호

        val javaMailProperties = Properties()
        javaMailProperties["mail.transport.protocol"] = "smtp" //프로토콜로 smtp 사용
        javaMailProperties["mail.smtp.auth"] = "true" // smtp 서버 인증 필요
        javaMailProperties["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory" // SSL 소켓 팩토리 클래스 사용
        javaMailProperties["mail.smtp.starttls.enable"] = "true" // STARTTLS(TLS를 시작하는 명령) 사용, 암호화된 통신 활성화
        javaMailProperties["mail.debug"] = "true" // 디버깅 정보 출력
        javaMailProperties["mail.smtp.ssl.trust"] = "smtp.naver.com" // smtp 서버의 ssl 인증서 신뢰
        javaMailProperties["mail.smtp.ssl.protocols"] = "TLSv1.2" // ssl 프로토콜 버젼

        mailSender.javaMailProperties = javaMailProperties // mailSender 위 설정 추가

        return mailSender
    }
}