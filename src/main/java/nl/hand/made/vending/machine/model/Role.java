package nl.hand.made.vending.machine.model;

public enum Role {

    BUYER,
    SELLER,
    ADMIN;

    public static Role fromString(String stringValue) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(stringValue)) {
                return role;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
