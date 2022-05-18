package nl.hand.made.vending.machine.model.response

class ProductResponse(
    val amountAvailable: Int,
    val cost: Int,
    val productName: String,
    val seller: String
)