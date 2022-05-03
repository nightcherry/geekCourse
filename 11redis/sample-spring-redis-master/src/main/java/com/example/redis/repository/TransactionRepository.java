package com.example.redis.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.redis.model.Transaction;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findByAmount(Long amount);
}
