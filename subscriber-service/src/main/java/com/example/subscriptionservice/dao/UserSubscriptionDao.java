package com.example.subscriptionservice.dao;

import com.example.subscriptionservice.model.UserSubscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface UserSubscriptionDao {

    Optional<UserSubscription> findById(UUID id);
    UserSubscription save(UserSubscription userSubscription);
    UserSubscription update(UserSubscription userSubscription);
    Page<UserSubscription> findAll(Pageable pageable, UUID userId);
    void delete(UUID id);
}
