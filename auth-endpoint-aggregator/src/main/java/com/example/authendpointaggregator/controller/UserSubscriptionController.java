package com.example.authendpointaggregator.controller;

import com.example.authendpointaggregator.dto.request.UserSubscriptionRequest;
import com.example.authendpointaggregator.dto.response.UserSubscriptionResponse;
import com.example.authendpointaggregator.dto.update.UserSubscriptionUpdate;
import com.example.authendpointaggregator.feignclient.SubscriberServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user-subscription")
@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
public class UserSubscriptionController {

    private final SubscriberServiceFeignClient subscriberServiceFeignClient;

    @GetMapping
    public Page<UserSubscriptionResponse> getAllUserSubscriptions(
            @PageableDefault(size = 5) Pageable pageable,
            @RequestParam UUID userId
    ) {
        return subscriberServiceFeignClient.getAllUserSubscriptions(pageable, userId);
    }

    @GetMapping("/{id}")
    public UserSubscriptionResponse getUserSubscriptionById(@PathVariable UUID id) {
        return subscriberServiceFeignClient.getUserSubscriptionById(id);
    }

    @PostMapping
    public void addUserSubscription(@RequestBody UserSubscriptionRequest userSubscriptionRequest) {
        subscriberServiceFeignClient.addUserSubscription(userSubscriptionRequest);
    }

    @PutMapping
    public void updateUserSubscription(@RequestBody UserSubscriptionUpdate userSubscriptionUpdate) {
        subscriberServiceFeignClient.updateUserSubscription(userSubscriptionUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteUserSubscription(@PathVariable UUID id) {
        subscriberServiceFeignClient.deleteUserSubscription(id);
    }
}
