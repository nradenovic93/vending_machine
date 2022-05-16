package nl.hand.made.vending.machine.controller;

import lombok.RequiredArgsConstructor;
import nl.hand.made.vending.machine.service.VendingService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VendingController {

    private final VendingService vendingService;

    /**
     * Deposit money into account
     *
     * @param amount Amount to deposit
     * @param user   User to deposit it to
     */
    @PostMapping("/deposit")
    public void deposit(@RequestBody Integer amount, @AuthenticationPrincipal User user) {
        vendingService.deposit(amount, user);
    }

    /**
     * Buy a product from the user's deposit and return the remaining deposit
     *
     * @param productId The ID of the product to buy
     * @param user      User that does the buying
     * @return Any change that remains after purchase
     */
    @PostMapping("/buy")
    public List<Integer> buy(@RequestBody Integer productId, @AuthenticationPrincipal User user) {
        return vendingService.purchase(productId, user);
    }

    /**
     * Reset the deposit back to zero
     *
     * @param user User to reset the deposit for
     * @return The deposit, if there is any
     */
    @PostMapping("/reset")
    public List<Integer> reset(@AuthenticationPrincipal User user) {
        return vendingService.resetDeposit(user);
    }
}
