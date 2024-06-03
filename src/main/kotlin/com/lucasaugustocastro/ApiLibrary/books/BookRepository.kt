package com.lucasaugustocastro.ApiLibrary.books

import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository: JpaRepository<Book, Long> {
    fun findByName(name : String) : List<Book>
}