package com.example.subscriptionservice.mapper.row;

import com.example.subscriptionservice.model.Ticket;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class TicketRowMapper implements RowMapper<Ticket> {

    @Override
    public Ticket mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Ticket.builder()
                .id(resultSet.getObject("id", UUID.class))
                .concert(resultSet.getObject("concert_id", UUID.class))
                .user(resultSet.getObject("user_id", UUID.class))
                .price(resultSet.getInt("price"))
                .build();
    }
}
