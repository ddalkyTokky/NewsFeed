package com.noreabang.strawberryrabbit.domain.follow.repository

import com.noreabang.strawberryrabbit.domain.follow.model.Follow
import com.noreabang.strawberryrabbit.domain.follow.model.FollowId
import org.springframework.data.jpa.repository.JpaRepository

interface FollowRepository: JpaRepository<Follow, FollowId> {
    fun findByFollowId(followId: FollowId): Follow?
}