package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.dto.request.UserPlaylistRequest;
import com.example.subscriptionservice.dto.response.UserPlaylistResponse;
import com.example.subscriptionservice.dto.update.UserPlaylistUpdate;
import com.example.subscriptionservice.service.UserPlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user-playlist")
public class UserPlaylistController {

    private final UserPlaylistService userPlaylistService;

    @GetMapping
    public Page<UserPlaylistResponse> getAllUserPlaylists(@RequestParam(defaultValue = "0")
                                                              @Min(value = 0, message = "must not be less than zero")
                                                              int page,
                                                          @RequestParam(defaultValue = "5")
                                                              @Max(value = 50, message = "must not be more than 50 characters")
                                                              int size) {
        return userPlaylistService.getAllPage(PageRequest.of(page, size));
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
