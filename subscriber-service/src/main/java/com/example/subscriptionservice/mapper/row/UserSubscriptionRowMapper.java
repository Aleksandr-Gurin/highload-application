package com.example.subscriptionservice.mapper.row;

import com.example.subscriptionservice.model.UserSubscription;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class UserSubscriptionRowMapper implements RowMapper<UserSubscription> {

    @Override
    public UserSubscription mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return UserSubscription.builder()
                .id(resultSet.getObject("id", UUID.class))
                .subscription(resultSet.getObject("subscription_id", UUID.class))
                .user(resultSet.getObject("host_user_id", UUID.class))
                .isValid(resultSet.getBoolean("is_valid"))
                .build();
    }
}
