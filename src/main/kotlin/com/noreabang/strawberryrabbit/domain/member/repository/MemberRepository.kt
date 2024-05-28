package com.noreabang.strawberryrabbit.domain.member.repository

import com.noreabang.strawberryrabbit.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email: String): Member
}