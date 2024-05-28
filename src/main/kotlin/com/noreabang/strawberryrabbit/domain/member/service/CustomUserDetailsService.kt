package com.noreabang.strawberryrabbit.infra.secutiry

import com.noreabang.strawberryrabbit.domain.member.model.Member
import com.noreabang.strawberryrabbit.domain.member.repository.MemberRepository
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class CustomUserDetailsService(
    private val memberRepository: MemberRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        var member: Member

        try {
            member = memberRepository.findByEmail(email)

        } catch (e: InternalAuthenticationServiceException) {
            throw UsernameNotFoundException(email)
        }

        return CustomUserDetails(member) // TODO : CustomUserDetails 구현
    }
}