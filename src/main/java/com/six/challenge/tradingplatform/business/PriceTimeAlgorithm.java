package com.six.challenge.tradingplatform.business;

import com.six.challenge.tradingplatform.controller.v1.OrderController;
import com.six.challenge.tradingplatform.model.database.BuyOrderDao;
import com.six.challenge.tradingplatform.model.database.SellOrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PriceTimeAlgorithm implements OrderMatchingAlgorithm {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Override
    public void executeOrders(List<BuyOrderDao> buyOrders, List<SellOrderDao> sellOrders) {
        logger.info("Execute Price/Time Algorithm");
    }
}
