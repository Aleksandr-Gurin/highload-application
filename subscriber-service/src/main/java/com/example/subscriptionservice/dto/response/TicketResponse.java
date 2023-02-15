package com.example.subscriptionservice.dto.response;

import com.example.subscriptionservice.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {

    private UUID id;
    private Integer price;
    private ConcertResponse concert;
    private UserResponseDTO user;
}
