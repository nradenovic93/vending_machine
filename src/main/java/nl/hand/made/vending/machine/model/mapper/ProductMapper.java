package nl.hand.made.vending.machine.model.mapper;

import nl.hand.made.vending.machine.model.request.ProductCreate;
import nl.hand.made.vending.machine.model.response.ProductResponse;
import nl.hand.made.vending.machine.repository.model.ProductEntity;
import nl.hand.made.vending.machine.repository.model.UserEntity;

public class ProductMapper {

    public static ProductResponse mapToResponseModel(ProductEntity productEntity) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductName(productEntity.getProductName());
        productResponse.setAmountAvailable(productEntity.getAmountAvailable());
        productResponse.setCost(productEntity.getCost());
        productResponse.setSeller(productEntity.getSeller().getUserName());
        return productResponse;
    }

    public static ProductEntity mapToEntityModel(ProductCreate productCreate, UserEntity sellerEntity) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(productCreate.getProductName());
        productEntity.setCost(productCreate.getCost());
        productEntity.setAmountAvailable(productCreate.getAmountAvailable());
        productEntity.setSeller(sellerEntity);
        return productEntity;
    }
}
