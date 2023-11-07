package com.example.ubi.api.response

data class CheckBoardResponse(
    val content: String,
    val id: String,
    val likeCount: String,
    val thumbnailImage: String,
    val title: String,
    val viewCount: String,
    val writer: String
)