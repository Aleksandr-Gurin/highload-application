package com.example.subscriptionservice.dao;

import com.example.subscriptionservice.model.UserPlaylist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface UserPlaylistDao {

    Optional<UserPlaylist> findById(UUID id);
    UserPlaylist save(UserPlaylist userPlaylist);
    UserPlaylist update(UserPlaylist userPlaylist);
    Page<UserPlaylist> findAll(Pageable pageable);
    void delete(UUID id);
}
