package validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import dto.UserFormDTO;

public class PasswordValidator implements ConstraintValidator<PasswordMatches, Object> {
	
	  @Override
	    public void initialize(PasswordMatches constraintAnnotation) {       
	    }

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		UserFormDTO userDTO = (UserFormDTO) obj;
		
		return userDTO.getConfirmPassword().equals(userDTO.getPassword());
	}
	
}


