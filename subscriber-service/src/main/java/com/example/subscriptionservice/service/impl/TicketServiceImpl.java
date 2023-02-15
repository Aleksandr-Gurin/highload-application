package com.example.subscriptionservice.service.impl;

import com.example.subscriptionservice.dao.TicketDao;
import com.example.subscriptionservice.dto.request.TicketRequest;
import com.example.subscriptionservice.dto.response.TicketResponse;
import com.example.subscriptionservice.dto.update.TicketUpdate;
import com.example.subscriptionservice.exception.ObjectNotFoundException;
import com.example.subscriptionservice.mapper.dto.TicketMapper;
import com.example.subscriptionservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketDao ticketDao;
    private final TicketMapper ticketMapper;

    @Override
    public Page<TicketResponse> getAllPage(Pageable pageable) {
        return ticketMapper.ticketToTicketResponsePage(ticketDao.findAll(pageable));
    }

    @Override
    public TicketResponse findById(UUID id) {
        return ticketMapper
                .ticketToTicketResponse(ticketDao
                        .findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException("Ticket with id " + id + " not found")));
    }

    @Override
    public void save(TicketRequest ticketRequest) {
        ticketDao.save(ticketMapper.ticketRequestToTicket(ticketRequest));
    }

    @Override
    public void update(TicketUpdate ticketUpdate) {
        ticketDao.save(ticketMapper.ticketUpdateToTicket(ticketUpdate));
    }

    @Override
    public void deleteById(UUID id) {
        ticketDao.delete(id);
    }
}
