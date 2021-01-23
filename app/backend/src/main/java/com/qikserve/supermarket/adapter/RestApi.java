package com.qikserve.supermarket.adapter;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Service
public class RestApi {
    private static Logger logger = LoggerFactory.getLogger(RestApi.class);
    private RetryRegistry retryRegistry;

    @Value("integration.rest.retry.maxAttempts")
    private Integer maxAttempts;
    @Value("integration.rest.retry.waitDurationMilis")
    private Integer millis;

    private Retry retry = retryRegistry.retry("myRetryPolice", retryPolice());

    public RestApi(RetryRegistry retryRegistry) {
        this.retryRegistry = retryRegistry;
    }

    private RetryConfig retryPolice() {
        return RetryConfig.<Response>custom()
                .maxAttempts(maxAttempts)
                .waitDuration(Duration.ofMillis(millis))
                .retryExceptions(NullPointerException.class, TimeoutException.class, IOException.class)
            .retryOnResult(this::checkResult)
            .build();
    }

    private boolean checkResult(Response response) {
        return Arrays.asList(HttpStatus.REQUEST_TIMEOUT.value(), HttpStatus.GATEWAY_TIMEOUT.value()).contains(response.code());
    }

    private String execute(Request request) {
        CheckedFunction0<Response> retryableSupplier = Retry
                .decorateCheckedSupplier(retry, () -> new OkHttpClient().newCall(request).execute());

        Response response = Try.of(retryableSupplier).get();
        ResponseBody responseBody = response.body();

        if (response.isSuccessful() && responseBody != null) {
            String body = null;
            try {
                body = responseBody.string();
            } catch (IOException e) {
                logger.error("Error Transformation");
                throw new RuntimeException(e.getMessage());
            }
            logger.info("Response", kv("response", response));
            return body;
        } else {
            logger.error("Response Error", kv("response", response));
            return null;
        }
    }

    public String get(String url) {
        Request build = new Request.Builder()
                .url(url)
                .build();
        return execute(build);
    }
}
