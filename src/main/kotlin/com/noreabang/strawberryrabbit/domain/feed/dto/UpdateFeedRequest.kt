package com.noreabang.strawberryrabbit.domain.feed.dto

import com.noreabang.strawberryrabbit.domain.music.model.Music

data class UpdateFeedRequest(
    val title:String,
    val content: String,
    val music: Music?
)