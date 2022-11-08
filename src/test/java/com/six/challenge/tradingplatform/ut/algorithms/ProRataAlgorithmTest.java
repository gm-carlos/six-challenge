package com.six.challenge.tradingplatform.ut.algorithms;

import com.six.challenge.tradingplatform.business.ProRataAlgorithmImpl;
import com.six.challenge.tradingplatform.model.TradeResult;
import com.six.challenge.tradingplatform.model.database.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

public class ProRataAlgorithmTest {

    Logger logger = LoggerFactory.getLogger(ProRataAlgorithmTest.class);

    ProRataAlgorithmImpl algorithm = new ProRataAlgorithmImpl();

    List<UserDao> allUsers = Arrays.asList(
            new UserDao("user1", "pass1"),
            new UserDao("user2", "pass2"),
            new UserDao("user3", "pass3"),
            new UserDao("user3", "pass3")
    );

    List<SecurityDao> allSecurities = Arrays.asList(
            new SecurityDao("sec1"),
            new SecurityDao("sec2")
    );

    @BeforeClass
    public void setUp() {
        allUsers.forEach(u -> u.setId(UUID.randomUUID()));
        allSecurities.forEach(s -> s.setId(UUID.randomUUID()));
    }

    @Test
    public void testOneBuyOneSell() {
        // One buy order and One sell order
        OrderDao currentOrder = new OrderDao(allUsers.get(0), allSecurities.get(0), 100.0, 100L, OrderType.SELL);
        currentOrder.setId(UUID.randomUUID());
        List<OrderDao> matchingOrders = Arrays.asList(
                new OrderDao(allUsers.get(1), allSecurities.get(0), 101.0, 50L, OrderType.BUY)
        );
        matchingOrders.forEach(o -> o.setId(UUID.randomUUID()));
        List<TradeDao> expectedTrades = Arrays.asList(
                new TradeDao(currentOrder, matchingOrders.get(0), 100.0, 50L)
        );

        TradeResult result = algorithm.executeOrder(currentOrder, matchingOrders);

        Assert.assertEquals(result.getOrders().size(), 2);
        Assert.assertEquals(expectedTrades.size(), result.getTrades().size());
        assertTrades(expectedTrades, result.getTrades());
    }

    @Test
    public void testMultipleSellLower() {
        // Multiple sell orders with lower price
        OrderDao currentOrder = new OrderDao(allUsers.get(0), allSecurities.get(0), 12.0, 100L, OrderType.BUY);
        currentOrder.setId(UUID.randomUUID());
        List<OrderDao> matchingOrders = Arrays.asList(
                new OrderDao(allUsers.get(1), allSecurities.get(0), 11.0, 40L, OrderType.SELL),
                new OrderDao(allUsers.get(2), allSecurities.get(0), 10.0, 40L, OrderType.SELL),
                new OrderDao(allUsers.get(2), allSecurities.get(0), 10.0, 40L, OrderType.SELL)
        );
        matchingOrders.forEach(o -> o.setId(UUID.randomUUID()));
        List<TradeDao> expectedTrades = Arrays.asList(
                new TradeDao(matchingOrders.get(0), currentOrder, 11.0, 20L),
                new TradeDao(matchingOrders.get(1), currentOrder, 10.0, 40L),
                new TradeDao(matchingOrders.get(2), currentOrder, 10.0, 40L)
        );

        TradeResult result = algorithm.executeOrder(currentOrder, matchingOrders);

        Assert.assertEquals(result.getOrders().size(), 4);
        Assert.assertEquals(expectedTrades.size(), result.getTrades().size());
        assertTrades(expectedTrades, result.getTrades());
    }

    @Test
    public void testMultipleSellHigher() {
        // Multiple sell orders with higher price
        OrderDao currentOrder = new OrderDao(allUsers.get(0), allSecurities.get(0), 12.0, 100L, OrderType.BUY);
        currentOrder.setId(UUID.randomUUID());
        List<OrderDao> matchingOrders = Arrays.asList(
                new OrderDao(allUsers.get(1), allSecurities.get(0), 11.0, 40L, OrderType.SELL),
                new OrderDao(allUsers.get(2), allSecurities.get(0), 11.0, 40L, OrderType.SELL),
                new OrderDao(allUsers.get(2), allSecurities.get(0), 10.0, 40L, OrderType.SELL)
        );
        matchingOrders.forEach(o -> o.setId(UUID.randomUUID()));
        List<TradeDao> expectedTrades = Arrays.asList(
                new TradeDao(matchingOrders.get(0), currentOrder, 11.0, 30L),
                new TradeDao(matchingOrders.get(1), currentOrder, 11.0, 30L),
                new TradeDao(matchingOrders.get(2), currentOrder, 10.0, 40L)
        );

        TradeResult result = algorithm.executeOrder(currentOrder, matchingOrders);

        Assert.assertEquals(result.getOrders().size(), 4);
        Assert.assertEquals(expectedTrades.size(), result.getTrades().size());
        assertTrades(expectedTrades, result.getTrades());
    }

