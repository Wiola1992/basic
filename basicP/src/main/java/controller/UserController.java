package controller;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dto.UserFormDTO;
import model.User;
import services.UserService;
import validation.EmailExistsException;
import validation.PasswordValidator;

import javax.validation.ConstraintValidatorContext;

@Controller
public class UserController {
	
	 private static final ConstraintValidatorContext ConstraintValidatorContext = null;

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	UserService userService;

	@Secured(value = { "ROLE_ANONYMOUS" })
	@RequestMapping(value="/registration", method=RequestMethod.GET)
	public String showRegistrationForm (Model model) {
		UserFormDTO userDto = new UserFormDTO();
		model.addAttribute("user", userDto);
	
		return "registrationForm";
	}
	
	
	@Secured(value = { "ROLE_ANONYMOUS" })
	@RequestMapping(value="/registration", method=RequestMethod.POST)
	public String obsluzFormUser(Model model, @ModelAttribute("user") @Valid UserFormDTO userDTO, BindingResult result, Errors errors ) throws EmailExistsException {
		 
			User registered = new User();
		    if (!result.hasErrors()) {
		        registered = createUserAccount(userDTO, result);
		        //LOGGER.debug("Registering user account with information: {}", userDTO);
		    }
		    if (registered == null) {
		    	model.addAttribute("s", "Podany email już istnieje w naszej bazie.");
		    	return  "registrationForm";
		        //result.rejectValue("email", "Podany email już istnieje w naszej bazie");
		        
		    }
		    if (result.hasErrors()) {
		    	
		        return "registrationForm";
		    } 
		    return "poFormularzu";
		}
		

	private User createUserAccount(UserFormDTO userDTO, BindingResult result) {
		User registered = null;
		try {
			registered = userService.registerUser(userDTO);
		} catch (EmailExistsException e) {
			return null;
		}    
		return registered;
	}
}
