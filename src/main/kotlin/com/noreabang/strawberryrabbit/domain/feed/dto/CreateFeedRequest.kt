package com.noreabang.strawberryrabbit.domain.feed.dto

import com.noreabang.strawberryrabbit.domain.member.model.Member
import com.noreabang.strawberryrabbit.domain.music.model.Music

data class CreateFeedRequest (
    val title:String?,
    val content: String?
)