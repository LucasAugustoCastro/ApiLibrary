package com.lucasaugustocastro.ApiLibrary.orders

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/orders")
class OrderController(val orderService: OrderService) {
    @GetMapping
    fun getOrders() = orderService.getOrders()

    @GetMapping("/{id}")
    fun getOrder(@PathVariable id: Long) = orderService.getOrderById(id)
        ?.let { ResponseEntity.ok(it) }

    @PatchMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    fun updateOrder(@PathVariable id:Long, @RequestBody order: OrderRequest) = ResponseEntity
        .status(HttpStatus.CREATED)
        .body(orderService.updateOrder(id, order))


    @PostMapping
    @SecurityRequirement(name = "JWT")
    fun createOrder(@RequestBody order: OrderRequest) =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(orderService.createOrder(order))

    @PutMapping("/{id}/book/{bookId}")
    @SecurityRequirement(name = "JWT")
    fun addBookOrder(@PathVariable id: Long, @PathVariable bookId: Long) =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(orderService.addBookOrder(id, bookId))

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    fun delete(@PathVariable id:Long): ResponseEntity<Void> = orderService.deleteOrder(id)
        ?.let { ResponseEntity.ok().build() }
        ?: ResponseEntity.notFound().build()

    @DeleteMapping("/{id}/book/{bookId}")
    @SecurityRequirement(name = "JWT")
    fun deleteBookOrder(@PathVariable id: Long, @PathVariable bookId: Long) =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(orderService.removeBookOrder(id, bookId))
}