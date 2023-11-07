package com.example.ubi.api.response

data class GuidedResponse<T>(
    val data: T?,
    val message: String,
    val status: Int
)