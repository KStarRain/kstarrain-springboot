package com.kstarrain.provider.utils;

import com.kstarrain.provider.exception.BizErrorCode;
import com.kstarrain.provider.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.csv.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: DongYu
 * @create: 2019-11-06 14:27
 * @description: Csv 工具类
 * */
@Slf4j
public class CsvUtils {




    /**
     * 将Bean中写入到指定路径的csv文件中
     * @param beans
     * @param titlePropertyMap csv中标题名字与bean中属性名字的映射Map [例如： key -- 姓名;  value -- name]
     * @param filePath
     * @throws ReflectiveOperationException
     * @throws IOException
     */
    public static <T>void writeByBeans(List<T> beans, Map<String, String> titlePropertyMap, String filePath) throws ReflectiveOperationException, IOException {
        writeByBeans(beans, titlePropertyMap, null, filePath);
    }


    /**
     * 将Bean中写入到指定路径的csv文件中
     * @param beans
     * @param titlePropertyMap csv文件中标题名字与bean中属性名字的映射Map [例如： key -- 姓名;  value -- name]
     * @param propertyDateFormatMap 对待bean中的Date转化为csv文件中的字符串时间特殊处理 [例如： key -- birthday; value -- new SimpleDateFormat("yyyy-MM-dd")]
     * @param filePath
     * @return
     * @throws ReflectiveOperationException
     */
    public static <T>void writeByBeans(List<T> beans, Map<String, String> titlePropertyMap, Map<String, DateFormat> propertyDateFormatMap, String filePath) throws ReflectiveOperationException, IOException {

        if (MapUtils.isEmpty(titlePropertyMap)) {
            throw new IllegalArgumentException("TitlePropertyMap is empty. ");
        }

        String suffix = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
        if (!".csv".equals(suffix)){
            throw new IllegalArgumentException("file name suffix is not .csv ");
        }


        List<String> title = new ArrayList<>();
        List<Method> usefulGetMethods = new ArrayList<>();

        if (CollectionUtils.isEmpty(beans)){
            title = new ArrayList(titlePropertyMap.keySet());
        } else {
            Map<String, Method> clazzMethodMap = new HashMap<>();
            Class<?> clazz = beans.get(0).getClass();
            for (Method method : clazz.getMethods()) {
                clazzMethodMap.put(method.getName(),method);
            }

            for (Map.Entry<String, String> titlePropertyEntry : titlePropertyMap.entrySet()) {
                String methodName = "get" + StringUtils.capitalize(titlePropertyEntry.getValue());
                Method method = clazzMethodMap.get(methodName);
                if (method != null){
                    usefulGetMethods.add(method);
                    title.add(titlePropertyEntry.getKey());
                }else {
                    throw new NoSuchMethodException(" Method [" + methodName + "] not found in class [" + clazz.getName() + "].");
                }
            }
        }

        FileUtils.forceMkdirParent(new File(filePath));

        try (OutputStream out = new FileOutputStream(filePath);
             Writer writer = new OutputStreamWriter(out,"UTF-8"); //如果是UTF-8时，WPS打开是正常显示，windows系统下用微软的excel打开是乱码
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withQuoteMode(QuoteMode.ALL).withHeader(title.toArray(new String[title.size()])))){

            if (CollectionUtils.isEmpty(beans)){
                csvPrinter.println();
                return;
            }

            List<String> csvContent = new ArrayList<>();
            for (T bean : beans) {
                csvContent.clear();

                for (Method method : usefulGetMethods) {
                    Object invoke = method.invoke(bean);

                    if (MapUtils.isNotEmpty(propertyDateFormatMap) && Date.class.equals(method.getReturnType())) {
                        String propertyName = StringUtils.uncapitalize(method.getName().substring(3));
                        DateFormat dateFormat = propertyDateFormatMap.get(propertyName);
                        if (dateFormat != null) {
                            invoke = dateFormat.format((Date) invoke);
                        }
                    }
                    csvContent.add(objectToString(invoke));
                }
                csvPrinter.printRecord(csvContent);
            }

        }
    }



    /**
     * 读取csv中数据转化为bean
     * @param inputStream            输入流
     * @param titlePropertyMap       csv文件中标题名字与bean中属性名字的映射Map [例如： key -- 姓名;  value -- name]
     * @param clazz                  需要转化成的bean.Class
     * @param <T>
     * @return
     * @throws ReflectiveOperationException
     * @throws ParseException
     */
    public static <T> List<T> readToBeans(InputStream inputStream, Map<String, String> titlePropertyMap, Class<T> clazz) throws ReflectiveOperationException, ParseException, IOException {
        return readToBeans(inputStream, titlePropertyMap, null, clazz);
    }



        /**
         * 读取csv中数据转化为bean
         * @param inputStream            输入流
         * @param titlePropertyMap       csv文件中标题名字与bean中属性名字的映射Map [例如： key -- 姓名;  value -- name]
         * @param propertyDateFormatMap  对待csv文件中的字符串时间转化为bean中的Date特殊处理 [例如： key -- birthday; value -- new SimpleDateFormat("yyyy-MM-dd")]
         * @param clazz                  需要转化成的bean.Class
         * @param <T>
         * @return
         * @throws ReflectiveOperationException
         * @throws ParseException
         */
    public static <T> List<T> readToBeans(InputStream inputStream, Map<String, String> titlePropertyMap, Map<String, DateFormat> propertyDateFormatMap, Class<T> clazz) throws ReflectiveOperationException, ParseException, IOException {


        if (MapUtils.isEmpty(titlePropertyMap)) {
            throw new IllegalArgumentException("TitlePropertyMap is empty. ");
        }

        Map<String, Method> clazzMethodMap = new HashMap<>();
        for (Method method : clazz.getMethods()) {
            clazzMethodMap.put(method.getName(), method);
        }


        try(InputStream bomIn = new BOMInputStream(inputStream);
            Reader reader = new InputStreamReader(bomIn)){

            CSVParser parse;
            try {
                parse = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.ALL).withHeader().parse(reader);
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage());
                if (StringUtils.isNotBlank(e.getMessage()) && e.getMessage().startsWith("The header contains a duplicate name:")){
                    throw new BizException(BizErrorCode.EXPORT_CSV_DATA_ERROR);
                } else {
                    throw e;
                }
            }

            Map<String, Integer> headerMap = parse.getHeaderMap();
            if (MapUtils.isEmpty(headerMap)){return new ArrayList<>();}


            Map<Method, String> usefulSetMethodsTitle = new HashMap<>();
            for (Map.Entry<String, Integer> headerEntry : headerMap.entrySet()) {

                if (StringUtils.isBlank(titlePropertyMap.get(headerEntry.getKey()))) {continue;}

                String methodName = "set" + StringUtils.capitalize(titlePropertyMap.get(headerEntry.getKey()));
                Method method = clazzMethodMap.get(methodName);
                if (method != null) {
                    usefulSetMethodsTitle.put(method, headerEntry.getKey());
                } else {
                    throw new NoSuchMethodException("Method [" + methodName + "] not found in class [" + clazz.getName() + "].");
                }
            }

            List<T> result = new ArrayList<>();
            if (MapUtils.isEmpty(usefulSetMethodsTitle)){return result;}

            Iterable<CSVRecord> records = parse.getRecords();


            for (CSVRecord record : records) {

                T bean = clazz.newInstance();
                int emptyCsvContextNum = 0;
                for (Map.Entry<Method, String> methodsTitleEntry : usefulSetMethodsTitle.entrySet()) {

                    Object csvContext = record.get(methodsTitleEntry.getValue());

                    if (String.class.equals(csvContext.getClass()) && StringUtils.isBlank((String)csvContext)){
                        emptyCsvContextNum ++;
                        continue;
                    }

                    Method usefulSetMethod = methodsTitleEntry.getKey();
                    Class<?> parameterType = usefulSetMethod.getParameterTypes()[0];
                    if (!csvContext.getClass().equals(parameterType)) {
                        if (MapUtils.isNotEmpty(propertyDateFormatMap) && String.class.equals(csvContext.getClass()) && Date.class.equals(parameterType)){
                            String propertyName = StringUtils.uncapitalize(usefulSetMethod.getName().substring(3));
                            DateFormat dateFormat = propertyDateFormatMap.get(propertyName);
                            if (dateFormat != null) {
                                csvContext = dateFormat.parse((String)csvContext);
                            } else {
                                throw new IllegalArgumentException("String convert Date failed because the dateFormat of property [" + propertyName + "] mapping could not be found in propertyDateFormatMap.");
                            }
                        } else {
                            csvContext = ConvertUtils.convert(csvContext, parameterType);
                        }
                    }
                    usefulSetMethod.invoke(bean, csvContext);
                }
                if (emptyCsvContextNum != usefulSetMethodsTitle.size()){
                    result.add(bean);
                }
            }
            return result;
        }
    }



    private static String objectToString(Object value) {

        if (value == null) {
            return "";
        } else if (value instanceof Date) {
            return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format((Date)value);
        } else {
            return value.toString();
        }
    }
}
