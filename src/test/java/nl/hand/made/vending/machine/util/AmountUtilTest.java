package nl.hand.made.vending.machine.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

class AmountUtilTest {

    @Test
    void calculateChange() {
        new BCryptPasswordEncoder().encode("randompassword");
        List<Integer> change = AmountUtil.calculateChange(5);
        Assertions.assertFalse(change.isEmpty());
        Assertions.assertEquals(5, change.get(0));

        change = AmountUtil.calculateChange(15);
        Assertions.assertFalse(change.isEmpty());
        Assertions.assertEquals(List.of(10, 5), change);

        change = AmountUtil.calculateChange(135);
        Assertions.assertFalse(change.isEmpty());
        Assertions.assertEquals(List.of(100, 20, 10, 5), change);
    }
}
