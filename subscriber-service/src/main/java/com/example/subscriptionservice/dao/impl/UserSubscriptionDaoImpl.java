package com.example.subscriptionservice.dao.impl;

import com.example.subscriptionservice.dao.UserSubscriptionDao;
import com.example.subscriptionservice.model.UserSubscription;
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
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserSubscriptionDaoImpl implements UserSubscriptionDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<UserSubscription> rowMapper;

    @Override
    public Optional<UserSubscription> findById(UUID id) {
        return jdbcTemplate.queryForStream(
                "SELECT * FROM user_subscriptions where id = :id",
                Collections.singletonMap("id", id),
                rowMapper
        ).findAny();
    }

    @Override
    public UserSubscription save(UserSubscription userSubscription) {
        userSubscription.setId(UUID.randomUUID());
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userSubscription.getId())
                .addValue("isValid", userSubscription.isValid())
                .addValue("subscription", userSubscription.getSubscription())
                .addValue("user", userSubscription.getUser());

        jdbcTemplate.update(
                "INSERT INTO user_subscriptions VALUES (:id, :isValid, :user, :subscription)",
                namedParameters
        );
        return userSubscription;
    }

    @Override
    public UserSubscription update(UserSubscription userSubscription) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userSubscription.getId())
                .addValue("isValid", userSubscription.isValid())
                .addValue("subscription", userSubscription.getSubscription())
                .addValue("user", userSubscription.getUser());

        jdbcTemplate.update(
                "UPDATE user_subscriptions SET is_valid = :isValid, subscription_id = :subscription, host_user_id = :user WHERE id = :id",
                namedParameters
        );
        return userSubscription;
    }

    @Override
    public Page<UserSubscription> findAll(Pageable pageable, UUID userId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM user_subscriptions",
                new MapSqlParameterSource(),
                Integer.class
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("size", pageable.getPageSize())
                .addValue("offset", pageable.getOffset())
                .addValue("userId", userId);

        List<UserSubscription> userSubscriptions;
        if (Objects.nonNull(userId)){
            userSubscriptions = jdbcTemplate.query(
                    "SELECT * FROM user_subscriptions " +
                            "WHERE user_id = :userId LIMIT :size OFFSET :offset",
                    namedParameters,
                    rowMapper);
        } else {
            userSubscriptions = jdbcTemplate.query(
                    "SELECT * FROM user_subscriptions LIMIT :size OFFSET :offset",
                    namedParameters,
                    rowMapper
            );
        }

        return new PageImpl<>(userSubscriptions, pageable, count);
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(
                "DELETE FROM user_subscriptions WHERE id = :id",
                Collections.singletonMap("id", id)
        );
    }
}
