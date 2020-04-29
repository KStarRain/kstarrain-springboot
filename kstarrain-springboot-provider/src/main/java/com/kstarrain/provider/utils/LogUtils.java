package com.kstarrain.provider.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: DongYu
 * @create: 2020-04-26 09:11
 * @description:
 */
@Slf4j
public class LogUtils {



    public static String packageReq(HttpServletRequest request, List<MultipartFile> files){

        StringBuilder reqInfo = new StringBuilder();
        String method = request.getMethod();
        reqInfo.append("\n    Request Url     ： ").append(RequestUtils.getURL(request, false)).append(request.getRequestURI());
        if (StringUtils.isNotBlank(request.getQueryString())){
            reqInfo.append("?").append(request.getQueryString());
        }

        reqInfo.append("\n    Request Method  ： ").append(method);
        reqInfo.append("\n    Request Headers");
        String contentType = request.getContentType();
        if (StringUtils.isNotBlank(contentType)){
            reqInfo.append("\n      Content-Type  ： ").append(contentType);
        }
        if (StringUtils.isNotBlank(request.getHeader(HttpHeaders.USER_AGENT))){
            reqInfo.append("\n      User-Agent    ： ").append(request.getHeader(HttpHeaders.USER_AGENT));
        }
        if (StringUtils.isNotBlank(request.getHeader(HttpHeaders.COOKIE))){
            reqInfo.append("\n      Cookie        ： ").append(request.getHeader(HttpHeaders.COOKIE));
        }

        if (StringUtils.isNotBlank(request.getQueryString())){
            reqInfo.append("\n    Query String    ： ").append(request.getQueryString());
        }

        if (StringUtils.equalsIgnoreCase(request.getMethod(), HttpMethod.GET.name())){
            return reqInfo.toString();
        }

        if (StringUtils.isNotBlank(contentType) && contentType.contains("x-www-form-urlencoded")){
            String requestBody = RequestUtils.getRequestBodyStr(request);
            if (StringUtils.isNotBlank(requestBody)) {
                reqInfo.append("\n    Form Data       ： ").append(requestBody);
            }
        } else if (StringUtils.isNotBlank(contentType) && contentType.contains("multipart/form-data")){
            String formData = "";
            if (CollectionUtils.isNotEmpty(files)){
                for (MultipartFile file : files) {
                    if (!file.isEmpty()){
                        formData = formData + file.getName()+"=MultipartFile["+file.getOriginalFilename()+"]&";
                    }
                }
            }
            formData = formData + RequestUtils.getRequestBodyStr(request);
            if (StringUtils.isNotBlank(formData)){
                formData = formData.endsWith("&") ? formData.substring(0, formData.length() - 1) : formData;
                reqInfo.append("\n    Form Data       ： ").append(formData);
            }

        } else {
            String requestBody = RequestUtils.getRequestBodyStr(request);
            if (StringUtils.isNotBlank(requestBody)) {
                reqInfo.append("\n    Request Body    ： ").append(requestBody);
            }
        }
        return reqInfo.toString();
    }


    public static String packageRes(String responseBody){
        return packageRes(null, responseBody);
    }


    public static String packageRes(Integer statusCode, String responseBody){
        StringBuilder resInfo = new StringBuilder();
        if (statusCode != null){
            resInfo.append("\n    Response Code   ： ").append(statusCode);
        }
        resInfo.append("\n    Response Body   ： ").append(responseBody);
        return resInfo.toString();
    }



}
