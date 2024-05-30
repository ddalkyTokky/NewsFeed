package com.noreabang.strawberryrabbit.infra.email.service

import com.noreabang.strawberryrabbit.domain.member.service.MemberService
import jakarta.mail.MessagingException
import org.apache.logging.log4j.LogManager
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import kotlin.random.Random


@Service
class MailSendService(
    private val mailSender: JavaMailSender,
    private val redisService: RedisService,
    private val memberService: MemberService
) {
    private val logger = LogManager.getLogger()
    private var authNumber = ""

    fun makeRandomNumber() { // 랜덤 6자리 숫자 생성
        var randomNumber = ""
        for (i in 0..5) {
            val r: Int = Random.nextInt(9)
            randomNumber += r.toString()
        }

        authNumber = randomNumber
    }

    fun joinEmail(email: String): String {
        try {
            val isExistEmail = memberService.getMemberByEmail(email)

            if (isExistEmail != null) { // 이메일이 존재하는 경우
                throw IllegalArgumentException("This email already exists.")
            }

            return email

        } catch (e: EmptyResultDataAccessException) { // 데이터가 없는 경우 발생
            logger.error(e.message)

            val setFrom = "hellou2312@google.com" // EmailConfig에 설정한 이메일 주소
            val toMail = email
            val title = "딸기토끼의 노래방 회원 가입 인증 이메일 입니다." // 이메일 제목, content: 이메일 본문
            val content = """
                <div style="
                    width: 700px;
                    height: 300px;
                    border: 1px solid black;
                    border-radius: 10px;
                    display: flex;
                    gap: 10px;
                    align-items: center;
                    justify-content: center;">
                  <img
                    src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fx6E8T%2FbtsHHcdiXZ3%2Ftv1eQiyucK6FWGNPpVkFE0%2Fimg.jpg"
                    alt="딸기토끼"
                    style="width: 300px; height: 300px; border-radius: 10px; display: block"
                  />
                  <div>
                    <p>딸기토끼의 노래방 가입을 위한 인증 메일 입니다.</p>
                    <p>딸기토끼의 노래방을 이용해 주셔서 감사합니다.</p>
                    <p>아래 번호를 이용해 인증을 완료해 주세요.</p>
                    <p>인증 번호는 authNumber 입니다.</p>
                  </div>
                </div>
            """.trimIndent()
            mailSend(setFrom, toMail, title, content)

            return "Sent an email"
        }
    }

    fun mailSend(setFrom: String?, toMail: String?, title: String?, content: String?) { // 이메일 전송
        val message = mailSender.createMimeMessage()

        try {
            val helper = MimeMessageHelper(message, true, "utf-8") //이메일 메시지 관련 설정
            helper.setFrom((setFrom)!!) // 이메일 발신자 주소 설정
            helper.setTo((toMail)!!) // 이메일 수신자 주소 설정
            helper.setSubject((title)!!) // 이메일 제목 설정
            helper.setText((content)!!, true) // 이메일 내용을 HTML로 설정

            mailSender.send(message) // 이메일 전송

            redisService.create(toMail, authNumber)
        } catch (e: MessagingException) { // 이메일 서버 연결 안됨, 잘못된 이메일 주소 사용, 인증 오류 발생 등
            logger.error(e.message)
        }
    }
}