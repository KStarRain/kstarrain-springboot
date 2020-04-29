/*
 * Copyright (c) 2016. Runyi Co., Ltd. All rights reserved.
 */

package com.kstarrain.provider.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: Dong Yu
 * @create: 2019-04-24 14:58
 * @description: Bean转换工具类
 */
@Deprecated
public final class BeanConvertOldUtils {

    private static final Log log = LogFactory.getLog(BeanConvertOldUtils.class);

    private static final Enhancer enhancer = new Enhancer();

    private static final Object[] NULL_ARGS = new Object[0];

    private BeanConvertOldUtils() {}

    /**
     * Convert map to java bean instance, a new instance will be returned.
     * <b>This method implements a glib proxy to improve performance,
     * but could influence serializing process.</b>
     * @param map map to be converted
     * @param clazz class of the instance
     * @param <T> type parameter
     * @return the result instance
     */
    public static <T> T mapToBeanEffective(Map<String, Object> map, Class<T> clazz) {
        MapToBeanProxyHandler<T> handler = new MapToBeanProxyHandler<T>();
        return handler.getProxyObject(map, clazz);
    }

    /**
     * Convert map to java bean instance with a instance but not a class.
     * <b>instance will not be changed, a new instance will be returned</b>
     * @param map map to be converted
     * @param instance a instance of the class to be converted
     * @param <T> type parameter
     * @return the result instance with type T
     */
    public static <T> T mapToBeanEffective(Map<String, Object> map, T instance) {
        return (T) mapToBeanEffective(map, instance.getClass());
    }

    /**
     * Convert map to java bean instance, a new instance will be returned.
     * <b>This method is friendly with the serializing process but has a bad performance</b>
     * @param map map to be converted
     * @param clazz class of the instance
     * @param <T> type parameter
     * @return the result instance with type T
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {

        T instance = null;
        try {
            instance = clazz.newInstance();
            BeanUtils.populate(instance, map);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            log.error(e);
        }
        return instance;
    }

    private static void invokeMethod(Method method, Object instance, Map<String, Object> map) throws InvocationTargetException, IllegalAccessException {
        String name = method.getName();
        String key = name.substring(3, 4).toLowerCase() + name.substring(4);
        if (name.startsWith("set") && map.containsKey(key)) {
            method.invoke(instance, map.get(key));
        }
    }

    /**
     * Convert map to java bean instance with a instance but not a class.
     * <b>instance will not be changed, a new instance will be returned</b>
     * @param map map to be converted
     * @param instance a instance of the class to be converted
     * @param <T> type parameter
     * @return the result instance with type T
     */
    public static <T> T mapToBean(Map<String, Object> map, T instance) {
        return (T) mapToBean(map, instance.getClass());
    }

    /**
     * Copy properties from `src` instance to a new `distClass`'s instance
     * <b>This method implements a glib proxy to improve performance,
     * but could influence serializing process.</b>
     * @param src instance that copy from
     * @param distClass instance's class that copy to
     * @param <D> dist instance type
     * @param <S> src instance type
     * @return the result instance with type D
     */
    public static <D, S> D beanToBeanEffective(S src, Class<D> distClass) {
        BeanToBeanProxyHandler<D, S> handler = new BeanToBeanProxyHandler<>();
        return handler.getProxyObject(src, distClass);
    }

    /**
     * Copy properties from `src` instance to a new `distClass`'s instance
     * <b>instance will not be changed, a new instance will be returned</b>
     * @param src instance that copy from
     * @param dist a instance of the class to be converted
     * @param <D> dist instance type
     * @param <S> src instance type
     * @return the result instance with type D
     */
    public static <D, S> D beanToBeanEffective(S src, D dist) {
        return (D) beanToBeanEffective(src, dist.getClass());
    }

