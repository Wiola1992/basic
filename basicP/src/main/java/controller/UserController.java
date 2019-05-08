package controller;

import java.time.LocalDateTime;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import dto.EmailForm;
import dto.NewPasswordForm;
import dto.UserFormDTO;
import model.PasswordResetToken;
import model.User;
import model.VerificationToken;
import repository.PasswordResetTokenRepository;
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
	PasswordResetTokenRepository tokenResetDao;
	
	@Autowired
	UserRepository userDao;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	DateService dateService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
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
		if (error != null) {
			errorMessage = "Niepoprawny login lub hasło! ";
			String linkReset =  "link";
			model.addAttribute("linkResetPassword", linkReset); 
		}
		if (logout != null) {
			errorMessage= "Zostałeś wylogowany prawidłowo.";
		}
		
		model.addAttribute("msg", errorMessage);
		return "login";
	}
	
	@RequestMapping(value="user/resetPassword", method= {RequestMethod.GET})
	public String resetPassword(Model model) {
		EmailForm emailDto = new EmailForm();
		model.addAttribute("emailForm", emailDto);
		return "emailForm";
		
	}
	
	@RequestMapping(value="user/resetPassword", method = RequestMethod.POST)
	public String resetPassword (@ModelAttribute("emailForm") @Valid EmailForm emailDto, BindingResult result, Model model ) {
		if (result.hasErrors())
    	{
    		model.addAttribute("inf", "Error");
    	}
		
		String userEmail = emailDto.getEmail();
		User user = userService.findUserByEmail(userEmail);
		String errorMessage = null;
		if (user == null) {
			errorMessage = "Podany mail nie istnieje w naszej bazie! ";
		} else {
			LocalDateTime resetTime = LocalDateTime.now();
			PasswordResetToken resetToken = new PasswordResetToken(user, resetTime);
			tokenResetDao.save(resetToken);
	    	String message = "W celu zmiany hasła kliknij w poniższy link: " 
	    			+"http://localhost:8080/basicP/user/resetPassword/confirm?token="
	    			+ resetToken.getToken();
	    	if (emailService.sendEmail(userEmail, "Zmiana hasła", message))
	    	{
	    		model.addAttribute("inf", "Na podany adres wysłano link umożliwiający zmianę hasła");
	    	}
		}
		
		model.addAttribute("msg", errorMessage);
		return "resetPassword";
	}
	
	@RequestMapping(value="user/resetPassword/confirm", method= RequestMethod.GET)
	public String confirmResetPassword(Model model, @RequestParam("token")String stringResetToken) {
		
		LocalDateTime now = LocalDateTime.now();
		User user = userService.resetPasswordUser(stringResetToken, now);
		if(user!=null) {
			model.addAttribute("user", user);
			model.addAttribute("inf", "Podaj nowe hasło");
			NewPasswordForm newPassword = new NewPasswordForm();
			//newPassword.setEmail(user.getEmail());
			model.addAttribute("newPassword", newPassword);
		}
		else {
			model.addAttribute("inf", "Błąd. Upłyneło ponad 24h, token nieaktywny.");
		}
		return "newPasswordForm";
		
	}
	
	
	@RequestMapping(value="/user/resetPassword/confirm", method=RequestMethod.POST)
	public String changePassword (Model model, @ModelAttribute("newPassword") @Valid NewPasswordForm newPasswordForm, BindingResult result, 
			WebRequest request, Errors errors, @RequestParam("token")String stringResetToken ) {
		 
		
			if (result.hasErrors()) {
				model.addAttribute("inf", "błędy");
				return "newPasswordForm";
			} 
			
			PasswordResetToken passwordResetToken = tokenResetDao.findPasswordResetTokenByToken(stringResetToken);
			User userek = passwordResetToken.getUser();
		    if (userek == null) {
		    	model.addAttribute("inf", "Nie istnieje taki email w bazie. Zarejestruj się");
		    	return  "registrationForm";
		    } else {
		    	
		    	userek.setPassword(passwordEncoder.encode(newPasswordForm.getPassword()));
		    	userDao.save(userek);
		    	
		    		model.addAttribute("inf", "Hasło zmienione, zaloguj się");
		    	
		    }
		    	
		    	
		    return "registrationConfirm";
	}
	
	
		
}


