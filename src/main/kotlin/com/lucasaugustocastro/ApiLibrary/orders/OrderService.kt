package com.lucasaugustocastro.ApiLibrary.orders

import com.lucasaugustocastro.ApiLibrary.books.BookRepository
import com.lucasaugustocastro.ApiLibrary.exception.BadRequestException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrderService(
    val orderRepository: OrderRepository,
    val bookRepository: BookRepository
) {
    fun getOrders() : List<Order> = orderRepository.findAll()

    fun getOrderById(id: Long) : Order? = orderRepository.findByIdOrNull(id)
        ?: throw BadRequestException("Order with id $id does not exist")

    fun createOrder(order: OrderRequest): Order = orderRepository.save(order.toOrder())

    fun updateOrder(id: Long, orderRequest: OrderRequest) = orderRepository.findByIdOrNull(id)
        ?.let { order ->
            order.status = orderRequest.status ?: order.status
            order.message = orderRequest.message ?: order.message
            order.address = orderRequest.address ?: order.address
            order.paymentType = orderRequest.paymentType ?: order.paymentType
            order.paymentAmount = orderRequest.paymentAmount ?: order.paymentAmount
            order.paymentDate = orderRequest.paymentDate ?: order.paymentDate
            order.paymentMethod = orderRequest.paymentMethod ?: order.paymentMethod
            order.paymentStatus = orderRequest.paymentStatus ?: order.paymentStatus

            orderRepository.save(order)
        } ?: throw BadRequestException("Order with id $id not found")

    fun deleteOrder(id: Long) = orderRepository.findByIdOrNull(id)
        ?.also { orderRepository.deleteById(id) }

    fun addBookOrder(orderId: Long, bookId: Long) = orderRepository.findByIdOrNull(orderId)
        ?.let { order ->
            order.books.find { book -> book.id == bookId }
                ?.let { throw BadRequestException("This book is already taken") }

            val book = bookRepository.findByIdOrNull(bookId)
                ?: throw BadRequestException("Book with id = $bookId don't find")

            order.books.add(book)
            orderRepository.save(order)
        }
        ?: throw BadRequestException("Order with id = $orderId does not exist")

    fun removeBookOrder(orderId: Long, bookId: Long) = orderRepository.findByIdOrNull(orderId)
        ?.let { order ->
            order.books.find { book -> book.id == bookId }
                ?.let { book ->
                    order.books.remove(book)
                    orderRepository.save(order)
                }
                ?: throw BadRequestException("Book with id = $bookId does not exist in this Order")
           }
        ?: throw BadRequestException("Order with id = $orderId does not exist")
}