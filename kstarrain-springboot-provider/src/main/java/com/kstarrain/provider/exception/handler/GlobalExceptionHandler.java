package com.kstarrain.provider.exception.handler;

import com.kstarrain.framework.api.dto.response.ResultDTO;
import com.kstarrain.provider.exception.BizErrorCode;
import com.kstarrain.provider.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Dong Yu
 * @create: 2019-07-25 14:35
 * @description: 全局异常处理（该类主要用于处理filter异常，也可以处理controller异常，但是本项目处理controller异常使用ControllerExceptionHandler类）
 */
@Slf4j
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class GlobalExceptionHandler implements ErrorController {

    private static final String ERROR_ATTRIBUTE = DefaultErrorAttributes.class.getName() + ".ERROR";


    @RequestMapping
    @ResponseBody
    public ResponseEntity<ResultDTO> errorHanlder(HttpServletRequest request, HttpServletResponse response) {

        System.out.println(request.getContentType());
        Throwable e = (Throwable) request.getAttribute(ERROR_ATTRIBUTE);
        if (e == null) {
            e = (Throwable) request.getAttribute("javax.servlet.error.exception");
        }

        if (e instanceof BizException) {
            ResultDTO result = new ResultDTO();
            result.setSuccess(false);
            result.setCode(((BizException) e).getErrorCode());
            result.setMessage(((BizException) e).getErrorMessage());
            log.error("【业务异常】-- [errorCode：{}， errorMsg：{}]", ((BizException) e).getErrorCode(), ((BizException) e).getErrorMessage(), e);
            return ResponseEntity.ok(result);
        } else {
            if (e != null){
                ResultDTO result = new ResultDTO();
                result.setSuccess(false);
                result.setCode(BizErrorCode.SYSTEM_ERROR.getCode());
                result.setMessage(BizErrorCode.SYSTEM_ERROR.getMessage());
                log.error("【全局异常】-- [{}]", e.getMessage(), e);
                return ResponseEntity.ok(result);
            }
        }

        log.error("【全局异常】--  responseCode[{}]",  response.getStatus());
        return null;
    }



    @Override
    public String getErrorPath() {
        return "/error";
    }
}