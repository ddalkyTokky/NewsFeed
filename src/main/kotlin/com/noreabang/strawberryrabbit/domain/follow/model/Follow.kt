package com.noreabang.strawberryrabbit.domain.follow.model

import com.noreabang.strawberryrabbit.domain.feedlike.model.FeedLikePK
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity

@Entity
class Follow {
    @EmbeddedId
    val followId: FollowPK = FollowPK()
}