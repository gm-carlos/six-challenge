package com.six.challenge.tradingplatform.repository;

import com.six.challenge.tradingplatform.model.database.TradeDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TradeJpaRepository extends JpaRepository<TradeDao, UUID> {
}