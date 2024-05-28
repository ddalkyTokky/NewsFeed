package com.noreabang.strawberryrabbit.domain.member.service

import com.noreabang.strawberryrabbit.domain.member.dto.MemberCreateRequest
import com.noreabang.strawberryrabbit.domain.member.dto.MemberResponse
import com.noreabang.strawberryrabbit.domain.member.model.Member
import com.noreabang.strawberryrabbit.domain.member.repository.MemberRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService (
    private val memberRepository: MemberRepository
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
){
    @Transactional
    fun createUser(memberCreateRequest: MemberCreateRequest): MemberResponse {
        return memberRepository.save(
            Member.createMember(
                memberCreateRequest,
                bCryptPasswordEncoder.encode(memberCreateRequest.password),
            )
        ).toResponse()
    }
}