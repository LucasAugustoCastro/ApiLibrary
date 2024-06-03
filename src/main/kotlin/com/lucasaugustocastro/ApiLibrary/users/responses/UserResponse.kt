package com.lucasaugustocastro.ApiLibrary.users.responses

import com.lucasaugustocastro.ApiLibrary.users.User

data class UserResponse (
    val id: Long,
    val email: String,
    val name: String
) {
    constructor (user: User) : this(
        id = user.id!!,
        email = user.email,
        name = user.name
    )
}