package com.tangshengbo.core.extension;

import com.alibaba.fastjson.JSON;
import com.tangshengbo.core.security.EncryptUtil;
import com.tangshengbo.core.security.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;

import java.util.Map;

/**
 * Created by TangShengBo on 2018/12/6
 */
public class MyRequestParamMethodArgumentResolver extends RequestParamMethodArgumentResolver {

    private static final Logger logger = LoggerFactory.getLogger(MyRequestParamMethodArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestParamDecode.class);
    }

    public MyRequestParamMethodArgumentResolver(boolean useDefaultResolution) {
        super(useDefaultResolution);
    }

    @Override
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        logger.info("RequestParamDecode参数解析开始..........");
        Object resultValue = super.resolveName(name, parameter, request);
        if (!(resultValue instanceof String)) {
            return resultValue;
        }
        logger.info("解密前参数:{}", resultValue);
        String content = String.valueOf(resultValue);
        String decRequestStr = EncryptUtil.decrypt(content);
        Map<String, Object> decMap = JSON.parseObject(decRequestStr);
        String decContent = String.valueOf(decMap.get("content"));
        String sign = String.valueOf(decMap.get("sign"));
        logger.info("decContent:{},sign:{}", decContent, sign);
        boolean verifySign = SignUtil.verifySign(decContent, sign);
        logger.info("验签结果:{}", verifySign);
        logger.info("获取解密后的参数:{}", decContent);
        return decContent;
    }
}
