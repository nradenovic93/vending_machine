package nl.hand.made.vending.machine.controller

import nl.hand.made.vending.machine.model.request.ProductCreate
import nl.hand.made.vending.machine.model.request.ProductPatch
import nl.hand.made.vending.machine.service.ProductService
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@RestController
class ProductController(
    private val productService: ProductService
) {
    /**
     * Get all products
     *
     * @param showOnlyAvailable Whether to only show available products
     * @return All products, only the ones with stock if showOnlyAvailable=true
     */
    @GetMapping(path = ["/products"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getProducts(@RequestParam(required = false) showOnlyAvailable: Boolean) =
        productService.getProducts(showOnlyAvailable)

    /**
     * Get product by id
     *
     * @param id The id of the requested product
     * @return Product matching the provided id
     */
    @GetMapping(path = ["/product/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getProduct(@PathVariable id: Int) = productService.getProduct(id)

    /**
     * Create new product
     *
     * @param product Product data to save
     */
    @PostMapping(path = ["/product"], consumes = ["application/json"])
    fun createProduct(@RequestBody product: ProductCreate, @AuthenticationPrincipal seller: User) =
        productService.createProduct(product, seller)

    /**
     * Edit product data
     *
     * @param id      The id of the product to edit
     * @param product The product data
     */
    @PutMapping(path = ["/product/{id}"], consumes = ["application/json"])
    fun editProduct(
        @PathVariable id: Int,
        @RequestBody product: ProductPatch,
        @AuthenticationPrincipal seller: User
    ) = productService.editProduct(id, product, seller)

    /**
     * Delete product
     *
     * @param id The id of the product to delete
     */
    @DeleteMapping(path = ["/product/{id}"])
    fun deleteProduct(@PathVariable id: Int, @AuthenticationPrincipal seller: User) =
        productService.deleteProduct(id, seller)
}