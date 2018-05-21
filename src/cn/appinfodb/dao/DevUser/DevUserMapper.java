package cn.appinfodb.dao.DevUser;

import org.apache.ibatis.annotations.Param;

import cn.appinfodb.entity.DevUser;

public interface DevUserMapper {
	DevUser house(@Param("userCode")String userCode, @Param("userPassword")String userPassword);
	
	
}
