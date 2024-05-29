package com.noreabang.strawberryrabbit.domain.member.dto

data class MemberResponse (
    val id: Long,
    val email: String,
    val nickname: String,
    val image: String?
)