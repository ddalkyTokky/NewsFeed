package com.noreabang.strawberryrabbit.domain.music.dto

data class MusicRequest (
    val singer: String,
    val title: String,
    val cover: String?
)