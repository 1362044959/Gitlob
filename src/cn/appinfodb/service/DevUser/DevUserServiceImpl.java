package cn.appinfodb.service.DevUser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appinfodb.dao.DevUser.DevUserMapper;
import cn.appinfodb.entity.DevUser;
@Service("devUserService")
public class DevUserServiceImpl implements DevUserService{

	@Resource
	private DevUserMapper devUserMapper;
	
	@Override
	public DevUser house(String userCode, String userPassword) {
		
		return devUserMapper.house(userCode, userPassword);
	}

}
