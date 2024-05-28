package com.noreabang.strawberryrabbit.domain.comment.service

import com.noreabang.strawberryrabbit.domain.comment.dto.CommentRequest
import com.noreabang.strawberryrabbit.domain.comment.dto.CommentResponse

interface CommentService {
    fun getComment(commentId: Long): CommentResponse

    fun createComment(feedId : Long, request: CommentRequest) : CommentResponse

    fun updateComment(feedId : Long, commentId : Long, request : CommentRequest) : CommentResponse

    fun deleteComment(feedId : Long, commentId : Long) : CommentResponse
}