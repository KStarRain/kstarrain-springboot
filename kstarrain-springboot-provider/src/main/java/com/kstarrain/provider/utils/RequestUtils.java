package com.kstarrain.provider.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public final class RequestUtils {

    private static final String UNKNOWN                = "unknown";
    private static final String LOCALHOST              = "127.0.0.1";

    private RequestUtils() {
    }


    public static String getURL(HttpServletRequest req, boolean withContextPath) {
        String scheme = req.getScheme();             // http
        String serverName = req.getServerName();     // hostname.com
        int serverPort = req.getServerPort();        // 80
        String contextPath = req.getContextPath();   // /myWebapp

        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if (serverPort != 80 && serverPort != 443) {
            url.append(":").append(serverPort);
        }

        if (withContextPath) {
            url.append(contextPath);
        }
        return url.toString();
    }


    public static String getRemoteIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (LOCALHOST.equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                InetAddress inet = null;

                try {
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException var4) {
                    log.error(var4.getMessage(), var4);
                }
            }
        }

        if (ipAddress != null && ipAddress.length() > 15 && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }

        log.info("访问用户的真实地址为 {}", ipAddress);
        return ipAddress;
    }


    public static Map<String,String> parseQueryString(String queryString){

        if (StringUtils.isBlank(queryString)){
            return new HashMap<>();
        }
        String[] queryStringSplit = queryString.split("&");
        Map<String,String> queryStringMap = new HashMap<>(queryStringSplit.length);
        for (String qs : queryStringSplit) {
            int index = qs.indexOf("=");
            if (index < 0){
                queryStringMap.put(qs,"");
            } else if (index > 0){
                queryStringMap.put(qs.substring(0, index), qs.substring(index+1));
            }
        }
        return queryStringMap;
    }

    /**
     * 使用该方法要确保请求前调用了 new ContentCachingRequestWrapper() 方法
     * @param request
     * @return
     */
    public static String getRequestBodyStr(HttpServletRequest request){

        try {
            String contentType = request.getContentType();
            if (StringUtils.isNotBlank(contentType) && (contentType.contains("multipart/form-data") || contentType.contains("x-www-form-urlencoded"))) {
                return getSimpleFormData(request);
            } else {
                ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
                if(wrapper != null) {
                    byte[] buf = wrapper.getContentAsByteArray();
                    if(buf.length > 0) {
                        String payload;
                        try {
                            payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                        } catch (UnsupportedEncodingException e) {
                            payload = "[unknown]";
                        }
                        return payload.replaceAll("\\n","");
                    }
                }
                return "";
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

    private static String getSimpleFormData(HttpServletRequest request) {
        Map<String, String> queryStringMap = parseQueryString(request.getQueryString());
        StringBuilder formData = new StringBuilder();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String[] values = request.getParameterValues(name);
            boolean isFilterQS = false;
            for (String value : values) {
                if (isFilterQS){
                    formData.append(name).append("=").append(value).append("&");
                } else {
                    if (StringUtils.equalsIgnoreCase(value, queryStringMap.get(name))){
                        isFilterQS = true;
                    } else {
                        formData.append(name).append("=").append(value).append("&");
                    }
                }
            }
        }
        String formDataStr = formData.toString();
        return formDataStr.endsWith("&") ? formDataStr.substring(0, formDataStr.length() - 1) : formDataStr;
    }

}