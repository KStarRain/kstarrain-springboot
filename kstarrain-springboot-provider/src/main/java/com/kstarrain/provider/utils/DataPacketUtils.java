package com.kstarrain.provider.utils;



import java.util.ArrayList;
import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-11-08 09:06
 * @description: 数据分组包装工具类
 */
public class DataPacketUtils {


    public interface Callback<P> {
        void execute(P singleBatchData);
    }


    /**
     * 批量数据分组处理
     * 在单组处理逻辑中对该组数据进行修改会使原数据也发生修改
     * @param data              原数据
     * @param count             单组数据条数
     * @param function          单组处理数据业务函数
     */
    public static <T> void handle(List<T> data, int count, Callback<List<T>> function)  {

        for (int i = 0, len = data.size() / count + 1; i < len; i++) {
            //单组数据
            List<T> singleBatchData;
            if (data.size() == 0) {
                // 整除 多一次
                continue;
            }
            else if (data.size() >= count) {
                singleBatchData = data.subList(0, count);
                data = data.subList(count, data.size());
            }
            else {
                // 非整除，最后一次
                singleBatchData = data;
            }
            function.execute(singleBatchData);
        }
    }



    /**
     * 批量数据分组处理
     * 在单组处理逻辑中对该组数据进行修改不会使原数据发生修改
     * @param data              原数据
     * @param count             单组数据条数
     * @param function          单组处理数据业务函数
     */
    public static <T> void handleSerialize(List<T> data, int count, Callback<List<T>> function)  {

        for (int i = 0, len = data.size() / count + 1; i < len; i++) {
            //单组数据
            List<T> singleBatchData;
            if (data.size() == 0) {
                // 整除 多一次
                continue;
            }
            else if (data.size() >= count) {
                singleBatchData = new ArrayList<>(data.subList(0, count));
                data = data.subList(count, data.size());
            }
            else {
                // 非整除，最后一次
                singleBatchData = new ArrayList<>(data);
            }
            function.execute(singleBatchData);
        }
    }

}
