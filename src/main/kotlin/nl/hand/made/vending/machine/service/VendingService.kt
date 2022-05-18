package nl.hand.made.vending.machine.service

import nl.hand.made.vending.machine.exception.ValidationException
import nl.hand.made.vending.machine.repository.ProductRepository
import nl.hand.made.vending.machine.repository.UserRepository
import nl.hand.made.vending.machine.util.AmountUtil
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class VendingService(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
) {

    fun deposit(amount: Int, user: UserDetails) {
        AmountUtil.validateDeposit(amount)
        userRepository.findByUsername(user.username)?.run {
            this.deposit += amount
            userRepository.save(this)
        } ?: throw ValidationException("user with username not found")
    }

    fun purchase(productId: Int, user: UserDetails): List<Int> {
        val userEntity = userRepository.findByUsername(user.username)
            ?: throw ValidationException("user with username not found")
        val productEntity = productRepository.findById(productId)
            .orElseThrow { ValidationException("product with product id not found") }

        productEntity.amountAvailable >= 1 || throw ValidationException("product not available")
        productEntity.cost <= userEntity.deposit || throw ValidationException("user does not have enough deposit to purchase product")

        val change = AmountUtil.calculateChange(userEntity.deposit - productEntity.cost)

        userEntity.run {
            this.deposit = 0
            userRepository.save(this)
        }
        productEntity.run {
            this.amountAvailable -= 1
            productRepository.save(this)
        }
        return change
    }

    fun resetDeposit(user: UserDetails): List<Int> {
        val userEntity = userRepository.findByUsername(user.username)
            ?: throw ValidationException("user with username not found")
        val change = AmountUtil.calculateChange(userEntity.deposit)

        userEntity.run {
            this.deposit = 0
            userRepository.save(this)
        }
        return change
    }
}