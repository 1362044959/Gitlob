package cn.appinfodb.service.AppInfo;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appinfodb.dao.AppInfo.AppInfoMapper;
import cn.appinfodb.entity.AppInfo;
import cn.appinfodb.service.AppVersion.AppVersionService;
import cn.appsys.dao.appversion.AppVersionMapper;

@Service("appInfoService")
public class AppInfoServiceImpl implements AppInfoService{

	@Resource
	AppInfoMapper appInfoMapper;
	
	@Resource
	AppVersionService appVersionService;
	
	public List<AppInfo> getAppInfoList(int getCurrentPageNo, int getPageSize,String querySoftwareName,Integer queryCategoryLevel1All,Integer queryCategoryLevel2All,Integer queryCategoryLevel3All,Integer queryFlatformIdAll,Integer queryStatusAll) {
		// TODO 自动生成的方法存根
		return appInfoMapper.getAppInfoList(getCurrentPageNo,getPageSize,querySoftwareName,queryCategoryLevel1All,queryCategoryLevel2All,queryCategoryLevel3All,queryFlatformIdAll,queryStatusAll);
	}

	@Override
	public boolean deleteAppInfo(int id) {
		appVersionService.deleteAppVersion(id);
		if(appInfoMapper.deleteAppInfo(id)>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public AppInfo getAppInfoID(int id) {
		
		return appInfoMapper.getAppInfoID(id);
	}

	@Override
	public boolean updateAppInfo(AppInfo appInfo) {
		if(appInfoMapper.updateAppInfo(appInfo)>0) {
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public String getAppInfologoPicPath(int id) {
		
		return appInfoMapper.getAppInfologoPicPath(id);
	}

	@Override
	public int deleteAppInfologoPicPath(int id) {
		// TODO 自动生成的方法存根
		return appInfoMapper.deleteAppInfologoPicPath(id);
	}
	
	public AppInfo getAppInfo(Integer id,String APKName) {
		// TODO Auto-generated method stub
		return appInfoMapper.getAppInfo(id,APKName);
	}

	@Override
	public boolean add(AppInfo appInfo) {
		// TODO 自动生成的方法存根
		if(appInfoMapper.add(appInfo)>0) {
			return	true;
		}else {
			return	false;
		}
		
	}

	@Override
	public int updateAppInfolower(int id,int ja) {
		
		return appInfoMapper.updateAppInfolower(id,ja);
	}

	@Override
	public int AppInfoCount(String querySoftwareName,Integer queryCategoryLevel1All,Integer queryCategoryLevel2All,Integer queryCategoryLevel3All,Integer queryFlatformIdAll) {
		// TODO 自动生成的方法存根
		return appInfoMapper.AppInfoCount(querySoftwareName, queryCategoryLevel1All, queryCategoryLevel2All, queryCategoryLevel3All, queryFlatformIdAll);
	}

	@Override
	public int updatecre(int id, int cre) {
		// TODO 自动生成的方法存根
		return appInfoMapper.updatecre(id, cre);
	}

}
