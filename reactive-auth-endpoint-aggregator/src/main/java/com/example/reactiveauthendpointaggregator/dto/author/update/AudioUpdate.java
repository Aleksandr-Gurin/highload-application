package com.example.reactiveauthendpointaggregator.dto.author.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AudioUpdate {

    private UUID id;
    private String title;
    private byte[] audio;
    private UUID authorId;
}
