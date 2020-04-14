package com.kstarrain.provider.exception;

import lombok.Getter;

/**
 * @author: Dong Yu
 * @create: 2019-04-25 08:15
 * @description: 业务异常类
 */
@Getter
public class BizException extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;


    public BizException() {
        super();
        this.errorCode = null;
        this.errorMessage = null;
    }


    public BizException(String errorMessage) {
        super(errorMessage);
        this.errorCode = null;
        this.errorMessage = errorMessage;
    }


    public BizException(String errorCode, String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BizException(BizErrorCode bizErrorCode){
        super(bizErrorCode.getMessage());
        this.errorCode = bizErrorCode.getCode();
        this.errorMessage = bizErrorCode.getMessage();
    }

}
