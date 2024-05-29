package com.noreabang.strawberryrabbit.domain.music.dto

data class MusicResponse (
    val id: Long,
    val singer: String?,
    val title: String?,
    val cover: String?
)