    /**
     * Copy properties from `src` instance to a new `distClass`'s instance
     * <b>This method is friendly with the serializing process but has a bad performance</b>
     * @param src instance that copy from
     * @param distClass instance's class that copy to
     * @param <D> dist instance type
     * @param <S> src instance type
     * @return the result instance with type D
     */
    public static <D, S> D beanToBean(S src, Class<D> distClass) {

        D dist = null;
        try {
            dist = distClass.newInstance();
            BeanUtils.copyProperties(dist, src);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.error(e);
        }
        return dist;
    }

    /**
     * Copy properties from `src` instance to a new `distClass`'s instance
     * <b>instance will not be changed, a new instance will be returned</b>
     * @param src instance that copy from
     * @param dist a instance of the class to be converted
     * @param <D> dist instance type
     * @param <S> src instance type
     * @return the result instance with type D
     */
    public static <D, S> D beanToBean(S src, D dist) {
        return (D) beanToBean(src, dist.getClass());
    }

    /**
     * Convert a java bean to a map.
     * @param bean bean to be converted
     * @param <T> bean type
     * @return result map
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        return BeanMap.create(bean);
    }

    public static <T> List<T> mapToBeanInList(List<Map<String, Object>> mapList, final Class<T> clazz) {

        if (CollectionUtils.isEmpty(mapList)){return new ArrayList<>();}

        return mapList.stream().map(input -> mapToBean(input, clazz)).collect(Collectors.toList());
    }

    public static <T> List<Map<String, Object>> beanToMapInList(List<T> beanList) {

        if (CollectionUtils.isEmpty(beanList)){return new ArrayList<>();}

        return beanList.stream().map(input -> beanToMap(input)).collect(Collectors.toList());
    }


    public static <D, S> List<D> beanToBeanInList(List<S> srcList, final Class<D> distClass) {

        if (CollectionUtils.isEmpty(srcList)){return new ArrayList<>();}

        return srcList.stream().map(input -> beanToBean(input, distClass)).collect(Collectors.toList());
    }

    // D-Proxy Handler defined.

    /**
     * cglib proxy handler for map-bean converting
     * @param <T> Type of bean to convert to
     */
    private static class MapToBeanProxyHandler<T> implements MethodInterceptor, Serializable {

        private transient Map<String, Object> map;

        public T getProxyObject(Map<String, Object> map, Class<? extends T> clazz) {

            this.map = map;
            enhancer.setSuperclass(clazz);
            enhancer.setCallback(this);

            return (T) enhancer.create();
        }

        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy proxy)
                throws Throwable {

            String name = method.getName();
            String key = name.substring(3, 4).toLowerCase() + name.substring(4);
            if (name.startsWith("set")) {
                Object value = null;
                if (args[0] != null) {
                    value = args[0];
                }
                map.put(key, value);
                return null;
            } else if (name.startsWith("get")) {
                return map.get(key);
            }
            return proxy.invokeSuper(o, args);
        }
    }

    /**
     * cglib proxy handler for bean-bean converting
     * @param <D> Type of bean to convert to
     * @param <S> Type of bean to convert from
     */
    private static class BeanToBeanProxyHandler<D, S> implements MethodInterceptor, Serializable {

        private transient S source;

        D getProxyObject(S bean, Class<D> clazz) {

            source = bean;
            enhancer.setSuperclass(clazz);
            enhancer.setCallback(this);

            return (D) enhancer.create();
        }

        @Override
        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

            String name = method.getName();
            if (name.startsWith("set")) {
                Object value = null;
                if (args[0] != null) {
                    value = args[0];
                }
                try {
                    source.getClass().getDeclaredMethod(name).invoke(source, value);
                } catch (NoSuchMethodException e) {
                    source.getClass().getSuperclass().getDeclaredMethod(name).invoke(source, value);
                }
                return null;
            } else if (name.startsWith("get")) {
                try {
                    return source.getClass().getDeclaredMethod(name).invoke(source, NULL_ARGS);
                } catch (NoSuchMethodException e) {
                    return source.getClass().getSuperclass().getDeclaredMethod(name).invoke(source, NULL_ARGS);
                }
            }
            return methodProxy.invokeSuper(proxy, args);
        }
    }

}
