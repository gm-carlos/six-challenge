package com.six.challenge.tradingplatform.ut.algorithms;

import com.six.challenge.tradingplatform.business.ProRataAlgorithmImpl;
import com.six.challenge.tradingplatform.model.TradeResult;
import com.six.challenge.tradingplatform.model.database.OrderDao;
import com.six.challenge.tradingplatform.model.database.OrderType;
import com.six.challenge.tradingplatform.model.database.SecurityDao;
import com.six.challenge.tradingplatform.model.database.UserDao;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ProRataAlgorithmTest {

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
    public void testAlgorithm() {

        OrderDao currentOrder = new OrderDao(allUsers.get(0), allSecurities.get(0), 10.0, 100L, OrderType.BUY);
        currentOrder.setId(UUID.randomUUID());
        List<OrderDao> matchingOrders = Arrays.asList(
                new OrderDao(allUsers.get(1), allSecurities.get(0), 10.0, 50L, OrderType.SELL),
                new OrderDao(allUsers.get(2), allSecurities.get(1), 10.0, 50L, OrderType.SELL),
                new OrderDao(allUsers.get(2), allSecurities.get(1), 9.0, 50L, OrderType.SELL)
        );
        matchingOrders.forEach(o -> o.setId(UUID.randomUUID()));

        TradeResult result = algorithm.executeOrder(currentOrder, matchingOrders);



        Assert.assertEquals(result.getOrders().size(), 0);
    }

}
