package dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserFormDTO {

	@NotEmpty (message = "Podaj nazwę użytkownika")
	@Size(min=5, message="Podaj minimum 5 znaków")
	private String login;
	
	@Size(min=5, message="Podaj minimum 5 znaków")
	private char[] password;
	

	public UserFormDTO() {
		super();
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public char[] getPassword() {
		return password;
	}
	public void setPassword(char[] password) {
		this.password = password;
	}
}
