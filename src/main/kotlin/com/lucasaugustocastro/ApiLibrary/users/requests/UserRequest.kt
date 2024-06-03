package com.lucasaugustocastro.ApiLibrary.users.requests

import com.lucasaugustocastro.ApiLibrary.users.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserRequest(
    @field:Email
    var email: String?,

    @field:NotBlank
    var password: String?,
    var name: String?
) {
    fun toUser() = User(

        email=email!!,
        password = password!!,
        name = name ?: ""
    )
}
