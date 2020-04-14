package com.kstarrain.provider.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ListUtils {
	
	private ListUtils() {
	}


	/**
	 * 多个集合取交集
	 * @param datum
	 * @return
	 */
    public static List<String> intersection(List<String>... datum) {

        return intersection(Arrays.asList(datum));
    }


	private static List<String> intersection(List<List<String>> datum) {

		if(CollectionUtils.isEmpty(datum)){return null;}

		//有效集合
		List<List<String>> efficientList = new ArrayList<>();
		for (List<String> data : datum) {
			if (CollectionUtils.isNotEmpty(data)){
				efficientList.add(data);
			}
		}

		if(CollectionUtils.isEmpty(efficientList)){return null;}

        List<String> intersection = efficientList.get(0);

		if (efficientList.size() == 1){ //如果只有一个非空集合，直接返回第一个

			return intersection;

		} else { // 有多个非空集合，直接挨个交集

            for (List<String> strings : efficientList) {
                intersection.retainAll(strings);
            }
            return intersection;
		}
	}


	/**
	 * 去重
	 */
	public static <T> List<T> distinct(List<T> datum, String... referProperties) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Map<String, T> distinctMap = distinctReturnKeyMap(datum, referProperties);
		return new ArrayList<>(distinctMap.values());
	}


	/**
	 * 去重
	 */
	public static <T> Map<String, T> distinctReturnKeyMap(List<T> data,  String... referProperties) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

		if (CollectionUtils.isEmpty(data)) { return new HashMap<>(); }

		Class<?> clazz = data.get(0).getClass();

		List<Method> referMethods = new ArrayList<>();
		for (String relationProperty : referProperties) {
			Method method = clazz.getMethod("get" + StringUtils.capitalize(relationProperty));
			referMethods.add(method);
		}

		LinkedHashMap<String, T> map = new LinkedHashMap<>();
		StringBuilder key = new StringBuilder();
		for (T t : data) {
			key.setLength(0);
			for (Method m : referMethods) {
				key.append(m.invoke(t));
			}
			map.put(key.toString(), t);
		}

		return map;
	}


}
