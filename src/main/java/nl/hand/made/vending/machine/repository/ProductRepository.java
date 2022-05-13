package nl.hand.made.vending.machine.repository;

import nl.hand.made.vending.machine.repository.model.ProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<ProductEntity, Integer> {

    List<ProductEntity> findAll();

    List<ProductEntity> findAllByAmountAvailableIsGreaterThan(Integer amount);
}
