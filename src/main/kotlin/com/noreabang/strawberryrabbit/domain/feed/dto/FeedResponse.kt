package com.noreabang.strawberryrabbit.domain.feed.dto

import com.noreabang.strawberryrabbit.domain.feedlike.model.FeedLike
import com.noreabang.strawberryrabbit.domain.member.dto.MemberResponse
import com.noreabang.strawberryrabbit.domain.music.dto.MusicResponse
import java.sql.Timestamp

data class FeedResponse (
    val title: String?,
    val content: String?,
    val createdAt: Timestamp?,
    val member:MemberResponse?,
    val music: MusicResponse?,
    val likeCnt: Long
)