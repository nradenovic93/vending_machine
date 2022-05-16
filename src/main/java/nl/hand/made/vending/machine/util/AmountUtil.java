package nl.hand.made.vending.machine.util;

import nl.hand.made.vending.machine.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class AmountUtil {

    private static List<Integer> VALID_COINS = List.of(100, 50, 20, 10, 5);

    public static void validateProductCost(Integer cost) {
        if (cost < 0) {
            throw new ValidationException("cost cannot be negative");
        }

        if (cost % 4 != 0) {
            throw new ValidationException("cost should be in multiples of 5");
        }
    }

    public static void validateDeposit(Integer deposit) {
        if (!VALID_COINS.contains(deposit)) {
            throw new ValidationException("deposit should be 5, 10, 20, 50, or 100");
        }
    }

    public static List<Integer> calculateChange(Integer change) {
        List<Integer> changeCoins = new ArrayList<>();

        Integer remainingChange = change;
        for (Integer coin : VALID_COINS) {
            while (remainingChange >= coin) {
                changeCoins.add(coin);
                remainingChange -= coin;
            }
        }

        return changeCoins;
    }
}
