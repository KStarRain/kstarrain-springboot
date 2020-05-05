package com.kstarrain.provider.exception;

import com.kstarrain.framework.web.exception.ErrorCode;

public enum BizErrorCode implements ErrorCode {

    SYSTEM_ERROR                                   ("9999", "服务器正忙，请稍后再试"),
    INCORRECT_PARAMETERS                           ("0002", "Incorrect parameters"),
    IMPORT_DATA_EMPTY                              ("0003", "导入的数据不能为空"),

    ;

    private String code;
    private String desc;

    BizErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

}
