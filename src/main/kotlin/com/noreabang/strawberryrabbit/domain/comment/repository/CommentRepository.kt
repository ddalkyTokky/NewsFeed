package com.noreabang.strawberryrabbit.domain.comment.repository

import com.noreabang.strawberryrabbit.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {

}