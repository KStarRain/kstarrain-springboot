package com.kstarrain.provider.service;

import com.kstarrain.api.dto.request.UserQueryReq;
import com.kstarrain.api.dto.response.UserDTO;
import com.kstarrain.framework.api.dto.response.PageResultDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author: DongYu
 * @create: 2020-04-10 15:46
 * @description:
 */
public interface UserService {

    int importUser(MultipartFile file) throws ParseException, ReflectiveOperationException, IOException;

    PageResultDTO<UserDTO> queryUserPageList(UserQueryReq requestBody);

    void exportUserList(UserQueryReq requestBody, HttpServletResponse response) throws ReflectiveOperationException, IOException;
}
