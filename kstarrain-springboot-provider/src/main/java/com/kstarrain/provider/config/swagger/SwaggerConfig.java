package com.kstarrain.provider.config.swagger;

import com.kstarrain.provider.config.swagger.properties.SwaggerProperties;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: DongYu
 * @create: 2020-04-21 14:25
 * @description: Swagger配置类
 */
@EnableSwagger2
@Configuration
@EnableConfigurationProperties({SwaggerProperties.class})
@ConditionalOnProperty(prefix = "spring.swagger", name = {"enabled"}, havingValue = "true")
public class SwaggerConfig {


    @Autowired
    private SwaggerProperties swaggerProperties;



    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())                // 文档页标题
                .description(swaggerProperties.getDescription())    // 描述
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl()) //（不可见）条款地址，公司内部使用的话不需要配
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail())) // 联系人信息
                .version(swaggerProperties.getVersion()).build(); //版本号
    }

    @Bean
    public Docket configure(SwaggerProperties swaggerProperties) {

        return new Docket(DocumentationType.SWAGGER_2) // 指定api类型为swagger2
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)) // 扫描注解
                .paths(PathSelectors.any())
                .build().apiInfo(this.apiInfo(swaggerProperties)) // 用于定义api文档汇总信息
//                        .useDefaultResponseMessages(false)
//                        .globalResponseMessage(RequestMethod.OPTIONS, responseMessages)
//                        .globalResponseMessage(RequestMethod.GET, responseMessages)
//                        .globalResponseMessage(RequestMethod.POST, responseMessages)
//                        .globalResponseMessage(RequestMethod.PUT, responseMessages)
//                        .globalResponseMessage(RequestMethod.DELETE, responseMessages)
//
//                        .globalOperationParameters(Collections.singletonList((new ParameterBuilder())
//                                .name("Authorization")
//                                .description("授权获得的访问令牌")
//                                .modelRef(new ModelRef("string"))
//                                .parameterType("header")
//                                .required(false).build()))
//                        .enableUrlTemplating(true)
//                        .forCodeGeneration(true)
                ;
    }



}
