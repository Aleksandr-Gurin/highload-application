package com.example.subscriptionservice.mapper.dto;

import com.example.subscriptionservice.dto.request.UserSubscriptionRequest;
import com.example.subscriptionservice.dto.response.UserSubscriptionResponse;
import com.example.subscriptionservice.dto.update.UserSubscriptionUpdate;
import com.example.subscriptionservice.model.UserSubscription;
import com.example.subscriptionservice.service.SubscriptionService;
import com.example.subscriptionservice.service.feign.clients.UserServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserSubscriptionMapper {

    private final ModelMapper mapper;
    private final UserServiceFeignClient userServiceFeignClient;
    private final SubscriptionService subscriptionService;

    public UserSubscriptionResponse userSubscriptionToUserSubscriptionResponse(UserSubscription userSubscription) {
        return UserSubscriptionResponse.builder()
                .id(userSubscription.getId())
                .user(userServiceFeignClient.findById(userSubscription.getUser()))
                .subscription(subscriptionService.findById(userSubscription.getSubscription()))
                .isValid(userSubscription.isValid())
                .build();
    }

    public UserSubscription userSubscriptionRequestToUserSubscription(UserSubscriptionRequest request) {
        return mapper.map(request, UserSubscription.class);
    }

    public UserSubscription userSubscriptionUpdateToUserSubscription(UserSubscriptionUpdate update) {
        return mapper.map(update, UserSubscription.class);
    }

    public List<UserSubscriptionResponse> userSubscriptionToUserSubscriptionResponseList(List<UserSubscription> userSubscription) {
        return userSubscription
                .stream()
                .map(this::userSubscriptionToUserSubscriptionResponse)
                .collect(Collectors.toList());
    }

    public Page<UserSubscriptionResponse> userSubscriptionTUserSubscriptionResponsePage(Page<UserSubscription> userSubscriptionPage) {
        return userSubscriptionPage
                .map(this::userSubscriptionToUserSubscriptionResponse);
    }

}
