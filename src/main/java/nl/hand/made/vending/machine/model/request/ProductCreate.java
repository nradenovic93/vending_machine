package nl.hand.made.vending.machine.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreate {

    private Integer amountAvailable;
    private Integer cost;
    private String productName;
    private String sellerName;
}
