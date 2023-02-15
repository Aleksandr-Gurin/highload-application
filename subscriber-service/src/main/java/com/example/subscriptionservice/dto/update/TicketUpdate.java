package com.example.subscriptionservice.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketUpdate {

    private Long id;

    @Positive(message = "Price must be greater than zero")
    private Integer price;

    @NotNull(message = "Concert is mandatory")
    private UUID concertId;

    private UUID userId;
}
