package com.six.challenge.tradingplatform.cucumber.client.v1;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.six.challenge.tradingplatform.controller.v1.OrderController;
import com.six.challenge.tradingplatform.cucumber.client.v1.model.HTTPResponse;
import com.six.challenge.tradingplatform.cucumber.client.v1.model.HTTPResponseList;
import org.asynchttpclient.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class BaseClient {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    private AsyncHttpClient client;
    protected ObjectMapper mapper = new ObjectMapper();
    private String url;
    private String port;
    protected BaseClient(String url, String port) {
        this.client = Dsl.asyncHttpClient(Dsl.config().
                setUseInsecureTrustManager(true).setKeepAlive(false).build());
        this.url = url;
        this.port = port;
    }

    public HTTPResponse<?> map(Response response) throws Exception {
        HTTPResponse<?> r = new HTTPResponse<>();
        r.setStatus(HttpStatus.resolve(response.getStatusCode()));
        r.setRawResponse(response.getResponseBody());
        return r;
    }

    public <T> HTTPResponse<T> map(Response response, Class<T> type)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T r;
        HTTPResponse<T> httpResponse = new HTTPResponse<>();
        try {
            r = mapper.readValue(response.getResponseBody(), type);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            logger.warn("Error mapping response to " + type.getCanonicalName() + ". Setting empty...");
            r = type.getDeclaredConstructor().newInstance();
        }
        httpResponse.setStatus(HttpStatus.resolve(response.getStatusCode()));
        httpResponse.setRawResponse(response.getResponseBody());
        httpResponse.setResponseObject(r);
        return httpResponse;
    }

    public <T> HTTPResponseList<T> mapList(Response response, Class<T> type) {
        List<T> r;
        HTTPResponseList<T> rList = new HTTPResponseList<>();
        TypeFactory t = TypeFactory.defaultInstance();

        try {
            r = mapper.readValue(response.getResponseBody(), t.constructCollectionType(ArrayList.class, type));
        } catch (Exception e) {
            logger.warn(e.getMessage());
            logger.warn("Error mapping response to " + type.getCanonicalName() + ". Setting empty...");
            r =  new ArrayList<>();
        }

        rList.setResponseObjectList(r);
        rList.setStatus(HttpStatus.resolve(response.getStatusCode()));
        rList.setRawResponse(response.getResponseBody());
        return rList;
    }

    protected Response get(String endpoint) throws ExecutionException, InterruptedException {
        BoundRequestBuilder request = this.client.prepareGet(endpoint);
        Response response = request.execute().get();
        logger.debug("GET to " + response.getUri() + ":" + response.getResponseBody());
        return response;
    }

    protected Response get(String endpoint, Map<String, String> queryParams)
            throws ExecutionException, InterruptedException {
        BoundRequestBuilder request = this.client.prepareGet(endpoint);
        List<Param> params = queryParams.entrySet().stream()
                .map(queryParam -> new Param(queryParam.getKey(), queryParam.getValue())).collect(Collectors.toList());
        request = request.setQueryParams(params);
        Response response = request.execute().get();
        logger.debug("GET to " + response.getUri() + ":" + response.getResponseBody());
        return response;
    }

    protected Response delete(String endpoint) throws ExecutionException, InterruptedException {
        BoundRequestBuilder request = this.client.prepareDelete(endpoint);
        Response response = request.execute().get();
        logger.debug("DELETE to " + response.getUri() + ":" + response.getResponseBody());
        return response;
    }

    protected Response put(String endpoint) throws ExecutionException, InterruptedException {
        BoundRequestBuilder request = this.client.preparePut(endpoint);
        Response response = request.execute().get();
        logger.debug("PUT to " + response.getUri() + ":" + response.getResponseBody());
        return response;
    }

    protected Response post(String endpoint, String data) throws ExecutionException, InterruptedException {
        BoundRequestBuilder request = this.client.preparePost(endpoint);
        request = request.setHeader("Content-Type", "application/json; charset=UTF-8");
        request = request.setBody(data);
        Response response = request.execute().get();
        logger.debug("POST to " + response.getUri() + ":" + response.getResponseBody());
        return response;
    }

    protected Response put(String endpoint, String data) throws ExecutionException, InterruptedException {
        BoundRequestBuilder request = this.client.preparePut(endpoint);
        request = request.setHeader("Content-Type", "application/json; charset=UTF-8");
        request = request.setBody(data);
        Response response = request.execute().get();
        logger.debug("PUT to " + response.getUri() + ":" + response.getResponseBody());
        return response;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public AsyncHttpClient getClient() {
        return client;
    }
}
