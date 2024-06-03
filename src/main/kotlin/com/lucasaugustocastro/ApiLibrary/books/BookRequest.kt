package com.lucasaugustocastro.ApiLibrary.books

import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull

data class BookRequest (
    @field:NotBlank
    @field:NotNull
    val name: String?,
    val description: String?,

    @field:NotBlank
    @field:NotNull
    val author: String?,

    @field:NotBlank
    @field:NotNull
    val publisher: String?,

    @field:NotNull
    val pages: Int?,
){
    fun toBook() = Book(
        name = name!!,
        description = description!!,
        author = author!!,
        publisher = publisher!!,
        pages = pages!!
    )
}