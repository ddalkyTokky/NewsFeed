package com.noreabang.strawberryrabbit.domain.follow.model

import com.noreabang.strawberryrabbit.domain.follow.dto.FollowResponse
import com.noreabang.strawberryrabbit.domain.member.model.Member
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity

@Entity
class Follow {
    @EmbeddedId
    val followId: FollowId = FollowId()

    companion object{
        fun createFollow(follower: Member, followee: Member): Follow{
            val follow = Follow()
            follow.followId.follower = follower
            follow.followId.followee = followee
            return follow
        }
    }

    fun toResponse(): FollowResponse {
        return FollowResponse(
            followId.follower!!.nickname!!,
            followId.followee!!.nickname!!
        )
    }
}