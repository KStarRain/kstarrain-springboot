package com.kstarrain.provider.filter;

import com.kstarrain.provider.enums.LogKeyEnum;
import com.kstarrain.provider.utils.KeyGenUtils;
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

        try {
            // 日志中设置 request_id
            MDC.put(LogKeyEnum.REQUEST_ID.getCode(), KeyGenUtils.newUuid());
            // 将servletRequest封装成ContentCachingRequestWrapper后，body被读取时，会被它缓存
            filterChain.doFilter(new ContentCachingRequestWrapper(httpServletRequest), httpServletResponse);

        } finally {
            // 清除日志中的 request_id
            MDC.remove(LogKeyEnum.REQUEST_ID.getCode());
        }

    }

}
