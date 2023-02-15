package com.example.subscriptionservice.dao;

import com.example.subscriptionservice.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface SubscriptionDao {

    Optional<Subscription> findById(UUID id);
    Subscription save(Subscription subscription);
    Subscription update(Subscription subscription);
    Page<Subscription> findAll(Pageable pageable);
    void delete(UUID id);
}
