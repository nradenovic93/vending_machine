package nl.hand.made.vending.machine.service;

import lombok.RequiredArgsConstructor;
import nl.hand.made.vending.machine.exception.ValidationException;
import nl.hand.made.vending.machine.repository.ProductRepository;
import nl.hand.made.vending.machine.repository.UserRepository;
import nl.hand.made.vending.machine.repository.model.ProductEntity;
import nl.hand.made.vending.machine.repository.model.UserEntity;
import nl.hand.made.vending.machine.util.AmountUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendingService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public void deposit(Integer amount, UserDetails user) {
        AmountUtil.validateDeposit(amount);

        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ValidationException("user with username not found"));
        userEntity.setDeposit(userEntity.getDeposit() + amount);
        userRepository.save(userEntity);
    }

    public List<Integer> purchase(Integer productId, UserDetails user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ValidationException("user with username not found"));
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new ValidationException("product with product id not found"));

        if (productEntity.getAmountAvailable() < 1) {
            throw new ValidationException("product not available");
        }
        if (productEntity.getCost() > userEntity.getDeposit()) {
            throw new ValidationException("user does not have enough deposit to purchase product");
        }

        Integer change = userEntity.getDeposit() - productEntity.getCost();

        userEntity.setDeposit(0);
        userRepository.save(userEntity);

        productEntity.setAmountAvailable(productEntity.getAmountAvailable() - 1);
        productRepository.save(productEntity);
        return AmountUtil.calculateChange(change);
    }

    public List<Integer> resetDeposit(UserDetails user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ValidationException("user with username not found"));

        List<Integer> change = AmountUtil.calculateChange(userEntity.getDeposit());

        userEntity.setDeposit(0);
        userRepository.save(userEntity);

        return change;
    }
}
