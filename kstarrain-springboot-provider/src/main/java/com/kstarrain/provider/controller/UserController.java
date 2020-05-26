package com.kstarrain.provider.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.kstarrain.api.dto.request.UserQueryReq;
import com.kstarrain.api.dto.response.UserDTO;
import com.kstarrain.api.url.UrlMapping;
import com.kstarrain.framework.api.dto.response.PageResultDTO;
import com.kstarrain.framework.api.dto.response.ResultDTO;
import com.kstarrain.framework.web.aspect.annotation.ExcludeAspectLog;
import com.kstarrain.framework.web.aspect.annotation.PrintAspectLog;
import com.kstarrain.provider.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
    @ApiOperationSupport(order = 1)
    @PostMapping(value = UrlMapping.USER_IMPORT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDTO<Integer> importDitch(@ApiParam("用户excel/csv文件") @RequestParam(value = "file") MultipartFile file) throws ParseException, ReflectiveOperationException, IOException {
        return new ResultDTO<>(userService.importUser(file));
    }


    /**
     * 条件查询用户列表
     * @param requestBody
     * @return
     */
    @ExcludeAspectLog
    @ApiOperation(value = "条件查询用户列表")
    @ApiOperationSupport(order = 2)
    @PostMapping(value = UrlMapping.USER_LIST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageResultDTO<UserDTO> queryUserList(@ApiParam("查询条件") @RequestBody UserQueryReq requestBody) {
        return userService.queryUserPageList(requestBody);
    }


    /**
     * 根据id查询用户
     * @return
     */
    @ExcludeAspectLog
    @ApiOperation(value = "根据id查询用户详情")
    @ApiOperationSupport(order = 3)
    @GetMapping(value = UrlMapping.USER_DETAIL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDTO<UserDTO> queryUserDetail(@ApiParam(name = "id", required = true, example = "7")
                                              @RequestParam("id") Integer id) {
        return new ResultDTO<>(userService.queryUserDetailById(id));
    }


    /**
     * 导出用户列表
     * @param requestBody
     * @param response
     */

    @ExcludeAspectLog
    @ApiOperation(value = "导出用户列表")
    @ApiOperationSupport(order = 4)
    @PostMapping(value = UrlMapping.USER_EXPORT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void exportUserList(@ApiParam(name="导出条件") @RequestBody UserQueryReq requestBody,
                               HttpServletResponse response) throws IOException, ReflectiveOperationException {
        userService.exportUserList(requestBody, response);
    }

}
