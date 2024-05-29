package com.noreabang.strawberryrabbit.domain.feed.dto

import com.noreabang.strawberryrabbit.domain.music.dto.MusicResponse

data class UpdateFeedRequest(
    val title:String,
    val content: String,
    val musicId: Long?
)