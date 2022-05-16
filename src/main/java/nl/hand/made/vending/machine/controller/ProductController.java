package nl.hand.made.vending.machine.controller;

import lombok.RequiredArgsConstructor;
import nl.hand.made.vending.machine.model.request.ProductData;
import nl.hand.made.vending.machine.model.response.ProductResponse;
import nl.hand.made.vending.machine.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Get all products
     *
     * @param showOnlyAvailable Whether to only show available products
     * @return All products, only the ones with stock if showOnlyAvailable=true
     */
    @GetMapping(
            path = "/products",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<ProductResponse> getProducts(@RequestParam(required = false) boolean showOnlyAvailable) {
        return productService.getProducts(showOnlyAvailable);
    }

    /**
     * Get product by id
     *
     * @param id The id of the requested product
     * @return Product matching the provided id
     */
    @GetMapping(
            path = "/product/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ProductResponse getProduct(@PathVariable Integer id) {
        return productService.getProduct(id);
    }

    /**
     * Create new product
     *
     * @param product Product data to save
     */
    @PostMapping(
            path = "/product",
            consumes = "application/json"
    )
    public Integer createProduct(@RequestBody ProductData product, @AuthenticationPrincipal User seller) {
        return productService.createProduct(product, seller);
    }

    /**
     * Edit product data
     *
     * @param id      The id of the product to edit
     * @param product The product data
     */
    @PutMapping(
            path = "/product/{id}",
            consumes = "application/json"
    )
    public void editProduct(@PathVariable Integer id, @RequestBody ProductData product, @AuthenticationPrincipal User seller) {
        productService.editProduct(id, product, seller);
    }

    /**
     * Delete product
     *
     * @param id The id of the product to delete
     */
    @DeleteMapping(
            path = "/product/{id}"
    )
    public void deleteProduct(@PathVariable Integer id, @AuthenticationPrincipal User seller) {
        productService.deleteProduct(id, seller);
    }
}
