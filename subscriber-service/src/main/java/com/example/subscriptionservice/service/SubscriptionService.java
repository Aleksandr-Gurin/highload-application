package com.example.subscriptionservice.service;

import com.example.subscriptionservice.dto.request.SubscriptionRequest;
import com.example.subscriptionservice.dto.response.SubscriptionResponse;
import com.example.subscriptionservice.dto.update.SubscriptionUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface SubscriptionService {
    Page<SubscriptionResponse> getAllPage(Pageable pageable);

    SubscriptionResponse findById(UUID id);

    void save(SubscriptionRequest subscriptionRequest);

    void update(SubscriptionUpdate subscriptionUpdate);

    void deleteById(UUID id);
}
