package com.noreabang.strawberryrabbit.infra.exception

data class ModelNotFoundException(
    val modelName: String, val userid: Long):
    RuntimeException("Model $modelName not found with id $userid")
