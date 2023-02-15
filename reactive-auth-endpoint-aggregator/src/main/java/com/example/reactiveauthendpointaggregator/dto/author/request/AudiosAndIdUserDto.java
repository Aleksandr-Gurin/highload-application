package com.example.reactiveauthendpointaggregator.dto.author.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AudiosAndIdUserDto {

    private UUID userId;
    private List<UUID> audiosId;
}
