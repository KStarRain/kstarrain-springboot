/*
 * Copyright (c) 2016. Runyi Co., Ltd. All rights reserved.
 */

package com.kstarrain.provider.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cglib.core.Converter;

import java.text.ParseException;
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
        return beanToBean(src, distClass,null);
    }

    public static <D, S> D beanToBean(S src, Class<D> distClass, Converter converter) {
        if (src == null){return null;}
        D dist = null;
        try {
            dist = distClass.newInstance();
            BeanCopier beanCopier = BeanCopier.create(src.getClass(), distClass, true);
            if (converter == null){
                converter = (srcValue, targetClass, setMethodName) -> {
                    if (srcValue == null){return null;}

                    if (String.class.equals(srcValue.getClass()) && Date.class.equals(targetClass)){
                        try {
                            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String) srcValue);
                        } catch (ParseException e) {
                            return new RuntimeException(e.getClass().getName()+ " : " +e.getMessage());
                        }
                    }

                    if (Date.class.equals(srcValue.getClass()) && String.class.equals(targetClass)) {
                        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(srcValue);
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


    public static <D, S> List<D> beanToBeanInList(List<S> srcList, final Class<D> distClass) {
        return beanToBeanInList(srcList, distClass, null);
    }

    public static <D, S> List<D> beanToBeanInList(List<S> srcList, final Class<D> distClass, Converter converter) {
        if (CollectionUtils.isEmpty(srcList)){return new ArrayList<>();}
        return srcList.stream().map(input -> beanToBean(input, distClass, converter)).collect(Collectors.toList());
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
