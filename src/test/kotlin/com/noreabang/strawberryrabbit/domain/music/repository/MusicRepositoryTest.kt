package com.noreabang.strawberryrabbit.domain.music.repository

import com.noreabang.strawberryrabbit.domain.music.dto.MusicRequest
import com.noreabang.strawberryrabbit.domain.music.model.Music
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class MusicRepositoryTest {
    @Autowired
    private lateinit var musicRepository: MusicRepository

    @Test
    @Rollback(false)
    fun musicMaker(){
        for (i in 1..101) {
            musicRepository.save(Music.createMusic(MusicRequest("singer $i", "title $i", "cover $i")))
        }
    }
}