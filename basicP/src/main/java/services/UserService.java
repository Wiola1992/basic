package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dto.UserFormDTO;
import model.User;
import repository.UserRepository;

@Service
public class UserService  {
	
	@Autowired
	UserRepository userDAO;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	public void createUser(UserFormDTO formUser)  {
		User user = new User();
		user.setLogin(formUser.getLogin());
		user.setPassword(passwordEncoder.encode(formUser.getPassword()));
		user.setEnabled(1);
		user.setRole("ROLE_USER");
		userDAO.save(user);
	}
	
	
}
