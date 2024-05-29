package com.noreabang.strawberryrabbit.domain.feedlike.repository

import com.noreabang.strawberryrabbit.domain.feedlike.model.FeedLike
import com.noreabang.strawberryrabbit.domain.feedlike.model.FeedLikeId
import org.springframework.data.jpa.repository.JpaRepository

interface FeedLikeRepository: JpaRepository<FeedLike, FeedLikeId> {
    fun findByFeedLikeId(feedLikeId: FeedLikeId): FeedLike?
}