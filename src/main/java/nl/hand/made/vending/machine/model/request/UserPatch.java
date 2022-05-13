package nl.hand.made.vending.machine.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserPatch {

    private String password;
    private Integer deposit;
    private List<String> roles;
}
