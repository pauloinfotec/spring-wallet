package com.example.wallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wallet.entity.Transaction;
import com.example.wallet.entity.User;

import jakarta.transaction.Transactional;

@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
}
