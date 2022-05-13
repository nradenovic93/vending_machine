package nl.hand.made.vending.machine.repository;

import nl.hand.made.vending.machine.repository.model.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Integer> {
}
