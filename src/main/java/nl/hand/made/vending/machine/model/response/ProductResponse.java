package nl.hand.made.vending.machine.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

    private Integer amountAvailable;
    private Integer cost;
    private String productName;
    private String seller;
}
