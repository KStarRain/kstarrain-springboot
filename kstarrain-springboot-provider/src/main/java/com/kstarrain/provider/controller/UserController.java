package com.kstarrain.provider.controller;

import com.kstarrain.api.dto.request.UserQueryReq;
import com.kstarrain.api.dto.response.UserDTO;
import com.kstarrain.api.url.UrlMapping;
import com.kstarrain.framework.api.dto.response.PageResultDTO;
import com.kstarrain.framework.api.dto.response.ResultDTO;
import com.kstarrain.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author: DongYu
 * @create: 2020-04-10 15:32
 * @description:
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 导入用户数据
     * @param file
     * @return
     */
    @PostMapping(value = UrlMapping.USER_IMPORT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDTO<Integer> importDitch(@RequestParam(value = "file") MultipartFile file) throws ParseException, ReflectiveOperationException, IOException {
        return new ResultDTO<>(userService.importUser(file));
    }


    /**
     * 条件查询用户列表
     * @param requestBody
     * @return
     */
    @PostMapping(value = UrlMapping.USER_LIST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageResultDTO<UserDTO> queryUserList(@RequestBody UserQueryReq requestBody) {
        return userService.queryUserPageList(requestBody);
    }


    /**
     * 条件查询用户列表
     * @param requestBody
     * @return
     */
    @PostMapping(value = UrlMapping.USER_EXPORT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDTO<Integer> exportUserList(@RequestBody UserQueryReq requestBody) {
        return new ResultDTO<>(userService.exportUserList(requestBody));
    }


}