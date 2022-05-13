package nl.hand.made.vending.machine.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Transaction {

    private Integer id;
    private String userName;
    private Integer productId;
    private LocalDateTime timestamp;
}
