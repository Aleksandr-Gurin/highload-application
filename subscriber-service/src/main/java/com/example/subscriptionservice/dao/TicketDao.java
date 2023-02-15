package com.example.subscriptionservice.dao;

import com.example.subscriptionservice.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TicketDao {

    Optional<Ticket> findById(UUID id);
    Ticket save(Ticket ticket);
    Ticket update(Ticket ticket);
    Page<Ticket> findAll(Pageable pageable);
    void delete(UUID id);
}
