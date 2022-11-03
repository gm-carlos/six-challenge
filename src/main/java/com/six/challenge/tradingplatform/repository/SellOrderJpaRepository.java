package com.six.challenge.tradingplatform.repository;

import com.six.challenge.tradingplatform.model.Security;
import com.six.challenge.tradingplatform.model.SellOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SellOrderJpaRepository extends JpaRepository<SellOrder, UUID> {
}