package cn.appinfodb.service.AppInfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appinfodb.entity.AppInfo;


public interface AppInfoService {
	List<AppInfo> getAppInfoList(int getCurrentPageNo, int getPageSize,String querySoftwareName,Integer queryCategoryLevel1All,Integer queryCategoryLevel2All,Integer queryCategoryLevel3All,Integer queryFlatformIdAll,Integer queryStatusAll);
	
	boolean deleteAppInfo(int id);
	
	AppInfo getAppInfoID(int id);
	
	boolean updateAppInfo(AppInfo appInfo);
	
	String getAppInfologoPicPath(int id);
	
	int deleteAppInfologoPicPath(int id);
	
	AppInfo getAppInfo(Integer id,String APKName);
	
	boolean add(AppInfo appInfo) ;
	
	int updateAppInfolower(int id,int ja);
	
	int AppInfoCount(String querySoftwareName,Integer queryCategoryLevel1All,Integer queryCategoryLevel2All,Integer queryCategoryLevel3All,Integer queryFlatformIdAll);
	
	int updatecre(int id ,int cre);
	
}
