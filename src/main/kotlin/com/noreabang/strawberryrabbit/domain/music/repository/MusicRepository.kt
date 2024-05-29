package com.noreabang.strawberryrabbit.domain.music.repository

import com.noreabang.strawberryrabbit.domain.music.model.Music
import org.springframework.data.jpa.repository.JpaRepository

interface MusicRepository: JpaRepository<Music, Long> {
}