package com.lucasaugustocastro.ApiLibrary.books

import com.lucasaugustocastro.ApiLibrary.SortDir
import com.lucasaugustocastro.ApiLibrary.exception.BadRequestException

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import io.mockk.checkUnnecessaryStub
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull

class BookServiceTest {

    private val bookRepository = mockk<BookRepository>()


    private val service = BookService(bookRepository)

    @BeforeEach
    fun setup() = clearAllMocks()
    @AfterEach
    fun cleanUp() = checkUnnecessaryStub()

    @Test
    fun `getAll must return a list of Books`(){
        val book = _book(id=1, name = "Game of Thrones")
        val book1 = _book(id=1, name = "Maze Runner")

        every { bookRepository.findAll() } returns listOf(book, book1)

        service.getAll("", SortDir.ASC).toList().shouldHaveSize(2)

    }
    @Test
    fun `getAll must return a list of Books when filtered`(){
        val book = _book(id=1, name = "Game of Thrones")

        every { bookRepository.findByName(book.name) } returns listOf(book)

        service.getAll(book.name, SortDir.ASC).toList().shouldHaveSize(1)

    }

    @Test
    fun `getByIdOrNull throws BadRequestException if book does not exist`(){
        val book = _book(id=1)
        every { bookRepository.findByIdOrNull(book.id) } returns null
        assertThrows<BadRequestException> {
            service.getByIdOrNull(book.id!!)
        } shouldHaveMessage "Book not found"
    }

    @Test
    fun `getByIdOrNull must return book`(){
        val book = _book(id=1)
        every { bookRepository.findByIdOrNull(book.id) } returns book
        service.getByIdOrNull(book.id!!) shouldBe book
    }


}