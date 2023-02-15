package com.example.authendpointaggregator.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSubscriptionRequest {

    private Long id;
    private boolean isValid;
    private Long subscriptionId;
    private Long hostUserId;
}
