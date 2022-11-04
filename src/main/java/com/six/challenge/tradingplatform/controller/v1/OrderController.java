package com.six.challenge.tradingplatform.controller.v1;

import com.six.challenge.tradingplatform.constants.Endpoints;
import com.six.challenge.tradingplatform.exceptions.InvalidOrderException;
import com.six.challenge.tradingplatform.exceptions.UserNotFoundException;
import com.six.challenge.tradingplatform.model.api.v1.order.OrderInputDto;
import com.six.challenge.tradingplatform.model.api.v1.order.OrderOutputDto;
import com.six.challenge.tradingplatform.model.database.BuyOrderDao;
import com.six.challenge.tradingplatform.model.database.OrderDao;
import com.six.challenge.tradingplatform.model.database.OrderType;
import com.six.challenge.tradingplatform.model.database.SellOrderDao;
import com.six.challenge.tradingplatform.repository.BuyOrderJpaRepository;
import com.six.challenge.tradingplatform.repository.SellOrderJpaRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(Endpoints.ORDER_V1)
public class OrderController {
    private final SellOrderJpaRepository sellOrderRepository;
    private final BuyOrderJpaRepository buyOrderRepository;

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();

    OrderController(SellOrderJpaRepository sellOrderRepository,
                       BuyOrderJpaRepository buyOrderJpaRepository) {
        this.sellOrderRepository = sellOrderRepository;
        this.buyOrderRepository = buyOrderJpaRepository;
    }

    @GetMapping(Endpoints.FIND_ALL)
    List<OrderOutputDto> findAll() {
        Stream<OrderOutputDto> sellOrders = sellOrderRepository.findAll().stream().map(OrderDao::toDto);
        Stream<OrderOutputDto> buyOrders = buyOrderRepository.findAll().stream().map(OrderDao::toDto);
        return Stream.concat(buyOrders, sellOrders)
                .collect(Collectors.toList());
    }

    @GetMapping(Endpoints.FIND_ALL_BUY_ORDER)
    List<OrderOutputDto> findAllBuyOrders() {
        return buyOrderRepository.findAll().stream()
                .map(OrderDao::toDto).collect(Collectors.toList());
    }

    @GetMapping(Endpoints.FIND_ALL_SELL_ORDER)
    List<OrderOutputDto> findAllSellOrders() {
        return sellOrderRepository.findAll().stream()
                .map(OrderDao::toDto).collect(Collectors.toList());
    }

    @GetMapping(Endpoints.FIND_BUY_ORDER_BY_ID)
    OrderOutputDto findBuyOrderById(@PathVariable UUID id) {
        return buyOrderRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)).toDto();
    }

    @GetMapping(Endpoints.FIND_SELL_ORDER_BY_ID)
    OrderOutputDto findSellOrderById(@PathVariable UUID id) {
        return sellOrderRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)).toDto();
    }

    @PostMapping(Endpoints.CREATE)
    OrderOutputDto create(@RequestBody OrderInputDto order) {
        if (order.getType() == OrderType.BUY) {
            BuyOrderDao dao = order.toBuyOrderDao();
            validateOrderDao(dao);
            return buyOrderRepository.save(dao).toDto();
        } else if (order.getType() == OrderType.SELL) {
            SellOrderDao dao = order.toSellOrderDao();
            validateOrderDao(dao);
            return sellOrderRepository.save(dao).toDto();
        } else {
            throw new InvalidOrderException(order.getType());
        }
    }

    private void validateOrderDao(OrderDao dao) {
        Set<ConstraintViolation<OrderDao>> violations = validator.validate(dao);;
        if (violations.size() > 0) {
            List<String> errors = new ArrayList<String>();
            for (ConstraintViolation<OrderDao> violation : violations) {
                errors.add(violation.getPropertyPath().toString() + ": " +
                        violation.getMessage());
            }
            throw new InvalidOrderException(String.join(", ", errors));
        }
    }
}
