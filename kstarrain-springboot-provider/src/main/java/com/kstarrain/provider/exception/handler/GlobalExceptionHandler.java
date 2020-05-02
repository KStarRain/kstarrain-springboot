package com.kstarrain.provider.exception.handler;

import com.kstarrain.framework.api.dto.response.ResultDTO;
import com.kstarrain.provider.enums.LogKeyEnum;
import com.kstarrain.provider.exception.BizErrorCode;
import com.kstarrain.provider.exception.BizException;
import com.kstarrain.provider.utils.JacksonUtils;
import com.kstarrain.provider.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: Dong Yu
 * @create: 2019-07-25 14:35
 * @description: 全局异常处理（包含filter异常）
 */
@Slf4j
@RestController
public class GlobalExceptionHandler extends AbstractErrorController {

    private static final String ERROR_PATH = "/error";


    public GlobalExceptionHandler(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }


    @RequestMapping(value = ERROR_PATH)
    public ResponseEntity<ResultDTO> errorHandler(HttpServletRequest request) {

        try {
            Throwable e = (Throwable) request.getAttribute("javax.servlet.error.exception");

            Map<String, Object> attributes = getErrorAttributes(request, false);
            String url = RequestUtils.getURL(request, false) + attributes.get("path");

            ResultDTO result = new ResultDTO();
            result.setSuccess(false);
            result.setCode(BizErrorCode.SYSTEM_ERROR.getCode());
            result.setMessage(BizErrorCode.SYSTEM_ERROR.getMessage());

            if (e == null) {
                log.error("【filter-系统异常】-- url:{},  异常信息：{}", url, JacksonUtils.toJSONString(attributes));
            } else if (e instanceof BizException) {
                result.setCode(((BizException) e).getErrorCode());
                result.setMessage(((BizException) e).getErrorMessage());
                log.error("【filter-业务异常】-- url:{},  异常信息：[{}] [{}]", url, ((BizException) e).getErrorCode(),((BizException) e).getErrorMessage());
            } else {
                log.error("【filter-系统异常】-- url:{},  异常信息：{}", url, e.getMessage(), e);
            }

            return ResponseEntity.ok().body(result);
        } finally {
            // 清除日志中的 request_id
            MDC.remove(LogKeyEnum.REQUEST_ID.getCode());
        }
    }





    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}