package com.noreabang.strawberryrabbit.domain.comment.service

import com.noreabang.strawberryrabbit.domain.comment.dto.CommentRequest
import com.noreabang.strawberryrabbit.domain.comment.dto.CommentResponse
import com.noreabang.strawberryrabbit.domain.comment.model.Comment
import com.noreabang.strawberryrabbit.domain.comment.repository.CommentRepository
import com.noreabang.strawberryrabbit.infra.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class CommentService(
    private val commentRepository: CommentRepository,

) {
    fun getComment(commentId: Long): CommentResponse {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        return comment.toResponse()
    }

    @Transactional
    fun createComment(feedId: Long, request: CommentRequest): CommentResponse {
        return commentRepository.save(
            Comment.createComment(
                request
            )
        ).toResponse()
    }

    @Transactional
    fun updateComment(commentId: Long, request: CommentRequest): CommentResponse {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
            comment.content = request.content
            return commentRepository.save(comment).toResponse()

    }

    @Transactional
    fun deleteComment(commentId: Long) {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        commentRepository.delete(comment)
    }
}