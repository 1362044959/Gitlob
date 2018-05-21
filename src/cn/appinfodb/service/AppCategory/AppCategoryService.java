package cn.appinfodb.service.AppCategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appinfodb.entity.AppCategory;

public interface AppCategoryService {
	List<AppCategory> categoryLevel1List(int id);
	
	List<AppCategory> categoryLevel1List2(@Param("id")int id);
	List<AppCategory> appInfoList();
}
