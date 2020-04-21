package com.kstarrain.provider.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author: DongYu
 * @create: 2020-04-16 14:35
 * @description:
 */
public class JacksonUtils {

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JacksonUtils() {
    }

    public static <T> String toJSONString(T obj) {
        try {
            return obj == null ? null : MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> String toJSONStringPretty(T obj) {
        try {
            return obj == null ? null : MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        try {
            return MAPPER.readValue(text, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T parseObject(String text, TypeReference<T> valueTypeRef) {
        try {
            return MAPPER.readValue(text, valueTypeRef);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T parseObject(String text, Type type) {
        return parseObject(text, (Class<T>) type);
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
            return MAPPER.readValue(text, javaType);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
