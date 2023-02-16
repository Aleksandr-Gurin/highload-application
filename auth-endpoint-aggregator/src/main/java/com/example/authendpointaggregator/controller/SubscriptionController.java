package com.example.authendpointaggregator.controller;

import com.example.authendpointaggregator.dto.request.SubscriptionRequest;
import com.example.authendpointaggregator.dto.response.SubscriptionResponse;
import com.example.authendpointaggregator.dto.update.SubscriptionUpdate;
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
@RequestMapping(value = "/api/v1/subscription")
public class SubscriptionController {

    private final SubscriberServiceFeignClient subscriberServiceFeignClient;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<SubscriptionResponse> getAllSubscriptions(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int size) {
        return subscriberServiceFeignClient.getAllSubscriptions(page, size);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public SubscriptionResponse getSubscriptionById(@PathVariable UUID id) {
        return subscriberServiceFeignClient.getSubscriptionById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addSubscription(@RequestBody SubscriptionRequest subscriptionRequest) {
        subscriberServiceFeignClient.addSubscription(subscriptionRequest);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void updateSubscription(@RequestBody SubscriptionUpdate subscriptionUpdate) {
        subscriberServiceFeignClient.updateSubscription(subscriptionUpdate);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteSubscription(@PathVariable UUID id) {
        subscriberServiceFeignClient.deleteSubscription(id);
    }
}
