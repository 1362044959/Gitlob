package cn.appinfodb.dao.DataDictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appinfodb.entity.DataDictionary;

public interface DataDictionaryMapper {
	
	List<DataDictionary> flatFormList(@Param("name")String name);
	
	/*public List<DataDictionary> categoryLevel1List();*/
}
