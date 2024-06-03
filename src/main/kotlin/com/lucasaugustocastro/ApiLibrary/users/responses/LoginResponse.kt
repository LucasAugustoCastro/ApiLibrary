package com.lucasaugustocastro.ApiLibrary.users.responses

data class LoginResponse(
    val token: String,
    val user: UserResponse
)