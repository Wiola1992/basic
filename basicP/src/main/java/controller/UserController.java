package controller;

import java.time.LocalDateTime;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import dto.Login;
import dto.UserFormDTO;
import model.User;
import model.VerificationToken;
import repository.UserRepository;
import repository.VerificationTokenRepository;
import services.DateService;
import services.EmailService;
import services.UserService;
import validation.EmailExistsException;

@Controller
public class UserController {
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	@Autowired
	VerificationTokenRepository tokenDao;
	
	@Autowired
	UserRepository userDao;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	DateService dateService;
	
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
	public String obsluzFormUser(Model model, @ModelAttribute("user") @Valid UserFormDTO userDTO, BindingResult result, 
			WebRequest request, Errors errors ) throws EmailExistsException {
		 
			if (result.hasErrors()) {
				return "registrationForm";
			} 
			
			User user = userService.createUserAccount(userDTO);
		    
		    if (user == null) {
		    	model.addAttribute("inf", "Podany email już istnieje w naszej bazie.");
		    	return  "registrationForm";
		    } else { 
		    	LocalDateTime todaysDate = LocalDateTime.now();
		    	VerificationToken verificationToken = new VerificationToken(user, todaysDate);
		    	tokenDao.save(verificationToken);
		    	String recipientEmail = userDTO.getEmail();
		    	String message = "W celu potwierdzenia rejestracji kliknij w poniższy link: " 
		    			+"http://localhost:8080/basicP/confirm-account?token="
		    			+ verificationToken.getToken();
		    	if (emailService.sendEmail(recipientEmail, "Potwierdzenie rejestracji", message))
		    	{
		    		model.addAttribute("inf", "Na podany adres wysłano link aktywacyjny, potwierd rejestrację");
		    	}
		    }
		    	
		    	
		    return "registrationConfirm";
	}
	
	@RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
	public String confirmRegistration(Model model, @RequestParam("token")String confirmationToken) {
		
		VerificationToken verificationToken = tokenDao.findVerificationTokenByToken(confirmationToken);
		LocalDateTime confirmationTime = LocalDateTime.now();
		//model.addAttribute("czas" , czas);
		
		if(userService.confirmRegistrationService(confirmationToken, confirmationTime)) {
			
			model.addAttribute("inf", "Rejestracja przebiegła prawidłowo. Konto zostało aktywowane");
		}
		else {
			model.addAttribute("inf", "Błąd. Konto nieaktywne. Spróbuj zarejestrować się ponownie.");
		}
		return "registrationConfirm";
		
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model)
	{
		String errorMessage = null;
	//	model.addAttribute("login", new Login()); 
		if (error != null) {
			errorMessage = "Niepoprawny login lub hasło!";
		}
		if (logout != null) {
			errorMessage= "Zostałeś wylogowany prawidłowo.";
		}
		
		model.addAttribute("msg", errorMessage);
		return "login";
	}
	
}


