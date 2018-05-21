package cn.appinfodb.servlet.backend;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.appinfodb.entity.AppCategory;
import cn.appinfodb.entity.AppInfo;
import cn.appinfodb.entity.BackendUser;
import cn.appinfodb.entity.DataDictionary;
import cn.appinfodb.service.AppCategory.AppCategoryService;
import cn.appinfodb.service.AppInfo.AppInfoService;
import cn.appinfodb.service.BackendUser.BackendUserService;
import cn.appinfodb.service.DataDictionary.DataDictionaryService;
import cn.appinfodb.tools.Constants;
import cn.appinfodb.tools.PageSupport;

@Controller
@RequestMapping("BDevUser")
public class backendDevUserServlet {

	@Resource
	AppInfoService appInfoService;
	@Resource
	AppCategoryService appCategoryService;
	@Resource
	DataDictionaryService dataDictionaryService;
	
	@Resource
	private BackendUserService backendUserService;
	//��¼
	@RequestMapping(value="backendlogin",method=RequestMethod.POST)
	public String deng(String userCode,String userPassword ,HttpSession session,Map map){
		BackendUser user= backendUserService.house(userCode, userPassword);
		if(user !=null) {
			session.setAttribute(Constants.USER_SESSION, user);
			return "backend/main";
		}else {
			map.put("error", "�û����˺Ż��������");
			return "redirect:/403.jsp";
		}
	}
	//ע��
	@RequestMapping("/tui")
	public String tui(HttpSession session) {
		session.removeAttribute(Constants.USER_SESSION);
		return "redirect:/index.jsp";
	}
	
	//APP��Ϣ����ҳ��
		@RequestMapping("/applist")
		public String appinfolist(String pageIndex,String querySoftwareName,String queryFlatformId,String queryCategoryLevel1,String queryCategoryLevel2,String queryCategoryLevel3,Map map,HttpServletRequest req) {
			//querySoftwareName	�������
			String path = req.getContextPath();
			//queryFlatformId	����ƽ̨
			//queryCategoryLevel1	һ������
			//queryCategoryLevel2 	�����˵�
			//queryCategoryLevel3	�����˵�
			
			
			

			Integer queryCategoryLevel1All=null;
			if(queryCategoryLevel1!=null && queryCategoryLevel1 !="") {
				queryCategoryLevel1All =Integer.parseInt(queryCategoryLevel1);
			}
			Integer queryCategoryLevel2All=null;
			if(queryCategoryLevel2!=null && queryCategoryLevel2 !="") {
				queryCategoryLevel2All =Integer.parseInt(queryCategoryLevel2);
			}
			Integer queryCategoryLevel3All=null;
			if(queryCategoryLevel3!=null && queryCategoryLevel3 !="") {
				queryCategoryLevel3All =Integer.parseInt(queryCategoryLevel3);
			}
			Integer queryFlatformIdAll=null;
			if(queryFlatformId!=null && queryFlatformId !="") {
				queryFlatformIdAll =Integer.parseInt(queryFlatformId);
			}
			//��ҳ
			PageSupport pages = new PageSupport();
			pages.setTotalCount(appInfoService.AppInfoCount(querySoftwareName, queryCategoryLevel1All, queryCategoryLevel2All, queryCategoryLevel3All, queryFlatformIdAll));//�ܼ�¼��
			if(pageIndex!=null) {	//��ǰҳ��
				pages.setCurrentPageNo(Integer.parseInt(pageIndex));
			}
			
			List<DataDictionary> statusList =dataDictionaryService.flatFormList("APP_STATUS");//״̬
			List<DataDictionary> flatFormList =dataDictionaryService.flatFormList("APP_FLATFORM");//����ƽ̨
			List<AppCategory> categoryLevel1List =appCategoryService.categoryLevel1List(0);//һ������
			List<AppInfo> appInfoList =appInfoService.getAppInfoList(((pages.getCurrentPageNo()-1)*pages.getPageSize()),pages.getPageSize(),querySoftwareName,queryCategoryLevel1All,queryCategoryLevel2All,queryCategoryLevel3All,queryFlatformIdAll,null);//������Ϣ
			map.put("pages", pages);
			map.put("queryCategoryLevel1", queryCategoryLevel1);
			map.put("queryFlatformId", queryFlatformId);
			map.put("querySoftwareName", querySoftwareName);
			map.put("statusList", statusList);
			map.put("flatFormList", flatFormList);
			map.put("categoryLevel1List", categoryLevel1List);
			map.put("path", path);
			map.put("appInfoList", appInfoList);
			return "/backend/applist";
		}
		
		//����������
		@RequestMapping(value="categorylevellist",produces="application/json;charset=UTF-8")
		@ResponseBody
		public  Object Level1List(String pid) {
			List<AppCategory> categoryLevel1List=null;
			if(pid!=null) {
				 categoryLevel1List =appCategoryService.categoryLevel1List(Integer.parseInt(pid));
			}
			return JSONArray.toJSONString(categoryLevel1List);
		}
	
}
