package validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import dto.NewPasswordForm;

public class ResetPasswordValidator implements ConstraintValidator<ResetPasswordMatches, Object> {
	
	  @Override
	    public void initialize(ResetPasswordMatches constraintAnnotation) {       
	    }

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		NewPasswordForm newPasswordForm = (NewPasswordForm) obj;
		
		return newPasswordForm.getConfirmPassword().equals(newPasswordForm.getPassword());
	}
	
	
}


