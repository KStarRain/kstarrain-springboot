package com.kstarrain.provider;

import com.kstarrain.framework.web.aspect.AspectLog;
import com.kstarrain.framework.web.exception.handler.ControllerExceptionHandler;
import com.kstarrain.framework.web.filter.RequestWrapperFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
//@MapperScan(basePackages ={"com.kstarrain.provider.persistence.mappers"})
@Import({AspectLog.class, RequestWrapperFilter.class, ControllerExceptionHandler.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
