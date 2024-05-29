package com.noreabang.strawberryrabbit.domain.feed.dto

import com.noreabang.strawberryrabbit.domain.comment.dto.CommentResponse
import com.noreabang.strawberryrabbit.domain.feedlike.model.FeedLike
import com.noreabang.strawberryrabbit.domain.member.dto.MemberResponse
import com.noreabang.strawberryrabbit.domain.music.dto.MusicResponse
import java.sql.Timestamp

data class FeedDetailResponse(
//    val id:Long,
    val title: String?,
    val content: String?,
    val member: MemberResponse?,
    val music: MusicResponse?,
    val createAt: Timestamp?,
//    val feedLike: List<FeedLike>?,
//    val comments: List<CommentResponse>
)