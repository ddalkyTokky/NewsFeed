package com.noreabang.strawberryrabbit.domain.comment.controller

import com.noreabang.strawberryrabbit.domain.comment.dto.CommentRequest
import com.noreabang.strawberryrabbit.domain.comment.dto.CommentResponse
import com.noreabang.strawberryrabbit.domain.comment.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/comments")
@RestController
class CommentController(
    private val commentService: CommentService,
) {
    @PostMapping("/{feedId}}")
    fun createComment(
        @PathVariable feedId: Long,
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(feedId, commentRequest))
    } // 댓글 등록

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(commentId, commentRequest))
    } // 댓글 수정

    @DeleteMapping("/c{commentId}")
    fun deleteComment(
        @PathVariable commentId: Long,
    ): ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    } // 댓글 삭제
}