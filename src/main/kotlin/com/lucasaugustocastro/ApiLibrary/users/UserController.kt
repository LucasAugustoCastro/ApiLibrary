package com.lucasaugustocastro.ApiLibrary.users

import com.lucasaugustocastro.ApiLibrary.SortDir
import com.lucasaugustocastro.ApiLibrary.users.requests.LoginRequest
import com.lucasaugustocastro.ApiLibrary.users.requests.UserRequest
import com.lucasaugustocastro.ApiLibrary.users.responses.LoginResponse
import com.lucasaugustocastro.ApiLibrary.users.responses.UserResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/users")
class UserController(val userService: UserService)  {
    @PostMapping
     fun insert(@RequestBody @Valid user: UserRequest):ResponseEntity<UserResponse> =
         ResponseEntity.status(HttpStatus.CREATED)
             .body(UserResponse(userService.save(user.toUser())))

    @GetMapping
    fun findAll(
        @RequestParam sortDir: String? = null,
        @RequestParam role: String? = null
    ) =
        SortDir.entries.firstOrNull { it.name == (sortDir ?: "ASC").uppercase() }
            ?.let { userService.findAll(it, role) }
            ?.map { UserResponse(it) }
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.badRequest().build()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) =
        userService.findByIdOrNull(id)
            ?.let { UserResponse(it) }
            ?.let {ResponseEntity.ok(it)}
            ?: ResponseEntity.notFound().build()


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "JWT")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void>  =
        userService.delete(id)
            ?.let { ResponseEntity.ok().build() }
            ?: ResponseEntity.notFound().build()

    @PutMapping("/{id}/roles/{role}")
    fun addRole(
        @PathVariable id: Long,
        @PathVariable role: String
    ):ResponseEntity<Void> =
        if (userService.addRole(id, role)) ResponseEntity.ok().build()
        else ResponseEntity.noContent().build()

    @PostMapping("/login")
    fun login(@Valid @RequestBody login: LoginRequest):ResponseEntity<LoginResponse> =
        userService.login(login.email!!, login.password!!)
            ?.let { ResponseEntity.ok(it)}
            ?: ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
}