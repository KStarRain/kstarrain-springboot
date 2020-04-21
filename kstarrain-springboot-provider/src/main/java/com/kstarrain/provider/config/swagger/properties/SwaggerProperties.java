package com.kstarrain.provider.config.swagger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: DongYu
 * @create: 2020-04-21 14:32
 * @description:
 */
@Data
@ConfigurationProperties("spring.swagger")
public class SwaggerProperties {

    /** 文档页标题 */
    private String title;

    /** 描述 */
    private String description;

    /** 版本号 */
    private String version;

    /** 条款地址(不可见)，公司内部使用的话不需要配 */
    private String termsOfServiceUrl;

    /** 联系人名字 */
    private String contactName;

    /** 联系人网址 */
    private String contactUrl;

    /** 联系人邮箱 */
    private String contactEmail;

}
