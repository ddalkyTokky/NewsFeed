package com.noreabang.strawberryrabbit.domain.member.model

import com.noreabang.strawberryrabbit.domain.member.dto.MemberCreateRequest
import com.noreabang.strawberryrabbit.domain.member.dto.MemberResponse
import com.noreabang.strawberryrabbit.domain.member.dto.MemberUpdateRequest
import jakarta.persistence.*
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.PostgreSQLEnumJdbcType

@Entity
class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false, length = 32)
    var nickname: String? = null

    @Column(nullable = false, unique = true, length = 100)
    var email: String? = null

    @Column(nullable = false, length = 64)
    var password: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "signup_type", nullable = false)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    var signupType: SignUpType = SignUpType.EMAIL

    @Column(length = 1000)
    var image: String? = null

    companion object {
        fun createMember(
            memberCreateRequest: MemberCreateRequest,
            password: String,
            image:String?,
            signUpType: SignUpType
        ): Member {
            val member: Member = Member()
            member.email = memberCreateRequest.email
            member.nickname = memberCreateRequest.nickname
            member.image = image
            member.password = password
            member.signupType = signUpType
            return member
        }
    }

    fun update(
        memberUpdateRequest: MemberUpdateRequest,
        password: String,
        image: String?
    ): Member{
        this.nickname = memberUpdateRequest.nickname
        if(image!=null) this.image = image
        this.password = password
        return this
    }

    fun toResponse(): MemberResponse {
        return MemberResponse(
            this.id!!,
            this.email!!,
            this.nickname!!,
            this.image
        )
    }
}