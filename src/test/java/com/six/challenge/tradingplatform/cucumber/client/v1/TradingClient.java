package com.six.challenge.tradingplatform.cucumber.client.v1;

import com.six.challenge.tradingplatform.constants.Endpoints;
import com.six.challenge.tradingplatform.cucumber.client.v1.model.HTTPResponse;
import com.six.challenge.tradingplatform.model.api.v1.security.SecurityInputDto;
import com.six.challenge.tradingplatform.model.api.v1.security.SecurityOutputDto;
import com.six.challenge.tradingplatform.model.api.v1.user.UserInputDto;
import com.six.challenge.tradingplatform.model.api.v1.user.UserOutputDto;
import org.asynchttpclient.Response;

public class TradingClient extends BaseClient {

    private String baseUrl;
    public TradingClient(String url, String port) {
        super(url, port);
        baseUrl = "http://".concat(this.getUrl()).concat(":").concat(this.getPort());
    }

    public HTTPResponse<UserOutputDto> findById(String id) {
        String url = baseUrl.concat(Endpoints.USER_V1).concat(Endpoints.FIND_BY_ID).concat("/" + id) ;
        try {
            Response response = get(url);
            return map(response, UserOutputDto.class);
        } catch (Exception e) {
           return new HTTPResponse<>();
        }
    }

    public HTTPResponse<UserOutputDto> findByName(String name) {
        String url = baseUrl.concat(Endpoints.USER_V1).concat(Endpoints.FIND_BY_NAME).concat("/" + name) ;
        try {
            Response response = get(url);
            return map(response, UserOutputDto.class);
        } catch (Exception e) {
            return new HTTPResponse<>();
        }
    }

    public HTTPResponse<UserOutputDto> createUser(String name, String password) {
        String url = baseUrl.concat(Endpoints.USER_V1).concat(Endpoints.CREATE);
        UserInputDto user = new UserInputDto(name, password);
        try {
            String data = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(user);
            Response response = post(url, data);
            return map(response, UserOutputDto.class);
        } catch (Exception e) {
            return new HTTPResponse<>();
        }
    }

    public HTTPResponse<SecurityOutputDto> createSecurity(String name) {
        String url = baseUrl.concat(Endpoints.SECURITY_V1).concat(Endpoints.CREATE);
        SecurityInputDto security = new SecurityInputDto(name);
        try {
            String data = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(security);
            Response response = post(url, data);
            return map(response, SecurityOutputDto.class);
        } catch (Exception e) {
            return new HTTPResponse<>();
        }
    }
}
