package com.kstarrain.provider.filter;

import com.kstarrain.framework.common.utils.UUIDUtils;
import com.kstarrain.provider.enums.LogKeyEnum;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: DongYu
 * @create: 2020-04-27 16:58
 * @description:
 */
@Component
public class RequestWrapperFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {


        // 日志中设置 request_id
        MDC.put(LogKeyEnum.REQUEST_ID.getCode(), UUIDUtils.newUuid());

        /** HttpServletRequest 请求中的 body 内容仅能调用 request.getInputStream()，request.getReader()和request.getParameter("key") 方法读取一次，
         *  重复读取会报 java.io.IOException: Stream closed 异常
         *  因此将servletRequest封装成ContentCachingRequestWrapper后，body被读取时，会被它缓存
         */
        filterChain.doFilter(new ContentCachingRequestWrapper(httpServletRequest), httpServletResponse);

        // 清除日志中的 request_id
        MDC.remove(LogKeyEnum.REQUEST_ID.getCode());
    }

}
