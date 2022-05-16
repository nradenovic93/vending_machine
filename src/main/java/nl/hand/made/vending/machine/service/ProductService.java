package nl.hand.made.vending.machine.service;

import lombok.RequiredArgsConstructor;
import nl.hand.made.vending.machine.exception.ValidationException;
import nl.hand.made.vending.machine.model.mapper.ProductMapper;
import nl.hand.made.vending.machine.model.request.ProductData;
import nl.hand.made.vending.machine.model.response.ProductResponse;
import nl.hand.made.vending.machine.repository.ProductRepository;
import nl.hand.made.vending.machine.repository.UserRepository;
import nl.hand.made.vending.machine.repository.model.ProductEntity;
import nl.hand.made.vending.machine.repository.model.UserEntity;
import nl.hand.made.vending.machine.util.AmountUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<ProductResponse> getProducts(boolean showOnlyAvailable) {
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

    public Integer createProduct(ProductData product, UserDetails seller) {
        validate(product);

        UserEntity userEntity = userRepository.findByUsername(seller.getUsername())
                .orElseThrow(() -> new ValidationException("seller not found"));

        ProductEntity productEntity = ProductMapper.mapToEntityModel(product, userEntity);
        return productRepository.save(productEntity).getId();
    }

    public void editProduct(Integer id, ProductData product, UserDetails user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ValidationException("seller not found"));

        ProductEntity productEntity = productRepository.findByIdAndSeller(id, userEntity)
                .map(entity -> ProductMapper.patch(entity, product))
                .orElseThrow(() -> new ValidationException("product with product id and seller not found"));

        productRepository.save(productEntity);
    }

    public void deleteProduct(Integer id, UserDetails user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ValidationException("seller not found"));

        productRepository.findByIdAndSeller(id, userEntity)
                .orElseThrow(() -> new ValidationException("product with product id and seller not found"));
        productRepository.deleteById(id);
    }

    private void validate(ProductData product) {
        List<String> validationErrors = new ArrayList<>();

        if (StringUtils.isNotBlank(product.getProductName())) {
            validationErrors.add("product name cannot be empty");
        }

        if (product.getCost() == null || product.getCost() < 0) {
            AmountUtil.validateProductCost(product.getCost());
            validationErrors.add("cost cannot be empty or negative");
        }

        if (product.getAmountAvailable() == null || product.getAmountAvailable() < 0) {
            validationErrors.add("amount available cannot be empty");
        }

        if (!validationErrors.isEmpty()) {
            throw new ValidationException(String.join(", ", validationErrors));
        }
    }
}
