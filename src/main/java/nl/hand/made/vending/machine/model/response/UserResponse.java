package nl.hand.made.vending.machine.model.response;

import lombok.Getter;
import lombok.Setter;
import nl.hand.made.vending.machine.model.Role;

import java.util.List;

@Getter
@Setter
public class UserResponse {

    private String username;
    private Integer deposit;
    private List<Role> roles;
}
