package com.noreabang.strawberryrabbit.domain.feed.model

import com.noreabang.strawberryrabbit.domain.CreatedAtEntity
import com.noreabang.strawberryrabbit.domain.comment.model.Comment
import com.noreabang.strawberryrabbit.domain.feed.dto.CreateFeedRequest
import com.noreabang.strawberryrabbit.domain.feed.dto.FeedDetailResponse
import com.noreabang.strawberryrabbit.domain.feed.dto.FeedResponse
import com.noreabang.strawberryrabbit.domain.feed.dto.UpdateFeedRequest
import com.noreabang.strawberryrabbit.domain.feedlike.model.FeedLike
import com.noreabang.strawberryrabbit.domain.member.model.Member
import com.noreabang.strawberryrabbit.domain.music.model.Music
import jakarta.persistence.*

@Entity
class Feed: CreatedAtEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id", nullable = false)
    var music: Music? = null

    @Column(nullable = false, length = 200)
    var title: String? = null

    @Column(nullable = false, length = 1000)
    var content: String? = null

    @OneToMany(mappedBy = "feed", fetch = FetchType.LAZY, orphanRemoval = true)
    val comments: MutableList<Comment> = mutableListOf()

    @OneToMany(mappedBy = "feedLikeId.feed", fetch = FetchType.LAZY, orphanRemoval = true)
    val feedLikes: MutableList<FeedLike> = mutableListOf()

    companion object{
        fun createFeed(feedRequest: CreateFeedRequest, member: Member, music:Music): Feed {
            val feed = Feed()
            feed.title = feedRequest.title
            feed.content = feedRequest.content
            feed.music = music
            feed.member = member
            return feed
        }
    }

    fun updateFeed(feedRequest: UpdateFeedRequest, music: Music){
        this.title = feedRequest.title
        this.content = feedRequest.content
        this.music = music
    }

    fun toSimpleResponse(): FeedResponse {
        return FeedResponse(
            title = this.title,
            content = this.content,
            music = this.music!!.toResponse(),
            member = this.member!!.toResponse(),
            createdAt = this.createdAt,
            likeCnt = this.feedLikes.size.toLong(),
        )
    }

    fun toDetailResponse(): FeedDetailResponse {
        return FeedDetailResponse(
            title = this.title,
            content = this.content,
            music = this.music!!.toResponse(),
            member = this.member!!.toResponse(),
            createAt = this.createdAt,
            feedLikes = this.feedLikes.map { it.toResponse() },
            comments = this.comments.map { it.toResponse() }
        )
    }
}