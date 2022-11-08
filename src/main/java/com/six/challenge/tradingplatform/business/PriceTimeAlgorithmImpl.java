package com.six.challenge.tradingplatform.business;

import com.six.challenge.tradingplatform.controller.v1.OrderController;
import com.six.challenge.tradingplatform.exceptions.AlgorithmNotImplementedException;
import com.six.challenge.tradingplatform.model.TradeResult;
import com.six.challenge.tradingplatform.model.database.OrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PriceTimeAlgorithmImpl implements OrderMatchingAlgorithm {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Override
    public TradeResult executeOrder(OrderDao currentOrder, List<OrderDao> matchingOrders) {
        LOGGER.warn("Price/Time Algorithm not implemented");
        // TODO - Implement this method
        throw new AlgorithmNotImplementedException(this.getClass().getCanonicalName());
    }
}
