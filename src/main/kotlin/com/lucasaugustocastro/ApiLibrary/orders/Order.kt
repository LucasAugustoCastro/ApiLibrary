package com.lucasaugustocastro.ApiLibrary.orders

import com.lucasaugustocastro.ApiLibrary.books.Book
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "tbl_orders")
class Order (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @NotNull
    var status: OrderStatus,

    @NotNull
    var message: String,

    @NotNull
    var address: String,

    @NotNull
    var paymentType: String,

    @NotNull
    var paymentAmount: Double,

    @NotNull
    var paymentDate: String,

    @NotNull
    var paymentMethod: String,

    @NotNull
    var paymentStatus: String,
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now(),


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "OrderBook",
        joinColumns = [JoinColumn(name = "idOrder", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "idBook")]
    )
    val books: MutableSet<Book> = mutableSetOf()



)