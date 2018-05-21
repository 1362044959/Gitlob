package cn.appinfodb.service.AppVersion;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appinfodb.dao.AppVersion.AppVersionMapper;
import cn.appinfodb.entity.AppVersion;


@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService{

	@Resource
	private AppVersionMapper appVersionMapper;
	
	@Override
	public int deleteAppVersion(int id) {
		return appVersionMapper.deleteAppVersion(id);
	}

	@Override
	public AppVersion getAppVersionById(Integer id) {
		// TODO 自动生成的方法存根
		return appVersionMapper.getAppVersionById(id);
	}

	public boolean deleteApkFile(Integer id){
		// TODO Auto-generated method stub
		boolean flag = false;
		if(appVersionMapper.deleteApkFile(id) > 0){
			flag = true;
		}
		return flag;
	}

	@Override
	public List<AppVersion> getAppVersionlist(int id) {
		// TODO 自动生成的方法存根
		return appVersionMapper.getAppVersionlist(id);
	}

	@Override
	public int addAppVersion(AppVersion appVersion) {
		// TODO 自动生成的方法存根
		return appVersionMapper.addAppVersion(appVersion);
	}

	@Override
	public AppVersion getAppVersionByIdAll(Integer id) {
		// TODO 自动生成的方法存根
		return appVersionMapper.getAppVersionByIdAll(id);
	}

	@Override
	public int updateAppVarsion(AppVersion appVersion) {
		// TODO 自动生成的方法存根
		return appVersionMapper.updateAppVarsion(appVersion);
	}

	@Override
	public String selectcar() {
		// TODO 自动生成的方法存根
		return appVersionMapper.selectcar();
	}

	@Override
	public int selectcarAll(String cre) {
		// TODO 自动生成的方法存根
		return appVersionMapper.selectcarAll(cre);
	}
	
}
