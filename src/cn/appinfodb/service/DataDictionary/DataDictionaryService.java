package cn.appinfodb.service.DataDictionary;

import java.util.List;

import cn.appinfodb.entity.DataDictionary;

public interface DataDictionaryService {
	List<DataDictionary> flatFormList(String name);
}
