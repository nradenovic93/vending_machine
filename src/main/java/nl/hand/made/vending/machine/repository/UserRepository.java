package nl.hand.made.vending.machine.repository;

import nl.hand.made.vending.machine.repository.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface UserRepository extends CrudRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);

    void deleteByUsername(String username);
}
