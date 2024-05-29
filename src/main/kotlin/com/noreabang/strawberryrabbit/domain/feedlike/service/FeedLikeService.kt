package com.noreabang.strawberryrabbit.domain.feedlike.service

import com.noreabang.strawberryrabbit.domain.feed.service.FeedService
import com.noreabang.strawberryrabbit.domain.feedlike.model.FeedLike
import com.noreabang.strawberryrabbit.domain.feedlike.model.FeedLikeId
import com.noreabang.strawberryrabbit.domain.feedlike.repository.FeedLikeRepository
import com.noreabang.strawberryrabbit.domain.follow.dto.FollowResponse
import com.noreabang.strawberryrabbit.domain.follow.model.Follow
import com.noreabang.strawberryrabbit.domain.follow.model.FollowId
import com.noreabang.strawberryrabbit.domain.member.service.MemberService
import com.noreabang.strawberryrabbit.infra.exception.ModelNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FeedLikeService (
    private val memberService: MemberService,
    private val feedService: FeedService,
    private val feedLikeRepository: FeedLikeRepository
){
    @Transactional
    fun follow(memberId: Long, feedId: Long): FollowResponse {
        val member = memberService.getMemberById(memberId)
        val feed = feedService.getFeedById(feedId)
        return feedLikeRepository.save(
            FeedLike.createFedLike(
                member,
                feed
            )
        ).toResponse()
    }

    @Transactional
    fun unFollow(memberId: Long, feedId: Long){
        val feedLikeId: FeedLikeId = FeedLikeId()
        feedLikeId.member = memberService.getMemberById(memberId)
        feedLikeId.feed = feedService.getFeedById(feedId)
        val follow = followRepository.findByFollowId(followId) ?: throw ModelNotFoundException("Follow", memberId)
        followRepository.delete(follow)
    }
}