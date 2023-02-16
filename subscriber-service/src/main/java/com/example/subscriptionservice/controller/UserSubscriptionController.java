package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.dto.request.UserSubscriptionRequest;
import com.example.subscriptionservice.dto.response.UserSubscriptionResponse;
import com.example.subscriptionservice.dto.update.UserSubscriptionUpdate;
import com.example.subscriptionservice.service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user-subscription")
public class UserSubscriptionController {

    private final UserSubscriptionService userSubscriptionService;

    @GetMapping
    public Page<UserSubscriptionResponse> getAllUserSubscriptions(
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "must not be less than zero")
            int page,
            @RequestParam(defaultValue = "5")
            @Max(value = 50, message = "must not be more than 50 characters")
            int size,
            @RequestParam UUID userId
    ) {
        return userSubscriptionService.getAllPage(PageRequest.of(page, size), userId);
    }

    @GetMapping("/{id}")
    public UserSubscriptionResponse getUserSubscriptionById(@PathVariable UUID id) {
        return userSubscriptionService.findById(id);
    }

    @PostMapping
    public void addUserSubscription(@Valid @RequestBody UserSubscriptionRequest userSubscriptionRequest) {
        userSubscriptionService.save(userSubscriptionRequest);
    }

    @PutMapping
    public void updateUserSubscription(@Valid @RequestBody UserSubscriptionUpdate userSubscriptionUpdate) {
        userSubscriptionService.update(userSubscriptionUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteUserSubscription(@PathVariable UUID id) {
        userSubscriptionService.deleteById(id);
    }
}
