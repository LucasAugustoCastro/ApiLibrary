package com.lucasaugustocastro.ApiLibrary.books

import com.lucasaugustocastro.ApiLibrary.SortDir
import com.lucasaugustocastro.ApiLibrary.exception.BadRequestException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BookService(val repository: BookRepository) {
    fun insert(book: Book) = repository.save(book)

    fun delete(id:Long) = repository.findByIdOrNull(id)
        .also { repository.deleteById(id) }

    fun getAll(bookName: String, dir: SortDir): List<Book> =
        if (bookName.isBlank()) {
            when (dir) {
                SortDir.ASC -> repository.findAll().sortedBy { it.name }
                SortDir.DESC -> repository.findAll().sortedByDescending { it.name }
            }
        } else {
            when (dir) {
                SortDir.ASC -> repository.findByName(bookName).sortedBy { it.name }
                SortDir.DESC -> repository.findByName(bookName).sortedByDescending { it.name }
            }
        }

    fun getByIdOrNull(id: Long) = repository.findByIdOrNull(id)
        ?: throw BadRequestException("Book not found")

}