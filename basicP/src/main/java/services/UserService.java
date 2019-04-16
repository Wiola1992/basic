package services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dto.UserFormDTO;
import model.User;
import repository.UserRepository;
import validation.EmailExistsException;

@Service
public class UserService  {
	
	@Autowired
	UserRepository userDAO;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	public User registerUser(UserFormDTO userDto) throws EmailExistsException {
		
		if (emailExists(userDto.getEmail())) {
	        throw new EmailExistsException
	          ("There is an account with that email adress: " + userDto.getEmail());
	    }
		
		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		//user.setPassword(userDto.getPassword());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		//user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setEmail(userDto.getEmail());
		user.setEnabled(1);
		user.setRole("ROLE_USER");
		userDAO.save(user);
		return userDAO.save(user);
	}
	
	private boolean emailExists(final String email) {
		 User user = userDAO.findByEmail(email);
	        if (user != null) {
	            return true;
	        }
	        return false;
	}
	
	   public Optional<User> getUserByID(final long id) {
	        return userDAO.findById(id);
	    }
	
	
}
