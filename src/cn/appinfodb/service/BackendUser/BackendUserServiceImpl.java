package cn.appinfodb.service.BackendUser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appinfodb.dao.BackendUser.BackendUserMapper;
import cn.appinfodb.entity.BackendUser;
@Service("backendUserService")
public class BackendUserServiceImpl implements BackendUserService {

	@Resource
	private BackendUserMapper backendUserMapper;
	
	@Override
	public BackendUser house(String userCode, String userPassword) {
		
		return backendUserMapper.house(userCode, userPassword);
	}

}
