package nl.hand.made.vending.machine.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nl.hand.made.vending.machine.model.Role;

import java.util.List;

@Builder
@Getter
@Setter
public class UserData {

    private String username;
    private String password;
    private List<Role> roles;
}
