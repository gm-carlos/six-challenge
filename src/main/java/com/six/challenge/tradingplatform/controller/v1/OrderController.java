package com.six.challenge.tradingplatform.controller.v1;

import com.six.challenge.tradingplatform.business.OrderMatchingAlgorithm;
import com.six.challenge.tradingplatform.constants.Defaults;
import com.six.challenge.tradingplatform.constants.Endpoints;
import com.six.challenge.tradingplatform.exceptions.*;
import com.six.challenge.tradingplatform.model.api.v1.order.OrderExecuteDto;
import com.six.challenge.tradingplatform.model.api.v1.order.OrderInputDto;
import com.six.challenge.tradingplatform.model.api.v1.order.OrderOutputDto;
import com.six.challenge.tradingplatform.model.database.*;
import com.six.challenge.tradingplatform.repository.BuyOrderJpaRepository;
import com.six.challenge.tradingplatform.repository.SecurityJpaRepository;
import com.six.challenge.tradingplatform.repository.SellOrderJpaRepository;
import com.six.challenge.tradingplatform.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(Endpoints.ORDER_V1)
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final SellOrderJpaRepository sellOrderRepository;
    private final BuyOrderJpaRepository buyOrderRepository;
    private final UserJpaRepository userRepository;
    private final SecurityJpaRepository securityRepository;

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();

    OrderController(SellOrderJpaRepository sellOrderRepository,
                    BuyOrderJpaRepository buyOrderJpaRepository,
                    UserJpaRepository userRepository,
                    SecurityJpaRepository securityRepository) {
        this.sellOrderRepository = sellOrderRepository;
        this.buyOrderRepository = buyOrderJpaRepository;
        this.userRepository = userRepository;
        this.securityRepository = securityRepository;
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

        UserDao user;
        SecurityDao security;
        OrderOutputDto orderDto;

        switch(order.getType()) {
            case BUY:
                user = userRepository.findById(order.getUserId()).orElseThrow(
                        () -> new UserNotFoundException(order.getUserId()));
                security = securityRepository.findById(order.getSecurityId()).orElseThrow(
                        () -> new SecurityNotFoundException(order.getSecurityId()));
                BuyOrderDao buyOrderDao = order.toBuyOrderDao(user, security);
                validateOrderDao(buyOrderDao);
                orderDto = buyOrderRepository.save(buyOrderDao).toDto();
                break;
            case SELL:
                user = userRepository.findById(order.getUserId()).orElseThrow(
                        () -> new UserNotFoundException(order.getUserId()));
                security = securityRepository.findById(order.getSecurityId()).orElseThrow(
                        () -> new SecurityNotFoundException(order.getSecurityId()));
                SellOrderDao sellOrderDao = order.toSellOrderDao(user, security);
                validateOrderDao(sellOrderDao);
                orderDto = sellOrderRepository.save(sellOrderDao).toDto();
                break;
            default:
                throw new InvalidOrderException(order.getType());
        }
        return orderDto;
    }

    @PostMapping(Endpoints.EXECUTE_ORDERS)
    void executeOrders(@RequestBody(required = false) OrderExecuteDto orderExecute) {
        String className = (orderExecute == null) ? Defaults.ALGORITHM_CLASS : orderExecute.getAlgorithm();
        try {
            Class<OrderMatchingAlgorithm> clazz = (Class<OrderMatchingAlgorithm>) Class.forName(className);
            OrderMatchingAlgorithm algorithm = clazz.getDeclaredConstructor().newInstance();
            algorithm.executeOrders(new ArrayList<BuyOrderDao>(), new ArrayList<SellOrderDao>());
        } catch (ClassNotFoundException exception) {
            throw new AlgorithmNotFoundException(className);
        } catch (InvocationTargetException | InstantiationException | NoSuchMethodException |
                IllegalAccessException exception) {
            throw new AlgorithmNotValidException(className);
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
