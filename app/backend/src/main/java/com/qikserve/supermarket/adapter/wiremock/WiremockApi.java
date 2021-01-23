package com.qikserve.supermarket.adapter.wiremock;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockProduct;
import com.qikserve.supermarket.adapter.RestApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WiremockApi {
    @Value("integration.wiremock.baseUrl")
    private String baseUrl;

    private RestApi restApi;

    public WiremockApi(RestApi restApi) {
        this.restApi = restApi;
    }

    public WiremockProduct getOne(String id) {
        String response = restApi.get(productUri().concat("/").concat(id));
        return new Gson().fromJson(response, WiremockProduct.class);
    }

    public List<WiremockProduct> getAll() {
        String response = restApi.get(productUri());
        return new Gson().fromJson(response, new TypeToken<List<WiremockProduct>>(){}.getType());
    }

    private String productUri() {
        return "http://".concat(baseUrl).concat("/products");
    }
}
