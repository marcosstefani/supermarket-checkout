package com.qikserve.supermarket.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
public class RestApi {
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<String> get(String url) {
        String result = null;
        try {
            log.info("Request", kv("url", url));
            result = this.restTemplate.getForObject(url, String.class);
            log.info("Response", kv("response", result));
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<String>(e.getResponseBodyAsString(), e.getStatusCode());
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
