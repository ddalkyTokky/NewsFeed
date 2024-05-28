package com.noreabang.strawberryrabbit.domain.comment.service

import com.noreabang.strawberryrabbit.domain.comment.dto.CommentRequest
import com.noreabang.strawberryrabbit.domain.comment.dto.CommentResponse
import com.noreabang.strawberryrabbit.domain.comment.model.Comment
import com.noreabang.strawberryrabbit.domain.comment.model.toResponse
import com.noreabang.strawberryrabbit.domain.comment.repository.CommentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.todoapplication.todoapplication.domain.exception.ModelNotFoundException

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
//    private val feedRepository: FeedRepository
) : CommentService {
    override fun getComment(commentId: Long): CommentResponse {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        return comment.toResponse()
    }

    @Transactional
    override fun createComment(feedId: Long, request: CommentRequest): CommentResponse {
//        val feed = feedRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("Feed", id)
        return commentRepository.save(
            Comment(
                content = request.content,
            )
        ).toResponse()
    }

    @Transactional
    override fun updateComment(feedId: Long, commentId: Long, request: CommentRequest): CommentResponse {
        val comment = commentRepository.findByFeedIdAndId(feedId, commentId) ?: throw ModelNotFoundException("Comment", commentId)
            comment.content = request.content
            return commentRepository.save(comment).toResponse()

    }

    @Transactional
    override fun deleteComment(feedId: Long, commentId: Long) {
        val comment = commentRepository.findByFeedIdAndId(feedId, commentId) ?: throw ModelNotFoundException("Comment", commentId)
        commentRepository.delete(comment)
    }
}