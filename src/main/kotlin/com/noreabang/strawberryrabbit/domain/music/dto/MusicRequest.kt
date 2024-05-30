package com.noreabang.strawberryrabbit.domain.music.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class MusicRequest (
    @field: NotBlank(message = "singer cannot be blank")
    @field: Size(min = 1, max = 100, message = "singer must be 1 ~ 100")
    val singer: String,

    @field: NotBlank(message = "title cannot be blank")
    @field: Size(min = 1, max = 100, message = "title must be 1 ~ 100")
    val title: String,

    val cover: String?
)