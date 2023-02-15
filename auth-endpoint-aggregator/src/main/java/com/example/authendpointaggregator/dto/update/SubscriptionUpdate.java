package com.example.authendpointaggregator.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionUpdate {

    private UUID id;
    private String name;
    private Integer price;
    private String description;
}
