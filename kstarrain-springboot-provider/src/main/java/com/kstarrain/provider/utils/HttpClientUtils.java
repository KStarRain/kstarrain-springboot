package com.kstarrain.provider.utils;

import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: Dong Yu
 * @create: 2019-08-14 08:59
 * @description: 基于spring下RestTemplate的Http调用工具类
 */
public class HttpClientUtils {

    private static final RestTemplate restTemplate;


    /**
     * 重新设置restTemplate中的StringHttpMessageConverter字符集为UTF-8，解决中文乱码问题
     */
    static {

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000); //建立连接的超时时间(未建立连接会报错 connect timed out)
        requestFactory.setReadTimeout(20000);   //已经建立连接，并开始读取服务端资源的超时时间(服务端处理业务太慢超时会报错 Read timed out)


        restTemplate = new RestTemplate(requestFactory);
        // 删除 StringHttpMessageConverter（ISO-8859-1）
        restTemplate.getMessageConverters().removeIf(converter -> converter instanceof StringHttpMessageConverter);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8)); // 添加 StringHttpMessageConverter（UTF-8）
    }


    /**
     * GET请求调用方式
     *
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendGet(String url, Class<T> responseType) {
        return send(url, HttpMethod.GET, HttpEntity.EMPTY, responseType, null);
    }

    /**
     * GET请求调用方式
     *
     * @param url           请求URL
     * @param responseType  返回对象类型
     * @param pathVariables url路径变量参数
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendGet(String url, Class<T> responseType, Map<String, ?> pathVariables) {
        return send(url, HttpMethod.GET, HttpEntity.EMPTY, responseType, pathVariables);
    }


    /**
     * GET请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendGet(String url, HttpHeaders headers, Class<T> responseType) {
        return send(url, HttpMethod.GET, new HttpEntity<>(headers), responseType, null);
    }

    /**
     * GET请求调用方式
     *
     * @param url           请求URL
     * @param headers       请求头参数
     * @param responseType  返回对象类型
     * @param pathVariables url路径变量参数
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendGet(String url, HttpHeaders headers, Class<T> responseType, Map<String, ?> pathVariables) {
        return send(url, HttpMethod.GET, new HttpEntity<>(headers), responseType, pathVariables);
    }


    /**
     * POST请求调用方式
     *
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @return
     */
    public static <T> ResponseEntity<T> sendPost(String url, Class<T> responseType) {
        return send(url, HttpMethod.POST, HttpEntity.EMPTY, responseType, null);
    }

    /**
     * POST请求调用方式
     *
     * @param url           请求URL
     * @param responseType  返回对象类型
     * @param pathVariables url路径变量参数
     * @return
     */
    public static <T> ResponseEntity<T> sendPost(String url, Class<T> responseType, Map<String, ?> pathVariables) {
        return send(url, HttpMethod.POST, HttpEntity.EMPTY, responseType, pathVariables);
    }


    /**
     * POST请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendPost(String url, HttpHeaders headers, Class<T> responseType) {
        return send(url, HttpMethod.POST, new HttpEntity<>(headers), responseType, null);
    }


    /**
     * POST请求调用方式
     *
     * @param url           请求URL
     * @param headers       请求头参数
     * @param responseType  返回对象类型
     * @param pathVariables url路径变量参数
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendPost(String url, HttpHeaders headers, Class<T> responseType, Map<String, ?> pathVariables) {
        return send(url, HttpMethod.POST, new HttpEntity<>(headers), responseType, pathVariables);
    }


    /**
     * POST请求调用方式
     *
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendPost(String url, Object requestBody, Class<T> responseType) {
        return send(url, HttpMethod.POST, new HttpEntity<>(requestBody), responseType, null);
    }


    /**
     * POST请求调用方式
     *
     * @param url           请求URL
     * @param requestBody   请求参数体
     * @param responseType  返回对象类型
     * @param pathVariables url路径变量参数
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendPost(String url, Object requestBody, Class<T> responseType, Map<String, ?> pathVariables) {
        return send(url, HttpMethod.POST, new HttpEntity<>(requestBody), responseType, pathVariables);
    }


    /**
     * POST请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendPost(String url, HttpHeaders headers, Object requestBody, Class<T> responseType) {
        return send(url, HttpMethod.POST, new HttpEntity<>(requestBody, headers), responseType, null);
    }

    /**
     * POST请求调用方式
     *
     * @param url           请求URL
     * @param headers       请求头参数
     * @param requestBody   请求参数体
     * @param responseType  返回对象类型
     * @param pathVariables url路径变量参数
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendPost(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Map<String, ?> pathVariables) {
        return send(url, HttpMethod.POST, new HttpEntity<>(requestBody, headers), responseType, pathVariables);
    }


    /**
     * PUT请求调用方式
     *
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendPut(String url, Class<T> responseType) {
        return send(url, HttpMethod.PUT, HttpEntity.EMPTY, responseType, null);
    }


    /**
     * PUT请求调用方式
     *
     * @param url           请求URL
     * @param responseType  返回对象类型
     * @param pathVariables url路径变量参数
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendPut(String url, Class<T> responseType, Map<String, ?> pathVariables) {
        return send(url, HttpMethod.PUT, HttpEntity.EMPTY, responseType, pathVariables);
    }


    /**
     * PUT请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendPut(String url, HttpHeaders headers, Class<T> responseType) {
        return send(url, HttpMethod.PUT, new HttpEntity<>(headers), responseType, null);
    }


    /**
     * PUT请求调用方式
     *
     * @param url           请求URL
     * @param headers       请求头参数
     * @param responseType  返回对象类型
     * @param pathVariables url路径变量参数
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendPut(String url, HttpHeaders headers, Class<T> responseType, Map<String, ?> pathVariables) {
        return send(url, HttpMethod.PUT, new HttpEntity<>(headers), responseType, pathVariables);
    }


    /**
     * PUT请求调用方式
     *
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendPut(String url, Object requestBody, Class<T> responseType) {
        return send(url, HttpMethod.PUT, new HttpEntity<>(requestBody), responseType, null);
    }


    /**
     * PUT请求调用方式
     *
     * @param url           请求URL
     * @param requestBody   请求参数体
     * @param responseType  返回对象类型
     * @param pathVariables url路径变量参数
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendPut(String url, Object requestBody, Class<T> responseType, Map<String, ?> pathVariables) {
        return send(url, HttpMethod.PUT, new HttpEntity<>(requestBody), responseType, pathVariables);
    }


    /**
     * PUT请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendPut(String url, HttpHeaders headers, Object requestBody, Class<T> responseType) {
        return send(url, HttpMethod.PUT, new HttpEntity<>(requestBody, headers), responseType, null);
    }


    /**
     * PUT请求调用方式
     *
     * @param url           请求URL
     * @param headers       请求头参数
     * @param requestBody   请求参数体
     * @param responseType  返回对象类型
     * @param pathVariables url路径变量参数
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendPut(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Map<String, ?> pathVariables) {
        return send(url, HttpMethod.PUT, new HttpEntity<>(requestBody, headers), responseType, pathVariables);
    }


    /**
     * DELETE请求调用方式
     *
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendDelete(String url, Class<T> responseType) {
        return send(url, HttpMethod.DELETE, HttpEntity.EMPTY, responseType, null);
    }


    /**
     * DELETE请求调用方式
     *
     * @param url           请求URL
     * @param responseType  返回对象类型
     * @param pathVariables url路径变量参数
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendDelete(String url, Class<T> responseType, Map<String, ?> pathVariables) {
        return send(url, HttpMethod.DELETE, HttpEntity.EMPTY, responseType, pathVariables);
    }


    /**
     * DELETE请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendDelete(String url, HttpHeaders headers, Class<T> responseType) {
        return send(url, HttpMethod.DELETE, new HttpEntity<>(headers), responseType, null);
    }


    /**
     * DELETE请求调用方式
     *
     * @param url           请求URL
     * @param headers       请求头参数
     * @param responseType  返回对象类型
     * @param pathVariables url路径变量参数
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendDelete(String url, HttpHeaders headers, Class<T> responseType, Map<String, ?> pathVariables) {
        return send(url, HttpMethod.DELETE, new HttpEntity<>(headers), responseType, pathVariables);
    }


    /**
     * DELETE请求调用方式
     *
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendDelete(String url, Object requestBody, Class<T> responseType) {
        return send(url, HttpMethod.DELETE, new HttpEntity<>(requestBody), responseType, null);
    }


    /**
     * DELETE请求调用方式
     *
     * @param url           请求URL
     * @param requestBody   请求参数体
     * @param responseType  返回对象类型
     * @param pathVariables url路径变量参数
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendDelete(String url, Object requestBody, Class<T> responseType, Map<String, ?> pathVariables) {
        return send(url, HttpMethod.DELETE, new HttpEntity<>(requestBody), responseType, pathVariables);
    }


    /**
     * DELETE请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendDelete(String url, HttpHeaders headers, Object requestBody, Class<T> responseType) {
        return send(url, HttpMethod.DELETE, new HttpEntity<>(requestBody, headers), responseType, null);
    }


    /**
     * DELETE请求调用方式
     *
     * @param url           请求URL
     * @param headers       请求头参数
     * @param requestBody   请求参数体
     * @param responseType  返回对象类型
     * @param pathVariables url路径变量参数
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> sendDelete(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Map<String, ?> pathVariables) {
        return send(url, HttpMethod.DELETE, new HttpEntity<>(requestBody, headers), responseType, pathVariables);
    }


    // ----------------------------------通用方法-------------------------------------------------------

    /**
     * 通用调用方式
     *
     * @param url           请求URL
     * @param method        请求方法类型
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> send(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> pathVariables) {
        try {

            if (pathVariables == null) {
                return restTemplate.exchange(url, method, requestEntity, responseType);
            } else {
                return restTemplate.exchange(url, method, requestEntity, responseType, pathVariables);
            }

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            if (String.class.equals(responseType)) {
                return ResponseEntity.status(e.getStatusCode()).headers(e.getResponseHeaders()).body((T) e.getResponseBodyAsString());
            } else {
                throw e;
            }
        }
    }


    /**
     * 获取RestTemplate实例对象，可自由调用其方法
     *
     * @return RestTemplate实例对象
     */
    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }


    /**
     * url构造器
     *
     * @return
     */
    public static UrlBuilder url(String url) {
        return new UrlBuilder(url);
    }

    public static class UrlBuilder {

        private UriComponentsBuilder uriComponentsBuilder;

        public UrlBuilder(String url) {
            this.uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);
        }

        public UrlBuilder addParam(String name, Object value) {
            this.uriComponentsBuilder.queryParam(name, value);
            return this;
        }

        public String build() {
            return this.uriComponentsBuilder.build().toUriString();
        }
    }


    /**
     * headers构造器
     *
     * @return
     */
    public static HeadersBuilder headers() {
        return new HeadersBuilder();
    }

    public static class HeadersBuilder {

        private HttpHeaders headers = new HttpHeaders();

        public HeadersBuilder setContentType(MediaType mediaType) {
            this.headers.setContentType(mediaType);
            return this;
        }

        public HeadersBuilder addCookie(String cookieName, String cookieValue) {
            this.headers.add(HttpHeaders.COOKIE, cookieName + "=" + cookieValue);
            return this;
        }

        public HeadersBuilder addParam(String headerName, String headerValue) {
            this.headers.add(headerName, headerValue);
            return this;
        }

        public HttpHeaders build() {
            return this.headers;
        }
    }


    /**
     * form表单requestBody构造器
     *
     * @return
     */
    public static FormBodyBuilder formBody() {
        return new FormBodyBuilder();
    }

    public static class FormBodyBuilder {

        private MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        public FormBodyBuilder addParam(String name, Object value) {
            this.requestBody.add(name, value);
            return this;
        }

        public MultiValueMap<String, Object> build() {
            return this.requestBody;
        }
    }


    /**
     * json参数requestBody构造器
     *
     * @return
     */
    public static JsonBodyBuilder jsonBody() {
        return new JsonBodyBuilder();
    }

    public static class JsonBodyBuilder {

        private Map<String, Object> requestBody = new LinkedHashMap<>();

        public JsonBodyBuilder addParam(String name, Object value) {
            this.requestBody.put(name, value);
            return this;
        }

        public Map<String, Object> build() {
            return this.requestBody;
        }
    }


}
