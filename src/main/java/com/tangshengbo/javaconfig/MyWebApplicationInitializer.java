package com.tangshengbo.javaconfig;

import com.tangshengbo.core.extension.XssFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;

/**
 * Created by TangShengBo on 2018/11/26
 */
public class MyWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

//        ServletRegistration.Dynamic dynamic = servletContext.addServlet("initServlet", InitServlet.class);
//        dynamic.setLoadOnStartup(-1);
//        dynamic.addMapping("/init");

        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setAsyncSupported(true);
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, false, "*");

        FilterRegistration.Dynamic corsFilter = servletContext.addFilter("corsFilter", corsFilter());
        corsFilter.addMappingForUrlPatterns(null, false, "*");

        FilterRegistration.Dynamic xssFilter = servletContext.addFilter("xssFilter", XssFilter.class);
        xssFilter.addMappingForUrlPatterns(null, false, "*");
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
//        registration.setInitParameter("detectAllHandlerAdapters", "false");
        int maxFileSize = (1024 * 1024) * 100;
        registration.setMultipartConfig(new MultipartConfigElement("E:/temp/", maxFileSize, maxFileSize, 0));
    }

    private CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addExposedHeader("Content-Disposition");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
