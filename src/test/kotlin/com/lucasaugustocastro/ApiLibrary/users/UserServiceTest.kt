package com.lucasaugustocastro.ApiLibrary.users

import com.lucasaugustocastro.ApiLibrary.exception.BadRequestException
import com.lucasaugustocastro.ApiLibrary.roles.Role
import com.lucasaugustocastro.ApiLibrary.roles.RoleRepository
import com.lucasaugustocastro.ApiLibrary.security.Jwt
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import io.mockk.checkUnnecessaryStub
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull

class UserServiceTest {
    private val userRepository = mockk<UserRepository>()
    private val roleRepository = mockk<RoleRepository>()
    private val jwt = mockk<Jwt>()

    private val service = UserService(userRepository, roleRepository, jwt)

    @BeforeEach fun setup() = clearAllMocks()
    @AfterEach fun cleanUp() = checkUnnecessaryStub()

    @Test
    fun `insert throws BadRequestException if an user with the same e-mail is found`(){
        val user = _user(id = null)
        every { userRepository.findByEmail(user.email) } returns _user(id=1)

        assertThrows<BadRequestException> {
            service.save(user)
        } shouldHaveMessage  "User already exists"

    }

    @Test
    fun `insert must return the saved user if it is inserted`() {
        val user = _user(id = null)
        every { userRepository.findByEmail(user.email) } returns null

        val saved = _user(id=1)
        every { userRepository.save(user) } returns saved

        service.save(user) shouldBe saved
    }

    @Test
    fun `addRole throws BadRequestException if user does not exist`(){
        val user = _user(id = 1)
        every { userRepository.findByIdOrNull(user.id) } returns null
        assertThrows<BadRequestException> {
            service.addRole(1, "ADMIN")
        } shouldHaveMessage  "User ${user.id} not found!"

    }

    @Test
    fun `addRole throws BadRequestException if role does not exists`(){
        val user = _user(id = 1)
        every { userRepository.findByIdOrNull(user.id) } returns user
        every { roleRepository.findByName("ADMIN") } returns null

        assertThrows<BadRequestException> {
            service.addRole(1, "ADMIN")
        } shouldHaveMessage  "Invalid role ADMIN!"
    }

    @Test
    fun `addRole must return true if role was add in user`() {
        val user = _user(id = 1)
        every { userRepository.findByIdOrNull(user.id) } returns user
        every { roleRepository.findByName("ADMIN") } returns Role(name = "ADMIN", description = "ADMIN ROLE")
        every { userRepository.save(user) } returns _user(id = 1, roles = listOf("ADMIN"))
        service.addRole(1, "ADMIN") shouldBe true
    }
}