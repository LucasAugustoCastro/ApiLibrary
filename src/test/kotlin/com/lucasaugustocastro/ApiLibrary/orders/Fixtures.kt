package com.lucasaugustocastro.ApiLibrary.orders

import com.lucasaugustocastro.ApiLibrary.books.Book

fun _order(
    id: Long?=null,
    status: OrderStatus=OrderStatus.NEW,
    message: String = "Some Message",
    address: String = "Some Address",
    paymentType: String = "Some PaymentType",
    paymentAmount: Double = 0.0,
    paymentDate: String = "Some PaymentDate",
    paymentMethod: String = "Some Method",
    paymentStatus: String = "Some Status",
) = Order(
    id=id,
    status=status,
    message = message,
    address = address,
    paymentType = paymentType,
    paymentAmount = paymentAmount,
    paymentDate = paymentDate,
    paymentMethod = paymentMethod,
    paymentStatus = paymentStatus
)

fun _book(
    id: Long?=null,
    name: String = "Book",
    author: String = "Author",
    pages: Int = 1,
    description: String = "Description",
    publisher: String = "Publiser"
) = Book(
    id=id,
    name=name,
    author=author,
    pages=pages,
    description=description,
    publisher=publisher
)