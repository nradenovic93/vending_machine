package nl.hand.made.vending.machine.repository;

import nl.hand.made.vending.machine.repository.model.ProductEntity;
import nl.hand.made.vending.machine.repository.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface ProductRepository extends CrudRepository<ProductEntity, Integer> {

    List<ProductEntity> findAll();

    List<ProductEntity> findAllByAmountAvailableIsGreaterThan(Integer amount);

    Optional<ProductEntity> findByIdAndSeller(Integer id, UserEntity userEntity);
}
