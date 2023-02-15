package com.example.subscriptionservice.service.impl;

import com.example.subscriptionservice.dao.UserSubscriptionDao;
import com.example.subscriptionservice.dto.request.UserSubscriptionRequest;
import com.example.subscriptionservice.dto.response.UserSubscriptionResponse;
import com.example.subscriptionservice.dto.update.UserSubscriptionUpdate;
import com.example.subscriptionservice.exception.ObjectNotFoundException;
import com.example.subscriptionservice.mapper.dto.UserSubscriptionMapper;
import com.example.subscriptionservice.service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserSubscriptionServiceImpl implements UserSubscriptionService {
    private final UserSubscriptionDao userSubscriptionDao;
    private final UserSubscriptionMapper userSubscriptionMapper;

    @Override
    public Page<UserSubscriptionResponse> getAllPage(Pageable pageable, UUID userId) {
        return userSubscriptionMapper.userSubscriptionTUserSubscriptionResponsePage(userSubscriptionDao.findAll(pageable, userId));
    }

    @Override
    public UserSubscriptionResponse findById(UUID id) {
        return userSubscriptionMapper
                .userSubscriptionToUserSubscriptionResponse(userSubscriptionDao
                        .findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException("UserSubscription with id " + id + " not found")));
    }

    @Override
    public void save(UserSubscriptionRequest userSubscriptionRequest) {
        userSubscriptionDao.save(userSubscriptionMapper.userSubscriptionRequestToUserSubscription(userSubscriptionRequest));
    }

    @Override
    public void update(UserSubscriptionUpdate userSubscriptionUpdate) {
        userSubscriptionDao.save(userSubscriptionMapper.userSubscriptionUpdateToUserSubscription(userSubscriptionUpdate));
    }

    @Override
    public void deleteById(UUID id) {
        userSubscriptionDao.delete(id);
    }
}
