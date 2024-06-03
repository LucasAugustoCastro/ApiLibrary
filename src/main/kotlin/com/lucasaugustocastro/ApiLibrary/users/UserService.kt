package com.lucasaugustocastro.ApiLibrary.users

import com.lucasaugustocastro.ApiLibrary.SortDir
import com.lucasaugustocastro.ApiLibrary.exception.BadRequestException
import com.lucasaugustocastro.ApiLibrary.roles.RoleRepository
import com.lucasaugustocastro.ApiLibrary.security.Jwt
import com.lucasaugustocastro.ApiLibrary.users.responses.LoginResponse
import com.lucasaugustocastro.ApiLibrary.users.responses.UserResponse
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val jwt: Jwt
)  {
    fun save(user: User) = userRepository.findByEmail(user.email)
        ?.let { throw BadRequestException("User already exists") }
        ?: userRepository.save(user)
            .also { log.info("User inserted: ${it.id}") }

    fun findAll(dir: SortDir, role: String?) =
        role?.let{ r ->
            when (dir) {
                SortDir.ASC -> userRepository.findByRole(r.uppercase()).sortedBy { it.name }
                SortDir.DESC -> userRepository.findByRole(r.uppercase()).sortedByDescending { it.name }
            }
        } ?:
        when (dir) {
            SortDir.ASC -> userRepository.findAll(Sort.by("name").ascending())
            SortDir.DESC -> userRepository.findAll(Sort.by("name").descending())
        }

    fun findByIdOrNull(id:Long) = userRepository.findByIdOrNull(id)

    fun delete(id:Long) = userRepository.findByIdOrNull(id)
        ?.let { user ->
            if (user.roles.any {it.name == "ADMIN"}) {
                val count = userRepository.findByRole("ADMIN").size
                if (count == 1) throw BadRequestException("Cannot delete the last system admin!")
            }
            userRepository.delete(user)
        }


    fun addRole(id: Long, roleName: String): Boolean {
        val user = userRepository.findByIdOrNull(id)
            ?: throw BadRequestException("User $id not found!")
        if (user.roles.any {it.name == roleName }) return false

        val role = roleRepository.findByName(roleName)
            ?: throw BadRequestException("Invalid role $roleName!")

        user.roles.add(role)
        userRepository.save(user)
        return true
    }

    fun login(email: String, password: String): LoginResponse? {
        val user = userRepository.findByEmail(email)
        if (user == null) {
            log.warn("User $email not found!")
            return null
        }
        if (password != user.password) {
            log.warn("Invalid password!!")
            return null
        }
        log.info("User logged in: id = ${user.id}, name=${user.name}!")
        return LoginResponse(
            token = jwt.createToken(user),
            UserResponse(user)
        )
    }
    companion object {
        val log = LoggerFactory.getLogger(UserService::class.java)
    }
}