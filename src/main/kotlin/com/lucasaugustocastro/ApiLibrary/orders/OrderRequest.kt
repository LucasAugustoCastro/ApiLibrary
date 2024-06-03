package com.lucasaugustocastro.ApiLibrary.orders

data class OrderRequest (
    var status: OrderStatus?,
    var message: String?,
    var address: String?,
    var paymentType: String?,
    var paymentAmount: Double?,
    var paymentDate: String?,
    var paymentMethod: String?,
    var paymentStatus: String?,
){
    fun toOrder() = Order(
        status = status!!,
        message = message!!,
        address = address!!,
        paymentType = paymentType!!,
        paymentAmount = paymentAmount!!,
        paymentDate = paymentDate!!,
        paymentMethod = paymentMethod!!,
        paymentStatus = paymentStatus!!
    )
}