package com.six.challenge.tradingplatform.controller.v1;

import com.six.challenge.tradingplatform.business.OrderMatchingAlgorithm;
import com.six.challenge.tradingplatform.constants.Defaults;
import com.six.challenge.tradingplatform.constants.Endpoints;
import com.six.challenge.tradingplatform.exceptions.*;
import com.six.challenge.tradingplatform.model.TradeResult;
import com.six.challenge.tradingplatform.model.api.v1.order.OrderInputDto;
import com.six.challenge.tradingplatform.model.api.v1.order.OrderOutputDto;
import com.six.challenge.tradingplatform.model.database.*;
import com.six.challenge.tradingplatform.repository.OrderJpaRepository;
import com.six.challenge.tradingplatform.repository.SecurityJpaRepository;
import com.six.challenge.tradingplatform.repository.TradeJpaRepository;
import com.six.challenge.tradingplatform.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

    private final OrderJpaRepository orderRepository;
    private final UserJpaRepository userRepository;
    private final SecurityJpaRepository securityRepository;
    private final TradeJpaRepository tradeJpaRepository;

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();

    OrderController(OrderJpaRepository orderRepository,
                    UserJpaRepository userRepository,
                    SecurityJpaRepository securityRepository,
                    TradeJpaRepository tradeJpaRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.securityRepository = securityRepository;
        this.tradeJpaRepository = tradeJpaRepository;
    }

    @GetMapping(Endpoints.FIND_ALL)
    List<OrderOutputDto> findAll() {
        Stream<OrderOutputDto> orders = orderRepository.findAll().stream().map(OrderDao::toDto);
        return orders.collect(Collectors.toList());
    }

    @GetMapping(Endpoints.FIND_BY_ID)
    OrderOutputDto findBuyOrderById(@PathVariable UUID id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)).toDto();
    }

    @PostMapping(Endpoints.CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    OrderOutputDto create(@RequestBody OrderInputDto order) {

        UserDao user = userRepository.findById(order.getUserId()).orElseThrow(
                () -> new UserNotFoundException(order.getUserId()));
        SecurityDao security = securityRepository.findById(order.getSecurityId())
                .orElseThrow(() -> new SecurityNotFoundException(order.getSecurityId()));
        OrderDao dao = order.toOrderDao(user, security);
        validateOrderDao(dao);
        OrderOutputDto orderDto = orderRepository.save(dao).toDto();
        executeOrder(dao);
        return orderDto;
    }

    private void executeOrder(OrderDao order) {
        String className = Defaults.ALGORITHM_CLASS;
        try {
            // Get Algorithm class
            Class<OrderMatchingAlgorithm> clazz = (Class<OrderMatchingAlgorithm>) Class.forName(className);
            OrderMatchingAlgorithm algorithm = clazz.getDeclaredConstructor().newInstance();

            // Get matching orders
            List<OrderDao> matchingOrders = order.getType() == OrderType.BUY ?
                    orderRepository.findAllMatchingSellOrders(order.getUser().getId().toString(),
                            order.getSecurity().getId().toString(), order.getPrice()) :
                    orderRepository.findAllMatchingBuyOrders(order.getUser().getId().toString(),
                            order.getSecurity().getId().toString(), order.getPrice());
            TradeResult result = algorithm.executeOrder(order, matchingOrders);
            result.getOrders().forEach(orderRepository::save);
            result.getTrades().forEach(tradeJpaRepository::save);
        } catch (ClassNotFoundException exception) {
            throw new AlgorithmNotFoundException(className);
        } catch (InvocationTargetException | InstantiationException | NoSuchMethodException |
                 IllegalAccessException exception) {
            throw new AlgorithmNotValidException(className);
        }
    }

    private void validateOrderDao(OrderDao dao) {
        Set<ConstraintViolation<OrderDao>> violations = validator.validate(dao);
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
