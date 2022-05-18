package nl.hand.made.vending.machine.controller

import nl.hand.made.vending.machine.model.request.UserCreate
import nl.hand.made.vending.machine.model.request.UserPatch
import nl.hand.made.vending.machine.service.UserService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService
) {

    /**
     * Get user by username
     *
     * @param username The username of the requested user
     * @return User matching the provided username
     */
    @GetMapping(path = ["/user"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUser(@RequestParam username: String) = userService.getUser(username)

    /**
     * Create new user
     *
     * @param user User data to save
     */
    @PostMapping(path = ["/user"], consumes = ["application/json"])
    fun createUser(@RequestBody user: UserCreate) = userService.createUser(user)

    /**
     * Edit user data
     *
     * @param user The user data
     */
    @PutMapping(path = ["/user"], consumes = ["application/json"])
    fun editUser(@RequestBody user: UserPatch) = userService.editUser(user)

    /**
     * Delete user
     *
     * @param username The username of the user to delete
     */
    @DeleteMapping(path = ["/user"])
    fun deleteUser(@RequestParam username: String) = userService.deleteUser(username)
}