package controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes("userName")
@Controller
public class MainController {

	
	@RequestMapping(value="", method=RequestMethod.GET)
	public  String firstPage () {
		return "glowny";
	}
	
	@RequestMapping(value="user", method=RequestMethod.GET)
	public  String shop (Principal principal, Model model) {
		String userName = principal.getName();
		model.addAttribute("userName", userName);
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
