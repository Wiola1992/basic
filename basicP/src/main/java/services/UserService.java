package services;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import dto.NewPasswordForm;
import dto.UserFormDTO;
import model.PasswordResetToken;
import model.User;
import model.VerificationToken;
import repository.PasswordResetTokenRepository;
import repository.UserRepository;
import repository.VerificationTokenRepository;
import validation.EmailExistsException;

@Service
public class UserService  {
	
	@Autowired
	UserRepository userDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	VerificationTokenRepository tokenDao;
	
	@Autowired
	PasswordResetTokenRepository tokenResetDao;
	
	@Autowired
	DateService dateService;
	
	public User createUserAccount(UserFormDTO userDTO) {
		User registered = null;
		try {
			registered = registerUser(userDTO);
		} catch (EmailExistsException e) {
			return null;
		}    
		return registered;
	}

	public User registerUser(UserFormDTO userDto) throws EmailExistsException {
		
		if (emailExists(userDto.getEmail())) {
	        throw new EmailExistsException
	          ("There is an account with that email adress: " + userDto.getEmail());
	    }
		
		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setEmail(userDto.getEmail());
		user.setEnabled(0);
		user.setRole("ROLE_USER");
		userDao.save(user);
		return userDao.save(user);
	}
	
	private boolean emailExists(final String email) {
		 User user = userDao.findByEmail(email);
	        if (user != null) {
	            return true;
	        }
	        return false;
	}
	
	   public Optional<User> getUserByID(final long id) {
	        return userDao.findById(id);
	    }
	   
	   public User findUserByEmail(final String email) {
	        return userDao.findByEmail(email);
	    }
	
	   public Boolean confirmRegistrationService(String confirmationToken, LocalDateTime confirmationTime) {
			VerificationToken verificationToken = tokenDao.findVerificationTokenByToken(confirmationToken);
			if(verificationToken !=null) {
				if (confirmationTime.isBefore(verificationToken.getExpiryDate())) {
					User user = verificationToken.getUser();
					user.setEnabled(1);
					userDao.save(user);
					return true;
				}
				
			}
			return false;
		}
	
	   public User resetPasswordUser (String stringResetToken, LocalDateTime now) {
			PasswordResetToken resetToken = tokenResetDao.findPasswordResetTokenByToken(stringResetToken);
			if(resetToken !=null) {
				if (now.isBefore(resetToken.getExpiryDate())) {
					User user = resetToken.getUser();
					
					return user;
				}
				
			}
			return null;
		}
	   
	 /*  public Boolean saveNewPassword( NewPasswordForm newPasswordForm) {
		   String password = passwordEncoder.encode(newPasswordForm.getPassword());
		   
		   
	   } */
 }
