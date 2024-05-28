package com.noreabang.strawberryrabbit.domain.member.dto

data class MemberResponse (
    val nickname: String,
    val email: String,
    val image: String?
)