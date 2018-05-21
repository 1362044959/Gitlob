package cn.appinfodb.dao.AppCategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appinfodb.entity.AppCategory;

public interface AppCategoryMapper {
	List<AppCategory> categoryLevel1List(@Param("id")int id);
	
	List<AppCategory> categoryLevel1List2(@Param("id")int id);
	
	public List<AppCategory> appInfoList();
}
