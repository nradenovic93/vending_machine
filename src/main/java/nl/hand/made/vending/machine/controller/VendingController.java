package nl.hand.made.vending.machine.controller;

import lombok.RequiredArgsConstructor;
import nl.hand.made.vending.machine.service.VendingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VendingController {

    private final VendingService vendingService;

    @PostMapping("/deposit")
    public void deposit() {

    }

    @PostMapping("/buy")
    public void buy() {

    }

    @PostMapping("/reset")
    public void reset() {

    }
}
