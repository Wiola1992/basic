package repository;

import org.springframework.data.repository.CrudRepository;

import model.PasswordResetToken;
import model.VerificationToken;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {

	public PasswordResetToken findPasswordResetTokenByToken(String token);
	
	
}
