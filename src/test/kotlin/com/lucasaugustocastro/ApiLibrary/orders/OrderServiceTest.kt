package com.lucasaugustocastro.ApiLibrary.orders

import com.lucasaugustocastro.ApiLibrary.books.BookRepository
import com.lucasaugustocastro.ApiLibrary.exception.BadRequestException
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

class OrderServiceTest {
    private val orderRepository = mockk<OrderRepository>()
    private val bookRepository = mockk<BookRepository>()


    private val service = OrderService(orderRepository, bookRepository)

    @BeforeEach
    fun setup() = clearAllMocks()
    @AfterEach
    fun cleanUp() = checkUnnecessaryStub()

    @Test
    fun `addBookOrder throws BadRequestException if order does not exists`(){
        val order = _order(id=1)

        every { orderRepository.findByIdOrNull(order.id) } returns null

        assertThrows<BadRequestException> {
            service.addBookOrder(order.id!!, 1)
        } shouldHaveMessage  "Order with id = ${order.id} does not exist"

    }

    @Test
    fun `addBookOrder throws BadRequestException if book does not exists`(){
        val order = _order(id=1)
        every { orderRepository.findByIdOrNull(order.id) } returns order
        every { bookRepository.findByIdOrNull(1) } returns null
        assertThrows<BadRequestException> {
            service.addBookOrder(order.id!!, 1)
        } shouldHaveMessage  "Book with id = 1 don't find"
    }

    @Test
    fun `addBookOrder must return updated order`(){
        val order = _order(id=1)
        val book = _book(1)
        every { orderRepository.findByIdOrNull(order.id) } returns order
        every { bookRepository.findByIdOrNull(book.id) } returns book

        every { orderRepository.save(order) } returns order

        service.addBookOrder(order.id!!, book.id!!) shouldBe order

    }
}