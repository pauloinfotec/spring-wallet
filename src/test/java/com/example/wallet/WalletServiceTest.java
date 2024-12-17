package com.example.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.wallet.dto.TransactionDto;
import com.example.wallet.entity.Transaction;
import com.example.wallet.entity.TransactionType;
import com.example.wallet.entity.User;
import com.example.wallet.exception.BusinessException;
import com.example.wallet.repository.TransactionRepository;
import com.example.wallet.repository.UserRepository;
import com.example.wallet.service.impl.WalletServiceImpl;

public class WalletServiceTest {
	
	@InjectMocks
    private WalletServiceImpl service;

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setup() {
    	MockitoAnnotations.openMocks(this);
        service = new WalletServiceImpl(userRepository, transactionRepository);
    }

    @Test
    void testDepositSuccessful() {
        User user = new User("testuser");
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        service.deposit("testuser", BigDecimal.valueOf(50.0));
        
        verify(userRepository, times(1)).save(user);
    }
    
    @Test
    void testDepositNotGreaterThanZero() {
        User user = new User("testuser");
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        
        var exception  = assertThrows(BusinessException.class, () -> service.deposit("testuser", BigDecimal.valueOf(-50.0)));
        assertEquals("Amount must be greater than zero", exception.getMessage());
    }
    
    @Test
    void testDepositUserNotFound() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        
        var exception  = assertThrows(BusinessException.class, () -> service.deposit("testuser", BigDecimal.valueOf(10.0)));
        assertEquals("User not found", exception.getMessage());
    }
    
    @Test
    void testWithSuccessful() {
        User user = new User("testuser");
        user.setBalance(BigDecimal.valueOf(100.0));
        
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        service.withdraw("testuser", BigDecimal.valueOf(30.0));
        
        verify(userRepository, times(1)).save(user);
    }
    
    @Test
    void testWithdrawNotGreaterThanZero() {
        User user = new User("testuser");
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        
        var exception  = assertThrows(BusinessException.class, () -> service.withdraw("testuser", BigDecimal.valueOf(-50.0)));
        assertEquals("Amount must be greater than zero", exception.getMessage());
    }
    
    @Test
    void testWithdrawUserNotFound() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        
        var exception  = assertThrows(BusinessException.class, () -> service.withdraw("testuser", BigDecimal.valueOf(10.0)));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testWithdrawInsufficientBalance() {
    	User user = new User("testuser");
    	when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
    	
        var exception = assertThrows(BusinessException.class, () -> service.withdraw("testuser", BigDecimal.valueOf(50.0)));
        assertEquals("Insufficient balance", exception.getMessage());
    }
    
    @Test
    void testGetBalanceSuccessful() {
    	User user = new User("testuser");
    	when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertEquals(BigDecimal.valueOf(0), user.getBalance());
    }
    
    @Test
    void testGetBalanceUserNotFound() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        
        var exception  = assertThrows(BusinessException.class, () -> service.getBalance("testuser"));
        assertEquals("User not found", exception.getMessage());
    }
    
    @Test
    void testCreateWalletUsernameNull() {
    	User user = new User(null);
    	
        var exception  = assertThrows(BusinessException.class, () -> service.create(user));
        assertEquals("Username cannot be null", exception.getMessage());
    }
    
    @Test
    void testCreateWalletSameUser() {
    	User user = new User("testuser");
    	when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        
        var exception  = assertThrows(BusinessException.class, () -> service.create(user));
        assertEquals("Username already exists", exception.getMessage());
    }
    
    @Test
    void testCreateWalletUserSuccessful() {
    	User user = new User("testuser");
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        service.create(user);
        
        verify(userRepository, times(1)).save(user);
    }
    
    @Test
    void testGetTransactionsUserNotFound() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        
        var exception  = assertThrows(BusinessException.class, () -> service.getTransactions("testuser"));
        assertEquals("User not found", exception.getMessage());
        
    }

    @Test
    void testGetTransactions() {
    	User user = new User("testuser");
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(transactionRepository.findByUser(any())).thenReturn(List.of(new Transaction(user, BigDecimal.TEN, TransactionType.DEPOSIT)));        
        List<TransactionDto> transactions = service.getTransactions("testuser");
        
        assertEquals(1, transactions.size());
    }
}
