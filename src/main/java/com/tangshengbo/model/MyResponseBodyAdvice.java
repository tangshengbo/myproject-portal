package com.tangshengbo.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.tangshengbo.core.ResponseMessage;
import com.tangshengbo.core.security.EncryptUtil;
import com.tangshengbo.core.security.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

/**
 * Created by TangShengBo on 2018/12/8
 */
public class MyResponseBodyAdvice implements ResponseBodyAdvice {

    private static final Logger logger = LoggerFactory.getLogger(MyResponseBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.hasMethodAnnotation(ResponseBodyEncode.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        logger.info("body:{}, MethodParameter:{}, MediaType:{}, selectedConverterType:{}", body, returnType,
                selectedContentType, selectedConverterType);
        if (!(body instanceof ResponseMessage)) {
            return body;
        }
        ResponseMessage responseMessage = (ResponseMessage) body;
        String jsonString = JSON.toJSONString(responseMessage.getData());
        logger.info("加密前:{}", jsonString);
        Map<String, Object> params = Maps.newLinkedHashMap();
        params.put("content", jsonString);
        String encryptStr = null;
        try {
            encryptStr = EncryptUtil.encrypt(JSON.toJSONString(params));
            Map<String, Object> ecParams = JSON.parseObject(encryptStr);
            String sign = SignUtil.sign(jsonString);
            ecParams.put("sign", sign);
            encryptStr = ((JSONObject) ecParams).toJSONString();
            logger.info("加密后:{}", encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        responseMessage.setData(encryptStr);
        return responseMessage;

    }
}
