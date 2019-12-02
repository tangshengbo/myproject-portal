package com.tangshengbo.core;

import com.alibaba.fastjson.JSONObject;
import com.tangshengbo.enums.ResponseCode;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Tangshengbo
 *
 * @author Tangshengbo
 * @date 2019/12/2
 */
@Component
public class RestTemplateUtil implements InitializingBean {

    @Resource
    private RestTemplate xRestTemplate;

    private static RestTemplate restTemplate;

    public static <T> ResponseMessage<List<T>> exchangeAsList(HttpMethod method, String url, Map<String, Object> params,
                                                              ParameterizedTypeReference<ResponseMessage<List<T>>> responseType) {
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params);
        return restTemplate.exchange(url, method, requestEntity, responseType).getBody();
    }

    public static <T> ResponseMessage<List<T>> postForList(String url, Map<String, Object> params,
                                                           ParameterizedTypeReference<ResponseMessage<List<T>>> responseType) {
        return exchangeAsList(HttpMethod.POST, url, params, responseType);
    }

    /**
     * 远程调用API
     *
     * @param url          地址
     * @param restTemplate 模板
     * @param <T>          泛型
     * @param clazz        泛型
     * @return 集合
     */
    public static <T> List<T> getForList(String url, RestTemplate restTemplate, Class<T> clazz) {
        ResponseMessage<List<T>> data = restTemplate.exchange(url, HttpMethod.GET, initHttpEntity(),
                new ParameterizedTypeReference<ResponseMessage<List<T>>>() {
                }).getBody();
        if (data.getCode() == ResponseCode.SUCCESS.code) {
            return data.getData();
        }
        return Collections.emptyList();
    }


    private static HttpEntity<JSONObject> initHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new HttpEntity<>(headers);
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        restTemplate = this.xRestTemplate;
    }


    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public static void setRestTemplate(RestTemplate restTemplate) {
        RestTemplateUtil.restTemplate = restTemplate;
    }

    public RestTemplate getxRestTemplate() {
        return xRestTemplate;
    }

    public void setxRestTemplate(RestTemplate xRestTemplate) {
        this.xRestTemplate = xRestTemplate;
    }

}
