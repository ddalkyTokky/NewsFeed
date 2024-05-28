package com.noreabang.strawberryrabbit.domain.music.model

import jakarta.persistence.*

@Entity
class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false, length = 100)
    var singer: String? = null

    @Column(nullable = false, length = 100)
    var title: String? = null

    @Column(length = 1000)
    var cover: String? = null

//    companion object {
//        fun createMusic(musicRequest: MusicRequest): Music {
//            val music: Music = Music()
//            // TODO musicRequest DTO 를 만들어 완성해주세요!!
//            return music
//        }
//    }

//    fun toResponse(): MusicResponse {
//        return MusicResponse(
//            // TODO musicResponse DTO 를 만들어 완성해주세요!!
//        )
//    }
}