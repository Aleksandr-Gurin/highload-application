package com.example.subscriptionservice.service;

import com.example.subscriptionservice.dto.request.UserSubscriptionRequest;
import com.example.subscriptionservice.dto.response.UserSubscriptionResponse;
import com.example.subscriptionservice.dto.update.UserSubscriptionUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserSubscriptionService {
    Page<UserSubscriptionResponse> getAllPage(Pageable pageable, UUID userId);

    UserSubscriptionResponse findById(UUID id);

    void save(UserSubscriptionRequest userSubscriptionRequest);

    void update(UserSubscriptionUpdate userSubscriptionUpdate);

    void deleteById(UUID id);
}
