package com.kstarrain.provider.service.impl;

import com.kstarrain.api.dto.request.UserQueryReq;
import com.kstarrain.api.dto.response.UserDTO;
import com.kstarrain.framework.api.dto.response.PageResultDTO;
import com.kstarrain.framework.common.utils.*;
import com.kstarrain.framework.common.utils.extra.Excel;
import com.kstarrain.framework.web.enums.AliveFlagEnum;
import com.kstarrain.framework.web.exception.BizException;
import com.kstarrain.framework.web.utils.ResponseUtils;
import com.kstarrain.provider.constants.Constants;
import com.kstarrain.provider.exception.BizErrorCode;
import com.kstarrain.provider.model.UserImportModel;
import com.kstarrain.provider.persistence.entities.User;
import com.kstarrain.provider.persistence.mappers.UserMapper;
import com.kstarrain.provider.service.UserService;
import com.kstarrain.provider.utils.PagingUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: DongYu
 * @create: 2020-04-10 15:46
 * @description:
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Value("${download.temp.path}")
    private String downloadTempPath;

    @Autowired
    private UserMapper userMapper;


    @Override
    @Transactional
    public int importUser(MultipartFile file) throws ParseException, ReflectiveOperationException, IOException {

        if (file == null || file.isEmpty()){
            throw new BizException(BizErrorCode.INCORRECT_PARAMETERS);
        }

        Map<String, String> titlePropertyMap = new LinkedHashMap<>();
        titlePropertyMap.put("姓名", "name");
        titlePropertyMap.put("年龄", "age");
        titlePropertyMap.put("生日", "birthday");
        titlePropertyMap.put("存款", "deposit");

        Map<String, DateFormat> propertyDateFormatMap = new LinkedHashMap<>();
        propertyDateFormatMap.put("birthday", new SimpleDateFormat("yyyy-MM-dd"));

        List<UserImportModel> importUserDatum;
        if (ExtraFileUtils.isCsvFile(file.getOriginalFilename())){
            importUserDatum = CsvUtils.readToBeans(file.getInputStream(), titlePropertyMap, propertyDateFormatMap, UserImportModel.class);
            log.info("【Csv文件:{}】解析完成，共解析出{}条有效数据", file.getOriginalFilename(), importUserDatum.size());
        } else {
            Excel excel = ExcelUtils.create(file.getOriginalFilename(), file.getInputStream()).selectSheet(0);
            importUserDatum = ExcelUtils.readToBeans(excel, titlePropertyMap, propertyDateFormatMap, UserImportModel.class);
            log.info("【Excel文件:{}】解析完成，共解析出{}条有效数据", file.getOriginalFilename(), importUserDatum.size());
        }

        if (CollectionUtils.isEmpty(importUserDatum)) {
            throw new BizException(BizErrorCode.IMPORT_DATA_EMPTY);
        }

        // 前置-根据姓名去重
        Map<String, UserImportModel> distinctNameUserModelMap = ListUtils.distinctReturnKeyMap(importUserDatum, "name");

        // 查询姓名是否已存在于库中
        Map<String, User> existNameUserMap = new HashMap<>();
        BatchUtils.handleSerialize(new ArrayList<>(distinctNameUserModelMap.keySet()), 1000, singleBatchNames -> {
            List<User> existRecords = userMapper.queryByNames(singleBatchNames);
            if (CollectionUtils.isEmpty(existRecords)){return;}
            for (User existRecord : existRecords) {
                existNameUserMap.put(existRecord.getName(),existRecord);
            }
        });


        List<User> records = new ArrayList<>();
        for (Map.Entry<String, UserImportModel> entry : distinctNameUserModelMap.entrySet()) {
            // 已存在姓名忽略
            if (MapUtils.isNotEmpty(existNameUserMap) && existNameUserMap.get(entry.getValue().getName()) != null){
                continue;
            }
            User record = BeanConvertUtils.beanToBean(entry.getValue(), User.class);
            record.setCreateDate(new Date());
            record.setCreateUser(Constants.SYSTEM);
            record.setAliveFlag(AliveFlagEnum.ENABLED.getCode());
            records.add(record);
        }

        final int[] successCount = {0};
        if (CollectionUtils.isEmpty(records)){return successCount[0];}
        BatchUtils.handleSerialize(records, 300, singleBatchRecords -> {
            successCount[0] += userMapper.insertBatch(singleBatchRecords);
        });
        return successCount[0];


    }

    @Override
    public PageResultDTO<UserDTO> queryUserPageList(UserQueryReq requestBody) {
        User criteria = BeanConvertUtils.beanToBean(requestBody, User.class);
        PagingUtils.startPage(requestBody.toPageInfo());
        List<User> users = userMapper.querySelective(criteria);
        return PagingUtils.handleResult(users, UserDTO.class);
    }


    @Override
    public void exportUserList(UserQueryReq requestBody, HttpServletResponse response) throws ReflectiveOperationException, IOException {

        User criteria = BeanConvertUtils.beanToBean(requestBody, User.class);
        List<User> users = userMapper.querySelective(criteria);
        List<UserDTO> result = BeanConvertUtils.beanToBeanInList(users, UserDTO.class);

        String suffix = result.size() <= Constants.MAX_EXCEL_ROW ? ".xlsx" : ".csv";
        Date date = new Date();
        String fileName = "用户列表_" + new SimpleDateFormat("yyyyMMddHHmmss").format(date) + suffix;
        String tempFilePath = downloadTempPath + File.separator + fileName;

        Map<String, String> titlePropertyMap = new LinkedHashMap<>();
        titlePropertyMap.put("姓名", "name");
        titlePropertyMap.put("年龄", "age");
        titlePropertyMap.put("生日", "birthday");
        titlePropertyMap.put("存款", "deposit");

        Map<String, DateFormat> propertyDateFormatMap = new LinkedHashMap<>();
        propertyDateFormatMap.put("birthday", new SimpleDateFormat("yyyy-MM-dd"));

        try {
            if (result.size() <= Constants.MAX_EXCEL_ROW){
                ExcelUtils.createByBeans(Excel.Type.LARGE_XLSX, result, titlePropertyMap, propertyDateFormatMap).write(tempFilePath);
            } else {
                CsvUtils.writeByBeans(result, titlePropertyMap, propertyDateFormatMap, tempFilePath);
            }

            ByteArrayInputStream inputStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File(tempFilePath)));
            ResponseUtils.setDownloadResponse(fileName, inputStream, response);
        } finally {
            ExtraFileUtils.deleteExistFile(new File(tempFilePath));
        }
    }

}
