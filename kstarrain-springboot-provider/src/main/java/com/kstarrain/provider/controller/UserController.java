package com.kstarrain.provider.controller;

import com.kstarrain.api.dto.request.UserQueryReq;
import com.kstarrain.api.dto.response.UserDTO;
import com.kstarrain.api.url.UrlMapping;
import com.kstarrain.framework.api.dto.response.PageResultDTO;
import com.kstarrain.framework.api.dto.response.ResultDTO;
import com.kstarrain.framework.web.aspect.annotation.PrintAspectLog;
import com.kstarrain.provider.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author: DongYu
 * @create: 2020-04-10 15:32
 * @description:
 */
@Slf4j
@PrintAspectLog
@Api(tags = "用户相关接口")
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 导入用户数据
     * @param file
     * @return
     */
    @ApiOperation(value = "导入用户数据")
    @PostMapping(value = UrlMapping.USER_IMPORT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDTO<Integer> importDitch(@ApiParam("用户excel/csv文件") @RequestParam(value = "file") MultipartFile file) throws ParseException, ReflectiveOperationException, IOException {
        return new ResultDTO<>(userService.importUser(file));
    }


    /**
     * 条件查询用户列表
     * @param requestBody
     * @return
     */
//    @ExcludeAspectLog
    @ApiOperation(value = "条件查询用户列表")
    @PostMapping(value = UrlMapping.USER_LIST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageResultDTO<UserDTO> queryUserList(@ApiParam("查询条件") @RequestBody UserQueryReq requestBody) {
        return userService.queryUserPageList(requestBody);
    }


    /**
     * 导出用户列表
     * @param requestBody
     * @param response
     */
    @ApiOperation(value = "导出用户列表")
    @PostMapping(value = UrlMapping.USER_EXPORT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void exportUserList(@ApiParam("导出条件") @RequestBody UserQueryReq requestBody,
                               HttpServletResponse response) throws IOException, ReflectiveOperationException {
        userService.exportUserList(requestBody, response);
    }

}
