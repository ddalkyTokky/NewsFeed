package com.noreabang.strawberryrabbit.domain.feed.model

import com.noreabang.strawberryrabbit.domain.CreatedAtEntity
import com.noreabang.strawberryrabbit.domain.comment.model.Comment
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

//    @Column(name = "like_cnt", nullable = false)
//    var likeCnt: Long = 0L

//    companion object{
//        fun createFeed(feedRequest: FeedRequest, member: Member): Feed {
//            val feed: Feed = Feed()
//            // TODO FeedRequest DTO 를 만들어 완성해주세요!!
//            return feed
//        }
//    }

//    fun updateFeed(feedRequest: FeedRequest): Feed{
//        // TODO FeedRequest DTO 를 만들어 완성해주세요!!
//        return this
//    }

//    fun toSimpleResponse(): FeedSimpleResponse {
//        return FeedSimpleResponse(
//            // TODO FeedSimpleResponse DTO 를 만들어 완성해주세요!!
//        )
//    }

//    fun toDetailResponse(): FeedDetailResponse {
//        return FeedDetailResponse(
//            // TODO FeedDetailResponse DTO 를 만들어 완성해주세요!!
//            comments = this.comments.map {it.toResponse()}
//        )
//    }
}