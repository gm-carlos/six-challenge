package com.six.challenge.tradingplatform.repository;

import com.six.challenge.tradingplatform.model.database.BuyOrderDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BuyOrderJpaRepository extends JpaRepository<BuyOrderDao, UUID> {
}