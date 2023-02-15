package com.example.subscriptionservice.mapper.row;

import com.example.subscriptionservice.model.UserPlaylist;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class UserPlaylistRowMapper implements RowMapper<UserPlaylist> {

    @Override
    public UserPlaylist mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return UserPlaylist.builder()
                .id(resultSet.getObject("id", UUID.class))
                .title(resultSet.getString("title"))
                .description(resultSet.getString("description"))
                .user(resultSet.getObject("user_id", UUID.class))
                .build();
    }
}
