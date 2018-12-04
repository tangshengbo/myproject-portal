package com.tangshengbo.interceptor;

import com.alibaba.fastjson.JSON;
import com.tangshengbo.core.crypto.EncryptUtil;
import com.tangshengbo.core.crypto.SignUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Tangshengbo on 2018/12/4
 */
public class SecurityIntercept extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityIntercept.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Boolean rsa = Boolean.valueOf(request.getParameter("rsa"));
        if (!rsa) {
            return true;
        }
        try {
            String requestStr = request.getParameter("requestStr");
            if (StringUtils.isEmpty(requestStr)) {
                return false;
            }
            String sign = request.getParameter("sign");
            String decRequestStr = EncryptUtil.decrypt(requestStr);
            Map<String, Object> decMap = JSON.parseObject(decRequestStr);
            String decContent = String.valueOf(decMap.get("content"));
            logger.info("decContent:{}", decContent);
            request.setAttribute("content", decContent);
            return SignUtil.verifySign(decContent, sign);
        } catch (Exception e) {
            logger.error("{}", e);
            try (ServletOutputStream outputStream = response.getOutputStream()) {
                IOUtils.write("解密失败", outputStream, "UTF-8");
                outputStream.flush();
                return false;
            }
        }
    }
}
