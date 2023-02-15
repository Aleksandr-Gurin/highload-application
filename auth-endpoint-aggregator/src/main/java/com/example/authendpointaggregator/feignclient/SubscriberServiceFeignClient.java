package com.example.authendpointaggregator.feignclient;

import com.example.authendpointaggregator.dto.request.SubscriptionRequest;
import com.example.authendpointaggregator.dto.request.TicketRequest;
import com.example.authendpointaggregator.dto.request.UserPlaylistRequest;
import com.example.authendpointaggregator.dto.request.UserSubscriptionRequest;
import com.example.authendpointaggregator.dto.response.SubscriptionResponse;
import com.example.authendpointaggregator.dto.response.TicketResponse;
import com.example.authendpointaggregator.dto.response.UserPlaylistResponse;
import com.example.authendpointaggregator.dto.response.UserSubscriptionResponse;
import com.example.authendpointaggregator.dto.update.SubscriptionUpdate;
import com.example.authendpointaggregator.dto.update.TicketUpdate;
import com.example.authendpointaggregator.dto.update.UserPlaylistUpdate;
import com.example.authendpointaggregator.dto.update.UserSubscriptionUpdate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "subscriber-service")
public interface SubscriberServiceFeignClient {

    @GetMapping("/api/v1/ticket")
    Page<SubscriptionResponse> getAllSubscriptions(@PageableDefault(size = 5) Pageable pageable);

    @GetMapping("/api/v1/ticket/{id}")
    SubscriptionResponse getSubscriptionById(@PathVariable UUID id);

    @PostMapping("/api/v1/ticket")
    void addSubscription(@RequestBody SubscriptionRequest subscriptionRequest);

    @PutMapping("/api/v1/ticket")
    void updateSubscription(@RequestBody SubscriptionUpdate subscriptionUpdate);

    @DeleteMapping("/api/v1/ticket/{id}")
    void deleteSubscription(@PathVariable UUID id);

    @GetMapping("/api/v1/ticket")
    Page<TicketResponse> getAllTickets(@PageableDefault(size = 5) Pageable pageable);

    @GetMapping("/api/v1/ticket/{id}")
    TicketResponse getTicketById(@PathVariable UUID id);

    @PostMapping("/api/v1/ticket")
    void addTicket(@RequestBody TicketRequest ticketRequest);

    @PutMapping("/api/v1/ticket")
    void updateTicket(@RequestBody TicketUpdate ticketUpdate);

    @DeleteMapping("/api/v1/ticket/{id}")
    void deleteTicket(@PathVariable UUID id);

    @GetMapping("/api/v1/user-playlist")
    Page<UserPlaylistResponse> getAllUserPlaylists(@PageableDefault(size = 5) Pageable pageable);

    @GetMapping("/api/v1/user-playlist/{id}")
    UserPlaylistResponse getUserPlaylistById(@PathVariable UUID id);

    @PostMapping("/api/v1/user-playlist")
    void addUserPlaylist(@RequestBody UserPlaylistRequest userPlaylistRequest);

    @PutMapping("/api/v1/user-playlist")
    void updateUserPlaylist(@RequestBody UserPlaylistUpdate userPlaylistUpdate);

    @DeleteMapping("/api/v1/user-playlist/{id}")
    void deleteUserPlaylist(@PathVariable UUID id);

    @GetMapping("/api/v1/user-subscription")
    Page<UserSubscriptionResponse> getAllUserSubscriptions(
            @PageableDefault(size = 5) Pageable pageable,
            @RequestParam UUID userId
    );

    @GetMapping("/api/v1/user-subscription/{id}")
    UserSubscriptionResponse getUserSubscriptionById(@PathVariable UUID id);

    @PostMapping("/api/v1/user-subscription")
    void addUserSubscription(@RequestBody UserSubscriptionRequest userSubscriptionRequest);

    @PutMapping("/api/v1/user-subscription")
    void updateUserSubscription(@RequestBody UserSubscriptionUpdate userSubscriptionUpdate);

    @DeleteMapping("/api/v1/user-subscription/{id}")
    void deleteUserSubscription(@PathVariable UUID id);
}
