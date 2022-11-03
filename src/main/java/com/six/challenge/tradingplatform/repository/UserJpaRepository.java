package com.six.challenge.tradingplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.six.challenge.tradingplatform.model.database.UserDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserDao, UUID> {

    @Query(
            value = "SELECT * FROM TRADING_USERS u WHERE u.name = :name",
            nativeQuery = true
    )
    Optional<UserDao> findByName(@Param("name") String userName);
}
