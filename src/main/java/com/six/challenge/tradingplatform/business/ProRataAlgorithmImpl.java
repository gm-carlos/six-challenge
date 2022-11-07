package com.six.challenge.tradingplatform.business;

import com.six.challenge.tradingplatform.controller.v1.OrderController;
import com.six.challenge.tradingplatform.model.TradeResult;
import com.six.challenge.tradingplatform.model.database.OrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class ProRataAlgorithmImpl implements OrderMatchingAlgorithm {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Override
    public TradeResult executeOrder(OrderDao currentOrder, List<OrderDao> matchingOrders) {
        logger.info("Executing ProRata Algorithm for order " + currentOrder.getId());
        TradeResult result = new TradeResult();
        result.addOrder(currentOrder);
        Map<Double, List<OrderDao>> matchingOrdersByPrice = matchingOrders.stream()
                .collect(Collectors.groupingBy(OrderDao::getPrice));

        List<Double> prices = new ArrayList<>(matchingOrdersByPrice.keySet());
        // Start with highest price or lower price? (Dependending if currentOrder is buy or sell?)
        Collections.reverse(prices);

        prices.forEach( p ->
                prorata(currentOrder, matchingOrdersByPrice.get(p), result)
        );

        return result;
    }

    private void prorata(OrderDao currentOrder, List<OrderDao> ordersByPrice, TradeResult result) {

        List<Long> quantities = ordersByPrice.stream().map(OrderDao::getCurrentQuantity).collect(Collectors.toList());
        Long sumQuantities = quantities.stream().reduce(0L, Long::sum);

        if (sumQuantities > currentOrder.getCurrentQuantity()) {
            // If there are more stock in matching orders we have to prorata quantities
            for(OrderDao order: ordersByPrice) {
                Long prorataQuantity = order.getCurrentQuantity() //
                currentOrder.setCurrentQuantity(currentOrder.getCurrentQuantity() - prorataQuantity);
                
            }
        } else {
            // If less or equal, we can complete the trade with existing matching orders
            for(OrderDao order: ordersByPrice) {
                currentOrder.setCurrentQuantity(currentOrder.getCurrentQuantity() - order.getCurrentQuantity());
                order.setCurrentQuantity(0L);
                order.setFulfilled(true);
            }
        }

        if (currentOrder.getCurrentQuantity() == 0L) currentOrder.setFulfilled(true);
    }
}
