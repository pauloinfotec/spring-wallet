package com.example.wallet.service;

import java.math.BigDecimal;
import java.util.List;

import com.example.wallet.dto.BalanceDto;
import com.example.wallet.dto.TransactionDto;

public interface WalletService {

	void deposit(String username, BigDecimal amount);
	
	void withdraw(String username, BigDecimal amount);
	
	List<TransactionDto> getTransactions(String username);
	
	void create(String username);
	
	BalanceDto getBalance(String username);
}
