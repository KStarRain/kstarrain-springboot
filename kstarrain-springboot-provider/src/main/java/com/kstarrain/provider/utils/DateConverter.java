package com.kstarrain.provider.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: DongYu
 * @create: 2020-04-15 09:42
 * @description:
 */
public class DateConverter {

    private Map<String, String> methodPatternMap;

    private DateConverter(Map<String, String> methodPatternMap) {
        this.methodPatternMap = methodPatternMap;
    }

    public Map<String, String> getMethodPatternMap() {
        return methodPatternMap;
    }

    public static DateConverter.DateConverterBuilder builder() {
        return new DateConverter.DateConverterBuilder();
    }


    public static class DateConverterBuilder {

        private Map<String, String> methodPatternMap = new HashMap<>();

        public DateConverter.DateConverterBuilder addPattern(String propertyName, String pattern) {
            this.methodPatternMap.put("set"+StringUtils.capitalize(propertyName), pattern);
            return this;
        }

        public DateConverter build() {
            return new DateConverter(this.methodPatternMap);
        }
    }

}
