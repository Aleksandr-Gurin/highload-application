package com.example.subscriptionservice.dao.impl;

import com.example.subscriptionservice.dao.TicketDao;
import com.example.subscriptionservice.model.Ticket;
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
public class TicketDaoImpl implements TicketDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Ticket> rowMapper;

    @Override
    public Optional<Ticket> findById(UUID id) {
        return jdbcTemplate.queryForStream(
                "SELECT * FROM tickets where id = :id",
                Collections.singletonMap("id", id),
                rowMapper
        ).findAny();
    }

    @Override
    public Ticket save(Ticket ticket) {
        ticket.setId(UUID.randomUUID());
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", ticket.getId())
                .addValue("concert", ticket.getConcert())
                .addValue("price", ticket.getPrice())
                .addValue("user", ticket.getUser());

        jdbcTemplate.update(
                "INSERT INTO tickets VALUES (:id, :price, :concert, :user)",
                namedParameters
        );
        return ticket;
    }

    @Override
    public Ticket update(Ticket ticket) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", ticket.getId())
                .addValue("concert", ticket.getConcert())
                .addValue("price", ticket.getPrice())
                .addValue("user", ticket.getUser());

        jdbcTemplate.update(
                "UPDATE tickets SET concert_id = :concert, price = :price, user_id = :user WHERE id = :id",
                namedParameters
        );
        return ticket;
    }

    @Override
    public Page<Ticket> findAll(Pageable pageable) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM tickets",
                new MapSqlParameterSource(),
                Integer.class
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("size", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());

        List<Ticket> tickets = jdbcTemplate.query(
                "SELECT * FROM tickets LIMIT :size OFFSET :offset",
                namedParameters,
                rowMapper
        );

        return new PageImpl<>(tickets, pageable, count);
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(
                "DELETE FROM tickets WHERE id = :id",
                Collections.singletonMap("id", id)
        );
    }
}
