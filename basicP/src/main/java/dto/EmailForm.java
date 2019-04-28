package dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class EmailForm {

	@NotEmpty (message = "Podaj adres email")
	String email;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
