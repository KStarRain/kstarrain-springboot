//package com.kstarrain.provider.utils;
//
//import com.kstarrain.api.dto.response.UserDTO;
//import com.kstarrain.provider.constants.Constants;
//import com.kstarrain.provider.enums.AliveFlagEnum;
//import com.kstarrain.provider.persistence.entities.User;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//import java.util.*;
//
///**
// * @author: DongYu
// * @create: 2020-04-14 08:32
// * @description:
// */
//@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class BeanConvertUtilsTest {
//
//    @Test
//    public void test1(){
//
//        List<User> users = new ArrayList<>();
//        for (int i = 0; i < 1000000; i++) {
//            User user = new User();
//            user.setId(1);
//            user.setName("貂蝉");
//            user.setAge(20);
//            user.setBirthday(new Date());
//            user.setDeposit(new BigDecimal("521.34"));
//            user.setCreateUser(Constants.SYSTEM);
//            user.setCreateDate(new Date());
//            user.setUpdateUser(Constants.SYSTEM);
//            user.setUpdateDate(new Date());
//            user.setAliveFlag(AliveFlagEnum.ENABLED.getCode());
//            users.add(user);
//        }
//
//        log.info("-----------------------------------------------");
//
//        BeanConvertOldUtils.beanToBean(users.get(0), UserDTO.class);
//
//        long start2 = System.currentTimeMillis();
//        List<UserDTO> result2 = BeanConvertOldUtils.beanToBeanInList(users, UserDTO.class);
////        result2.get(0).setId("222");
//        long end2 = System.currentTimeMillis();
//        log.info("{}条数据耗时{}毫秒，BeanUtils.copyProperties()", result2.size(), end2 - start2);
//
//
////        long start3 = System.currentTimeMillis();
////        List<UserDTO> result3 = users.stream().map(user -> BeanConvertOldUtils.beanToBeanEffective(user, UserDTO.class)).collect(Collectors.toList());
////        long end3 = System.currentTimeMillis();
////        log.info("{}条数据耗时{}毫秒，net.sf.cglib.proxy, 但是结果没有set方法", result3.size(), end3 - start3);
//
//
//        long start5 = System.currentTimeMillis();
//        List<UserDTO> result5 = BeanConvertUtils.beanToBeanInList(users, UserDTO.class, DateConverter.builder().addPattern("birthday","yyyy-MM-dd HH:mm:ss").build());
//        long end5 = System.currentTimeMillis();
//        log.info("{}条数据耗时{}毫秒，BeanCopier.copy()", result5.size(), end5 - start5);
////        result5.get(0).setId("222");
//
//
////        long start6 = System.currentTimeMillis();
////        List<Map<String, Object>> result6 = BeanConvertUtils.beanToMapInList(users);
////        long end6 = System.currentTimeMillis();
////        log.info("{}条数据耗时{}毫秒，BeanMap.create()", result6.size(), end6 - start6);
//
//        log.info("-----------------------------------------------");
//
//    }
//
//
//
//    @Test
//    public void test2(){
//
//        User src = new User();
//        src.setId(1);
//        src.setName("貂蝉");
//        src.setAge(20);
//        src.setBirthday(new Date());
//        src.setDeposit(new BigDecimal("521.34"));
//        src.setCreateUser(Constants.SYSTEM);
//        src.setCreateDate(new Date());
//        src.setUpdateUser(Constants.SYSTEM);
//        src.setUpdateDate(new Date());
//        src.setAliveFlag(AliveFlagEnum.ENABLED.getCode());
//
////        UserDTO userDTO = BeanConvertUtils.beanToBean(src, UserDTO.class);
//        log.info("-----------------------------------------------");
//
//        Map<String, Object> map = BeanConvertUtils.beanToMap(src);
//
//        User user = BeanConvertUtils.mapToBean(map, User.class);
//        user.setId(6000);
//        log.info("-----------------------------------------------");
//
//        User build = User.builder().age(1).build();
//    }
//
//
//
//    @Test
//    public void test3(){
//
//        log.info("-----------------------------------------------");
//        UserDTO userDTO = BeanConvertUtils.beanToBean(null, UserDTO.class);
//        UserDTO userDTO1 = BeanConvertUtils.mapToBean(null, UserDTO.class);
//        UserDTO userDTO2 = BeanConvertUtils.mapToBean(new HashMap<>(), UserDTO.class);
//        Map<String, Object> stringObjectMap = BeanConvertUtils.beanToMap(null);
//        log.info("-----------------------------------------------");
//        List<UserDTO> userDTOS = BeanConvertUtils.beanToBeanInList(null, UserDTO.class);
//        List<UserDTO> userDTOS1 = BeanConvertUtils.mapToBeanInList(null, UserDTO.class);
//        List<UserDTO> userDTOS2 = BeanConvertUtils.mapToBeanInList(new ArrayList<>(), UserDTO.class);
//        List<Map<String, Object>> maps = BeanConvertUtils.beanToMapInList(null);
//        List<Map<String, Object>> maps1 = BeanConvertUtils.beanToMapInList(new ArrayList<>());
//        log.info("-----------------------------------------------");
//
//    }
//
//
//    @Test
//    public void test4(){
//
//        User src = new User();
//        src.setId(1);
//        src.setName("貂蝉");
//        src.setAge(20);
//        src.setBirthday(new Date());
//        src.setDeposit(new BigDecimal("521.34"));
//        src.setCreateUser(Constants.SYSTEM);
//        src.setCreateDate(new Date());
//        src.setUpdateUser(Constants.SYSTEM);
//        src.setUpdateDate(new Date());
//        src.setAliveFlag(AliveFlagEnum.ENABLED.getCode());
//
//        UserDTO userDTO = BeanConvertUtils.beanToBean(src, UserDTO.class,
//                DateConverter.builder().addPattern("birthday","yyyy-MM-dd HH:mm:ss").build());
//
//
//
//        System.out.println();
//    }
//
//}
