package com.example.wallet.dto;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.wallet.entity.Transaction;
import com.example.wallet.entity.TransactionType;

public record TransactionDto(Long id, BigDecimal amount, LocalDateTime dateTime, TransactionType type) implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	
	public TransactionDto(Transaction transaction) {
    	this(transaction.getId(), transaction.getAmount(), transaction.getDateTime(), transaction.getType());
	}
}
