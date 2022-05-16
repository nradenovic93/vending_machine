package nl.hand.made.vending.machine.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProductData {

    private Integer amountAvailable;
    private Integer cost;
    private String productName;
}
