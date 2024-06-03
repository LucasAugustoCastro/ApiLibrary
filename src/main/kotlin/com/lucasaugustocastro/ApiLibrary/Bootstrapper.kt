package com.lucasaugustocastro.ApiLibrary

import com.lucasaugustocastro.ApiLibrary.roles.Role
import com.lucasaugustocastro.ApiLibrary.roles.RoleRepository
import com.lucasaugustocastro.ApiLibrary.users.User
import com.lucasaugustocastro.ApiLibrary.users.UserRepository
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class Bootstrapper (
    val userRepository: UserRepository,
    val rolesRepository: RoleRepository
): ApplicationListener<ContextRefreshedEvent> {
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val adminRole =  rolesRepository.findByName("ADMIN")
            ?: rolesRepository.save(Role(name = "ADMIN", description = "System administration"))
                .also { rolesRepository.save(Role(name = "USER", description = "Premium User")) }

        if (userRepository.findByRole(adminRole.name).isEmpty()) {
            val admin = User(
                name = "Auth Server Administration",
                email = "admin@authserver.com",
                password = "admin"
            )
            admin.roles.add(adminRole)
            userRepository.save(admin)
        }

    }
}