package nl.hand.made.vending.machine.model.request

import nl.hand.made.vending.machine.model.Role

class UserPatch (
    val username: String,
    val password: String? = null,
    val roles: List<Role>? = null
)