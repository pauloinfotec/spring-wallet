package com.example.wallet.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wallet.entity.User;
import com.example.wallet.exception.BusinessException;
import com.example.wallet.service.WalletService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/wallet")
@Tag(name = "Wallet", description = "Wallet API")
public class WalletController {
	
    private final WalletService walletService;
    
    public WalletController(WalletService walletService) {
    	this.walletService = walletService;
    }
    
    @PostMapping("/create")
    @Operation(description = "Allow the creation of wallets for users")
    public ResponseEntity<String> create(@RequestParam(name = "username", required = true) String username) {
        try {
        	walletService.create(new User(username));
        	return ResponseEntity.ok("Create successful");
		} catch (BusinessException ex) {
			return ResponseEntity.unprocessableEntity().body(ex.getMessage());
		}
    }

    @PostMapping("/deposit")
    @Operation(description = "Enable users to deposit money into their wallets")
    public ResponseEntity<String> deposit(
    		@RequestParam(name = "username", required = true) String username,
    		@RequestParam(name = "amount", required = true) BigDecimal amount) {
        try {
        	walletService.deposit(username, amount);
        	return ResponseEntity.ok("Deposit successful");
		} catch (BusinessException ex) {
			return ResponseEntity.unprocessableEntity().body(ex.getMessage());
		}
    }

    @PostMapping("/withdraw")
    @Operation(description = "Enable users to withdraw money from their wallets")
    public ResponseEntity<Object> withdraw(
    		@RequestParam(name = "username", required = true) String username,
    		@RequestParam(name = "amount", required = true) BigDecimal amount) {
        try {
        	walletService.withdraw(username, amount);
        	return ResponseEntity.ok("Withdrawal successful");
		} catch (BusinessException ex) {
			return ResponseEntity.unprocessableEntity().body(ex.getMessage());
		}
    }

    @GetMapping("/transactions")
    @Operation(description = "Retrieve transactions of a specific user's wallet")
    public ResponseEntity<Object> getTransactions(@RequestParam(name = "username", required = true) String username) {
    	try {
    		return ResponseEntity.ok(walletService.getTransactions(username));
		} catch (BusinessException ex) {
			return ResponseEntity.unprocessableEntity().body(ex.getMessage());
		}
    }

    @GetMapping("/balance")
    @Operation(description = "Retrieve the current balance of a user's wallet")
    public ResponseEntity<Object> getBalance(@RequestParam(name = "username", required = true) String username) {
        try {
        	return ResponseEntity.ok(walletService.getBalance(username));
		} catch (BusinessException ex) {
			return ResponseEntity.unprocessableEntity().body(ex.getMessage());
		}
    }
}
