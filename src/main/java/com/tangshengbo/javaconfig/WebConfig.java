package com.tangshengbo.javaconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by TangShengBo on 2018/11/26
 */
//@EnableWebMvc
//@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@Configuration
//@ComponentScan(value = {"com.tangshengbo.controller"})
@ImportResource(value = {"classpath:spring-mvc.xml"})
public class WebConfig extends WebMvcConfigurerAdapter {

//    @Bean
//    public ViewResolver viewResolver() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setViewClass(JstlView.class);
//        viewResolver.setPrefix("/WEB-INF/views/");
//        viewResolver.setSuffix(".jsp");
//        return viewResolver;
//    }
//
//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }
}
