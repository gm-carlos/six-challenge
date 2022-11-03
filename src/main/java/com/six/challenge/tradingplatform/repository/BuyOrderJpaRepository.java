package com.six.challenge.tradingplatform.repository;

import com.six.challenge.tradingplatform.model.BuyOrder;
import com.six.challenge.tradingplatform.model.Security;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BuyOrderJpaRepository extends JpaRepository<BuyOrder, UUID> {
}