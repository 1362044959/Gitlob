package cn.appinfodb.service.DataDictionary;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appinfodb.dao.DataDictionary.DataDictionaryMapper;
import cn.appinfodb.entity.DataDictionary;
@Service("dataDictionaryService")
public class DataDictionaryServiceImpl implements DataDictionaryService {

	@Resource
	private DataDictionaryMapper dataDictionaryMapper;

	@Override
	public List<DataDictionary> flatFormList(String name) {
		// TODO �Զ����ɵķ������
		return dataDictionaryMapper.flatFormList(name);
	}
}
