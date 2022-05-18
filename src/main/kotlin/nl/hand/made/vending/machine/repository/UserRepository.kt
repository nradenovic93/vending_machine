package nl.hand.made.vending.machine.repository

import nl.hand.made.vending.machine.repository.model.UserEntity
import org.springframework.data.repository.CrudRepository
import javax.transaction.Transactional

@Transactional
interface UserRepository : CrudRepository<UserEntity, String> {
    fun findByUsername(username: String): UserEntity?
    fun deleteByUsername(username: String)
}