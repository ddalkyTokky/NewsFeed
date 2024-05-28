package com.noreabang.strawberryrabbit.domain.feed.service

import com.noreabang.strawberryrabbit.infra.exception.ModelNotFoundException
import com.noreabang.strawberryrabbit.domain.feed.dto.CreateFeedRequest
import com.noreabang.strawberryrabbit.domain.feed.dto.FeedDetailResponse
import com.noreabang.strawberryrabbit.domain.feed.dto.FeedResponse
import com.noreabang.strawberryrabbit.domain.feed.dto.UpdateFeedRequest
import com.noreabang.strawberryrabbit.domain.feed.model.Feed
import com.noreabang.strawberryrabbit.domain.feed.repository.FeedRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FeedService(
    private val repository: FeedRepository
) {
    fun getAllFeeds(pageable: Pageable, type: String?, search: String): Page<FeedResponse> {
        if(type.equals("title")) {
            val feeds : Page<Feed> = repository.findAllByTitleContains(search,pageable)
            return feeds.map { it.toSimpleResponse() }
        } else if(type.equals("member")) {
            val feeds : Page<Feed> = repository.findAllByMemberNicknameContains(search,pageable)
            return feeds.map { it.toSimpleResponse() }
        } else if(type.equals("content")) {
            val feeds : Page<Feed> = repository.findAllByContentContains(search,pageable)
            return feeds.map { it.toSimpleResponse() }
        } else {
        val feeds : Page<Feed> = repository.findAll(pageable)
        return feeds.map { it.toSimpleResponse() }
        }
    }

    fun getFeedById(id: Long): FeedDetailResponse {
        val feed = repository.findByIdOrNull(id) ?: throw ModelNotFoundException("Feed", id)
        return feed.toDetailResponse()
    }

    @Transactional
    fun createFeed(request: CreateFeedRequest,memberId:Long,musicId: Long): FeedResponse {
//        val member
//        val music
//        Feed.createFeed(request,member,music)
//
        TODO("멤버랑 뮤직 가져오고싶어요ㅠㅠ")
    }

    @Transactional
    fun updateFeed(request: UpdateFeedRequest, musicId: Long): FeedResponse {
//        val member //인증어케가져다씀?
//        val music
//        Feed.createFeed(request,member,music)
//
        TODO("멤버랑 뮤직 가져오고싶어요ㅠㅠ")
    }

    @Transactional
    fun deleteFeed(feedId:Long) {
        val feed = repository.findByIdOrNull(feedId) ?: throw ModelNotFoundException("Feed", feedId)
        repository.delete(feed)
    }

}