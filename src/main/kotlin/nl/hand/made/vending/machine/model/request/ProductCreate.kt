package nl.hand.made.vending.machine.model.request

class ProductCreate (
    val amountAvailable: Int,
    val cost: Int,
    val productName: String
)