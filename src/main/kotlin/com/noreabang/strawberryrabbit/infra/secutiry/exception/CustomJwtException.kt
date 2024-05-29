package com.noreabang.strawberryrabbit.infra.secutiry.exception

data class CustomJwtException(
    override val message: String,
): RuntimeException(message)