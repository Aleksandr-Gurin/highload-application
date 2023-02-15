package com.example.subscriptionservice.mapper.dto;

import com.example.subscriptionservice.dto.request.UserPlaylistRequest;
import com.example.subscriptionservice.dto.response.UserPlaylistResponse;
import com.example.subscriptionservice.dto.update.UserPlaylistUpdate;
import com.example.subscriptionservice.model.UserPlaylist;
import com.example.subscriptionservice.service.feign.clients.UserServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPlaylistMapper {

    private final ModelMapper mapper;
    private final UserServiceFeignClient userServiceFeignClient;

    public UserPlaylistResponse userPlaylistToUserPlaylistResponse(UserPlaylist userPlaylist) {
        return UserPlaylistResponse.builder()
                .id(userPlaylist.getId())
                .title(userPlaylist.getTitle())
                .description(userPlaylist.getDescription())
                .user(userServiceFeignClient.findById(userPlaylist.getUser()))
                .build();
    }

    public UserPlaylist userPlaylistRequestToUserPlaylist(UserPlaylistRequest request) {
        return mapper.map(request, UserPlaylist.class);
    }

    public UserPlaylist userPlaylistUpdateToUserPlaylist(UserPlaylistUpdate update) {
        return mapper.map(update, UserPlaylist.class);
    }

    public Page<UserPlaylistResponse> userPlaylistToUserPlaylistResponsePage(Page<UserPlaylist> userPlaylistPage) {
        return userPlaylistPage
                .map(this::userPlaylistToUserPlaylistResponse);
    }
}
