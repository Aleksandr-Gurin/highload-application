package com.example.subscriptionservice.mapper.row;

import com.example.subscriptionservice.model.Subscription;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class SubscriptionRowMapper implements RowMapper<Subscription> {

    @Override
    public Subscription mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Subscription.builder()
                .id(resultSet.getObject("id", UUID.class))
                .name(resultSet.getString("name"))
                .price(resultSet.getInt("price"))
                .description(resultSet.getString("description"))
                .build();
    }
}
