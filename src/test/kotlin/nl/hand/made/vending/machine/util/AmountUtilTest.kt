package nl.hand.made.vending.machine.util

import nl.hand.made.vending.machine.exception.ValidationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class AmountUtilTest {

    @Test
    fun `validate product cost`() {
        assertDoesNotThrow { AmountUtil.validateProductCost(5) }
        assertThrows<ValidationException>("cost cannot be negative") { AmountUtil.validateProductCost(-10) }
        assertThrows<ValidationException>("cost should be in multiples of 5") { AmountUtil.validateProductCost(7) }
    }

    @Test
    fun `validate deposit`() {
        assertDoesNotThrow { AmountUtil.validateDeposit(5) }
        assertThrows<ValidationException>("deposit should be 5, 10, 20, 50, or 100") { AmountUtil.validateDeposit(7) }
    }

    @Test
    fun `calculate change`() {
        var change = AmountUtil.calculateChange(5)
        Assertions.assertEquals(listOf(5), change)

        change = AmountUtil.calculateChange(15)
        Assertions.assertEquals(listOf(10, 5), change)

        change = AmountUtil.calculateChange(135)
        Assertions.assertEquals(listOf(100, 20, 10, 5), change)
    }
}