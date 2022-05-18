package nl.hand.made.vending.machine.repository

import nl.hand.made.vending.machine.repository.model.ProductEntity
import nl.hand.made.vending.machine.repository.model.UserEntity
import org.springframework.data.repository.CrudRepository
import javax.transaction.Transactional

@Transactional
interface ProductRepository : CrudRepository<ProductEntity, Int> {
    override fun findAll(): List<ProductEntity>
    fun findAllByAmountAvailableIsGreaterThan(amount: Int): List<ProductEntity>
    fun findByIdAndSeller(id: Int, userEntity: UserEntity): ProductEntity?
}