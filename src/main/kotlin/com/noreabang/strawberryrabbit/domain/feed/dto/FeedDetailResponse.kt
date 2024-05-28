package com.noreabang.strawberryrabbit.domain.feed.dto

import com.noreabang.strawberryrabbit.domain.comment.model.Comment
import com.noreabang.strawberryrabbit.domain.feedlike.model.FeedLike
import com.noreabang.strawberryrabbit.domain.member.model.Member
import com.noreabang.strawberryrabbit.domain.music.model.Music
import java.sql.Timestamp

data class FeedDetailResponse (
//    val id:Long,
    val title: String?,
    val content: String?,
    val member: Member?,
    val music: Music?,
    val createAt: Timestamp?,
    val feedLike: List<FeedLike>?,
    val comments: List<Comment>
)