package com.example.wallet.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.wallet.dto.BalanceDto;
import com.example.wallet.dto.TransactionDto;
import com.example.wallet.entity.Transaction;
import com.example.wallet.entity.TransactionType;
import com.example.wallet.entity.User;
import com.example.wallet.exception.BusinessException;
import com.example.wallet.repository.TransactionRepository;
import com.example.wallet.repository.UserRepository;
import com.example.wallet.service.WalletService;

@Service
public class WalletServiceImpl implements WalletService {
	
    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;
    
    public WalletServiceImpl(UserRepository userRepository, TransactionRepository transactionRepository) {
    	this.userRepository = userRepository;
    	this.transactionRepository = transactionRepository;
    }

    @Transactional
    @Override
    public void deposit(String username, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new BusinessException("Amount must be greater than zero");
        
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BusinessException("User not found"));
        
        user.setBalance(user.getBalance().add(amount));
        userRepository.save(user);
        transactionRepository.save(new Transaction(user, amount, TransactionType.DEPOSIT));
    }

    @Transactional
    @Override
    public void withdraw(String username, BigDecimal amount) {
    	if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new BusinessException("Amount must be greater than zero");
        
    	User user = userRepository.findByUsername(username).orElseThrow(() -> new BusinessException("User not found"));
        
    	if (user.getBalance().compareTo(amount) < 0) throw new BusinessException("Insufficient balance");
        
    	user.setBalance(user.getBalance().subtract(amount));
        userRepository.save(user);
        transactionRepository.save(new Transaction(user, amount, TransactionType.WITHDRAW));
    }

    @Override
    public List<TransactionDto> getTransactions(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new BusinessException("User not found"));
        
        return transactionRepository.findByUser(user).stream().map(TransactionDto::new).toList();
    }
    
    @Override
    public void create(String username) {
    	String newUsername = Optional.ofNullable(username)
    			.orElseThrow(() -> new BusinessException("Username cannot be null"));
    	
    	userRepository.findByUsername(newUsername)
    			.ifPresent(s -> { throw new BusinessException("Username already exists"); });
    	
    	userRepository.save(new User(newUsername));
    }
    
    @Override
    public BalanceDto getBalance(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new BusinessException("User not found"));
        
        return new BalanceDto(user.getBalance());
    }
}
