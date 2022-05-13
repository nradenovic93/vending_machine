package nl.hand.made.vending.machine.service;

import nl.hand.made.vending.machine.repository.ProductRepository;
import nl.hand.made.vending.machine.repository.TransactionRepository;
import nl.hand.made.vending.machine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendingService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
}
