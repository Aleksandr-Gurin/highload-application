package com.example.reactiveauthendpointaggregator.dto.author.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConcertUpdate {

    private UUID id;
    private String name;
    private String performer;
    private LocalDateTime concertDate;
    private UUID cityId;
}
