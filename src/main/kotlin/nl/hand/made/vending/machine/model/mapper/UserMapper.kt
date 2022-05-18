package nl.hand.made.vending.machine.model.mapper

import nl.hand.made.vending.machine.exception.ValidationException
import nl.hand.made.vending.machine.model.Role
import nl.hand.made.vending.machine.model.request.UserCreate
import nl.hand.made.vending.machine.model.request.UserPatch
import nl.hand.made.vending.machine.model.response.UserResponse
import nl.hand.made.vending.machine.repository.model.UserEntity
import org.springframework.security.crypto.password.PasswordEncoder

class UserMapper {

    companion object {
        fun mapToResponseModel(userEntity: UserEntity) =
            UserResponse(
                username = userEntity.username,
                deposit = userEntity.deposit,
                roles = userEntity.roles.mapNotNull { role -> Role.fromString(role) }
            )

        fun mapToEntityModel(userData: UserCreate, passwordEncoder: PasswordEncoder) =
            UserEntity(
                username = userData.username,
                password = passwordEncoder.encode(userData.password),
                roles = userData.roles.map { role -> role.toString() }.toMutableList()
            )

        fun patch(userEntity: UserEntity, userData: UserPatch, passwordEncoder: PasswordEncoder): UserEntity {
            userData.password?.run {
                this.isNotBlank() || throw ValidationException("user password cannot be empty")
                userEntity.password = passwordEncoder.encode(this)
            }

            userData.roles?.run {
                this.isNotEmpty() || throw ValidationException("user roles cannot be empty")
                this.map { role -> role.toString() }.toMutableList().also { userEntity.roles = it }
            }
            return userEntity;
        }
    }
}