package com.tangshengbo.core.extension;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by TangShengBo on 2018/12/9
 */
public class XssFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
