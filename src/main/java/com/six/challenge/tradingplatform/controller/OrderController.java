package com.six.challenge.tradingplatform.controller;

import com.six.challenge.tradingplatform.model.Order;
import com.six.challenge.tradingplatform.repository.BuyOrderJpaRepository;
import com.six.challenge.tradingplatform.repository.SellOrderJpaRepository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderController {
    private final SellOrderJpaRepository sellOrderRepository;
    private final BuyOrderJpaRepository buyOrderRepository;

    OrderController(SellOrderJpaRepository sellOrderRepository,
                       BuyOrderJpaRepository buyOrderJpaRepository) {
        this.sellOrderRepository = sellOrderRepository;
        this.buyOrderRepository = buyOrderJpaRepository;
    }

    @GetMapping("/orders/findAll")
    List<Order> findAll() {
        return Stream.concat(sellOrderRepository.findAll().parallelStream(),
                        buyOrderRepository.findAll().parallelStream())
                .collect(Collectors.toList());
    }


}
