package com.noreabang.strawberryrabbit.domain.feed.controller

import com.noreabang.strawberryrabbit.domain.feed.dto.CreateFeedRequest
import com.noreabang.strawberryrabbit.domain.feed.dto.FeedDetailResponse
import com.noreabang.strawberryrabbit.domain.feed.dto.FeedResponse
import com.noreabang.strawberryrabbit.domain.feed.dto.UpdateFeedRequest
import com.noreabang.strawberryrabbit.domain.feed.service.FeedService
import com.noreabang.strawberryrabbit.domain.member.service.MemberService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/feeds")
@RestController
class FeedController (
    private val service: FeedService,
    private val memberService: MemberService
) {
    // member={name}&title={title}&content={content}&page={0~}&size={0~}&sort={create-at/like_cnt},{asc/desc}
    @GetMapping()
    fun getFeedList(
        @RequestParam type: String?,
        @RequestParam content:String?,
        @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ) : ResponseEntity<Page<FeedResponse>>{
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllFeeds(pageable, type, content))
    }

    @GetMapping("/{feedId}")
    fun getFeed(@PathVariable feedId : Long) : ResponseEntity<FeedDetailResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(service.getFeedResponseById(feedId))
    }

    @PostMapping()
    fun createFeed(
                   @RequestBody createFeedRequest: CreateFeedRequest
    ) : ResponseEntity<FeedResponse> {
        val id : Long? = memberService.getMemberDetails()?.getMemberId()
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createFeed(createFeedRequest, id!!))
    }

    @PutMapping("/{feedId}")
    fun updateFeed(@PathVariable feedId : Long,
                   @RequestBody updateFeedRequest: UpdateFeedRequest) : ResponseEntity<FeedResponse> {
        val memberId = memberService.getMemberDetails()?.getMemberId()
        return ResponseEntity.status(HttpStatus.OK).body(service.updateFeed(updateFeedRequest, feedId, memberId!!))
    }

    @DeleteMapping("/{feedId}")
    fun deleteFeed(@PathVariable feedId : Long) : ResponseEntity<Unit> {
        service.deleteFeed(feedId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}