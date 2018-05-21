package cn.appinfodb.service.BackendUser;

import org.apache.ibatis.annotations.Param;

import cn.appinfodb.entity.BackendUser;

public interface BackendUserService {

	BackendUser house(String userCode, String userPassword);
	
}
