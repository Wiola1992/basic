package services;

import java.sql.Date;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import dto.UserFormDTO;
import model.User;
import model.VerificationToken;
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
	
	   public Boolean confirmRegistrationService(String confirmationToken) {
			VerificationToken verificationToken = tokenDao.findVerificationTokenByToken(confirmationToken);
			if(verificationToken !=null) {
				Calendar calendar = Calendar.getInstance();
				Date todaysDate = new Date(calendar.getTimeInMillis());
				if (verificationToken.getExpiryDate().after(todaysDate)) {
					User user = verificationToken.getUser();
					user.setEnabled(1);
					userDao.save(user);
					return true;
				}
				
			}
			return false;
		}
	
 }
