package com.example.subscriptionservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequest {

    private UUID id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Positive(message = "Price must be greater than zero")
    private Integer price;

    @NotBlank(message = "Description is mandatory")
    private String description;
}
