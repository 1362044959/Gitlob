package cn.appinfodb.servlet.developer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.sun.istack.internal.logging.Logger;

import cn.appinfodb.entity.AppCategory;
import cn.appinfodb.entity.AppInfo;
import cn.appinfodb.entity.AppVersion;
import cn.appinfodb.entity.DataDictionary;
import cn.appinfodb.entity.DevUser;
import cn.appinfodb.service.AppCategory.AppCategoryService;
import cn.appinfodb.service.AppInfo.AppInfoService;
import cn.appinfodb.service.AppVersion.AppVersionService;
import cn.appinfodb.service.DataDictionary.DataDictionaryService;
import cn.appinfodb.service.DevUser.DevUserService;
import cn.appinfodb.tools.Constants;
import cn.appinfodb.tools.PageSupport;
import jdk.nashorn.internal.ir.RuntimeNode.Request;

@Controller
@RequestMapping("ADevUser")
public class developerDevUserServlet {

	@Resource
	private DataDictionaryService dataDictionaryService;
	
	@Resource
	AppVersionService appVersionService;
	
	@Resource
	private AppCategoryService appCategoryService;
	
	@Resource
	private AppInfoService appInfoService;
	
	@Resource
	private DevUserService devUserService;
	//登录
	@RequestMapping(value="devlogin",method=RequestMethod.POST)
	public String deng(String devCode,String devPassword ,HttpSession session,Map map){
		DevUser user= devUserService.house(devCode, devPassword);
		if(user !=null) {
			session.setAttribute(Constants.DEV_USER_SESSION, user);
			return "developer/main";
		}else {
			map.put("error", "用户名账号或密码错误！");
			return "redirect:/403.jsp";
		}
	}
	//APP页面
	@RequestMapping("main")
	public String query() {
		return "developer/main";
	}
	//注销
	@RequestMapping("/tui")
	public String tui(HttpSession session) {
		session.removeAttribute(Constants.DEV_USER_SESSION);
		return "redirect:/index.jsp";
	}
	
