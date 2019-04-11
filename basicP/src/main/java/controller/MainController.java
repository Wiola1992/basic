package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

	
	@RequestMapping(value="", method=RequestMethod.GET)
	public  String firstPage () {
		return "glowny";
	}
	
	@RequestMapping(value="user", method=RequestMethod.GET)
	public  String shop () {
		return "user";
	}
	
	@RequestMapping(value="admin", method=RequestMethod.GET)
	public  String pageAdmin () {
		return "admin";
	}
	
	@RequestMapping(value="accessDenied", method=RequestMethod.GET)
	public  String accessDenied () {
		return "accessDenied";
	}
}
