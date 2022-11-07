package com.six.challenge.tradingplatform.at;

import com.six.challenge.tradingplatform.at.client.v1.TradingClient;
import com.six.challenge.tradingplatform.at.client.v1.model.HTTPResponse;
import com.six.challenge.tradingplatform.at.client.v1.model.HTTPResponseList;
import com.six.challenge.tradingplatform.controller.v1.OrderController;
import com.six.challenge.tradingplatform.model.api.v1.order.OrderOutputDto;
import com.six.challenge.tradingplatform.model.api.v1.security.SecurityOutputDto;
import com.six.challenge.tradingplatform.model.api.v1.trade.TradeOutputDto;
import com.six.challenge.tradingplatform.model.api.v1.user.UserOutputDto;
import com.six.challenge.tradingplatform.model.database.OrderType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.testng.Assert;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Steps {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    StepProperties props;
    public Steps(StepProperties props) {
        this.props = props;
    }

    @Given("^a trading platform running at host (.*) and port (.*)$")
    public void setPlatformUrl(String host, String port) throws Throwable {
        StepProperties.HOST = host;
        StepProperties.PORT = port;
    }

    @Given("^I find user with id (.*)$")
    public void findUserbyId(String id) throws Throwable {
        TradingClient client = StepProperties.getClientInstance();
        HTTPResponse<UserOutputDto> userResponse = client.findUserById(id);
        UserOutputDto user = userResponse.getResponseObject();
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getId(), UUID.fromString(id));
    }

    @Given("^I create user \"(.*)\" with password \"(.*)\"$")
    public void createUser(String userName, String password) {
        TradingClient client = StepProperties.getClientInstance();

        HTTPResponse<UserOutputDto> findUserResponse = client.findUserByName(userName);
        if(findUserResponse.getStatus() == HttpStatus.NOT_FOUND) {
            HTTPResponse<UserOutputDto> userResponse = client.createUser(userName, password);
            Assert.assertEquals(userResponse.getStatus(), HttpStatus.CREATED);
        }
    }

    @Given("^I create security \"(.*)\"$")
    public void createSecurity(String securityName) {
        TradingClient client = StepProperties.getClientInstance();

        HTTPResponse<SecurityOutputDto> findSecurityResponse = client.findSecurityByName(securityName);
        logger.info("Security status " + findSecurityResponse.getStatus());
        if(findSecurityResponse.getStatus() == HttpStatus.NOT_FOUND) {
            logger.info("Security " + securityName + " not found, creating...");
            HTTPResponse<SecurityOutputDto> securityResponse = client.createSecurity(securityName);
            Assert.assertEquals(securityResponse.getStatus(), HttpStatus.CREATED);
        }
    }

    @When("^User \"(.*)\" puts a \"(buy|sell)\" order for security \"(.*)\" with a price of \"(.*)\" and quantity of \"(.*)\"$")
    public void createOrder(String userName, String buyOrSell, String securityName, Double price, Long quantity) {
        TradingClient client = StepProperties.getClientInstance();

        HTTPResponse<SecurityOutputDto> securityResponse = client.findSecurityByName(securityName);
        Assert.assertEquals(securityResponse.getStatus(), HttpStatus.OK);
        HTTPResponse<UserOutputDto> userResponse = client.findUserByName(userName);
        Assert.assertEquals(userResponse.getStatus(), HttpStatus.OK);

        HTTPResponse<OrderOutputDto> orderResponse = client.createOrder(
                userResponse.getResponseObject().getId(),
                securityResponse.getResponseObject().getId(),
                price,
                quantity,
                Objects.equals(buyOrSell, "buy") ? OrderType.BUY : OrderType.SELL);
        Assert.assertEquals(orderResponse.getStatus(), HttpStatus.CREATED);
    }

    @Then("^a trade occurs with the price of \"(.*)\" and quantity of \"(.*)\"$")
    public void findTrade(Double price, Long quantity) throws Throwable {
        TradingClient client = StepProperties.getClientInstance();
        HTTPResponseList<TradeOutputDto> tradesResponse = client.findAllTrades();
        List<TradeOutputDto> trades = tradesResponse.getResponseObjectList();
        Assert.assertEquals(trades.size(), 1);
        TradeOutputDto trade = trades.get(0);
        Assert.assertEquals(trade.getPrice(), price);
        Assert.assertEquals(trade.getQuantity(), quantity);
    }

}
