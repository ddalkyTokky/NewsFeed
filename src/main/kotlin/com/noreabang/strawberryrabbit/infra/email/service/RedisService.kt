package com.noreabang.strawberryrabbit.infra.email.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, String>,
) {

    fun create(email: String, authNumber: String) {
        val valueOperations = redisTemplate.opsForValue()
        valueOperations.set(email, authNumber, Duration.ofMinutes(5)) // 5분 뒤 삭제
    }
}