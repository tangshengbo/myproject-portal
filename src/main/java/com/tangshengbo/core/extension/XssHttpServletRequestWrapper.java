package com.tangshengbo.core.extension;

import com.github.pagehelper.StringUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TangShengBo on 2018/12/9
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    HttpServletRequest orgRequest = null;

    private String body;

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
//        orgRequest = request;
//        body = HttpGetBody.getBodyString(request);

    }

    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/>
     * <p>
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
     * <p>
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (null != value) {
            value = xssEncode(value, 0);
        }
        return value;
    }

    @Override

    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];

//        for (int i = 0; i < count; i++) {
//            encodedValues[i] = xssEncode(values[i], 0);
//        }
        return encodedValues;
    }

    @Override
    public Map getParameterMap() {
        HashMap paramMap = (HashMap) super.getParameterMap();
        paramMap = (HashMap) paramMap.clone();
        for (Object o : paramMap.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String[] values = (String[]) entry.getValue();
            for (int i = 0; i < values.length; i++) {
                if (values[i] != null) {
                    values[i] = xssEncode(values[i], 0);
                }
            }
            entry.setValue(values);
        }
        return paramMap;
    }

    @Override

    public ServletInputStream getInputStream() throws IOException {
        ServletInputStream inputStream = null;
        if (StringUtil.isNotEmpty(body)) {
            body = xssEncode(body, 1);
            inputStream = new PostServletInputStream(body);
        }
        return inputStream;

    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/>
     * <p>
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
     * <p>
     * getHeaderNames 也可能需要覆盖
     */

    @Override

    public String getHeader(String name) {
        String value = super.getHeader(xssEncode(name, 0));
        if (value != null) {
            value = xssEncode(value, 0);
        }
        return value;

    }

    /**
     * 将容易引起xss漏洞的半角字符直接替换成全角字符
     *
     * @param s
     * @return
     */

    private static String xssEncode(String s, int type) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);

        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);

            if (type == 0) {

                switch (c) {

                    case '\'':

                        // 全角单引号

                        sb.append('‘');

                        break;

                    case '\"':

                        // 全角双引号

                        sb.append('“');

                        break;

                    case '>':

                        // 全角大于号

                        sb.append('＞');

                        break;

                    case '<':

                        // 全角小于号

                        sb.append('＜');

                        break;

                    case '&':

                        // 全角&符号

                        sb.append('＆');

                        break;

                    case '\\':

                        // 全角斜线

                        sb.append('＼');

                        break;

                    case '#':

                        // 全角井号

                        sb.append('＃');

                        break;

                    // < 字符的 URL 编码形式表示的 ASCII 字符（十六进制格式） 是: %3c

                    case '%':

                        processUrlEncoder(sb, s, i);

                        break;

                    default:

                        sb.append(c);

                        break;

                }

            } else {

                switch (c) {

                    case '>':

                        // 全角大于号

                        sb.append('＞');

                        break;

                    case '<':

                        // 全角小于号

                        sb.append('＜');

                        break;

                    case '&':

                        // 全角&符号

                        sb.append('＆');

                        break;

                    case '\\':

                        // 全角斜线

                        sb.append('＼');

                        break;

                    case '#':

                        // 全角井号

                        sb.append('＃');

                        break;

                    // < 字符的 URL 编码形式表示的 ASCII 字符（十六进制格式） 是: %3c

                    case '%':

                        processUrlEncoder(sb, s, i);

                        break;

                    default:

                        sb.append(c);

                        break;

                }

            }

        }

        return sb.toString();

    }

    public static void processUrlEncoder(StringBuilder sb, String s, int index) {

        if (s.length() >= index + 2) {

            // %3c, %3C

            if (s.charAt(index + 1) == '3' && (s.charAt(index + 2) == 'c' || s.charAt(index + 2) == 'C')) {

                sb.append('＜');

                return;

            }

            // %3c (0x3c=60)

            if (s.charAt(index + 1) == '6' && s.charAt(index + 2) == '0') {

                sb.append('＜');

                return;

            }

            // %3e, %3E

            if (s.charAt(index + 1) == '3' && (s.charAt(index + 2) == 'e' || s.charAt(index + 2) == 'E')) {

                sb.append('＞');

                return;

            }

            // %3e (0x3e=62)

            if (s.charAt(index + 1) == '6' && s.charAt(index + 2) == '2') {

                sb.append('＞');

                return;

            }

        }

        sb.append(s.charAt(index));

    }

    /**
     * 获取最原始的request
     *
     * @return
     */

    public HttpServletRequest getOrgRequest() {

        return orgRequest;

    }

    /**
     * 获取最原始的request的静态方法
     *
     * @return
     */

    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {

        if (req instanceof XssHttpServletRequestWrapper) {

            return ((XssHttpServletRequestWrapper) req).getOrgRequest();

        }

        return req;

    }

    public static class HttpGetBody {
        /**
         * 获取请求Body
         *
         * @param request
         * @return
         */
        public static String getBodyString(ServletRequest request) {
            //StringBuilder sb = new StringBuilder();
            StringBuffer sb = new StringBuffer();
            InputStream inputStream = null;
            BufferedReader reader = null;
            try {
                inputStream = request.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return sb.toString();
        }

    }

    public static class PostServletInputStream extends ServletInputStream {

        private InputStream inputStream;
        private String body;//解析json之后的文本

        public PostServletInputStream(String body) throws IOException {
            this.body = body;
            inputStream = null;
        }


        private InputStream acquireInputStream() throws IOException {
            if (inputStream == null) {
                inputStream = new ByteArrayInputStream(body.getBytes(Charset.forName("UTF-8")));
                //通过解析之后传入的文本生成inputStream以便后面control调用
            }
            return inputStream;
        }

        public void close() throws IOException {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                throw e;
            } finally {
                inputStream = null;
            }
        }

        public boolean markSupported() {
            return false;
        }

        public synchronized void mark(int i) {
            throw new UnsupportedOperationException("mark not supported");
        }


        public synchronized void reset() throws IOException {
            throw new IOException(new UnsupportedOperationException("reset not supported"));
        }

        @Override
        public int read() throws IOException {
            return acquireInputStream().read();

        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }

}
