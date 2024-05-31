package com.noreabang.strawberryrabbit.domain.comment.controller

import com.noreabang.strawberryrabbit.domain.comment.dto.CommentRequest
import com.noreabang.strawberryrabbit.domain.comment.dto.CommentResponse
import com.noreabang.strawberryrabbit.domain.comment.service.CommentService
import com.noreabang.strawberryrabbit.domain.member.service.MemberService
import jakarta.validation.Valid

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/comments")
@RestController
class CommentController(
    private val commentService: CommentService,
    private val memberService: MemberService,
) {
    @PostMapping("/{feedId}")
    fun createComment(
        @PathVariable feedId: Long,
        @RequestBody @Valid commentRequest: CommentRequest
    ): ResponseEntity<CommentResponse> {
        val memberId = memberService.getMemberDetails()?.getMemberId()
        println(memberId)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(feedId, memberId, commentRequest))
    } // 댓글 등록

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestBody @Valid commentRequest: CommentRequest
    ): ResponseEntity<CommentResponse> {
        val memberId = memberService.getMemberDetails()?.getMemberId()
        println(memberId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(commentId, memberId, commentRequest))
    } // 댓글 수정

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable commentId: Long,
    ): ResponseEntity<Unit> {
        val memberId = memberService.getMemberDetails()?.getMemberId()
        println(memberId)
        commentService.deleteComment(commentId, memberId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    } // 댓글 삭제
}