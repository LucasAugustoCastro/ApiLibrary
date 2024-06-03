package com.lucasaugustocastro.ApiLibrary.users

import com.fasterxml.jackson.annotation.JsonIgnore
import com.lucasaugustocastro.ApiLibrary.roles.Role
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name="tblUser")
class User (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @Column(nullable = false, unique = true)
    var email: String = "",
    @NotNull
    var password: String = "",
    @NotNull
    var name: String = "",

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "UserRole",
        joinColumns = [JoinColumn(name = "idUser")],
        inverseJoinColumns = [JoinColumn(name = "idRole")]
    )
    @JsonIgnore
    val roles: MutableSet<Role> = mutableSetOf()
)