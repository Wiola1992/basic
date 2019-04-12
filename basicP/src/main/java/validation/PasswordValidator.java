package validation;

import org.springframework.validation.Errors;

import dto.UserFormDTO;

public class PasswordValidator {

	public void validate (UserFormDTO userDTO, Errors errors) {
		if(!(userDTO.getConfirmPassword().equals(userDTO.getPassword()))){
			errors.rejectValue("password", "nie pasujące hasło");
		}
	}
	
}


