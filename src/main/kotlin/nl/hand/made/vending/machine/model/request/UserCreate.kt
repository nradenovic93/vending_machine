package nl.hand.made.vending.machine.model.request

import nl.hand.made.vending.machine.model.Role

class UserCreate(
    val username: String,
    val password: String,
    val roles: List<Role>
)