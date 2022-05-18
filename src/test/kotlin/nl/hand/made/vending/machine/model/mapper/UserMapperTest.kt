package nl.hand.made.vending.machine.model.mapper

import nl.hand.made.vending.machine.exception.ValidationException
import nl.hand.made.vending.machine.model.Role
import nl.hand.made.vending.machine.model.request.UserCreate
import nl.hand.made.vending.machine.model.request.UserPatch
import nl.hand.made.vending.machine.repository.model.UserEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserMapperTest {

    private val passwordEncoder = BCryptPasswordEncoder()

    @Test
    fun `map to response`() {
        val result = UserMapper.mapToResponseModel(userEntity())

        assertEquals("randomusername", result.username)
        assertEquals(0, result.deposit)
        assertEquals(listOf(Role.ROLE_BUYER), result.roles)
    }

    @Test
    fun `map to entity model`() {
        val userCreate = UserCreate(
            username = "randomusername",
            password = "password",
            roles = mutableListOf(Role.ROLE_BUYER, Role.ROLE_SELLER)
        )

        val result = UserMapper.mapToEntityModel(userCreate, passwordEncoder)

        assertEquals("randomusername", result.username)
        assertNotNull(result.password)
        assertNotEquals("password", result.password)
        assertEquals(mutableListOf("role_buyer", "role_seller"), result.roles)
        assertEquals(0, result.deposit)
    }

    @Test
    fun `patch existing user`() {
        val result = UserMapper.patch(
            userEntity(), passwordEncoder = passwordEncoder,
            userData = UserPatch(
                username = "randomusername",
                password = "newpassword",
                roles = mutableListOf(Role.ROLE_BUYER, Role.ROLE_SELLER)
            )
        );

        assertNotEquals("oldpassword", result.password)
        assertEquals(mutableListOf("role_buyer", "role_seller"), result.roles)
    }

    @Test
    fun `patch existing user roles only`() {
        // Roles only
        var result = UserMapper.patch(
            userEntity(), passwordEncoder = passwordEncoder,
            userData = UserPatch(username = "randomusername", roles = mutableListOf(Role.ROLE_BUYER, Role.ROLE_SELLER))
        );

        assertEquals("oldpassword", result.password)
        assertEquals(mutableListOf("role_buyer", "role_seller"), result.roles)

        // Password only
        result = UserMapper.patch(
            userEntity(), passwordEncoder = passwordEncoder,
            userData = UserPatch(username = "randomusername", password = "newpassword")
        );

        assertNotEquals("oldpassword", result.password)
        assertEquals(mutableListOf("role_buyer"), result.roles)
    }

    @Test
    fun `patch existing user with faulty data`() {
        // Roles only
        assertThrows<ValidationException>("user roles cannot be empty") {
            UserMapper.patch(
                userEntity(),
                passwordEncoder = passwordEncoder,
                userData = UserPatch(username = "randomusername", roles = mutableListOf())
            )
        }

        // Password only
        assertThrows<ValidationException>("user password cannot be empty") {
            UserMapper.patch(
                userEntity(),
                passwordEncoder = passwordEncoder,
                userData = UserPatch(username = "randomusername", password = "")
            )
        }
    }

    private fun userEntity() =
        UserEntity(username = "randomusername", password = "oldpassword", roles = mutableListOf("role_buyer"))
}