package com.lucasaugustocastro.ApiLibrary.roles

import jakarta.persistence.*

@Entity
class Role (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @Column(nullable = false, unique = true)
    var name: String,
    var description: String,

)