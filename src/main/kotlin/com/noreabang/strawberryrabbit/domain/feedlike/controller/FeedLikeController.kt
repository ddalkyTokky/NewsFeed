package com.noreabang.strawberryrabbit.domain.feedlike.controller

import com.noreabang.strawberryrabbit.domain.feedlike.dto.FeedLikeResponse
import com.noreabang.strawberryrabbit.domain.feedlike.service.FeedLikeService
import com.noreabang.strawberryrabbit.domain.member.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/like")
class FeedLikeController (
    private val memberService: MemberService,
    private val feedLkeService: FeedLikeService
) {
    @PostMapping("/{feedId}")
    fun feedLike(
        @PathVariable feedId: Long
    ): ResponseEntity<FeedLikeResponse> {
        val memberId = memberService.getMemberDetails()?.getMemberId()
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(feedLikeService.like(memberId!!, feedId))
    }

    @DeleteMapping("/{feedId}")
    fun unFeedLike(
        @PathVariable feedId: Long
    ): ResponseEntity<Unit> {
        val memberId = memberService.getMemberDetails()?.getMemberId()
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(feedLikeService.unlike(memberId!!, feedId))
    }
}