    @Test
    public void testMultipleSellWithLessThanRequested() {
        // Multiple sell orders with less quantity than requested
        OrderDao currentOrder = new OrderDao(allUsers.get(0), allSecurities.get(0), 12.0, 150L, OrderType.BUY);
        currentOrder.setId(UUID.randomUUID());
        List<OrderDao> matchingOrders = Arrays.asList(
                new OrderDao(allUsers.get(1), allSecurities.get(0), 11.0, 40L, OrderType.SELL),
                new OrderDao(allUsers.get(2), allSecurities.get(0), 11.0, 40L, OrderType.SELL),
                new OrderDao(allUsers.get(2), allSecurities.get(0), 11.0, 40L, OrderType.SELL)
        );
        matchingOrders.forEach(o -> o.setId(UUID.randomUUID()));
        List<TradeDao> expectedTrades = Arrays.asList(
                new TradeDao(matchingOrders.get(0), currentOrder, 11.0, 40L),
                new TradeDao(matchingOrders.get(1), currentOrder, 11.0, 40L),
                new TradeDao(matchingOrders.get(2), currentOrder, 11.0, 40L)
        );

        TradeResult result = algorithm.executeOrder(currentOrder, matchingOrders);

        Assert.assertEquals(result.getOrders().size(), 4);
        Assert.assertEquals(expectedTrades.size(), result.getTrades().size());
        assertTrades(expectedTrades, result.getTrades());
    }

    @Test
    public void testMultipleSellWithResidual() {
        // Multiple sell orders with residual quantity
        OrderDao currentOrder = new OrderDao(allUsers.get(0), allSecurities.get(0), 12.0, 100L, OrderType.BUY);
        currentOrder.setId(UUID.randomUUID());
        List<OrderDao> matchingOrders = Arrays.asList(
                new OrderDao(allUsers.get(1), allSecurities.get(0), 11.0, 40L, OrderType.SELL),
                new OrderDao(allUsers.get(2), allSecurities.get(0), 11.0, 40L, OrderType.SELL),
                new OrderDao(allUsers.get(2), allSecurities.get(0), 11.0, 40L, OrderType.SELL)
        );
        matchingOrders.forEach(o -> o.setId(UUID.randomUUID()));
        List<TradeDao> expectedTrades = Arrays.asList(
                new TradeDao(matchingOrders.get(0), currentOrder, 11.0, 33L),
                new TradeDao(matchingOrders.get(1), currentOrder, 11.0, 33L),
                new TradeDao(matchingOrders.get(2), currentOrder, 11.0, 33L),
                new TradeDao(matchingOrders.get(0), currentOrder, 11.0, 1L)
        );

        TradeResult result = algorithm.executeOrder(currentOrder, matchingOrders);

        Assert.assertEquals(result.getOrders().size(), 4);
        Assert.assertEquals(expectedTrades.size(), result.getTrades().size());
        assertTrades(expectedTrades, result.getTrades());
    }

    @Test
    public void testMultipleBuyLower() {
        // Multiple buy orders with lower price
        OrderDao currentOrder = new OrderDao(allUsers.get(0), allSecurities.get(0), 9.0, 100L, OrderType.SELL);
        currentOrder.setId(UUID.randomUUID());
        List<OrderDao> matchingOrders = Arrays.asList(
                new OrderDao(allUsers.get(1), allSecurities.get(0), 11.0, 40L, OrderType.BUY),
                new OrderDao(allUsers.get(2), allSecurities.get(0), 10.0, 40L, OrderType.BUY),
                new OrderDao(allUsers.get(2), allSecurities.get(0), 10.0, 40L, OrderType.BUY)
        );
        matchingOrders.forEach(o -> o.setId(UUID.randomUUID()));
        List<TradeDao> expectedTrades = Arrays.asList(
                new TradeDao(currentOrder, matchingOrders.get(0), 9.0, 40L),
                new TradeDao(currentOrder, matchingOrders.get(1), 9.0, 30L),
                new TradeDao(currentOrder, matchingOrders.get(2), 9.0, 30L)
        );

        TradeResult result = algorithm.executeOrder(currentOrder, matchingOrders);

        Assert.assertEquals(result.getOrders().size(), 4);
        Assert.assertEquals(expectedTrades.size(), result.getTrades().size());
        assertTrades(expectedTrades, result.getTrades());
    }

