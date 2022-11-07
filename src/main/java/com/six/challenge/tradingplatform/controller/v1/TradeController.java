package com.six.challenge.tradingplatform.controller.v1;

import com.six.challenge.tradingplatform.constants.Endpoints;
import com.six.challenge.tradingplatform.exceptions.UserNotFoundException;
import com.six.challenge.tradingplatform.model.api.v1.trade.TradeOutputDto;
import com.six.challenge.tradingplatform.model.database.TradeDao;
import com.six.challenge.tradingplatform.repository.TradeJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Endpoints.TRADE_V1)
class TradeController {

    Logger logger = LoggerFactory.getLogger(TradeController.class);
    private final TradeJpaRepository repository;

    TradeController(TradeJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping(Endpoints.FIND_ALL)
    List<TradeOutputDto> findAll() {
        return repository.findAll().stream()
                .map(TradeDao::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(Endpoints.FIND_BY_ID_WITH_PARAM)
    TradeOutputDto findById(@PathVariable UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)).toDto();
    }
}
