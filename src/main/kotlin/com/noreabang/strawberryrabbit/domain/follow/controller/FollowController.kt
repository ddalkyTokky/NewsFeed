package com.noreabang.strawberryrabbit.domain.follow.controller

import com.noreabang.strawberryrabbit.domain.follow.dto.FollowResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/follow")
class FollowController {

    @PostMapping
    fun follow(
        @Request
    ): ResponseEntity<FollowResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body()
    }
}