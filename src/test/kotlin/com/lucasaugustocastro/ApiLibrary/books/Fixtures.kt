package com.lucasaugustocastro.ApiLibrary.books

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