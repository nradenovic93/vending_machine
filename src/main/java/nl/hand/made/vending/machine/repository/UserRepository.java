package nl.hand.made.vending.machine.repository;

import nl.hand.made.vending.machine.repository.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> {

    UserEntity findByUserName(String userName);
}
