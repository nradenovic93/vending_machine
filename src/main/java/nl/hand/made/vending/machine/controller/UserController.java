package nl.hand.made.vending.machine.controller;

import lombok.RequiredArgsConstructor;
import nl.hand.made.vending.machine.model.request.UserData;
import nl.hand.made.vending.machine.model.response.UserResponse;
import nl.hand.made.vending.machine.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Get user by username
     *
     * @param username The username of the requested user
     * @return User matching the provided username
     */
    @GetMapping(
            path = "/user",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserResponse getUser(@RequestParam String username) {
        return userService.getUser(username);
    }

    /**
     * Create new user
     *
     * @param user User data to save
     */
    @PostMapping(
            path = "/user",
            consumes = "application/json"
    )
    public void createUser(@RequestBody UserData user) {
        userService.createUser(user);
    }

    /**
     * Edit user data
     *
     * @param user     The user data
     */
    @PutMapping(
            path = "/user",
            consumes = "application/json"
    )
    public void editUser(@RequestBody UserData user) {
        userService.editUser(user);
    }

    /**
     * Delete user
     *
     * @param username The username of the user to delete
     */
    @DeleteMapping(
            path = "/user"
    )
    public void deleteUser(@RequestParam String username) {
        userService.deleteUser(username);
    }
}