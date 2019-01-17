package com.chinalife.datacheck;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.chinalife.datacheck.config.ResponseBodyWrapFactoryBean;

/**
 * 入口类
 * @author changping123
 * 2018年5月4日上午8:38:58
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
       // SpringApplication application = new SpringApplication(Application.class);
        //application.setBannerMode(Banner.Mode.OFF);
       // application.run(args);
        SpringApplication.run(Application.class,args);
    }
    @Bean
    public ResponseBodyWrapFactoryBean getResponseBodyWrap() {
        return new ResponseBodyWrapFactoryBean();
    }
    @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(Application.class);
    }
}
