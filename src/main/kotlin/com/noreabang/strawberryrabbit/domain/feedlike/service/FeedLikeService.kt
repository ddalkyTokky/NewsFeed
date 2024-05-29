package com.noreabang.strawberryrabbit.domain.feedlike.service

import com.noreabang.strawberryrabbit.domain.feed.service.FeedService
import com.noreabang.strawberryrabbit.domain.feedlike.dto.FeedLikeResponse
import com.noreabang.strawberryrabbit.domain.feedlike.model.FeedLike
import com.noreabang.strawberryrabbit.domain.feedlike.model.FeedLikeId
import com.noreabang.strawberryrabbit.domain.feedlike.repository.FeedLikeRepository
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
    fun like(memberId: Long, feedId: Long): FeedLikeResponse {
        val member = memberService.getMemberById(memberId)
        val feed = feedService.getFeedById(feedId)
        return feedLikeRepository.save(
            FeedLike.createFeedLike(
                member,
                feed
            )
        ).toResponse()
    }

    @Transactional
    fun unlike(memberId: Long, feedId: Long){
        val feedLikeId: FeedLikeId = FeedLikeId()
        feedLikeId.member = memberService.getMemberById(memberId)
        feedLikeId.feed = feedService.getFeedById(feedId)
        val feedLike = feedLikeRepository.findByFeedLikeId(feedLikeId) ?: throw ModelNotFoundException("Follow", memberId)
        feedLikeRepository.delete(feedLike)
    }
}