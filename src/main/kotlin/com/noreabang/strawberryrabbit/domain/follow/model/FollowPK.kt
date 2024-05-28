package com.noreabang.strawberryrabbit.domain.follow.model

import com.noreabang.strawberryrabbit.domain.member.model.Member
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

class FollowPK {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var follower: Member? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var followee: Member? = null
}