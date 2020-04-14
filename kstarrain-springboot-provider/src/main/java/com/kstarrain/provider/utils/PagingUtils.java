package com.kstarrain.provider.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kstarrain.framework.api.dto.request.BasePageRequest;
import com.kstarrain.framework.api.dto.response.PageResultDTO;
import com.kstarrain.provider.exception.BizErrorCode;
import com.kstarrain.provider.exception.BizException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


public class PagingUtils {

    public static void startPage(BasePageRequest pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(), true);
        if (StringUtils.isNotBlank(pageInfo.getOrderBy())) {
            PageHelper.orderBy(pageInfo.getOrderBy());
        }

    }


    public static <T> PageResultDTO<T> handleResult(List<T> data) {
        if (!(data instanceof Page)) {
            throw new BizException(BizErrorCode.SYSTEM_ERROR);
        }

        Page<T> page = (Page<T>) data;
        PageResultDTO<T> result = new PageResultDTO<T>();
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setPageCount(page.getPages());
        result.setTotal(page.getTotal());
        result.setData(data);
        return result;
    }

    public static <F, T> PageResultDTO<T> handleResult(List<F> data, Class<T> clazz) {
        if (!(data instanceof Page)) {
            throw new BizException(BizErrorCode.SYSTEM_ERROR);
        }

        Page<F> page = (Page<F>) data;
        PageResultDTO<T> result = new PageResultDTO<T>();
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setPageCount(page.getPages());
        result.setTotal(page.getTotal());
        List<T> list = BeanConvertOldUtils.beanToBeanInList(data, clazz);
        result.setData(list);
        return result;
    }

}
