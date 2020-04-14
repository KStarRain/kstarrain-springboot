package com.kstarrain.provider.constants;

import org.springframework.http.MediaType;

/**
 * @author: DongYu
 * @create: 2019-12-20 08:52
 * @description:
 */
public class ExtraMediaType extends MediaType {

    public static final String APPLICATION_XML_UTF8 = "application/xml;charset=UTF-8";
    public static final String TEXT_XML_UTF8 = "text/xml;charset=UTF-8";


    public ExtraMediaType(String type) {
        super(type);
    }
}
