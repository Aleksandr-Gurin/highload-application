package com.example.subscriptionservice.mapper.dto;

import com.example.subscriptionservice.dto.request.TicketRequest;
import com.example.subscriptionservice.dto.response.TicketResponse;
import com.example.subscriptionservice.dto.update.TicketUpdate;
import com.example.subscriptionservice.model.Ticket;
import com.example.subscriptionservice.service.feign.clients.AuthorServiceFeignClient;
import com.example.subscriptionservice.service.feign.clients.UserServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketMapper {

    private final ModelMapper mapper;
    private final UserServiceFeignClient userServiceFeignClient;
    private final AuthorServiceFeignClient authorServiceFeignClient;

    public TicketResponse ticketToTicketResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .user(userServiceFeignClient.findById(ticket.getUser()))
                .concert(authorServiceFeignClient.findConcertById(ticket.getConcert()))
                .price(ticket.getPrice())
                .build();
    }

    public Ticket ticketRequestToTicket(TicketRequest request) {
        return mapper.map(request, Ticket.class);
    }

    public Ticket ticketUpdateToTicket(TicketUpdate update) {
        return mapper.map(update, Ticket.class);
    }

    public Page<TicketResponse> ticketToTicketResponsePage(Page<Ticket> ticketPage) {
        return ticketPage
                .map(this::ticketToTicketResponse);
    }
}
