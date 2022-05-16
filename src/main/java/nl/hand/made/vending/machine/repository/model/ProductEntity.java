package nl.hand.made.vending.machine.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "PRODUCT")
public class ProductEntity {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "AMOUNT_AVAILABLE", nullable = false)
    private Integer amountAvailable;

    @NotNull
    @Column(name = "COST", nullable = false)
    private Integer cost;

    @NotNull
    @Column(name = "PRODUCT_NAME", nullable = false)
    private String productName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "SELLER_NAME", referencedColumnName = "USERNAME")
    private UserEntity seller;
}
