package com.example.ubi.api.request

data class PostArticleRequest(
    val title: String,
    val content: String,
    val latitude: Double,
    val longitude: Double,
)