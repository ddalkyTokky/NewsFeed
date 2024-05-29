package com.noreabang.strawberryrabbit.domain.follow.controller

import com.noreabang.strawberryrabbit.domain.follow.dto.FollowResponse
import com.noreabang.strawberryrabbit.domain.follow.service.FollowService
import com.noreabang.strawberryrabbit.domain.member.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/follow")
class FollowController(
    private val memberService: MemberService,
    private val followService: FollowService
){
    @PostMapping("/{memberId}")
    fun follow(
        @PathVariable memberId: Long
    ): ResponseEntity<FollowResponse> {
        val followerId = memberService.getMemberDetails()?.getMemberId()
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(followService.follow(followerId!!, memberId))
    }

    @DeleteMapping("/{memberId}")
    fun unFollow(
        @PathVariable memberId: Long
    ): ResponseEntity<Unit> {
        val followerId = memberService.getMemberDetails()?.getMemberId()
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(followService.unFollow(followerId!!, memberId))
    }
}