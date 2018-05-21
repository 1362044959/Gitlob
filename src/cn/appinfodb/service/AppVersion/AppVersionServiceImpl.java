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
		// TODO �Զ����ɵķ������
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
		// TODO �Զ����ɵķ������
		return appVersionMapper.getAppVersionlist(id);
	}

	@Override
	public int addAppVersion(AppVersion appVersion) {
		// TODO �Զ����ɵķ������
		return appVersionMapper.addAppVersion(appVersion);
	}

	@Override
	public AppVersion getAppVersionByIdAll(Integer id) {
		// TODO �Զ����ɵķ������
		return appVersionMapper.getAppVersionByIdAll(id);
	}

	@Override
	public int updateAppVarsion(AppVersion appVersion) {
		// TODO �Զ����ɵķ������
		return appVersionMapper.updateAppVarsion(appVersion);
	}

	@Override
	public String selectcar() {
		// TODO �Զ����ɵķ������
		return appVersionMapper.selectcar();
	}

	@Override
	public int selectcarAll(String cre) {
		// TODO �Զ����ɵķ������
		return appVersionMapper.selectcarAll(cre);
	}
	
}
