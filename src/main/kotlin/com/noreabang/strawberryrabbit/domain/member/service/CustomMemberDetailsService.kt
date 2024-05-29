package com.noreabang.strawberryrabbit.infra.secutiry

import com.noreabang.strawberryrabbit.domain.member.model.Member
import com.noreabang.strawberryrabbit.domain.member.repository.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class CustomMemberDetailsService(
    private val memberRepository: MemberRepository
) : UserDetailsService {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun loadUserByUsername(email: String): UserDetails {
        var member: Member

        try {
            member = memberRepository.findByEmail(email)

        } catch (e: InternalAuthenticationServiceException) {
            // TODO: InternalAuthenticationServiceException: Result must not be null에 대한 처리 필요
            throw UsernameNotFoundException(email) 
        }

        return CustomMemberDetails(member)
    }
}