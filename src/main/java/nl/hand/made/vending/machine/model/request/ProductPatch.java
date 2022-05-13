package nl.hand.made.vending.machine.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPatch {

    private Integer id;
    private Integer amountAvailable;
    private Integer cost;
    private String productName;
}
