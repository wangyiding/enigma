package test.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("action")
public class TestAction {
	
	

	@RequestMapping("test.do")
	public String test(String names,Model m1){
		System.out.println("收到前台内容"+names);
		m1.addAttribute("names","ok this is "+names);
		return "springmvc.jsp";
	}
}
