package com.example.authendpointaggregator.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPlaylistUpdate {

    private UUID id;
    private String title;
    private String description;
    private UUID userId;
}
