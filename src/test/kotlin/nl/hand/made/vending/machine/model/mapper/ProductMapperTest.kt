package nl.hand.made.vending.machine.model.mapper

import nl.hand.made.vending.machine.exception.ValidationException
import nl.hand.made.vending.machine.model.request.ProductCreate
import nl.hand.made.vending.machine.model.request.ProductPatch
import nl.hand.made.vending.machine.repository.model.ProductEntity
import nl.hand.made.vending.machine.repository.model.UserEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductMapperTest {

    @Test
    fun `map to response`() {
        val result = ProductMapper.mapToResponseModel(productEntity())

        assertEquals(5, result.amountAvailable)
        assertEquals(15, result.cost)
        assertEquals("Gacha", result.productName)
        assertEquals("randomusername", result.seller)
    }

    @Test
    fun `map to entity model`() {
        val productCreate = ProductCreate(amountAvailable = 5, cost = 10, productName = "Gacha")

        val result = ProductMapper.mapToEntityModel(productCreate, userEntity())

        assertNull(result.id)
        assertEquals(5, result.amountAvailable)
        assertEquals(10, result.cost)
        assertEquals("Gacha", result.productName)
        assertEquals("randomusername", result.seller.username)
    }

    @Test
    fun `patch existing product`() {
        val result = ProductMapper.patch(
            productEntity(),
            ProductPatch(amountAvailable = 3, cost = 10, productName = "Totally legit product")
        );

        assertEquals(123, result.id)
        assertEquals("Totally legit product", result.productName)
        assertEquals(10, result.cost)
        assertEquals(3, result.amountAvailable)
        assertEquals("randomusername", result.seller.username)
    }

    @Test
    fun `patch existing product partially`() {
        // AmountAvailable only
        var result = ProductMapper.patch(
            productEntity(),
            ProductPatch(amountAvailable = 3)
        );

        assertEquals(123, result.id)
        assertEquals("Gacha", result.productName)
        assertEquals(15, result.cost)
        assertEquals(3, result.amountAvailable)
        assertEquals("randomusername", result.seller.username)

        // Cost only
        result = ProductMapper.patch(
            productEntity(),
            ProductPatch(cost = 10)
        );

        assertEquals(123, result.id)
        assertEquals("Gacha", result.productName)
        assertEquals(10, result.cost)
        assertEquals(5, result.amountAvailable)
        assertEquals("randomusername", result.seller.username)

        // Productname only
        result = ProductMapper.patch(
            productEntity(),
            ProductPatch(productName = "Totally legit product")
        );

        assertEquals(123, result.id)
        assertEquals("Totally legit product", result.productName)
        assertEquals(15, result.cost)
        assertEquals(5, result.amountAvailable)
        assertEquals("randomusername", result.seller.username)
    }

    @Test
    fun `patch existing user with faulty data`() {
        // AmountAvailable only
        assertThrows<ValidationException>("amount available cannot be negative") {
            ProductMapper.patch(productEntity(), ProductPatch(amountAvailable = -5))
        }

        // Productname only
        assertThrows<ValidationException>("product name cannot be empty") {
            ProductMapper.patch(productEntity(), ProductPatch(productName = ""))
        }

        // Cost only
        assertThrows<ValidationException>("cost cannot be negative") {
            ProductMapper.patch(productEntity(), ProductPatch(cost = -7))
        }

        assertThrows<ValidationException>("cost should be in multiples of 5") {
            ProductMapper.patch(productEntity(), ProductPatch(cost = 7))
        }
    }

    private fun productEntity() =
        ProductEntity(id = 123, amountAvailable = 5, cost = 15, productName = "Gacha", seller = userEntity())

    private fun userEntity() =
        UserEntity(username = "randomusername", password = "oldpassword", roles = mutableListOf("role_buyer"))
}