package com.tangshengbo.javaconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by TangShengBo on 2018/11/26
 */
//@EnableWebMvc
//@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@Configuration
//@ComponentScan(value = {"com.tangshengbo.controller"})
@ImportResource(value = {"classpath:spring-context.xml"})
public class WebConfig {

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
