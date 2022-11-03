package com.six.challenge.tradingplatform.repository;

import com.six.challenge.tradingplatform.model.database.SecurityDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SecurityJpaRepository extends JpaRepository<SecurityDao, UUID> {
}