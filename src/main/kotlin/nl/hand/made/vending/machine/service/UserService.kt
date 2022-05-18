package nl.hand.made.vending.machine.service

import nl.hand.made.vending.machine.exception.ValidationException
import nl.hand.made.vending.machine.model.mapper.UserMapper
import nl.hand.made.vending.machine.model.request.UserCreate
import nl.hand.made.vending.machine.model.request.UserPatch
import nl.hand.made.vending.machine.model.response.UserResponse
import nl.hand.made.vending.machine.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
) {

    fun getUser(username: String): UserResponse? =
        userRepository.findByUsername(username)?.let { user -> UserMapper.mapToResponseModel(user) }

    fun createUser(user: UserCreate) {
        validate(user)
        userRepository.findByUsername(user.username)
            ?.run { throw ValidationException("user with provided username already exists") }
        return UserMapper.mapToEntityModel(user, passwordEncoder).let {
            userRepository.save(it)
        }
    }

    fun editUser(user: UserPatch) {
        userRepository.findByUsername(user.username)
            ?.let { entity ->
                UserMapper.patch(entity, user, passwordEncoder).run { userRepository.save(this) }
            } ?: throw ValidationException("user with username not found")
    }

    fun deleteUser(username: String) {
        userRepository.findByUsername(username)
            ?: throw ValidationException("user with username not found")
        userRepository.deleteByUsername(username)
    }

    private fun validate(user: UserCreate) {
        val validationErrors = mutableListOf<String>()

        user.username.isNotBlank() || validationErrors.add("username cannot be empty")
        user.password.isNotBlank() || validationErrors.add("password cannot be empty")
        user.roles.isNotEmpty() || validationErrors.add("roles cannot be empty")

        validationErrors.isEmpty() || throw ValidationException(validationErrors.joinToString(", "))
    }
}