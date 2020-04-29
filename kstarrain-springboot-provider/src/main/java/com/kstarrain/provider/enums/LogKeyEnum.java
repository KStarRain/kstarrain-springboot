package com.kstarrain.provider.enums;

/**
 * @author: Dong Yu
 * @create: 2019-08-02 14:10
 * @description:
 */

public enum LogKeyEnum {

    REQUEST_ID("request_id",          "请求唯一标识"),
    ;

    private String code;
    private String desc;


    LogKeyEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
