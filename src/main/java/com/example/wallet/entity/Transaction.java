package com.example.wallet.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "transaction", schema = "public")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction implements Serializable {
	
    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private LocalDateTime dateTime = LocalDateTime.now();
    
    public Transaction(User user, BigDecimal amount, TransactionType transactionType) {
    	this.user = user;
    	this.amount = amount;
    	this.type = transactionType;
    }

}
