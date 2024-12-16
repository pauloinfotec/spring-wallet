package com.example.wallet.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "user", schema = "public")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {
	
    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private BigDecimal balance;
    
    public User(String username) {
    	this.username = username;
    	this.balance = BigDecimal.ZERO;
    }

}
