package nl.hand.made.vending.machine.model

import java.util.*

enum class Role {
    ROLE_BUYER, ROLE_SELLER;

    override fun toString() = name.lowercase(Locale.getDefault())

    companion object {
        fun fromString(stringValue: String) =
            values().firstOrNull { role -> role.name.equals(stringValue, ignoreCase = true) }
    }
}
