package cn.appinfodb.dao.BackendUser;

import org.apache.ibatis.annotations.Param;

import cn.appinfodb.entity.BackendUser;

public interface BackendUserMapper {

	BackendUser house(@Param("userCode")String userCode, @Param("userPassword")String userPassword);
}
