package cn.appinfodb.service.AppVersion;

import java.util.List;

import cn.appinfodb.entity.AppVersion;



public interface AppVersionService {
	int deleteAppVersion(int id);
	
	AppVersion getAppVersionById(Integer id);
	
	boolean deleteApkFile(Integer id);
	
	List<AppVersion> getAppVersionlist(int id);
	
	int addAppVersion(AppVersion appVersion);
	
	AppVersion getAppVersionByIdAll(Integer id);
	
	int updateAppVarsion(AppVersion appVersion);
	
	String selectcar();
	
	int selectcarAll(String cre);
}
