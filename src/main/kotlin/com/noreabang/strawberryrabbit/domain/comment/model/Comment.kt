package com.noreabang.strawberryrabbit.domain.comment.model

import com.noreabang.strawberryrabbit.domain.feed.model.Feed
import com.noreabang.strawberryrabbit.domain.member.model.Member
import jakarta.persistence.*

@Entity
class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    var feed: Feed? = null

    @Column(nullable = false)
    var content: String? = null

    companion object {
        fun createComment(member: Member, feed: Feed, commentRequest: CommentRequest): Comment {
            val comment: Comment = Comment()
            comment.member = member
            comment.feed = feed
            comment.content = commentRequest.content
            return comment
        }
    }

    fun updateComment(commentRequest: CommentRequest): Comment{
        this.content = commentRequest.content
        return this
    }

    fun toResponse(): CommentResponse {
        return CommentResponse(
        )
    }
}