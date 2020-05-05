package com.kstarrain.provider;

import com.kstarrain.framework.web.exception.handler.ControllerExceptionHandler;
import com.kstarrain.framework.web.filter.RequestWrapperFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({RequestWrapperFilter.class, ControllerExceptionHandler.class})
@MapperScan(basePackages ={"com.kstarrain.provider.persistence.mappers"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
