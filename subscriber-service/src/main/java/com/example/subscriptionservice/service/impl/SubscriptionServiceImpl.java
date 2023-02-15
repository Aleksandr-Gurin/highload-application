package com.example.subscriptionservice.service.impl;

import com.example.subscriptionservice.dao.SubscriptionDao;
import com.example.subscriptionservice.dto.request.SubscriptionRequest;
import com.example.subscriptionservice.dto.response.SubscriptionResponse;
import com.example.subscriptionservice.dto.update.SubscriptionUpdate;
import com.example.subscriptionservice.exception.ObjectNotFoundException;
import com.example.subscriptionservice.mapper.dto.SubscriptionMapper;
import com.example.subscriptionservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionDao subscriptionDao;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    public Page<SubscriptionResponse> getAllPage(Pageable pageable) {
        return subscriptionMapper.subscriptionToSubscriptionResponsePage(subscriptionDao.findAll(pageable));
    }

    @Override
    public SubscriptionResponse findById(UUID id) {
        return subscriptionMapper
                .subscriptionToSubscriptionResponse(subscriptionDao
                        .findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException("Subscription with id " + id + " not found")));
    }

    @Override
    public void save(SubscriptionRequest subscriptionRequest) {
        subscriptionDao.save(subscriptionMapper.subscriptionRequestToSubscription(subscriptionRequest));

    }

    @Override
    public void update(SubscriptionUpdate subscriptionUpdate) {
        subscriptionDao.save(subscriptionMapper.subscriptionUpdateToSubscription(subscriptionUpdate));
    }

    @Override
    public void deleteById(UUID id) {
        subscriptionDao.delete(id);
    }
}
