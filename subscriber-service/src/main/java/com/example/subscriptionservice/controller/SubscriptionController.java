package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.dto.request.SubscriptionRequest;
import com.example.subscriptionservice.dto.response.SubscriptionResponse;
import com.example.subscriptionservice.dto.update.SubscriptionUpdate;
import com.example.subscriptionservice.service.SubscriptionService;
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
@RequestMapping(value = "/api/v1/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public Page<SubscriptionResponse> getAllSubscriptions(@RequestParam(defaultValue = "0")
                                                              @Min(value = 0, message = "must not be less than zero")
                                                              int page,
                                                          @RequestParam(defaultValue = "5")
                                                              @Max(value = 50, message = "must not be more than 50 characters")
                                                              int size) {
        return subscriptionService.getAllPage(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public SubscriptionResponse getSubscriptionById(@PathVariable UUID id) {
        return subscriptionService.findById(id);
    }

    @PostMapping
    public void addSubscription(@Valid @RequestBody SubscriptionRequest subscriptionRequest) {
        subscriptionService.save(subscriptionRequest);
    }

    @PutMapping
    public void updateSubscription(@Valid @RequestBody SubscriptionUpdate subscriptionUpdate) {
        subscriptionService.update(subscriptionUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteSubscription(@PathVariable UUID id) {
        subscriptionService.deleteById(id);
    }
}
