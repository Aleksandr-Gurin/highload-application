package com.example.subscriptionservice.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSubscriptionUpdate {

    private UUID id;

    @NotNull(message = "IsValid is mandatory")
    private boolean isValid;

    @NotNull(message = "Subscription is mandatory")
    private UUID subscriptionId;

    @NotNull(message = "User is mandatory")
    private UUID hostUserId;
}
