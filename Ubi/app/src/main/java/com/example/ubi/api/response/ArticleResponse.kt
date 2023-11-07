package com.example.ubi.api.response

data class ArticleResponse(
    val date: String,
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val thumbnailImage: String,
    val title: String
)