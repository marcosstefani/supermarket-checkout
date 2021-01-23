package com.qikserve.supermarket.adapter.wiremock;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qikserve.supermarket.adapter.RestApi;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockProduct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WiremockApi {
    @Value("integration.wiremock.baseUrl")
    private String baseUrl;

    private final RestTemplate restTemplate;
    private final RestApi restApi;

    public WiremockApi(RestTemplateBuilder restTemplateBuilder, RestApi restApi) {
        this.restTemplate = restTemplateBuilder.build();
        this.restApi = restApi;
    }

    public WiremockProduct getOne(String id) {
        String response = restApi.get(productUri().concat("/").concat(id)).getBody();
        return new Gson().fromJson(response, WiremockProduct.class);
    }

    public List<WiremockProduct> getAll() {
        String response = restApi.get(productUri()).getBody();
        return new Gson().fromJson(response, new TypeToken<List<WiremockProduct>>(){}.getType());
    }

    private String productUri() {
        return "http://".concat(baseUrl).concat("/products");
    }
}
