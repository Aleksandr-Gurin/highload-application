package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.dto.request.TicketRequest;
import com.example.subscriptionservice.dto.response.TicketResponse;
import com.example.subscriptionservice.dto.update.TicketUpdate;
import com.example.subscriptionservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/ticket")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public Page<TicketResponse> getAllTickets(@PageableDefault(size = 5) Pageable pageable) {
        return ticketService.getAllPage(pageable);
    }

    @GetMapping("/{id}")
    public TicketResponse getTicketById(@PathVariable UUID id) {
        return ticketService.findById(id);
    }

    @PostMapping
    public void addTicket(@Valid @RequestBody TicketRequest ticketRequest) {
        ticketService.save(ticketRequest);
    }

    @PutMapping
    public void updateTicket(@Valid @RequestBody TicketUpdate ticketUpdate) {
        ticketService.update(ticketUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable UUID id) {
        ticketService.deleteById(id);
    }
}
