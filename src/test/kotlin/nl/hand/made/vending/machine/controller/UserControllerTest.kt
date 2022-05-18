package nl.hand.made.vending.machine.controller

import nl.hand.made.vending.machine.model.Role
import nl.hand.made.vending.machine.model.request.UserCreate
import nl.hand.made.vending.machine.model.request.UserPatch
import nl.hand.made.vending.machine.model.response.UserResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest @Autowired constructor(
    private val testRestTemplate: TestRestTemplate
) {

    @LocalServerPort
    private val port: Int = 0

    @BeforeEach
    fun setup() {
        testRestTemplate.restTemplate.requestFactory = HttpComponentsClientHttpRequestFactory()
    }

    @Test
    fun `get user admin access only`() {
        // Providing no credentials should result in UNAUTHORIZED response
        val wrongAuthResponse =
            testRestTemplate.getForEntity("http://localhost:$port/user?username=pinkunicorn", Void::class.java)
        assertEquals(HttpStatus.UNAUTHORIZED, wrongAuthResponse.statusCode)

        // Getting existing user with correct credentials should work
        val existingUser = testRestTemplate.withBasicAuth("admin", "admin")
            .getForObject("http://localhost:$port/user?username=pinkunicorn", UserResponse::class.java)
        assertEquals("pinkunicorn", existingUser.username)

        // Getting non-existent user with correct credentials should result null
        val nonExistentUser = testRestTemplate.withBasicAuth("admin", "admin")
            .getForObject("http://localhost:$port/user?username=randomuserthatdoesntexist", UserResponse::class.java)
        assertNull(nonExistentUser)
    }

    @Test
    fun `create user without auth`() {
        // Creating a user that doesn't exist should work
        var userData = UserCreate("random", "random", listOf(Role.ROLE_BUYER))
        var response = testRestTemplate.postForEntity("http://localhost:$port/user", userData, Void::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)

        // Creating a user that already exists should return a BAD_REQUEST
        userData = UserCreate("bluebear", "random", listOf(Role.ROLE_BUYER))
        response = testRestTemplate.postForEntity("http://localhost:$port/user", userData, Void::class.java)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    fun `edit user admin access only`() {
        val userData = UserPatch("bol", "randomnewpassword", listOf(Role.ROLE_BUYER))
        val response = testRestTemplate.withBasicAuth("admin", "admin")
            .exchange("http://localhost:$port/user", HttpMethod.PUT, HttpEntity<Any>(userData), Void::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `delete user admin access only`() {
        val response = testRestTemplate.withBasicAuth("admin", "admin")
            .exchange(
                "http://localhost:$port/user?username=crustytheclown",
                HttpMethod.DELETE,
                HttpEntity<Any>(null, null),
                Void::class.java
            )
        assertEquals(HttpStatus.OK, response.statusCode)
    }
}