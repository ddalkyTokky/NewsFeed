package com.noreabang.strawberryrabbit.domain.follow.model

import com.noreabang.strawberryrabbit.domain.member.model.Member
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

class FollowId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower", nullable = false)
    var follower: Member? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followee", nullable = false)
    var followee: Member? = null
}