	//APP信息管理页面
	@RequestMapping("/appinfolist")
	public String appinfolist(String pageIndex,String querySoftwareName,String queryStatus,String queryFlatformId,String queryCategoryLevel1,String queryCategoryLevel2,String queryCategoryLevel3,Map map,HttpServletRequest req) {
		//querySoftwareName	软件名称
		String path = req.getContextPath();
		//queryStatus	状态
		//queryFlatformId	所属平台
		//queryCategoryLevel1	一级分类
		//queryCategoryLevel2 	二级菜单
		//queryCategoryLevel3	三级菜单
		
		
		
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
		Integer queryStatusAll=null;
		if(queryStatus!=null && queryStatus !="") {
			queryStatusAll =Integer.parseInt(queryStatus);
		}
		
		//分页
				PageSupport pages = new PageSupport();
				pages.setTotalCount(appInfoService.AppInfoCount(querySoftwareName, queryCategoryLevel1All, queryCategoryLevel2All, queryCategoryLevel3All, queryFlatformIdAll));//总记录数
				if(pageIndex!=null) {	//当前页面
					pages.setCurrentPageNo(Integer.parseInt(pageIndex));
				}

		
		List<DataDictionary> statusList =dataDictionaryService.flatFormList("APP_STATUS");//状态
		List<DataDictionary> flatFormList =dataDictionaryService.flatFormList("APP_FLATFORM");//所属平台
		List<AppCategory> categoryLevel1List =appCategoryService.categoryLevel1List(0);//一级分类
		List<AppInfo> appInfoList =appInfoService.getAppInfoList(((pages.getCurrentPageNo()-1)*pages.getPageSize()),pages.getPageSize(),querySoftwareName,queryCategoryLevel1All,queryCategoryLevel2All,queryCategoryLevel3All,queryFlatformIdAll,queryStatusAll);//所有信息
		map.put("pages", pages);
		map.put("queryCategoryLevel1", queryCategoryLevel1);
		map.put("queryFlatformId", queryFlatformId);
		map.put("queryStatus", queryStatus);
		map.put("querySoftwareName", querySoftwareName);
		map.put("statusList", statusList);
		map.put("flatFormList", flatFormList);
		map.put("categoryLevel1List", categoryLevel1List);
		map.put("path", path);
		map.put("appInfoList", appInfoList);
		return "/developer/appinfolist";
	}
	//删除
	@RequestMapping(value="/deleteApp",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object deleteApp(String id) {
		HashMap<String ,String> resultMap = new HashMap<String ,String>();
		if(id!=null) {
			if(appInfoService.deleteAppInfo(Integer.parseInt(id))) {
				resultMap.put("delResult", "true");
			}else {
				resultMap.put("delResult", "false");
			}
		}
		return JSONArray.toJSONString(resultMap);
	}
	
	
	
	//二三级分类
	@RequestMapping(value="categorylevellist",produces="application/json;charset=UTF-8")
	@ResponseBody
	public  Object Level1List(String pid) {
		List<AppCategory> categoryLevel1List=null;
		if(pid!=null) {
			 categoryLevel1List =appCategoryService.categoryLevel1List(Integer.parseInt(pid));
		}
		return JSONArray.toJSONString(categoryLevel1List);
	}
	//修改页面
	@RequestMapping("appinfomodify")
	public String appinfomodify(String id,Model model) {
		if(id!=null) { 
			AppInfo app =appInfoService.getAppInfoID(Integer.parseInt(id));
			model.addAttribute("appInfo", app);
		}
		return "/developer/appinfomodify";
	}
	//平台状态
	@RequestMapping(value="datadictionarylist",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object datadictionarylist(String tcode) {
		List<DataDictionary> flatFormList=dataDictionaryService.flatFormList("APP_FLATFORM");
		return JSONArray.toJSONString(flatFormList);
	}
	//修改信息
	@RequestMapping(value="appinfomodifysave")
	public String appinfomodifysave(HttpServletRequest request,HttpServletResponse response,AppInfo appinfo,@RequestParam(value="attach",required=false) MultipartFile attach) {
		String logoPicPath =  null;
		String logoLocPath =  null;
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+java.io.File.separator+"uploadfiles");
			String oldFileName = attach.getOriginalFilename();
			String prefix = FilenameUtils.getExtension(oldFileName);
			int filesize = 500000;
			if(attach.getSize() > filesize){
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_4);
				return "developer/appinfoadd";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
			   ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")){
				 String fileName = appinfo.getAPKName() + ".jpg";
				 File targetFile = new File(path,fileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_2);
					return "developer/appinfoadd";
				} 
				 logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				 logoLocPath = path+File.separator+fileName;
			}else{
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);
				return "developer/appinfoadd";
			}
		}
		appinfo.setLogoLocPath(logoLocPath);
		appinfo.setLogoPicPath(logoPicPath);
		appinfo.setModifyDate(new Date());
		appinfo.setStatus(1);
		if(appInfoService.updateAppInfo(appinfo)) {
			return "redirect:/ADevUser/appinfolist";
		}else {
			return "redirect:/ADevUser/appinfolist";
		}
	}
	//删除图片
	@RequestMapping(value="delfile",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object delfile(String id,String flag) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String fileLocPath=null;
		if(id!=null && flag.equals("logo")) {
			 fileLocPath =appInfoService.getAppInfologoPicPath(Integer.parseInt(id));
			File fine = new File(fileLocPath);
			fine.delete();
			int i = appInfoService.deleteAppInfologoPicPath(Integer.parseInt(id));
			if(i>0) {
				resultMap.put("result", "success");
			}else {
				resultMap.put("result", "failed");
			}
			
		}
		return JSONArray.toJSONString(resultMap);
	}
	//新增页面
	@RequestMapping("appinfoadd")
	public String appinfoadd( AppInfo appInfo) {
		return "developer/appinfoadd";
	}
	
	@RequestMapping("/apkexist")
	@ResponseBody
	public Object apkNameIsExit(@RequestParam String APKName){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(APKName)){
			resultMap.put("APKName", "empty");
		}else{
			AppInfo appInfo = null;
			try {
				appInfo = appInfoService.getAppInfo(null, APKName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(null != appInfo)
				resultMap.put("APKName", "exist");
			else
				resultMap.put("APKName", "noexist");
		}
		return JSONArray.toJSONString(resultMap);
	}
	//新增信息
	@RequestMapping(value="/appinfoaddsave",method=RequestMethod.POST)
	public String addSave(AppInfo appInfo,HttpSession session,HttpServletRequest request,
					@RequestParam(value="a_logoPicPath",required= false) MultipartFile attach){		
		
		String logoPicPath =  null;
		String logoLocPath =  null;
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+java.io.File.separator+"uploadfiles");
		
			String oldFileName = attach.getOriginalFilename();
			String prefix = FilenameUtils.getExtension(oldFileName);
			int filesize = 500000;
			if(attach.getSize() > filesize){
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_4);
				return "developer/appinfoadd";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
			   ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")){
				 String fileName = appInfo.getAPKName() + ".jpg";
				 File targetFile = new File(path,fileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_2);
					return "developer/appinfoadd";
				} 
				 logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				 logoLocPath = path+File.separator+fileName;
			}else{
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);
				return "developer/appinfoadd";
			}
		}
		appInfo.setCreatedBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		appInfo.setCreationDate(new Date());
		appInfo.setLogoPicPath(logoPicPath);
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setDevId(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		appInfo.setStatus(1);
		try {
			if(appInfoService.add(appInfo)){
				return "redirect:/ADevUser/appinfolist";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "developer/appinfoadd";
	}
	//增加版本信息页面
	@RequestMapping("appversionadd")
	public String appversionadd(Map map,String id) {
		if(id!=null) {
			List<AppVersion> appVersionList = appVersionService.getAppVersionlist(Integer.parseInt(id));
			AppVersion appVersion = new AppVersion();
			appVersion.setAppId(Integer.parseInt(id));
			map.put("appVersionList", appVersionList);
			map.put("appVersion", appVersion);
		}
		
		return "developer/appversionadd";
	}

	//新增版本信息
	@RequestMapping(value="addversionsave",method=RequestMethod.POST)
	public String addversionsave(String AppInfo,AppVersion appVersion,HttpSession session,HttpServletRequest request,
			@RequestParam(value="a_downloadLink",required= false) MultipartFile attach) {
		String idPicPath ="";
		String apkFileName="";
		if(!attach.isEmpty()) {
			//路径
			String path =request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			System.out.println("路径======================="+path);
			//原文件名
			String oldFileName =attach.getOriginalFilename();
			System.out.println("原文件名======================="+oldFileName);
			//原后缀
			String prefix =FilenameUtils.getExtension("后缀名======================="+oldFileName);
			System.out.println(prefix);
			//判断文件大小和文件格式
			int filesize =50000000;
			if(attach.getSize()>filesize) {
				request.setAttribute("update", "上传文件大小不能超过500KB");
				return "";
			}else if(prefix.equalsIgnoreCase("apk")){
				String fileName = System.currentTimeMillis()+".apk";
				File targetFile= new File(path,fileName);
				if(!targetFile.exists()) {
					targetFile.mkdirs();
					try {
						attach.transferTo(targetFile);
						System.out.println(attach);
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					apkFileName=fileName;
					idPicPath =path+File.separator+fileName;
				}
			}
			//appVersion.setApkFileName();
			appVersion.setApkLocPath(idPicPath);
			appVersion.setApkFileName(apkFileName);
			appVersion.setDownloadLink(apkFileName);
		}
		appVersion.setCreationDate(new Date());
 		appVersion.setCreatedBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		if(appVersionService.addAppVersion(appVersion)>0) {
			/*String sre =appVersionService.selectcar();
			int id = appVersionService.selectcarAll(sre);*/
			AppInfo info = new AppInfo();
			info.setId(appVersion.getAppId());
			info.setVersionId(appVersion.getId());
			if(appInfoService.updateAppInfo(info)) {
				return "redirect:/ADevUser/appinfolist";
			}else {
				return "redirect:/ADevUser/appversionadd";
			}
		}else {
			return "redirect:/ADevUser/appversionadd";
		}
	}
	//
	@RequestMapping("appversionmodify")
	public String appversionmodify(String vid,String aid,Map map) {
		//appinof 的id
		//版本id
			List<AppVersion> appVersionList = appVersionService.getAppVersionlist(Integer.parseInt(aid));
			AppVersion appVersion =appVersionService.getAppVersionByIdAll(Integer.parseInt(vid));
			map.put("appVersionList", appVersionList);
			map.put("appVersion", appVersion);
		return "developer/appversionmodify";
	}
	
	@RequestMapping("appversionmodifysave")
	public String appversionmodifysave(AppVersion appVersion,HttpSession session,HttpServletRequest request,
			@RequestParam(value="attach",required= false) MultipartFile attach) {
		String idPicPath ="";
		String apkFileName="";
		if(!attach.isEmpty()) {
			//路径
			String path =request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			System.out.println("路径======================="+path);
			//原文件名
			String oldFileName =attach.getOriginalFilename();
			System.out.println("原文件名======================="+oldFileName);
			//原后缀
			String prefix =FilenameUtils.getExtension("后缀名======================="+oldFileName);
			System.out.println(prefix);
			//判断文件大小和文件格式
			int filesize =50000000;
			if(attach.getSize()>filesize) {
				request.setAttribute("update", "上传文件大小不能超过500KB");
				return "";
			}else if(prefix.equalsIgnoreCase("jpg") ){
			
				File targetFile= new File(path,oldFileName);
				if(!targetFile.exists()) {
					targetFile.mkdirs();
					try {
						attach.transferTo(targetFile);
						System.out.println(attach);
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					idPicPath =path+File.separator+oldFileName ;
					apkFileName=request.getContextPath()+"/statics/uploadfiles/"+oldFileName;
				}
			}
			appVersion.setApkLocPath(idPicPath);
		}
		appVersion.setModifyBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		appVersion.setModifyDate(new Date());
		if(appVersionService.updateAppVarsion(appVersion)>0) {
			return "redirect:/ADevUser/appinfolist";
		}else {
			return "redirect:/ADevUser/appversionmodify";
		}
		
	}
	@RequestMapping("appview")
	public String appview(String id,Map map) {
		List<AppVersion> appVersionList = appVersionService.getAppVersionlist(Integer.parseInt(id));
		if(id!=null) {
			AppInfo appInfo =appInfoService.getAppInfoID(Integer.parseInt(id));
			map.put("appInfo", appInfo);
			map.put("appVersionList", appVersionList);
		}
		
		return "developer/appinfoview";
	}
	
	//上架下架
	@RequestMapping(value="/{appId}/distribute",method=RequestMethod.PUT)
	@ResponseBody
	public Object distribute(@PathVariable String appId ,Map map) {
		HashMap<String, Object> resultMap =new HashMap<String, Object>();
		
		int id=0;
		if(appId!=null) {
			resultMap.put("errorCode", '0');
			id=Integer.parseInt(appId);
		}else {
			resultMap.put("errorCode", "exception000001");
		}
		AppInfo appInfo = appInfoService.getAppInfoID(id);
		if(appInfo.getStatus()==2 ||appInfo.getStatus()==5) {//审核通过，开始上架
			if(appInfoService.updateAppInfolower(id,4)>0) {
				resultMap.put("resultMsg", "success");
			}else {
				resultMap.put("resultMsg", "failed");
			}
			
		}else if(appInfo.getStatus()==4) {//上架完毕，开始下架
			if(appInfoService.updateAppInfolower(id,5)>0) {
				resultMap.put("resultMsg", "success");
			}else {
				resultMap.put("resultMsg", "failed");
			}
		}
		return JSONArray.toJSONString(resultMap);
	}
}
