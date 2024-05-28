package com.noreabang.strawberryrabbit.domain.comment.dto

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val content: String,
    val createdAt: LocalDateTime
)
