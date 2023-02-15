package com.example.authendpointaggregator.controller;

import com.example.authendpointaggregator.dto.request.TicketRequest;
import com.example.authendpointaggregator.dto.response.TicketResponse;
import com.example.authendpointaggregator.dto.update.TicketUpdate;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/ticket")
public class TicketController {

    private final SubscriberServiceFeignClient subscriberServiceFeignClient;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<TicketResponse> getAllTickets(@PageableDefault(size = 5) Pageable pageable) {
        return subscriberServiceFeignClient.getAllTickets(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public TicketResponse getTicketById(@PathVariable UUID id) {
        return subscriberServiceFeignClient.getTicketById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addTicket(@RequestBody TicketRequest ticketRequest) {
        subscriberServiceFeignClient.addTicket(ticketRequest);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void updateTicket(@RequestBody TicketUpdate ticketUpdate) {
        subscriberServiceFeignClient.updateTicket(ticketUpdate);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteTicket(@PathVariable UUID id) {
        subscriberServiceFeignClient.deleteTicket(id);
    }
}
