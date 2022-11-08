package com.six.challenge.tradingplatform.controller.v1;

import com.six.challenge.tradingplatform.business.OrderMatchingAlgorithm;
import com.six.challenge.tradingplatform.constants.Defaults;
import com.six.challenge.tradingplatform.constants.Endpoints;
import com.six.challenge.tradingplatform.exceptions.*;
import com.six.challenge.tradingplatform.model.TradeResult;
import com.six.challenge.tradingplatform.model.api.v1.order.OrderInputDto;
import com.six.challenge.tradingplatform.model.api.v1.order.OrderOutputDto;
import com.six.challenge.tradingplatform.model.api.v1.trade.TradeOutputDto;
import com.six.challenge.tradingplatform.model.database.*;
import com.six.challenge.tradingplatform.repository.OrderJpaRepository;
import com.six.challenge.tradingplatform.repository.SecurityJpaRepository;
import com.six.challenge.tradingplatform.repository.TradeJpaRepository;
import com.six.challenge.tradingplatform.repository.UserJpaRepository;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(Endpoints.ORDER_V1)
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private static final Map<String, OrderMatchingAlgorithm> algorithmMap = findAlgorithms();
    private final OrderJpaRepository orderRepository;
    private final UserJpaRepository userRepository;
    private final SecurityJpaRepository securityRepository;
    private final TradeJpaRepository tradeJpaRepository;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

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
        LOGGER.info(Endpoints.ORDER_V1 + Endpoints.FIND_ALL + " endpoint call");
        Stream<OrderOutputDto> orders = orderRepository.findAll().stream().map(OrderDao::toDto);
        return orders.collect(Collectors.toList());
    }

    @GetMapping(Endpoints.FIND_BY_ID)
    OrderOutputDto findOrderById(@PathVariable UUID id) {
        LOGGER.info(Endpoints.ORDER_V1 + Endpoints.FIND_BY_ID + " endpoint call with id: " + id);
        return orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException(id)).toDto();
    }

    @PostMapping(Endpoints.CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    OrderOutputDto create(@RequestBody OrderInputDto order) {
        LOGGER.info(Endpoints.ORDER_V1 + Endpoints.CREATE + " endpoint call with order: " + order.toString());
        UserDao user = userRepository.findById(order.getUserId()).orElseThrow(
                () -> new UserNotFoundException(order.getUserId()));
        SecurityDao security = securityRepository.findById(order.getSecurityId())
                .orElseThrow(() -> new SecurityNotFoundException(order.getSecurityId()));
        LOGGER.debug("User and security found for order");
        OrderDao dao = order.toOrderDao(user, security);
        validateOrderDao(dao);
        orderRepository.save(dao);
        LOGGER.debug("Order saved successfully in database");
        List<TradeOutputDto> trades = executeOrder(dao);
        LOGGER.info("Algorithm execution produced " + trades.size() + " trades");
        OrderOutputDto orderDto = orderRepository.findById(dao.getId()).orElseThrow(
                () -> new OrderNotFoundException(dao.getId())).toDto();
        orderDto.setTrades(trades);
        return orderDto;
    }

    private List<TradeOutputDto> executeOrder(OrderDao order) {
        // TODO: Make algorithm selection configurable through REST API
        String className = Defaults.ALGORITHM_CLASS;
        LOGGER.info("Trying to execute order using algorithm implementation at: " + className);
        // Get Algorithm
        if(algorithmMap.containsKey(className)) {
            OrderMatchingAlgorithm algorithm = algorithmMap.get(className);
            // Get matching orders
            List<OrderDao> matchingOrders = order.getType() == OrderType.BUY ?
                    orderRepository.findAllMatchingSellOrders(order.getUser().getId().toString(),
                            order.getSecurity().getId().toString(), order.getPrice()) :
                    orderRepository.findAllMatchingBuyOrders(order.getUser().getId().toString(),
                            order.getSecurity().getId().toString(), order.getPrice());
            LOGGER.info("Found " + matchingOrders.size() + " matching orders");
            TradeResult result = algorithm.executeOrder(order, matchingOrders);
            LOGGER.info("Algorithm executed. Saving result objects...");
            result.getOrders().forEach(orderRepository::save);
            result.getTrades().forEach(tradeJpaRepository::save);
            return result.getTrades().stream().map(TradeDao::toDto).collect(Collectors.toList());
        } else {
            LOGGER.warn("Algorithm " + className + " not found. Order won't be executed.");
        }
        return new ArrayList<>();
    }

    private void validateOrderDao(OrderDao dao) {
        Set<ConstraintViolation<OrderDao>> violations = validator.validate(dao);
        if (violations.size() > 0) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<OrderDao> violation : violations) {
                String violationMessage = violation.getPropertyPath().toString() + ": " +
                        violation.getMessage();
                LOGGER.info("Violation found while validating data: " + violationMessage);
                errors.add(violationMessage);
            }
            throw new InvalidOrderException(String.join(", ", errors));
        }
    }

    private static Map<String, OrderMatchingAlgorithm> findAlgorithms() {
        Map<String, OrderMatchingAlgorithm> algorithmMap = new HashMap<>();
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(Defaults.ALGORITHMS_PACKAGE));
        Set<Class<? extends OrderMatchingAlgorithm>> classes = reflections.getSubTypesOf(OrderMatchingAlgorithm.class);
        LOGGER.info("Found " + classes.size() + " classes implementing OrderMatchingAlgorithm.");
        for(Class<? extends OrderMatchingAlgorithm> clazz: classes) {
            try {
                OrderMatchingAlgorithm algorithm = clazz.getDeclaredConstructor().newInstance();
                algorithmMap.put(clazz.getCanonicalName(), algorithm);
                LOGGER.info("Algorithm instance for class: " + clazz.getName() +
                        " registered correctly.");
            } catch (NoSuchMethodException | InstantiationException
                     | IllegalAccessException | InvocationTargetException exception) {
                LOGGER.warn("Error create algorithm instance for class: " + clazz.getName() +
                        ". Skipping implementation...");
            }
        }
        return algorithmMap;
    }
}
