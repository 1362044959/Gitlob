package cn.appinfodb.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class first {

	//��ҳ
	@RequestMapping("/index")
	public String index() {
		return "redirect:/index.jsp";
	}
	//��̨��¼ҳ��
	@RequestMapping("/backendlogin")
	public String backendlogin() {
		return "backendlogin";
	}
	
	@RequestMapping("/devlogin")
	public String devlogin() {
		return "devlogin";
	}
}
