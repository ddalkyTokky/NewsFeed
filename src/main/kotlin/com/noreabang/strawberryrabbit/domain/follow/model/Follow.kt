package com.noreabang.strawberryrabbit.domain.follow.model

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity

@Entity
class Follow {
    @EmbeddedId
    val followId: FollowId = FollowId()
}