    @Test
    public void testMultipleBuyHigher() {
        // Multiple buy orders with higher price
        OrderDao currentOrder = new OrderDao(allUsers.get(0), allSecurities.get(0), 9.0, 100L, OrderType.SELL);
        currentOrder.setId(UUID.randomUUID());
        List<OrderDao> matchingOrders = Arrays.asList(
                new OrderDao(allUsers.get(1), allSecurities.get(0), 11.0, 40L, OrderType.BUY),
                new OrderDao(allUsers.get(2), allSecurities.get(0), 11.0, 40L, OrderType.BUY),
                new OrderDao(allUsers.get(2), allSecurities.get(0), 10.0, 40L, OrderType.BUY)
        );
        matchingOrders.forEach(o -> o.setId(UUID.randomUUID()));
        List<TradeDao> expectedTrades = Arrays.asList(
                new TradeDao(currentOrder, matchingOrders.get(0), 9.0, 40L),
                new TradeDao(currentOrder, matchingOrders.get(1), 9.0, 40L),
                new TradeDao(currentOrder, matchingOrders.get(2), 9.0, 20L)
        );

        TradeResult result = algorithm.executeOrder(currentOrder, matchingOrders);

        Assert.assertEquals(result.getOrders().size(), 4);
        Assert.assertEquals(expectedTrades.size(), result.getTrades().size());
        assertTrades(expectedTrades, result.getTrades());
    }

    @Test
    public void testMultipleBuyWithLessThanRequested() {
        // Multiple buy orders with less quantity than requested
        OrderDao currentOrder = new OrderDao(allUsers.get(0), allSecurities.get(0), 9.0, 150L, OrderType.SELL);
        currentOrder.setId(UUID.randomUUID());
        List<OrderDao> matchingOrders = Arrays.asList(
                new OrderDao(allUsers.get(1), allSecurities.get(0), 11.0, 40L, OrderType.BUY),
                new OrderDao(allUsers.get(2), allSecurities.get(0), 11.0, 40L, OrderType.BUY),
                new OrderDao(allUsers.get(2), allSecurities.get(0), 11.0, 40L, OrderType.BUY)
        );
        matchingOrders.forEach(o -> o.setId(UUID.randomUUID()));
        List<TradeDao> expectedTrades = Arrays.asList(
                new TradeDao(currentOrder, matchingOrders.get(0), 9.0, 40L),
                new TradeDao(currentOrder, matchingOrders.get(1), 9.0, 40L),
                new TradeDao(currentOrder, matchingOrders.get(2), 9.0, 40L)
        );

        TradeResult result = algorithm.executeOrder(currentOrder, matchingOrders);

        Assert.assertEquals(result.getOrders().size(), 4);
        Assert.assertEquals(expectedTrades.size(), result.getTrades().size());
        assertTrades(expectedTrades, result.getTrades());
    }

    @Test
    public void testMultipleBuyWithResidual() {
        // Multiple buy orders with residual quantity
        OrderDao currentOrder = new OrderDao(allUsers.get(0), allSecurities.get(0), 9.0, 100L, OrderType.SELL);
        currentOrder.setId(UUID.randomUUID());
        List<OrderDao> matchingOrders = Arrays.asList(
                new OrderDao(allUsers.get(1), allSecurities.get(0), 11.0, 40L, OrderType.BUY),
                new OrderDao(allUsers.get(2), allSecurities.get(0), 11.0, 40L, OrderType.BUY),
                new OrderDao(allUsers.get(2), allSecurities.get(0), 11.0, 40L, OrderType.BUY)
        );
        matchingOrders.forEach(o -> o.setId(UUID.randomUUID()));
        List<TradeDao> expectedTrades = Arrays.asList(
                new TradeDao(currentOrder, matchingOrders.get(0), 9.0, 33L),
                new TradeDao(currentOrder, matchingOrders.get(1), 9.0, 33L),
                new TradeDao(currentOrder, matchingOrders.get(2), 9.0, 33L),
                new TradeDao(currentOrder, matchingOrders.get(0), 9.0, 1L)
        );

        TradeResult result = algorithm.executeOrder(currentOrder, matchingOrders);

        Assert.assertEquals(result.getOrders().size(), 4);
        Assert.assertEquals(expectedTrades.size(), result.getTrades().size());
        assertTrades(expectedTrades, result.getTrades());
    }


    private void assertTrades(List<TradeDao> expected, Set<TradeDao> result) {
        for (TradeDao trade : expected) {
            List<TradeDao> tradesFound = result.stream().filter(t -> (
                    trade.getBuyOrderId() == t.getBuyOrderId() &&
                            trade.getSellOrderId() == t.getSellOrderId() &&
                            Objects.equals(trade.getPrice(), t.getPrice()) &&
                            Objects.equals(trade.getQuantity(), t.getQuantity())
            )).collect(Collectors.toList());
            Assert.assertFalse(tradesFound.stream().count() == 0);
        }
    }
}
