package nl.hand.made.vending.machine.controller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VendingControllerTest @Autowired constructor(
    private val testRestTemplate: TestRestTemplate
) {

    @LocalServerPort
    private val port: Int = 0

    @BeforeEach
    fun setup() {
        testRestTemplate.restTemplate.requestFactory = HttpComponentsClientHttpRequestFactory()
    }

    @Test
    fun depositBuyerAccessOnly() {
        // Try depositing for a BUYER role
        var response = testRestTemplate.withBasicAuth("pinkunicorn", "unicornmeat")
            .postForEntity("http://localhost:$port/deposit", 20, Void::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)

        // Won't work for ADMIN role
        response = testRestTemplate.withBasicAuth("admin", "admin")
            .postForEntity("http://localhost:$port/deposit", 5, Void::class.java)
        assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
    }

    @Test
    fun buyProduct() {
        // Try buying with no deposit
        val response = testRestTemplate.withBasicAuth("bluebear", "dancingbear")
            .postForEntity("http://localhost:$port/buy", 1, String::class.java)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)

        // Try buying with deposit, correct change
        val successResponse = testRestTemplate.withBasicAuth("orangeorange", "fruitfly")
            .postForEntity("http://localhost:$port/buy", 1, Array<Int>::class.java)
        assertEquals(HttpStatus.OK, successResponse.statusCode)
        assertNotNull(successResponse.body)
        assertEquals(1, successResponse.body!!.size)
        assertEquals(5, successResponse.body!![0])
    }

    @Test
    fun resetDeposit() {
        // Try resetting with no deposit
        var response = testRestTemplate.withBasicAuth("bluebear", "dancingbear")
            .postForEntity("http://localhost:$port/reset", null, Array<Int>::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(0, response.body!!.size)

        // Try resetting with some deposit
        response = testRestTemplate.withBasicAuth("rosie", "purple")
            .postForEntity("http://localhost:$port/reset", null, Array<Int>::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(1, response.body!!.size)
        assertEquals(20, response.body!![0])
    }
}