package nl.hand.made.vending.machine.model.request

class ProductPatch (
    val amountAvailable: Int? = null,
    val cost: Int? = null,
    val productName: String? = null
)