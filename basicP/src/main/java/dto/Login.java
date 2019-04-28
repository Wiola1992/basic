package dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Login {

	@NotEmpty (message = "Podaj adres email")
	String email;
	@Size(min=5, message="Podaj hasło, minimum 5 znaków")
	String password;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
