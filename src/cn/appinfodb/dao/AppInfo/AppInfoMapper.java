package cn.appinfodb.dao.AppInfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appinfodb.entity.AppInfo;


public interface AppInfoMapper {
	//��ѯȫ����Ϣ
	List<AppInfo> getAppInfoList(@Param("getCurrentPageNo")int getCurrentPageNo,@Param("getPageSize")int getPageSize,@Param("softwareName")String querySoftwareName,@Param("categoryLevel1")Integer queryCategoryLevel1All,@Param("categoryLevel2")Integer queryCategoryLevel2All,@Param("categoryLevel3")Integer queryCategoryLevel3All,@Param("flatformId")Integer queryFlatformIdAll,@Param("status")Integer queryStatusAll);
	//ɾ��
	int deleteAppInfo(@Param("id")int id);
	//��ID��ѯ��Ϣ
	AppInfo getAppInfoID(@Param("id")int id);
	//�޸�
	int updateAppInfo(AppInfo appInfo);
	//��ȡͼƬ·��
	String getAppInfologoPicPath(@Param("id")int id);
	//ɾ��ͼƬ·��
	int deleteAppInfologoPicPath(@Param("id")int id);
	
	AppInfo getAppInfo(@Param("id")Integer id,@Param("APKName")String APKName);
	
	int add(AppInfo appInfo) ;
	
	int updateAppInfolower(@Param("id")int id,@Param("ja")int ja);
	
	int AppInfoCount(@Param("softwareName")String querySoftwareName,@Param("categoryLevel1")Integer queryCategoryLevel1All,@Param("categoryLevel2")Integer queryCategoryLevel2All,@Param("categoryLevel3")Integer queryCategoryLevel3All,@Param("flatformId")Integer queryFlatformIdAll);
	
	int updatecre(@Param("id")int id ,@Param("cre")int cre);
}
