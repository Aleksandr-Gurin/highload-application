package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.dto.request.UserPlaylistRequest;
import com.example.subscriptionservice.dto.response.UserPlaylistResponse;
import com.example.subscriptionservice.dto.update.UserPlaylistUpdate;
import com.example.subscriptionservice.service.UserPlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user-playlist")
public class UserPlaylistController {

    private final UserPlaylistService userPlaylistService;

    @GetMapping
    public Page<UserPlaylistResponse> getAllUserPlaylists(@PageableDefault(size = 5) Pageable pageable) {
        return userPlaylistService.getAllPage(pageable);
    }

    @GetMapping("/{id}")
    public UserPlaylistResponse getUserPlaylistById(@PathVariable UUID id) {
        return userPlaylistService.findById(id);
    }

    @PostMapping
    public void addUserPlaylist(@Valid @RequestBody UserPlaylistRequest userPlaylistRequest) {
        userPlaylistService.save(userPlaylistRequest);
    }

    @PutMapping
    public void updateUserPlaylist(@Valid @RequestBody UserPlaylistUpdate userPlaylistUpdate) {
        userPlaylistService.update(userPlaylistUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteUserPlaylist(@PathVariable UUID id) {
        userPlaylistService.deleteById(id);
    }
}
