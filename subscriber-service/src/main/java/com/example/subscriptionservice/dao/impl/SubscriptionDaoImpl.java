package com.example.subscriptionservice.dao.impl;

import com.example.subscriptionservice.dao.SubscriptionDao;
import com.example.subscriptionservice.model.Subscription;
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
public class SubscriptionDaoImpl implements SubscriptionDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Subscription> rowMapper;

    @Override
    public Optional<Subscription> findById(UUID id) {
        return jdbcTemplate.queryForStream(
                "SELECT * FROM subscriptions where id = :id",
                Collections.singletonMap("id", id),
                rowMapper
        ).findAny();
    }

    @Override
    public Subscription save(Subscription subscription) {
        subscription.setId(UUID.randomUUID());
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", subscription.getId())
                .addValue("name", subscription.getName())
                .addValue("price", subscription.getPrice())
                .addValue("description", subscription.getDescription());

        jdbcTemplate.update(
                "INSERT INTO subscriptions VALUES (:id, :name, :price, :description)",
                namedParameters
        );
        return subscription;
    }

    @Override
    public Subscription update(Subscription subscription) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", subscription.getId())
                .addValue("name", subscription.getName())
                .addValue("price", subscription.getPrice())
                .addValue("description", subscription.getDescription());

        jdbcTemplate.update(
                "UPDATE subscriptions SET name = :name, price = :price, description = :description WHERE id = :id",
                namedParameters
        );
        return subscription;
    }

    @Override
    public Page<Subscription> findAll(Pageable pageable) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM subscriptions",
                new MapSqlParameterSource(),
                Integer.class
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("size", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());

        List<Subscription> subscriptions = jdbcTemplate.query(
                "SELECT * FROM subscriptions LIMIT :size OFFSET :offset",
                namedParameters,
                rowMapper
        );

        return new PageImpl<>(subscriptions, pageable, count);
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(
                "DELETE FROM subscriptions WHERE id = :id",
                Collections.singletonMap("id", id)
        );
    }
}
