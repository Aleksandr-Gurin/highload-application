package com.example.subscriptionservice.dao.impl;

import com.example.subscriptionservice.dao.UserPlaylistDao;
import com.example.subscriptionservice.model.UserPlaylist;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserPlaylistDaoImpl implements UserPlaylistDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<UserPlaylist> rowMapper;

    @Override
    public Optional<UserPlaylist> findById(UUID id) {
        return jdbcTemplate.queryForStream(
                "SELECT * FROM user_playlists where id = :id",
                Collections.singletonMap("id", id),
                rowMapper
        ).findAny();
    }

    @Override
    public UserPlaylist save(UserPlaylist userPlaylist) {
        userPlaylist.setId(UUID.randomUUID());
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userPlaylist.getId())
                .addValue("title", userPlaylist.getTitle())
                .addValue("user", userPlaylist.getUser())
                .addValue("description", userPlaylist.getDescription());

        jdbcTemplate.update(
                "INSERT INTO user_playlists VALUES (:id, :title, :description, :user)",
                namedParameters
        );
        return userPlaylist;
    }

    @Override
    public UserPlaylist update(UserPlaylist userPlaylist) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userPlaylist.getId())
                .addValue("title", userPlaylist.getTitle())
                .addValue("user", userPlaylist.getUser())
                .addValue("description", userPlaylist.getDescription());

        jdbcTemplate.update(
                "UPDATE user_playlists SET title = :title, user_id = :user, description = :description WHERE id = :id",
                namedParameters
        );
        return userPlaylist;
    }

    @Override
    public Page<UserPlaylist> findAll(Pageable pageable) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM user_playlists",
                new MapSqlParameterSource(),
                Integer.class
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("size", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());

        List<UserPlaylist> userPlaylists = jdbcTemplate.query(
                "SELECT * FROM user_playlists LIMIT :size OFFSET :offset",
                namedParameters,
                rowMapper
        );

        return new PageImpl<>(userPlaylists, pageable, count);
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(
                "DELETE FROM user_playlists WHERE id = :id",
                Collections.singletonMap("id", id)
        );
    }
}
