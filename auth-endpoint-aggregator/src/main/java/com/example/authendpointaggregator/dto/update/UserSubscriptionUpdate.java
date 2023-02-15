package com.example.authendpointaggregator.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSubscriptionUpdate {

    private UUID id;
    private boolean isValid;
    private UUID subscriptionId;
    private UUID hostUserId;
}
