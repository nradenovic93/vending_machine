package nl.hand.made.vending.machine.model.mapper;

import nl.hand.made.vending.machine.exception.ValidationException;
import nl.hand.made.vending.machine.model.request.ProductData;
import nl.hand.made.vending.machine.model.response.ProductResponse;
import nl.hand.made.vending.machine.repository.model.ProductEntity;
import nl.hand.made.vending.machine.repository.model.UserEntity;
import nl.hand.made.vending.machine.util.AmountUtil;
import org.apache.commons.lang3.StringUtils;

public class ProductMapper {

    public static ProductResponse mapToResponseModel(ProductEntity productEntity) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductName(productEntity.getProductName());
        productResponse.setAmountAvailable(productEntity.getAmountAvailable());
        productResponse.setCost(productEntity.getCost());
        productResponse.setSeller(productEntity.getSeller().getUsername());
        return productResponse;
    }

    public static ProductEntity mapToEntityModel(ProductData productData, UserEntity sellerEntity) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(productData.getProductName());
        productEntity.setCost(productData.getCost());
        productEntity.setAmountAvailable(productData.getAmountAvailable());
        productEntity.setSeller(sellerEntity);
        return productEntity;
    }

    public static ProductEntity patch(ProductEntity productEntity, ProductData productData) {
        if (StringUtils.isNotBlank(productData.getProductName())) {
            productEntity.setProductName(productData.getProductName());
        }

        if (productData.getAmountAvailable() != null) {
            if (productData.getAmountAvailable() < 0) {
                throw new ValidationException("product cost cannot be negative");
            }
            productEntity.setAmountAvailable(productData.getAmountAvailable());
        }

        if (productData.getCost() != null) {
            AmountUtil.validateProductCost(productData.getCost());
            productEntity.setCost(productData.getCost());
        }

        return productEntity;
    }
}
