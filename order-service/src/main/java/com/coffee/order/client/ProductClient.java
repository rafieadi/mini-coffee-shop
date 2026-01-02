package com.coffee.order.client;

import com.coffee.order.dto.ProductResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductClient {

    private final RestTemplate restTemplate;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProductResponse getProduct(Long ProductId) {
        return restTemplate.getForObject(
                "http://localhost:8083/products/" + ProductId,
                ProductResponse.class
        );
    }
}
