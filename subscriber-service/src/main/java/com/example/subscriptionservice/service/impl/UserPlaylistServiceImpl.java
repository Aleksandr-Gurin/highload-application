package com.example.subscriptionservice.service.impl;

import com.example.subscriptionservice.dao.UserPlaylistDao;
import com.example.subscriptionservice.dto.request.UserPlaylistRequest;
import com.example.subscriptionservice.dto.response.UserPlaylistResponse;
import com.example.subscriptionservice.dto.update.UserPlaylistUpdate;
import com.example.subscriptionservice.exception.ObjectNotFoundException;
import com.example.subscriptionservice.mapper.dto.UserPlaylistMapper;
import com.example.subscriptionservice.service.UserPlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPlaylistServiceImpl implements UserPlaylistService {
    private final UserPlaylistDao userPlaylistDao;
    private final UserPlaylistMapper userPlaylistMapper;

    @Override
    public Page<UserPlaylistResponse> getAllPage(Pageable pageable) {
        return userPlaylistMapper.userPlaylistToUserPlaylistResponsePage(userPlaylistDao.findAll(pageable));
    }

    @Override
    public UserPlaylistResponse findById(UUID id) {
        return userPlaylistMapper
                .userPlaylistToUserPlaylistResponse(userPlaylistDao
                        .findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException("UserPlaylist with id " + id + " not found")));
    }

    @Override
    public void save(UserPlaylistRequest userPlaylistRequest) {
        userPlaylistDao.save(userPlaylistMapper.userPlaylistRequestToUserPlaylist(userPlaylistRequest));
    }

    @Override
    public void update(UserPlaylistUpdate userPlaylistUpdate) {
        userPlaylistDao.save(userPlaylistMapper.userPlaylistUpdateToUserPlaylist(userPlaylistUpdate));
    }

    @Override
    public void deleteById(UUID id) {
        userPlaylistDao.delete(id);
    }
}
