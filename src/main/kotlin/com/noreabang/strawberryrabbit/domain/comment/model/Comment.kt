package com.noreabang.strawberryrabbit.domain.comment.model

import com.noreabang.strawberryrabbit.domain.CreatedAtEntity
import com.noreabang.strawberryrabbit.domain.comment.dto.CommentRequest
import com.noreabang.strawberryrabbit.domain.comment.dto.CommentResponse
import com.noreabang.strawberryrabbit.domain.feed.dto.CreateFeedRequest
import com.noreabang.strawberryrabbit.domain.feed.model.Feed
import com.noreabang.strawberryrabbit.domain.member.model.Member

import jakarta.persistence.*

@Entity
class Comment: CreatedAtEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var content: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    var feed: Feed? = null

    companion object{
        fun createComment(commentRequest: CommentRequest): Comment {
            val comment: Comment = Comment()
            comment.content = commentRequest.content
            return comment
        }
    }

    fun toResponse(): CommentResponse {
        return CommentResponse(
            id = id!!,
            content = content,
            createdAt = createdAt,
        )
    }
}