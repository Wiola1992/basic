package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.UserFormDTO;
import model.User;
import repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userDAO;

	public void createUser(UserFormDTO formUser) {
		User user = new User();
		user.setLogin(formUser.getLogin());
		user.setPassword(formUser.getPassword());
		user.setEnabled(1);
		user.setRole("ROLE_USER");
		userDAO.save(user);
	}
}
