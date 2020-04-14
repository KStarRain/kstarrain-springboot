package com.kstarrain.provider.persistence.mappers;

import com.kstarrain.provider.persistence.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;


@Mapper
public interface UserMapper {

	int insertSelective(User record);

	int insertBatch(@Param("recordList") List<User> recordList);

	int deleteByPrimaryKey(@Param("id") Integer id);

	int updateByPrimaryKeySelective(User record);

	int countSelective(User criteria);

	User queryByPrimaryKey(@Param("id") Integer id);

	List<User> querySelective(User criteria);

    List<User> queryByNames(@Param("names") List<String> names);
}
