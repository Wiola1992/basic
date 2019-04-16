package validation;

import java.lang.annotation.Documented;
//import java.lang.annotation.Retention;
//import java.lang.annotation.Target;
import java.lang.annotation.Retention;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

//@Target({TYPE, FIELD, ANNOTATION_TYPE}) 
//@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
	 String message() default "Invalid email";
	 Class<?>[] groups() default {}; 
	 Class<? extends Payload>[] payload() default {};

}
