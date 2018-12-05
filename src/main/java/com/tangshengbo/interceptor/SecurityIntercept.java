package com.tangshengbo.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.tangshengbo.core.security.EncryptUtil;
import com.tangshengbo.core.security.SignUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
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
            String decRequestStr = EncryptUtil.decrypt(requestStr);
            Map<String, Object> decMap = JSON.parseObject(decRequestStr);
            String decContent = String.valueOf(decMap.get("content"));
            String sign = String.valueOf(decMap.get("sign"));
            logger.info("decContent:{},sign:{}", decContent, sign);
            request.setAttribute("content", decContent);
            boolean verifySign = SignUtil.verifySign(decContent, sign);
            logger.info("验签结果:{}", verifySign);
            modifyRequestParam(request, decContent);
            logger.info("获取解密后的参数:{}", request.getParameter("content"));
            return verifySign;
        } catch (Exception e) {
            logger.error("{}", e);
            try (ServletOutputStream outputStream = response.getOutputStream()) {
                IOUtils.write("解密失败", outputStream, "UTF-8");
                outputStream.flush();
                return false;
            }
        }
    }

    private void modifyRequestParam(HttpServletRequest request, String decContent) {
        try {
            Class clazz = request.getClass();
            Field requestField = clazz.getDeclaredField("request");
            requestField.setAccessible(true);
            Object innerRequest = requestField.get(request);//获取到request对象

            //设置尚未初始化 (否则在获取一些参数的时候，可能会导致不一致)
            Field field = innerRequest.getClass().getDeclaredField("parametersParsed");
            field.setAccessible(true);
            field.setBoolean(innerRequest, false);

            Field coyoteRequestField = innerRequest.getClass().getDeclaredField("coyoteRequest");
            coyoteRequestField.setAccessible(true);
            //获取到coyoteRequest对象
            Object coyoteRequestObject = coyoteRequestField.get(innerRequest);
            Field parametersField = coyoteRequestObject.getClass().getDeclaredField("parameters");
            parametersField.setAccessible(true);
            //获取到parameter的对象
            Object parameterObject = parametersField.get(coyoteRequestObject);
            //获取到parameter的对象
            Field hashTabArrField = parameterObject.getClass().getDeclaredField("paramHashValues");
            hashTabArrField.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, ArrayList<String>> map = (Map<String, ArrayList<String>>) hashTabArrField.get(parameterObject);
            map.put("content", Lists.newArrayList(decContent));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    class ParameterRequestWrapper extends HttpServletRequestWrapper {

        private Map<String, String[]> params = new HashMap<>();

        @SuppressWarnings("unchecked")
        public ParameterRequestWrapper(HttpServletRequest request) {
            // 将request交给父类，以便于调用对应方法的时候，将其输出，其实父亲类的实现方式和第一种new的方式类似
            super(request);
            //将参数表，赋予给当前的Map以便于持有request中的参数
            this.params.putAll(request.getParameterMap());
        }

        //重载一个构造方法
        public ParameterRequestWrapper(HttpServletRequest request, Map<String, Object> extendParams) {
            this(request);
            addAllParameters(extendParams);//这里将扩展参数写入参数表
        }

        @Override
        public String getParameter(String name) {//重写getParameter，代表参数从当前类中的map获取
            String[] values = params.get(name);
            if (values == null || values.length == 0) {
                return null;
            }
            return values[0];
        }

        public String[] getParameterValues(String name) {//同上
            return params.get(name);
        }

        //增加多个参数
        public void addAllParameters(Map<String, Object> otherParams) {
            for (Map.Entry<String, Object> entry : otherParams.entrySet()) {
                addParameter(entry.getKey(), entry.getValue());
            }
        }

        //增加参数
        public void addParameter(String name, Object value) {
            if (value != null) {
                if (value instanceof String[]) {
                    params.put(name, (String[]) value);
                } else if (value instanceof String) {
                    params.put(name, new String[]{(String) value});
                } else {
                    params.put(name, new String[]{String.valueOf(value)});
                }
            }
        }
    }
}
