package com.kstarrain.provider.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kstarrain.framework.api.dto.request.BasePageRequest;
import com.kstarrain.framework.api.dto.response.PageResultDTO;
import com.kstarrain.framework.common.utils.BeanConvertUtils;
import com.kstarrain.framework.web.exception.BizException;
import com.kstarrain.provider.exception.BizErrorCode;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


public class PagingUtils {

    public static void startPageHelper(BasePageRequest pageInfo) {
        startPageHelper(pageInfo, true);
    }

    public static void startPageHelper(BasePageRequest pageInfo, boolean count) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(), count);
        if (StringUtils.isNotBlank(pageInfo.getOrderBy())) {
            StringBuilder orderBy = new StringBuilder();
            orderBy.append(pageInfo.getOrderBy());
            if (pageInfo.isAsc()) {
                orderBy.append(" ASC");
            } else {
                orderBy.append(" DESC");
            }
            PageHelper.orderBy(orderBy.toString());
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
        result.setData(BeanConvertUtils.beanToBeanInList(data, clazz));
        return result;
    }

}
