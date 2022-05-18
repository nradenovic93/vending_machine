package nl.hand.made.vending.machine.service

import nl.hand.made.vending.machine.exception.ValidationException
import nl.hand.made.vending.machine.model.mapper.ProductMapper
import nl.hand.made.vending.machine.model.request.ProductCreate
import nl.hand.made.vending.machine.model.request.ProductPatch
import nl.hand.made.vending.machine.model.response.ProductResponse
import nl.hand.made.vending.machine.repository.ProductRepository
import nl.hand.made.vending.machine.repository.UserRepository
import nl.hand.made.vending.machine.repository.model.UserEntity
import nl.hand.made.vending.machine.util.AmountUtil
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
) {

    fun getProducts(showOnlyAvailable: Boolean) =
        if (showOnlyAvailable) productRepository.findAllByAmountAvailableIsGreaterThan(0) else productRepository.findAll()
            .map { list -> ProductMapper.mapToResponseModel(list) }

    fun getProduct(id: Int): ProductResponse? =
        productRepository.findById(id)
            .map(ProductMapper::mapToResponseModel)
            .orElse(null)

    fun createProduct(product: ProductCreate, seller: UserDetails): Int {
        validate(product)
        val userEntity: UserEntity = userRepository.findByUsername(seller.username)
                ?: throw ValidationException("seller not found")
        return ProductMapper.mapToEntityModel(product, userEntity).let {
            productRepository.save(it).id!!
        }
    }

    fun editProduct(id: Int, product: ProductPatch, user: UserDetails) {
        val userEntity: UserEntity = userRepository.findByUsername(user.username)
            ?: throw ValidationException("seller not found")
        productRepository.findByIdAndSeller(id, userEntity)
            ?.let { entity ->
                ProductMapper.patch(entity, product).run { productRepository.save(this) }
            } ?: throw ValidationException("product with product id and seller not found")
    }

    fun deleteProduct(id: Int, user: UserDetails) {
        val userEntity: UserEntity = userRepository.findByUsername(user.username)
            ?: throw ValidationException("seller not found")
        productRepository.findByIdAndSeller(id, userEntity)
            ?: throw ValidationException("product with product id and seller not found")
        productRepository.deleteById(id)
    }

    private fun validate(product: ProductCreate) {
        val validationErrors = mutableListOf<String>()

        runCatching { AmountUtil.validateProductCost(product.cost) }.onFailure { ex -> validationErrors.add(ex.message!!) }
        product.productName.isNotBlank() || validationErrors.add("product name cannot be empty")
        product.amountAvailable >= 0 || validationErrors.add("amount available cannot be negative")

        validationErrors.isEmpty() || throw ValidationException(validationErrors.joinToString(", "))
    }
}