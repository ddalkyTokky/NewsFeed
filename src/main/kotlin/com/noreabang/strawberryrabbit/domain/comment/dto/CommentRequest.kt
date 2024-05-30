package com.noreabang.strawberryrabbit.domain.comment.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CommentRequest(
    @field: NotBlank(message = "content cannot be blank")
    @field: Size(min = 1, max = 200, message = "content must be 1 ~ 200")
    val content: String,
)