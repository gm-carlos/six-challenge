package com.six.challenge.tradingplatform.at;

import com.six.challenge.tradingplatform.at.client.v1.TradingClient;
import com.six.challenge.tradingplatform.at.client.v1.model.HTTPResponse;
import com.six.challenge.tradingplatform.model.api.v1.security.SecurityOutputDto;
import com.six.challenge.tradingplatform.model.api.v1.user.UserOutputDto;
import io.cucumber.java.en.Given;
import org.springframework.http.HttpStatus;
import org.testng.Assert;

import java.util.UUID;

public class Steps {

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
        HTTPResponse<UserOutputDto> userResponse = client.findById(id);
        UserOutputDto user = userResponse.getResponseObject();
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getId(), UUID.fromString(id));
    }

    @Given("^I create user \"(.*)\" with password \"(.*)\"$")
    public void createUser(String userName, String password) {
        TradingClient client = StepProperties.getClientInstance();

        HTTPResponse<UserOutputDto> userResponse = client.createUser(userName, password);
        Assert.assertEquals(userResponse.getStatus(), HttpStatus.CREATED);
    }

    @Given("^I create security \"(.*)\"$")
    public void createSecurity(String securityName) {
        TradingClient client = StepProperties.getClientInstance();

        HTTPResponse<SecurityOutputDto> securityResponse = client.createSecurity(securityName);
        Assert.assertEquals(securityResponse.getStatus(), HttpStatus.CREATED);
    }

}
