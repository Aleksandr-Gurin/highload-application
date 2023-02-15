package com.example.reactiveauthendpointaggregator.dto.author.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponse {

    private UUID id;
    private String name;
    private String description;
}
