package nl.hand.made.vending.machine.controller;

import nl.hand.made.vending.machine.model.Role;
import nl.hand.made.vending.machine.model.request.UserData;
import nl.hand.made.vending.machine.model.response.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    void getUserAdminAccessOnly() {
        // Providing no credentials should result in UNAUTHORIZED response
        ResponseEntity<Void> wrongAuthResponse = this.restTemplate.getForEntity("http://localhost:" + port + "/user?username=pinkunicorn", Void.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, wrongAuthResponse.getStatusCode());

        // Getting existing user with correct credentials should work
        UserResponse existingUser = this.restTemplate.withBasicAuth("admin", "admin")
                .getForObject("http://localhost:" + port + "/user?username=pinkunicorn", UserResponse.class);
        Assertions.assertNotNull(existingUser);
        Assertions.assertEquals("pinkunicorn", existingUser.getUsername());

        // Getting non-existent user with correct credentials should result null
        UserResponse nonExistentUser = this.restTemplate.withBasicAuth("admin", "admin")
                .getForObject("http://localhost:" + port + "/user?username=randomuserthatdoesntexist", UserResponse.class);
        Assertions.assertNull(nonExistentUser);
    }

    @Test
    void createUserWithoutAuth() {
        // Creating a user that doesn't exist should work
        UserData userData = UserData.builder().username("random").password("random").roles(List.of(Role.ROLE_BUYER)).build();
        ResponseEntity<Void> response = this.restTemplate.postForEntity("http://localhost:" + port + "/user", userData, Void.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // Creating a user that already exists should return a BAD_REQUEST
        UserData userThatExists = UserData.builder().username("random").password("random").roles(List.of(Role.ROLE_BUYER)).build();
        ResponseEntity<Void> badResponse = this.restTemplate.postForEntity("http://localhost:" + port + "/user", userThatExists, Void.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, badResponse.getStatusCode());
    }

    @Test
    void editUserAdminAccessOnly() {
        UserData userData = UserData.builder().username("bol").password("randomnewpassword").roles(List.of(Role.ROLE_BUYER)).build();
        ResponseEntity<Void> response = this.restTemplate.withBasicAuth("admin", "admin")
                .exchange("http://localhost:" + port + "/user", HttpMethod.PUT, new HttpEntity<>(userData), Void.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteUserAdminAccessOnly() {
        ResponseEntity<Void> response = this.restTemplate.withBasicAuth("admin", "admin")
                .exchange("http://localhost:" + port + "/user?username=crustytheclown", HttpMethod.DELETE, new HttpEntity<>(null), Void.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}