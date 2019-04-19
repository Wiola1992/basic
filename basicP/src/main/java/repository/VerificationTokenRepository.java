package repository;

import org.springframework.data.repository.CrudRepository;

import model.VerificationToken;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

	public VerificationToken findVerificationTokenByToken(String token);
	
	
}
