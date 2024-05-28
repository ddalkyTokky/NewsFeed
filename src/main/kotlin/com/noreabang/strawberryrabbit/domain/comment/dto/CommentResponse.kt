package com.noreabang.strawberryrabbit.domain.comment.dto

import java.sql.Timestamp

data class CommentResponse(
    val id: Long,
    val content: String?,
    val createdAt: Timestamp?
)
