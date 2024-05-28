package com.noreabang.strawberryrabbit.domain.comment.model

import com.noreabang.strawberryrabbit.domain.CreatedAtEntity
import com.noreabang.strawberryrabbit.domain.feed.model.Feed
import com.noreabang.strawberryrabbit.domain.member.model.Member
import jakarta.persistence.*

@Entity
class Comment: CreatedAtEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    var feed: Feed? = null

    @Column(nullable = false)
    var content: String? = null

//    companion object {
//        fun createComment(member: Member, feed: Feed, commentRequest: CommentRequest): Comment {
//            val comment: Comment = Comment()
//            // TODO commentRequest DTO 를 만들어 완성해주세요!!
//            return comment
//        }
//    }

//    fun updateComment(commentRequest: CommentRequest): Comment{
//        // TODO commentRequest DTO 를 만들어 완성해주세요!!
//        return this
//    }

//    fun toResponse(): CommentResponse {
//        return CommentResponse(
//            // TODO commentRequest DTO 를 만들어 완성해주세요!!
//        )
//    }
}