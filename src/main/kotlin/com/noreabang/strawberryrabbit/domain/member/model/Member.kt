package com.noreabang.strawberryrabbit.domain.member.model

import com.noreabang.strawberryrabbit.domain.member.dto.MemberCreateRequest
import com.noreabang.strawberryrabbit.domain.member.dto.MemberResponse
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

    companion object {
        fun createMember(memberCreateRequest: MemberCreateRequest, password: String): Member {
            val member: Member = Member()
            member.email = memberCreateRequest.email
            member.nickname = memberCreateRequest.nickname
            member.image = memberCreateRequest.image
            member.password = password
            member.signupType = SignUpType.EMAIL
            return member
        }
    }

    fun toResponse(): MemberResponse {
        return MemberResponse(
            this.id!!,
            this.nickname!!,
            this.email!!,
            this.image
        )
    }
}