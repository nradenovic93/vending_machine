package nl.hand.made.vending.machine.model.response

import nl.hand.made.vending.machine.model.Role

class UserResponse(
    val username: String,
    val deposit: Int,
    val roles: List<Role>
)