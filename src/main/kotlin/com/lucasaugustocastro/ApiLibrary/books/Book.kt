package com.lucasaugustocastro.ApiLibrary.books

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
class Book (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @NotNull
    var name: String,
    @NotNull
    var author: String,
    @NotNull
    var pages: Int,
    @NotNull
    var description: String,
    @NotNull
    var publisher: String
)