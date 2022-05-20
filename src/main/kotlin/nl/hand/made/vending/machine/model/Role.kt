package nl.hand.made.vending.machine.model

enum class Role {
    ROLE_BUYER, ROLE_SELLER;

    override fun toString() = name

    companion object {
        fun fromString(stringValue: String) =
            values().firstOrNull { role -> role.name.equals(stringValue, ignoreCase = true) }
    }
}
