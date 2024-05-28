package com.noreabang.strawberryrabbit.domain.comment.service

import com.noreabang.strawberryrabbit.domain.comment.dto.CommentRequest
import com.noreabang.strawberryrabbit.domain.comment.dto.CommentResponse
import com.noreabang.strawberryrabbit.domain.comment.model.Comment
import com.noreabang.strawberryrabbit.domain.comment.repository.CommentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.noreabang.strawberryrabbit.domain.exception.ModelNotFoundException
import com.noreabang.strawberryrabbit.domain.feed.service.FeedService

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val memberService : MemberService,
    private val feedService : FeedService
) {
    fun getComment(feedId: Long, commentId: Long): CommentResponse {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        return comment.toResponse()
    }

    @Transactional
    fun createComment(feedId: Long, memberId: Long, request: CommentRequest): CommentResponse {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw ModelNotFoundException("Feed", feedId)
        return commentRepository.save(
            Comment(content = request.content)
        ).toResponse()
    }

    @Transactional
    fun updateComment(memberId: Long, commentId: Long, request: CommentRequest): CommentResponse {
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