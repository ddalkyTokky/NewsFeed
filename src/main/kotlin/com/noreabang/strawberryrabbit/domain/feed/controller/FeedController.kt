package com.noreabang.strawberryrabbit.domain.feed.controller

import org.springframework.web.bind.annotation.*

@RequestMapping("/feeds")
@RestController
class FeedController {

    @GetMapping()
    fun getFeedList(){

    }

    @GetMapping("/{feedid}")
    fun getFeed() {

    }

    @PostMapping()
    fun createFeed(){

    }

    @PutMapping("/{feedid}")
    fun updateFeed(){

    }

    @DeleteMapping("/{feedid}")
    fun deleteFeed(){

    }

}