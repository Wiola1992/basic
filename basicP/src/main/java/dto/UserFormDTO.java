package dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserFormDTO {
	
	@NotEmpty (message = "Podaj imię użytkownika")
	@Size(min=5, message="Minimum 5 znaków")
	private String firstName;
	
	@NotEmpty (message = "Podaj nazwisko")
	@Size(min=5, message="Minimum 5 znaków")
	private String lastName;
	
	@NotEmpty (message = "Podaj email")
	@Email
	private String email;
	
	
	@Size(min=5, message="Podaj minimum 5 znaków")
	private String password;
	private String confirmPassword;
	
	public UserFormDTO() {
		super();
	}
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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


	public String getConfirmPassword() {
		return confirmPassword;
	}


	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
