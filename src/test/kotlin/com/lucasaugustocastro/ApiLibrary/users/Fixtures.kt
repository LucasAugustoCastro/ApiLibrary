package com.lucasaugustocastro.ApiLibrary.users

import com.lucasaugustocastro.ApiLibrary.roles.Role

fun _user(
    id: Long? = null,
    name: String = "name",
    roles: List<String> = listOf()
) =
    User(
        id = id,
        email = "${name}@example.com",
        password = "password",
        name = name,
        roles = roles.mapIndexed{ i, v -> Role(id=i.toLong(), name=v, description = v)}
            .toMutableSet()
    )