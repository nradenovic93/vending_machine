package nl.hand.made.vending.machine.model.mapper

import nl.hand.made.vending.machine.exception.ValidationException
import nl.hand.made.vending.machine.model.request.ProductCreate
import nl.hand.made.vending.machine.model.request.ProductPatch
import nl.hand.made.vending.machine.model.response.ProductResponse
import nl.hand.made.vending.machine.repository.model.ProductEntity
import nl.hand.made.vending.machine.repository.model.UserEntity
import nl.hand.made.vending.machine.util.AmountUtil

class ProductMapper {

    companion object {
        fun mapToResponseModel(productEntity: ProductEntity) =
            ProductResponse(
                productName = productEntity.productName,
                amountAvailable = productEntity.amountAvailable,
                cost = productEntity.cost,
                seller = productEntity.seller.username
            )

        fun mapToEntityModel(productData: ProductCreate, sellerEntity: UserEntity) =
            ProductEntity(
                productName = productData.productName,
                cost = productData.cost,
                amountAvailable = productData.amountAvailable,
                seller = sellerEntity
            )

        fun patch(productEntity: ProductEntity, productData: ProductPatch): ProductEntity {
            productData.productName?.run {
                this.isNotBlank() || throw ValidationException("product name cannot be empty")
                productEntity.productName = this
            }

            productData.amountAvailable?.run {
                this >= 0 || throw ValidationException("amount available cannot be negative")
                productEntity.amountAvailable = this
            }

            productData.cost?.run {
                AmountUtil.validateProductCost(this)
                productEntity.cost = this
            }
            return productEntity;
        }
    }
}