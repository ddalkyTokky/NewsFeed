package com.noreabang.strawberryrabbit.domain.feed.dto

import com.noreabang.strawberryrabbit.domain.feedlike.model.FeedLike
import com.noreabang.strawberryrabbit.domain.member.model.Member
import com.noreabang.strawberryrabbit.domain.music.model.Music
import java.sql.Timestamp

data class FeedResponse (
    val title: String?,
    val content: String?,
    val createdAt: Timestamp?,
    val member:Member?,
//    val memberName: String?,
    val music: Music?,
//    val feedLike: List<FeedLike>?,
)