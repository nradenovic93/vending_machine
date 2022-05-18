package nl.hand.made.vending.machine.repository.model

import javax.persistence.*

@Entity
@Table(name = "PRODUCT")
class ProductEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int? = null,
    var amountAvailable: Int,
    var cost: Int,
    var productName: String,

    @ManyToOne @JoinColumn(name = "SELLER_NAME", referencedColumnName = "USERNAME")
    var seller: UserEntity,
)
