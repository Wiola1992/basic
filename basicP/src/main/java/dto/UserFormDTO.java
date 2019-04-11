package dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserFormDTO {
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@NotEmpty (message = "Podaj nazwę użytkownika")
	@Size(min=5, message="Podaj minimum 5 znaków")
	private String login;
	
	@Size(min=5, message="Podaj minimum 5 znaków")
	private CharSequence password;
	

	public UserFormDTO() {
		super();
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public CharSequence getPassword() {
		return password;
	}
	public void setPassword(CharSequence password) {
		this.password = password;
	}
}
