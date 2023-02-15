package com.example.authendpointaggregator.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketUpdate {

    private Long id;
    private Integer price;
    private UUID concertId;
    private UUID userId;
}
