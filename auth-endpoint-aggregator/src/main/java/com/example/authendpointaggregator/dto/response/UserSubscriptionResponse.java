package com.example.authendpointaggregator.dto.response;

import com.example.authendpointaggregator.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSubscriptionResponse {

    private UUID id;
    private boolean isValid;
    private SubscriptionResponse subscription;
    private UserResponseDTO user;
}
