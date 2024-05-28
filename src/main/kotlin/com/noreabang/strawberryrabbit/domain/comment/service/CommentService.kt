package com.noreabang.strawberryrabbit.domain.comment.service

import com.noreabang.strawberryrabbit.domain.comment.dto.CommentRequest
import com.noreabang.strawberryrabbit.domain.comment.dto.CommentResponse

interface CommentService {
    fun getComment(commentId: Long): CommentResponse

    fun createComment(commentId: Long, request: CommentRequest) : CommentResponse
// feedId로 변경
    fun updateComment(commentId : Long, request : CommentRequest) : CommentResponse

    fun deleteComment(commentId : Long)
}