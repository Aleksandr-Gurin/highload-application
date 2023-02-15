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
public class UserPlaylistResponse {

    private UUID id;
    private String title;
    private String description;
    private UserResponseDTO user;
}
