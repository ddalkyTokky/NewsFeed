package com.noreabang.strawberryrabbit.domain.feedlike.model

import com.noreabang.strawberryrabbit.domain.feed.model.Feed
import com.noreabang.strawberryrabbit.domain.feedlike.dto.FeedLikeResponse
import com.noreabang.strawberryrabbit.domain.member.model.Member
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity

@Entity(name = "feedlike")
class FeedLike {
    @EmbeddedId
    val feedLikeId: FeedLikeId = FeedLikeId()

    companion object{
        fun createFeedLike(member: Member, feed: Feed): FeedLike {
            val feedLike = FeedLike()
            feedLike.feedLikeId.member = member
            feedLike.feedLikeId.feed = feed
            return feedLike
        }
    }

    fun toResponse(): FeedLikeResponse {
        return FeedLikeResponse(
            this.feedLikeId.member!!.nickname!!,
            this.feedLikeId.feed!!.title!!
        )
    }
}