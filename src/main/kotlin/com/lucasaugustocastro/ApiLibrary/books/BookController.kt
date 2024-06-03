package com.lucasaugustocastro.ApiLibrary.books

import com.lucasaugustocastro.ApiLibrary.SortDir
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(val service: BookService) {

    @GetMapping
    fun getBooks(bookName: String="", order: SortDir=SortDir.ASC) = service.getAll(bookName, order)

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Long) = service.getByIdOrNull(id)
        .let { ResponseEntity.ok(it) }


    @PostMapping
    @SecurityRequirement(name = "JWT")
    fun insertBook(@RequestBody @Valid book: BookRequest): ResponseEntity<Book> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(service.insert(book.toBook()))


    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> =
        service.delete(id)
            ?.let { ResponseEntity.status(HttpStatus.CREATED).build() }
            ?: ResponseEntity.notFound().build()
}