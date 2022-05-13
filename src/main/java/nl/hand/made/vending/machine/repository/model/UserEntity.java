package nl.hand.made.vending.machine.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "USER")
public class UserEntity {

    @Id
    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "DEPOSIT", nullable = false)
    private Integer deposit;

    @ElementCollection
    @CollectionTable(name = "ROLES", joinColumns = @JoinColumn(name = "USER_NAME", referencedColumnName = "USER_NAME"))
    @Column(name = "ROLE")
    private List<String> roles;
}
