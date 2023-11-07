package com.example.ubi.api.response

data class RegisterResponse(
    val phone: String,
    val email: String,
    val password: String,
    val name: String
)