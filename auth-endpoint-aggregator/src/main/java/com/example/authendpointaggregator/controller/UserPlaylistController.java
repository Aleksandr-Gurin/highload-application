package com.example.authendpointaggregator.controller;

import com.example.authendpointaggregator.dto.request.UserPlaylistRequest;
import com.example.authendpointaggregator.dto.response.UserPlaylistResponse;
import com.example.authendpointaggregator.dto.update.UserPlaylistUpdate;
import com.example.authendpointaggregator.feignclient.SubscriberServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user-playlist")
@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
public class UserPlaylistController {

    private final SubscriberServiceFeignClient subscriberServiceFeignClient;

    @GetMapping
    public Page<UserPlaylistResponse> getAllUserPlaylists(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int size) {
        return subscriberServiceFeignClient.getAllUserPlaylists(page, size);
    }

    @GetMapping("/{id}")
    public UserPlaylistResponse getUserPlaylistById(@PathVariable UUID id) {
        return subscriberServiceFeignClient.getUserPlaylistById(id);
    }

    @PostMapping
    public void addUserPlaylist(@RequestBody UserPlaylistRequest userPlaylistRequest) {
        subscriberServiceFeignClient.addUserPlaylist(userPlaylistRequest);
    }

    @PutMapping
    public void updateUserPlaylist(@RequestBody UserPlaylistUpdate userPlaylistUpdate) {
        subscriberServiceFeignClient.updateUserPlaylist(userPlaylistUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteUserPlaylist(@PathVariable UUID id) {
        subscriberServiceFeignClient.deleteUserPlaylist(id);
    }
}
