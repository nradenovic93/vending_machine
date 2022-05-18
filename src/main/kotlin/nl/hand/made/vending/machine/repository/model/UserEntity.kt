package nl.hand.made.vending.machine.repository.model

import javax.persistence.*

@Entity
@Table(name = "USERS")
class UserEntity(
    @Id var username: String,
    var password: String,
    var deposit: Int = 0,

    @ElementCollection @CollectionTable(name = "AUTHORITIES", joinColumns = [JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")])
    @Column(name = "AUTHORITY") var roles: MutableList<String>
)