/*
 * Copyright (c) 2016. Runyi Co., Ltd. All rights reserved.
 */

package com.kstarrain.provider.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cglib.core.Converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: Dong Yu
 * @create: 2020-04-14 14:58
 * @description: Bean转换工具类
 */
@Slf4j
public final class BeanConvertUtils {


    private BeanConvertUtils() {}


    public static <D, S> D beanToBean(S src, Class<D> distClass) {
        return beanToBean(src, distClass, null, null);
    }

    public static <D, S> D beanToBean(S src, Class<D> distClass, DateConverter dateConverter) {
        return beanToBean(src, distClass,null, dateConverter);
    }

    public static <D, S> D beanToBean(S src, Class<D> distClass, Converter converter) {
        return beanToBean(src, distClass, converter, null);
    }


    private static <D, S> D beanToBean(S src, Class<D> distClass, Converter converter, DateConverter dateConverter) {
        if (src == null){return null;}
        D dist = null;
        try {
            dist = distClass.newInstance();
            BeanCopier beanCopier = BeanCopier.create(src.getClass(), distClass, true);
            if (converter == null){
                converter = (srcValue, targetClass, setMethodName) -> {
                    if (srcValue == null){return null;}
                    if (srcValue.getClass().equals(targetClass)){return srcValue;}

                    String pattern;
                    if (dateConverter != null && MapUtils.isNotEmpty(dateConverter.getMethodPatternMap())
                            && StringUtils.isNotBlank(pattern = dateConverter.getMethodPatternMap().get(setMethodName))){

                        if (String.class.equals(srcValue.getClass()) && Date.class.equals(targetClass)){
                            try {
                                return new SimpleDateFormat(pattern).parse((String) srcValue);
                            } catch (Exception e) {
                                log.error("{} convert to {} error, targetClass[{}], methodName[{}], pattern[{}]", srcValue.getClass().getName(), targetClass.getName(), distClass.getName() ,setMethodName, pattern);
                                return e;
                            }
                        }

                        if (Date.class.equals(srcValue.getClass()) && String.class.equals(targetClass)) {
                            try {
                                return new SimpleDateFormat(pattern).format(srcValue);
                            } catch (Exception e) {
                                log.error("{} convert to {} error, targetClass[{}], methodName[{}], pattern[{}]", srcValue.getClass().getName(), targetClass.getName(), distClass.getName() ,setMethodName, pattern);
                                throw e;
                            }
                        }
                    }
                    return ConvertUtils.convert(srcValue, targetClass);
                };
            }
            beanCopier.copy(src, dist, converter);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
        return dist;
    }


    public static <D, S> List<D> beanToBeanInList(List<S> srcList, Class<D> distClass) {
        return beanToBeanInList(srcList, distClass, null, null);
    }

    public static <D, S> List<D> beanToBeanInList(List<S> srcList, Class<D> distClass, DateConverter dateConverter) {
        return beanToBeanInList(srcList, distClass, null, dateConverter);
    }

    public static <D, S> List<D> beanToBeanInList(List<S> srcList, Class<D> distClass, Converter converter) {
        return beanToBeanInList(srcList, distClass, converter, null);
    }


    private static<D, S> List<D> beanToBeanInList(List<S> srcList, Class<D> distClass, Converter converter, DateConverter dateConverter) {
        if (CollectionUtils.isEmpty(srcList)){return new ArrayList<>();}
        return srcList.stream().map(src -> beanToBean(src, distClass, converter, dateConverter)).collect(Collectors.toList());
    }


    public static <S> Map<String, Object> beanToMap(S src) {
        Map<String, Object> distMap = Maps.newHashMap();
        if (src != null) {
            BeanMap beanMap = BeanMap.create(src);
            for (Object key : beanMap.keySet()) {
                distMap.put(key+"", beanMap.get(key));
            }
        }
        return distMap;
    }

    public static <T> List<Map<String, Object>> beanToMapInList(List<T> srcList) {
        if (CollectionUtils.isEmpty(srcList)){return new ArrayList<>();}
        return srcList.stream().map(BeanConvertUtils::beanToMap).collect(Collectors.toList());
    }

    public static <D> D mapToBean(Map<String, Object> src, Class<D> distClass) {
        if (MapUtils.isEmpty(src)){return null;}
        D dist = null;
        try {
            dist = distClass.newInstance();
            BeanMap beanMap = BeanMap.create(dist);
            beanMap.putAll(src);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
        return dist;
    }

    public static <D> List<D> mapToBeanInList(List<Map<String, Object>> srcList, final Class<D> distClass) {
        if (CollectionUtils.isEmpty(srcList)){return new ArrayList<>();}
        return srcList.stream().map(input -> mapToBean(input, distClass)).collect(Collectors.toList());
    }



}
