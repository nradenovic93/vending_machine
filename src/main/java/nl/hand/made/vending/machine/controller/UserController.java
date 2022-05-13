package nl.hand.made.vending.machine.controller;

import lombok.RequiredArgsConstructor;
import nl.hand.made.vending.machine.model.request.UserCreate;
import nl.hand.made.vending.machine.model.request.UserPatch;
import nl.hand.made.vending.machine.model.response.UserResponse;
import nl.hand.made.vending.machine.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Get user by userName
     *
     * @param userName The userName of the requested user
     * @return User matching the provided userName
     */
    @GetMapping(
            path = "/user",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserResponse getUser(@RequestParam String userName) {
        return userService.getUser(userName);
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
    public void createUser(@RequestBody UserCreate user) {
        userService.createUser(user);
    }

    /**
     * Edit user data
     *
     * @param userName The userName of the user to edit
     * @param user     The user data
     */
    @PatchMapping(
            path = "/user",
            consumes = "application/json"
    )
    public void editUser(@RequestParam String userName, @RequestBody UserPatch user) {
        userService.editUser(userName, user);
    }

    /**
     * Delete user
     *
     * @param userName The userName of the user to delete
     */
    @DeleteMapping(
            path = "/user"
    )
    public void deleteUser(@RequestParam String userName) {
        userService.deleteUser(userName);
    }
}