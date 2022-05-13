package nl.hand.made.vending.machine.service;

import nl.hand.made.vending.machine.model.mapper.ProductMapper;
import nl.hand.made.vending.machine.model.request.ProductCreate;
import nl.hand.made.vending.machine.model.request.ProductPatch;
import nl.hand.made.vending.machine.model.response.ProductResponse;
import nl.hand.made.vending.machine.repository.ProductRepository;
import nl.hand.made.vending.machine.repository.UserRepository;
import nl.hand.made.vending.machine.repository.model.ProductEntity;
import nl.hand.made.vending.machine.repository.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ProductResponse> getProducts(Boolean showOnlyAvailable) {
        List<ProductEntity> products;
        if (showOnlyAvailable) {
            products = productRepository.findAllByAmountAvailableIsGreaterThan(0);
        } else {
            products = productRepository.findAll();
        }
        return products.stream()
                .map(ProductMapper::mapToResponseModel)
                .collect(Collectors.toList());
    }

    public ProductResponse getProduct(Integer id) {
        return productRepository.findById(id)
                .map(ProductMapper::mapToResponseModel)
                .orElse(null);
    }

    public Integer createProduct(ProductCreate product) {
        UserEntity userEntity = userRepository.findByUserName(product.getSellerName());

        ProductEntity productEntity = ProductMapper.mapToEntityModel(product, userEntity);
        return productRepository.save(productEntity).getId();
    }

    public void editProduct(Integer id, ProductPatch product) {
    }

    public void deleteProduct(Integer id) {
    }
}
