package com.noreabang.strawberryrabbit.domain.member.model

import jakarta.persistence.*

@Entity
class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false, length = 32)
    var nickname: String? = null

    @Column(unique = true, length = 100)
    var email: String? = null

    @Column(length = 100)
    var password: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "signup_type", nullable = false)
    var signupType: SignUpType = SignUpType.EMAIL

    @Column(length = 1000)
    var image: String? = null

//    companion object {
//        fun createMember(name: String, pw: String, secret: String): Member {
//            val member: Member = Member()
//            // TODO memberRequest DTO 를 만들어 완성해주세요!!
//            return member
//        }
//    }

//    fun toResponse(): MemberResponse {
//        return MemberResponse(
//            // TODO memberResponse DTO 를 만들어 완성해주세요!!
//        )
//    }
}