package com.six.challenge.tradingplatform.business;

import com.six.challenge.tradingplatform.controller.v1.OrderController;
import com.six.challenge.tradingplatform.model.TradeResult;
import com.six.challenge.tradingplatform.model.database.OrderDao;
import com.six.challenge.tradingplatform.model.database.OrderType;
import com.six.challenge.tradingplatform.model.database.TradeDao;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class ProRataAlgorithmImpl implements OrderMatchingAlgorithm {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Override
    public TradeResult executeOrder(OrderDao currentOrder, List<OrderDao> matchingOrders) {
        logger.debug("Executing ProRata Algorithm for order " + currentOrder.getId());
        TradeResult result = new TradeResult();
        // Add current order to result (will be updated)
        result.addOrder(currentOrder);
        Map<Double, List<OrderDao>> matchingOrdersByPrice = matchingOrders.stream()
                .collect(Collectors.groupingBy(OrderDao::getPrice));

        List<Double> prices = new ArrayList<>(matchingOrdersByPrice.keySet());
        // Start with the highest price or lower price? (Depending on if currentOrder is buy or sell?)
        if (currentOrder.getType() == OrderType.BUY) {
            Collections.sort(prices);
        } else {
            Collections.reverse(prices);
        }

        for(Double price: prices)  {
            prorata(price, currentOrder, matchingOrdersByPrice.get(price), result);
            if (currentOrder.isFulfilled()) {
                break;
            } else {
                // call again for residual quantities
                List<OrderDao> remainingOrders = matchingOrdersByPrice.get(price).stream()
                        .filter(o -> !o.isFulfilled()).collect(Collectors.toList());
                prorata(price, currentOrder, remainingOrders, result);
            }
        }

        return result;
    }

    private void prorata(Double price, OrderDao currentOrder, List<OrderDao> ordersByPrice, TradeResult result) {

        List<Long> quantities = ordersByPrice.stream().map(OrderDao::getCurrentQuantity).collect(Collectors.toList());
        Long sumQuantities = quantities.stream().reduce(0L, Long::sum);
        Long initialQuantity = currentOrder.getCurrentQuantity();

        for(OrderDao order: ordersByPrice) {
            logger.debug("Analyzing matching order: " + order.getId() + " with price " + order.getPrice());
            Long prorataQuantity;
            if (sumQuantities > initialQuantity) {
                Double prorataDouble = (Double.valueOf(initialQuantity) / Double.valueOf(sumQuantities)) * Double.valueOf(order.getCurrentQuantity());
                prorataQuantity = Math.max(prorataDouble.longValue(), 1);
                logger.debug("Prorata quantity: " + prorataQuantity);
            } else {
                prorataQuantity = order.getCurrentQuantity();
                logger.debug("Direct quantity: " + prorataQuantity);
            }
            currentOrder.setCurrentQuantity(currentOrder.getCurrentQuantity() - prorataQuantity);
            currentOrder.setFulfilled(currentOrder.getCurrentQuantity() == 0);
            order.setCurrentQuantity(order.getCurrentQuantity() - prorataQuantity);
            order.setFulfilled(order.getCurrentQuantity() == 0);
            result.addOrder(order);
            result.addTrade(new TradeDao(
                    currentOrder.getType() == OrderType.BUY ? order : currentOrder,
                    currentOrder.getType() == OrderType.BUY ? currentOrder : order,
                    price,
                    prorataQuantity
            ));
            if (currentOrder.isFulfilled()) return;
        }
    }

}
