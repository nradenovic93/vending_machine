package nl.hand.made.vending.machine.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "PRODUCT")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "AMOUNT_AVAILABLE", nullable = false)
    private Integer amountAvailable;

    @Column(name = "COST", nullable = false)
    private Integer cost;

    @Column(name = "PRODUCT_NAME", nullable = false)
    private String productName;

    @ManyToOne
    @JoinColumn(name = "SELLER_NAME", referencedColumnName = "USER_NAME")
    private UserEntity seller;
}
