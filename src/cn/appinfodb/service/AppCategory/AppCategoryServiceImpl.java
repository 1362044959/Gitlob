package cn.appinfodb.service.AppCategory;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appinfodb.dao.AppCategory.AppCategoryMapper;
import cn.appinfodb.entity.AppCategory;
@Service("appCategoryService")
public class AppCategoryServiceImpl implements AppCategoryService{

	@Resource
	private AppCategoryMapper appCategoryMapper;
	
	@Override
	public List<AppCategory> categoryLevel1List(int id) {
		
		return appCategoryMapper.categoryLevel1List(id);
	}

	@Override
	public List<AppCategory> categoryLevel1List2(int id) {
		return appCategoryMapper.categoryLevel1List2(id);
	}

	@Override
	public List<AppCategory> appInfoList() {
		// TODO �Զ����ɵķ������
		return appCategoryMapper.appInfoList();
	}
	
	
	
}
