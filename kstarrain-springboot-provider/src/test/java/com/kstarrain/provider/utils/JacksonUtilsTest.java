//package com.kstarrain.provider.utils;
//
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.kstarrain.api.dto.response.UserDTO;
//import com.kstarrain.framework.api.dto.response.ResultDTO;
//import com.kstarrain.framework.common.utils.JacksonUtils;
//import com.kstarrain.provider.constants.Constants;
//import com.kstarrain.provider.enums.AliveFlagEnum;
//import com.kstarrain.provider.persistence.entities.User;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//
//import java.lang.reflect.Type;
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
///**
// * @author: DongYu
// * @create: 2020-04-16 14:46
// * @description:
// */
//@Slf4j
//public class JacksonUtilsTest {
//
//    private User createUser() {
//
//        User user = new User();
//        user.setId(1);
//        user.setName("貂蝉");
//        user.setAge(20);
//        user.setBirthday(new Date());
//        user.setDeposit(new BigDecimal("521.34"));
//        user.setCreateUser(Constants.SYSTEM);
//        user.setCreateDate(new Date());
//        user.setUpdateUser(Constants.SYSTEM);
//        user.setUpdateDate(new Date());
//        user.setAliveFlag(AliveFlagEnum.ENABLED.getCode());
//
//        return user;
//
//    }
//
//    @Test
//    public void test1(){
//
//        User user = this.createUser();
//
//        log.info("-----------------------------------------------");
//        log.info(JacksonUtils.toJSONString(user));
//        log.info(JacksonUtils.toJSONStringPretty(user));
//        log.info("-----------------------------------------------");
//
//    }
//
//    @Test
//    public void test2(){
//
//        String text =   "{\n" +
//                        "  \"id\" : 1,\n" +
//                        "  \"name\" : \"貂蝉\",\n" +
//                        "  \"age\" : 20,\n" +
//                        "  \"birthday\" : \"2020-04-16\",\n" +
//                        "  \"deposit\" : 521.34,\n" +
//                        "  \"createUser\" : \"SYSTEM\",\n" +
//                        "  \"createDate\" : 1587022565317,\n" +
//                        "  \"updateUser\" : \"SYSTEM\",\n" +
//                        "  \"updateDate\" : 1587022565317,\n" +
//                        "  \"no\" : 123456,\n" +
//                        "  \"aliveFlag\" : 1\n" +
//                        "}";
//
//        log.info("-----------------------------------------------");
//        User user = JacksonUtils.parseObject(text, User.class);
//        log.info("-----------------------------------------------");
//
//    }
//
//    @Test
//    public void test3(){
//
//        ResultDTO<User> userResultDTO = new ResultDTO<>();
//        userResultDTO.setData(this.createUser());
//
//        log.info("-----------------------------------------------");
//        log.info(JacksonUtils.toJSONStringPretty(userResultDTO));
//        log.info("-----------------------------------------------");
//
//
//    }
//
//
//    @Test
//    public void test4(){
//
//        String text =   "{\n" +
//                        "  \"success\" : true,\n" +
//                        "  \"data\" : {\n" +
//                        "    \"id\" : 1,\n" +
//                        "    \"name\" : \"貂蝉\",\n" +
//                        "    \"age\" : 20,\n" +
//                        "    \"birthday\" : \"2020-04-16\",\n" +
//                        "    \"deposit\" : 521.34,\n" +
//                        "    \"createUser\" : \"SYSTEM\",\n" +
//                        "    \"createDate\" : 1587023806651,\n" +
//                        "    \"updateUser\" : \"SYSTEM\",\n" +
//                        "    \"updateDate\" : 1587023806651,\n" +
//                        "    \"no\" : 123456,\n" +
//                        "    \"aliveFlag\" : 1\n" +
//                        "  }\n" +
//                        "}";
//        log.info("-----------------------------------------------");
//        ResultDTO resultDTO = JacksonUtils.parseObject(text,  new TypeReference<ResultDTO<User>>(){});
//        log.info("-----------------------------------------------");
//
//
//    }
//
//
//    @Test
//    public void test5(){
//
//        List<User> users = Arrays.asList(createUser(), createUser());
//
//        log.info("-----------------------------------------------");
//        log.info(JacksonUtils.toJSONStringPretty(users));
//        log.info("-----------------------------------------------");
//
//    }
//
//    @Test
//    public void test6(){
//
//        String text =   "[ {\n" +
//                        "  \"id\" : 1,\n" +
//                        "  \"name\" : \"貂蝉\",\n" +
//                        "  \"age\" : 20,\n" +
//                        "  \"birthday\" : \"2020-04-16\",\n" +
//                        "  \"deposit\" : 521.34,\n" +
//                        "  \"createUser\" : \"SYSTEM\",\n" +
//                        "  \"createDate\" : 1587024010255,\n" +
//                        "  \"updateUser\" : \"SYSTEM\",\n" +
//                        "  \"updateDate\" : 1587024010255,\n" +
//                        "   \"no\" : 123456,\n" +
//                        "  \"aliveFlag\" : 1\n" +
//                        "}, {\n" +
//                        "  \"id\" : 1,\n" +
//                        "  \"name\" : \"貂蝉\",\n" +
//                        "  \"age\" : 20,\n" +
//                        "  \"birthday\" : \"2020-04-16\",\n" +
//                        "  \"deposit\" : 521.34,\n" +
//                        "  \"createUser\" : \"SYSTEM\",\n" +
//                        "  \"createDate\" : 1587024010256,\n" +
//                        "  \"updateUser\" : \"SYSTEM\",\n" +
//                        "  \"updateDate\" : 1587024010256,\n" +
//                        "  \"no\" : 123456,\n" +
//                        "  \"aliveFlag\" : 1\n" +
//                        "} ]";
//
//        log.info("-----------------------------------------------");
//        List<User> users = JacksonUtils.parseArray(text, User.class);
//        log.info("-----------------------------------------------");
//
//    }
//
//    @Test
//    public void test7(){
//
//        List<ResultDTO<User>> resultDTOS = Arrays.asList(new ResultDTO(createUser()), new ResultDTO(createUser()));
//
//        log.info("-----------------------------------------------");
//        log.info(JacksonUtils.toJSONStringPretty(resultDTOS));
//        log.info("-----------------------------------------------");
//
//    }
//
//
//    @Test
//    public void test8(){
//
//        String text =   "[ {\n" +
//                        "  \"success\" : true,\n" +
//                        "  \"data\" : {\n" +
//                        "    \"id\" : 1,\n" +
//                        "    \"name\" : \"貂蝉\",\n" +
//                        "    \"age\" : 20,\n" +
//                        "    \"birthday\" : \"2020-04-16\",\n" +
//                        "    \"deposit\" : 521.34,\n" +
//                        "    \"createUser\" : \"SYSTEM\",\n" +
//                        "    \"createDate\" : 1587024418622,\n" +
//                        "    \"updateUser\" : \"SYSTEM\",\n" +
//                        "    \"updateDate\" : 1587024418622,\n" +
//                        "  \"no\" : 123456,\n" +
//                        "    \"aliveFlag\" : 1\n" +
//                        "  }\n" +
//                        "}, {\n" +
//                        "  \"success\" : true,\n" +
//                        "  \"data\" : {\n" +
//                        "    \"id\" : 1,\n" +
//                        "    \"name\" : \"貂蝉\",\n" +
//                        "    \"age\" : 20,\n" +
//                        "    \"birthday\" : \"2020-04-16\",\n" +
//                        "    \"deposit\" : 521.34,\n" +
//                        "    \"createUser\" : \"SYSTEM\",\n" +
//                        "    \"createDate\" : 1587024418623,\n" +
//                        "    \"updateUser\" : \"SYSTEM\",\n" +
//                        "    \"updateDate\" : 1587024418623,\n" +
//                        "  \"no\" : 123456,\n" +
//                        "    \"aliveFlag\" : 1\n" +
//                        "  }\n" +
//                        "} ]";
//
//        log.info("-----------------------------------------------");
//        List<ResultDTO<User>> resultDTOS = JacksonUtils.parseObject(text, new TypeReference<List<ResultDTO<User>>>() {
//        });
//        log.info("-----------------------------------------------");
//    }
//
//
//    @Test
//    public void test9(){
//
//        Type type = User.class;
//        type = UserDTO.class;
//
//        String text =   "{\n" +
//                "  \"id\" : 1,\n" +
//                "  \"name\" : \"貂蝉\",\n" +
//                "  \"age\" : 20,\n" +
//                "  \"birthday\" : \"2020-04-16\",\n" +
//                "  \"deposit\" : 521.34,\n" +
//                "  \"createUser\" : \"SYSTEM\",\n" +
//                "  \"createDate\" : 1587022565317,\n" +
//                "  \"updateUser\" : \"SYSTEM\",\n" +
//                "  \"updateDate\" : 1587022565317,\n" +
//                "  \"no\" : 123456,\n" +
//                "  \"aliveFlag\" : 1\n" +
//                "}";
//
//        log.info("-----------------------------------------------");
//        Object o = JacksonUtils.parseObject(text, type);
//        log.info("-----------------------------------------------");
//
//    }
//
//
//    @Test
//    public void test10(){
//
//        JacksonUtils.toJSONString(createUser());
//        JSON.toJSONString(createUser());
//
//        List<User> users = new ArrayList<>();
//        for (int i = 0; i < 500000; i++) {
//            users.add(createUser());
//        }
//
//
//        log.info("-----------------------------------------------");
//        long time1 = System.currentTimeMillis();
//        String jacksonText = JacksonUtils.toJSONString(users);
//        long time2 = System.currentTimeMillis();
//        log.info("Jackson: {}条数据耗时{}毫秒，Object-->String ", users.size(), time2 - time1);
//
//        String alibabaText = JSON.toJSONString(users);
//        long time3 = System.currentTimeMillis();
//        log.info("阿里fastJson: {}条数据耗时{}毫秒，Object-->String ", users.size(), time3 - time2);
//
//        List<UserDTO> users1 = JacksonUtils.parseArray(jacksonText, UserDTO.class);
//        long time4 = System.currentTimeMillis();
//        log.info("Jackson: {}条数据耗时{}毫秒，String-->Object ", users.size(), time4 - time3);
//
//        List<UserDTO> users2 = JSON.parseArray(alibabaText, UserDTO.class);
//        long time5 = System.currentTimeMillis();
//        log.info("阿里fastJson: {}条数据耗时{}毫秒，String-->Object ", users.size(), time5 - time4);
//
//
//
//        log.info("-----------------------------------------------");
//
//
//
//
//    }
//
//
//    @Test
//    public void test11() throws ParseException {
//
//        UserDTO user = new UserDTO();
//        user.setBirthday(new SimpleDateFormat("yyyyMMdd").parse("20200417"));
//
//
//        System.out.println(JacksonUtils.toJSONString(user));
//
//
//    }
//
//
//}
