package com.six.challenge.tradingplatform.controller.v1;

import com.six.challenge.tradingplatform.model.api.v1.OrderDto;
import com.six.challenge.tradingplatform.model.database.OrderDao;
import com.six.challenge.tradingplatform.repository.BuyOrderJpaRepository;
import com.six.challenge.tradingplatform.repository.SellOrderJpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {
    private final SellOrderJpaRepository sellOrderRepository;
    private final BuyOrderJpaRepository buyOrderRepository;

    OrderController(SellOrderJpaRepository sellOrderRepository,
                       BuyOrderJpaRepository buyOrderJpaRepository) {
        this.sellOrderRepository = sellOrderRepository;
        this.buyOrderRepository = buyOrderJpaRepository;
    }

    @GetMapping("/findAll")
    List<OrderDto> findAll() {
        Stream<OrderDto> sellOrders = sellOrderRepository.findAll().stream().map(OrderDao::toDto);
        Stream<OrderDto> buyOrders = buyOrderRepository.findAll().stream().map(OrderDao::toDto);
        return Stream.concat(buyOrders, sellOrders)
                .collect(Collectors.toList());
    }
}
