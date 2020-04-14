package com.kstarrain.provider.enums;

public enum AliveFlagEnum {

    /**
     * 数据存活枚举
     */

    ENABLED                          (1,          "数据有效"),
    DISABLED                         (0,          "数据无效")
    ;

    private Integer code;
    private String desc;

    AliveFlagEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
