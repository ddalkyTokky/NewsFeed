package com.noreabang.strawberryrabbit.domain.follow.service

import com.noreabang.strawberryrabbit.domain.follow.dto.FollowResponse
import com.noreabang.strawberryrabbit.domain.follow.model.Follow
import com.noreabang.strawberryrabbit.domain.follow.model.FollowId
import com.noreabang.strawberryrabbit.domain.follow.repository.FollowRepository
import com.noreabang.strawberryrabbit.domain.member.service.MemberService
import com.noreabang.strawberryrabbit.infra.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FollowService (
    private val followRepository: FollowRepository,
    private val memberService: MemberService
){
    @Transactional
    fun follow(followerId: Long, followeeId: Long): FollowResponse{
        if(followerId == followeeId){
            throw IllegalArgumentException("You cant follow yourself")
        }
        val follower = memberService.getMemberById(followerId)
        val followee = memberService.getMemberById(followeeId)
        return followRepository.save(
            Follow.createFollow(
                follower,
                followee
            )
        ).toResponse()
    }

    @Transactional
    fun unFollow(followerId: Long, followeeId: Long){
        if(followerId == followeeId){
            throw IllegalArgumentException("You cant unfollow yourself")
        }
        val followId: FollowId = FollowId()
        followId.follower = memberService.getMemberById(followerId)
        followId.followee = memberService.getMemberById(followeeId)
        val follow = followRepository.findByFollowId(followId) ?: throw ModelNotFoundException("Follow", followerId)
        followRepository.delete(follow)
    }
}