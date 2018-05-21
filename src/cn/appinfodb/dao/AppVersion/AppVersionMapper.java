package cn.appinfodb.dao.AppVersion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appinfodb.entity.AppVersion;


public interface AppVersionMapper {

	int deleteAppVersion(@Param("id")int id);
	
	AppVersion getAppVersionById(Integer id);
	
	AppVersion getAppVersionByIdAll(Integer id);
	
	int deleteApkFile(Integer id);
	
	int getByid(int id);
	
	List<AppVersion> getAppVersionlist(int id);
	
	int addAppVersion(AppVersion appVersion);
	
	int updateAppVarsion(AppVersion appVersion);
	
	String selectcar();
	
	int selectcarAll(String cre);
}
