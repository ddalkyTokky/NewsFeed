package com.noreabang.strawberryrabbit.domain.comment.model

import com.noreabang.strawberryrabbit.domain.comment.dto.CommentResponse
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

@Entity
@Table(name="comment")
class Comment (
    @Column
    @NotNull
    var content: String,

    @Column(name = "created_at")
    @NotNull
    var createdAt: LocalDateTime = LocalDateTime.now(),

//    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
//    @JoinColumn(name = "feed_id")
//    val feed: Feed,
// 피드 연결

//    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
//    @JoinColumn(name = "member_id")
//    val member: Member,
// 멤버 연결

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? = null
}

fun Comment.toResponse(): CommentResponse {
    return CommentResponse(
        id = id!!,
        content = content,
        createdAt = createdAt,
    )
}