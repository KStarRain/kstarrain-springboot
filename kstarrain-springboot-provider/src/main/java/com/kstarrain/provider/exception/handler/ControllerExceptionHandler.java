package com.kstarrain.provider.exception.handler;

import com.kstarrain.framework.api.dto.response.ResultDTO;
import com.kstarrain.provider.exception.BizErrorCode;
import com.kstarrain.provider.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Dong Yu
 * @create: 2019-07-26 10:57
 * @description: Controller异常处理
 */
@Slf4j
@ControllerAdvice  //无法处理filter的异常
public class ControllerExceptionHandler {

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public ResponseEntity<ResultDTO> handleBizException(BizException e) {

        log.error("【业务异常】-- [errorCode：{}， errorMsg：{}]", e.getErrorCode(), e.getErrorMessage(), e);
        ResultDTO<Object> result = new ResultDTO<>();
        result.setSuccess(false);
        result.setCode(e.getErrorCode());
        result.setMessage(e.getErrorMessage());
        return ResponseEntity.ok(result);

    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ResultDTO> handleException(Exception e) {
        log.error("【全局异常】-- [{}]", e.getMessage(), e);
        ResultDTO<Object> result = new ResultDTO<>();
        result.setSuccess(false);
        result.setCode(BizErrorCode.SYSTEM_ERROR.getCode());
        result.setMessage(BizErrorCode.SYSTEM_ERROR.getMessage());
        return ResponseEntity.ok(result);
    }




}
