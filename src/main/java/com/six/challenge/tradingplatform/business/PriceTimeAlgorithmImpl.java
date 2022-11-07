package com.six.challenge.tradingplatform.business;

import com.six.challenge.tradingplatform.controller.v1.OrderController;
import com.six.challenge.tradingplatform.model.TradeResult;
import com.six.challenge.tradingplatform.model.database.OrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PriceTimeAlgorithmImpl implements OrderMatchingAlgorithm {

    Logger logger = LoggerFactory.getLogger(OrderController.class);


    @Override
    public TradeResult executeOrder(OrderDao currentOrder, List<OrderDao> matchingOrders) {
        logger.info("Execute Price/Time Algorithm for order: " + currentOrder.getId().toString());
        TradeResult result = new TradeResult();


        return result;
    }
}
