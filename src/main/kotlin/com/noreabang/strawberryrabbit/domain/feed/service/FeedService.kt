package com.noreabang.strawberryrabbit.domain.feed.service

import com.noreabang.strawberryrabbit.infra.exception.ModelNotFoundException
import com.noreabang.strawberryrabbit.domain.feed.dto.CreateFeedRequest
import com.noreabang.strawberryrabbit.domain.feed.dto.FeedDetailResponse
import com.noreabang.strawberryrabbit.domain.feed.dto.FeedResponse
import com.noreabang.strawberryrabbit.domain.feed.dto.UpdateFeedRequest
import com.noreabang.strawberryrabbit.domain.feed.model.Feed
import com.noreabang.strawberryrabbit.domain.feed.repository.FeedRepository
import com.noreabang.strawberryrabbit.domain.member.repository.MemberRepository
import com.noreabang.strawberryrabbit.domain.music.repository.MusicRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FeedService(
    private val feedRepository: FeedRepository,
    private val membersRepository:MemberRepository,
    private val musicRepository: MusicRepository,
) {
    @Transactional(readOnly = true)
    fun getAllFeeds(pageable: Pageable, type: String?, search: String?): Page<FeedResponse> {
        if(type.equals("title")) {
            val feeds : Page<Feed> = feedRepository.findAllByTitleContains(search,pageable)
            return feeds.map { it.toSimpleResponse() }
        } else if(type.equals("member")) {
            val feeds : Page<Feed> = feedRepository.findAllByMemberNicknameContains(search,pageable)
            return feeds.map { it.toSimpleResponse() }
        } else if(type.equals("content")) {
            val feeds : Page<Feed> = feedRepository.findAllByContentContains(search,pageable)
            return feeds.map { it.toSimpleResponse() }
        } else {
            val feeds : Page<Feed> = feedRepository.findAll(pageable)
            return feeds.map { it.toSimpleResponse() }
        }
    }

    @Transactional(readOnly = true)
    fun getFeedResponseById(id: Long): FeedDetailResponse {
        val feed = feedRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("Feed", id)
        return feed.toDetailResponse()
    }

    @Transactional(readOnly = true)
    fun getFeedById(id: Long): Feed {
        val feed = feedRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("Feed", id)
        return feed
    }

    fun createFeed(request: CreateFeedRequest,id: Long,musicId: Long): FeedResponse {
        val member = membersRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("Member", id)
        val music = musicRepository.findByIdOrNull(musicId) ?: throw ModelNotFoundException("Music", musicId)
        return feedRepository.save(
            Feed.createFeed(request, member, music)
        ).toSimpleResponse()
    }

    fun updateFeed(updateFeedRequest: UpdateFeedRequest, musicId: Long, feedId: Long): FeedResponse {
        val music = musicRepository.findByIdOrNull(musicId) ?: throw ModelNotFoundException("Music", musicId)
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw ModelNotFoundException("Feed", feedId)
        feed.updateFeed(updateFeedRequest, music)

        return feed.toSimpleResponse()
    }

    fun deleteFeed(feedId:Long) {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw ModelNotFoundException("Feed", feedId)
        feedRepository.delete(feed)
    }

}