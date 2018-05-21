package cn.appinfodb.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class first {

	//首页
	@RequestMapping("/index")
	public String index() {
		return "redirect:/index.jsp";
	}
	//后台登录页面
	@RequestMapping("/backendlogin")
	public String backendlogin() {
		return "backendlogin";
	}
	
	@RequestMapping("/devlogin")
	public String devlogin() {
		return "devlogin";
	}
}
