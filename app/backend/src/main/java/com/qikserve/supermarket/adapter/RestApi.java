package com.qikserve.supermarket.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RestApi {
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<String> get(String url) {
        String result = null;
        try {
            result = this.restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<String>(e.getResponseBodyAsString(), e.getStatusCode());
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
