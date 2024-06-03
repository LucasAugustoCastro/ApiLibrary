package com.lucasaugustocastro.ApiLibrary

import com.lucasaugustocastro.ApiLibrary.exception.BadRequestException

enum class SortDir {
    ASC, DESC;

    companion object {
        fun findOrThrow(sortDir: String) =
            entries.find { it.name == sortDir.uppercase() }
                ?: throw BadRequestException("Invalid sort dir!")
    }
}