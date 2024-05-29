package com.noreabang.strawberryrabbit.domain.feed.dto


data class CreateFeedRequest (
    val title:String?,
    val content: String?,
    val musicId: Long?
)