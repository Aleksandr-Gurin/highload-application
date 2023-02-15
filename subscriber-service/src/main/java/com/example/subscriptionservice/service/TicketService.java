package com.example.subscriptionservice.service;

import com.example.subscriptionservice.dto.request.TicketRequest;
import com.example.subscriptionservice.dto.response.TicketResponse;
import com.example.subscriptionservice.dto.update.TicketUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TicketService {
    Page<TicketResponse> getAllPage(Pageable pageable);

    TicketResponse findById(UUID id);

    void save(TicketRequest ticketRequest);

    void update(TicketUpdate ticketUpdate);

    void deleteById(UUID id);
}
