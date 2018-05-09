package com.tangshengbo.interceptor;


import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * rest template 耗时统计
 */
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution)
            throws IOException {
        Stopwatch start = Stopwatch.createStarted();
        ClientHttpResponse response = null;
        try {
            response = execution.execute(request, body);
            logger.info("rest_start# request={} cost={} status={} #rest_end", request.getURI(),
                    String.valueOf(start.elapsed(TimeUnit.MILLISECONDS)), "SUCCESS");
        } catch (Exception e) {
            logger.info("rest_start# request={} cost={} status={} #rest_end", request.getURI(),
                    String.valueOf(start.elapsed(TimeUnit.MILLISECONDS)), "FAIL");
            throw e;
        }
        return response;
    }
}
