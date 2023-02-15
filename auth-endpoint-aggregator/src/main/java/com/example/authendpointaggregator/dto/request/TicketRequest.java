package com.example.authendpointaggregator.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {

    private UUID id;
    private Integer price;
    private UUID concertId;
    private UUID userId;
}
