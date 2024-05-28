package com.noreabang.strawberryrabbit.domain.feedlike.model

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity

@Entity(name = "feedlike")
class FeedLike {
    @EmbeddedId
    val feedLikePK: FeedLikePK = FeedLikePK()
}