package com.example.subscriptionservice.service;

import com.example.subscriptionservice.dto.request.UserPlaylistRequest;
import com.example.subscriptionservice.dto.response.UserPlaylistResponse;
import com.example.subscriptionservice.dto.update.UserPlaylistUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserPlaylistService {
    Page<UserPlaylistResponse> getAllPage(Pageable pageable);

    UserPlaylistResponse findById(UUID id);

    void save(UserPlaylistRequest userPlaylistRequest);

    void update(UserPlaylistUpdate userPlaylistUpdate);

    void deleteById(UUID id);
}
