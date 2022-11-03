package com.six.challenge.tradingplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.six.challenge.tradingplatform.model.User;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<User, UUID> {
}
