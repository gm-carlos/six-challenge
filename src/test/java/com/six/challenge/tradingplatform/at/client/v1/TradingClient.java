package com.six.challenge.tradingplatform.at.client.v1;

import com.six.challenge.tradingplatform.at.client.v1.model.HTTPResponseList;
import com.six.challenge.tradingplatform.constants.Endpoints;
import com.six.challenge.tradingplatform.at.client.v1.model.HTTPResponse;
import com.six.challenge.tradingplatform.model.api.v1.order.OrderInputDto;
import com.six.challenge.tradingplatform.model.api.v1.order.OrderOutputDto;
import com.six.challenge.tradingplatform.model.api.v1.security.SecurityInputDto;
import com.six.challenge.tradingplatform.model.api.v1.security.SecurityOutputDto;
import com.six.challenge.tradingplatform.model.api.v1.trade.TradeOutputDto;
import com.six.challenge.tradingplatform.model.api.v1.user.UserInputDto;
import com.six.challenge.tradingplatform.model.api.v1.user.UserOutputDto;
import com.six.challenge.tradingplatform.model.database.OrderType;
import org.asynchttpclient.Response;

import java.util.UUID;

public class TradingClient extends BaseClient {

    private final String baseUrl;
    public TradingClient(String url, String port) {
        super(url, port);
        baseUrl = "http://".concat(this.getUrl()).concat(":").concat(this.getPort());
    }

    public HTTPResponse<UserOutputDto> findUserById(String id) {
        String url = baseUrl.concat(Endpoints.USER_V1).concat(Endpoints.FIND_BY_ID).concat("/" + id) ;
        try {
            Response response = get(url);
            return map(response, UserOutputDto.class);
        } catch (Exception e) {
           return new HTTPResponse<>();
        }
    }

    public HTTPResponse<UserOutputDto> findUserByName(String name) {
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

    public HTTPResponse<SecurityOutputDto> findSecurityByName(String name) {
        String url = baseUrl.concat(Endpoints.SECURITY_V1).concat(Endpoints.FIND_BY_NAME).concat("/" + name) ;
        try {
            Response response = get(url);
            return map(response, SecurityOutputDto.class);
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

    public HTTPResponse<OrderOutputDto> createOrder(UUID userId, UUID securityId, Double price,
                                                       Long quantity, OrderType type) {
        String url = baseUrl.concat(Endpoints.ORDER_V1).concat(Endpoints.CREATE);
        OrderInputDto order = new OrderInputDto(userId, securityId, price, quantity, type);
        try {
            String data = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(order);
            Response response = post(url, data);
            return map(response, OrderOutputDto.class);
        } catch (Exception e) {
            return new HTTPResponse<>();
        }
    }

    public HTTPResponseList<TradeOutputDto> findAllTrades() {
        String url = baseUrl.concat(Endpoints.TRADE_V1).concat(Endpoints.FIND_ALL);
        try {
            Response response = get(url);
            return mapList(response, TradeOutputDto.class);
        } catch (Exception e) {
            return new HTTPResponseList<>();
        }
    }

    public HTTPResponse<OrderOutputDto> findOrderById(String id) {
        String url = baseUrl.concat(Endpoints.ORDER_V1).concat(Endpoints.FIND_BY_ID).concat("/" + id) ;;
        try {
            Response response = get(url);
            return map(response, OrderOutputDto.class);
        } catch (Exception e) {
            return new HTTPResponse<>();
        }
    }
}
