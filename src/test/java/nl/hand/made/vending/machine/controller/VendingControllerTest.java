package nl.hand.made.vending.machine.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VendingControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    void depositBuyerAccessOnly() {
        // Try depositing for a BUYER role
        ResponseEntity<Void> response = this.restTemplate.withBasicAuth("pinkunicorn", "unicornmeat")
                .postForEntity("http://localhost:" + port + "/deposit", 20, Void.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // Won't work for ADMIN role
        ResponseEntity<Void> unauthorizedResponse = this.restTemplate.withBasicAuth("admin", "admin")
                .postForEntity("http://localhost:" + port + "/deposit", 5, Void.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, unauthorizedResponse.getStatusCode());
    }

    @Test
    void buyProduct() {
        // Try buying with no deposit
        ResponseEntity<String> response = this.restTemplate.withBasicAuth("bluebear", "dancingbear")
                .postForEntity("http://localhost:" + port + "/buy", 1, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Try buying with deposit, correct change
        ResponseEntity<Integer[]> succesResponse = this.restTemplate.withBasicAuth("orangeorange", "fruitfly")
                .postForEntity("http://localhost:" + port + "/buy", 1, Integer[].class);
        Assertions.assertEquals(HttpStatus.OK, succesResponse.getStatusCode());
        Assertions.assertNotNull(succesResponse.getBody());
        Assertions.assertEquals(1, succesResponse.getBody().length);
        Assertions.assertEquals(5, succesResponse.getBody()[0]);
    }

    @Test
    void resetDeposit() {
        // Try resetting with no deposit
        ResponseEntity<Integer[]> noChangeResponse = this.restTemplate.withBasicAuth("bluebear", "dancingbear")
                .postForEntity("http://localhost:" + port + "/reset", null, Integer[].class);
        Assertions.assertEquals(HttpStatus.OK, noChangeResponse.getStatusCode());
        Assertions.assertNotNull(noChangeResponse.getBody());
        Assertions.assertEquals(0, noChangeResponse.getBody().length);

        // Try resetting with some deposit
        ResponseEntity<Integer[]> changeResponse = this.restTemplate.withBasicAuth("rosie", "purple")
                .postForEntity("http://localhost:" + port + "/reset", null, Integer[].class);
        Assertions.assertEquals(HttpStatus.OK, changeResponse.getStatusCode());
        Assertions.assertNotNull(changeResponse.getBody());
        Assertions.assertEquals(1, changeResponse.getBody().length);
        Assertions.assertEquals(20, changeResponse.getBody()[0]);
    }
}
