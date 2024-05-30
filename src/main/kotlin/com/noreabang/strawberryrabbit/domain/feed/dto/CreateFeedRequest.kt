package com.noreabang.strawberryrabbit.domain.feed.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


data class CreateFeedRequest (
    @field: NotBlank(message = "title cannot be blank")
    @field: Size(min = 1, max = 200, message = "title must be 1 ~ 200")
    val title:String,

    @field: NotBlank(message = "content cannot be blank")
    @field: Size(min = 1, max = 1000, message = "content must be 1 ~ 1000")
    val content: String,

    @field: NotBlank(message = "musicId cannot be blank")
    val musicId: Long
)