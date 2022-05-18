package nl.hand.made.vending.machine.controller

import nl.hand.made.vending.machine.service.VendingService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class VendingController(
    private val vendingService: VendingService
) {

    /**
     * Deposit money into account
     *
     * @param amount Amount to deposit
     * @param user   User to deposit it to
     */
    @PostMapping("/deposit")
    fun deposit(@RequestBody amount: Int, @AuthenticationPrincipal user: User) = vendingService.deposit(amount, user)

    /**
     * Buy a product from the user's deposit and return the remaining deposit
     *
     * @param productId The ID of the product to buy
     * @param user      User that does the buying
     * @return Any change that remains after purchase
     */
    @PostMapping("/buy")
    fun buy(@RequestBody productId: Int, @AuthenticationPrincipal user: User) = vendingService.purchase(productId, user)

    /**
     * Reset the deposit back to zero
     *
     * @param user User to reset the deposit for
     * @return The deposit, if there is any
     */
    @PostMapping("/reset")
    fun reset(@AuthenticationPrincipal user: User) = vendingService.resetDeposit(user)
}