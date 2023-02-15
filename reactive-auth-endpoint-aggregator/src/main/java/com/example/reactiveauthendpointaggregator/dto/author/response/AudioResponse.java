package com.example.reactiveauthendpointaggregator.dto.author.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AudioResponse {

    private UUID id;
    private String title;
    private byte[] audio;
    private UUID author;
}
