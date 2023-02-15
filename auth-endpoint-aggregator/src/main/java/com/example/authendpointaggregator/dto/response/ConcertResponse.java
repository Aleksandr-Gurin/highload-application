package com.example.authendpointaggregator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConcertResponse {

    private UUID id;
    private String name;
    private String performer;
    private LocalDateTime concertDate;
    private UUID city;
}
