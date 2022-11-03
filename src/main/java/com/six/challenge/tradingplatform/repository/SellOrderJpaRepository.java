package com.six.challenge.tradingplatform.repository;

import com.six.challenge.tradingplatform.model.database.SellOrderDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SellOrderJpaRepository extends JpaRepository<SellOrderDao, UUID> {
}