package nl.hand.made.vending.machine.util

import nl.hand.made.vending.machine.exception.ValidationException

class AmountUtil {

    companion object {
        private val VALID_COINS = listOf(100, 50, 20, 10, 5)

        fun validateProductCost(cost: Int) {
            cost >= 0 || throw ValidationException("cost cannot be negative")
            cost % 5 == 0 || throw ValidationException("cost should be in multiples of 5")
        }

        fun validateDeposit(deposit: Int) {
            VALID_COINS.contains(deposit) || throw ValidationException("deposit should be 5, 10, 20, 50, or 100")
        }

        fun calculateChange(change: Int): List<Int> {
            val changeCoins = mutableListOf<Int>()
            var remainingChange = change
            VALID_COINS.forEach { coin ->
                while (remainingChange >= coin) {
                    changeCoins.add(coin)
                    remainingChange -= coin
                }
            }
            return changeCoins
        }
    }
}