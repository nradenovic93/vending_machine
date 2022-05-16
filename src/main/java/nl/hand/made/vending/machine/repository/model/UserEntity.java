package nl.hand.made.vending.machine.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "USERS")
public class UserEntity {

    @NotNull
    @Id
    @Column(name = "USERNAME", nullable = false)
    private String username;

    @NotNull
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @NotNull
    @Column(name = "DEPOSIT", nullable = false)
    private Integer deposit;

    @NotNull
    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "AUTHORITIES", joinColumns = @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME"))
    @Column(name = "AUTHORITY")
    private List<String> roles;
}
