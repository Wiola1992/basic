package dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import validation.ResetPasswordMatches;

@ResetPasswordMatches //(message = "Potwierdzenie hasła")
public class NewPasswordForm {
	
	
	@NotEmpty (message = "Podaj adres email")
	String email;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	@Size(min=5, message="Podaj minimum 5 znaków")
	private String password;
	private String confirmPassword;
	
	public NewPasswordForm() {
		super();
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
