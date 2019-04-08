package controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dto.UserFormDTO;
import services.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;

	@Secured(value = { "ROLE_ANONYMOUS" })
	@RequestMapping(value="/registration", method=RequestMethod.GET)
	public String registration (Model model) {
		model.addAttribute("formLogin", new UserFormDTO());
	
		return "registrationForm";
	}
	
	
	@Secured(value = { "ROLE_ANONYMOUS" })
	@RequestMapping(value="/registration", method=RequestMethod.POST)
	public String obsluzFormUser(@ModelAttribute("formLogin") @Valid UserFormDTO formLogin, BindingResult result ) {
		
		if(result.hasErrors()) {
			return "registrationForm";
		} else {
			userService.createUser(formLogin);
			return "poFormularzu";
		}
	}
}
