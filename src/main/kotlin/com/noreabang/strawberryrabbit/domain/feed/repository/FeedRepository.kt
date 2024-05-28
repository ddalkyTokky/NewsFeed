package com.noreabang.strawberryrabbit.domain.feed.repository

import com.noreabang.strawberryrabbit.domain.feed.model.Feed
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface FeedRepository : JpaRepository<Feed, Long>{
    override fun findAll(pageable:Pageable): Page<Feed>
    fun findAllByMemberNicknameContains(nickname:String, pageable: Pageable): Page<Feed>
    fun findAllByTitleContains(title:String, pageable: Pageable): Page<Feed>
    fun findAllByContentContains(content:String, pageable: Pageable): Page<Feed>
}