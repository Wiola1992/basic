package model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class PasswordResetToken {

	 private static final int EXPIRATION = 24;
	 
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="token_id")
	    private Long tokenId;
	     
	    private String token;
	   
	    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	    @JoinColumn(nullable = false, name = "user_id")
	    private User user;
	     
	   private LocalDateTime expiryDate;
	   private LocalDateTime now;
	    
	    public PasswordResetToken() {
		super();
	}
		public LocalDateTime getNow() {
		return now;
	}
	public void setNow(LocalDateTime now) {
		this.now = now;
	}
		
	    public PasswordResetToken(User user, LocalDateTime registrationDate) {
			this.user = user;
			token = UUID.randomUUID().toString();
			expiryDate = calculateExpiryDate(registrationDate);
			this.now = registrationDate;
		}

	/*	private Date calculateExpiryDate(int expiryTimeInMinutes) {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(new Timestamp(cal.getTime().getTime()));
	        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
	        return new Date(cal.getTime().getTime());
	    }*/
	    
	    private LocalDateTime calculateExpiryDate (LocalDateTime registrationDate) {
	    	LocalDateTime expiryDate = registrationDate.plusHours(EXPIRATION);
	    	 return expiryDate;
	    }

		public Long getTokenId() {
			return tokenId;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public LocalDateTime getExpiryDate() {
			return expiryDate;
		}
		
		public void setExpiryDate(LocalDateTime registrationDate) {
			LocalDateTime expiryDate = calculateExpiryDate(registrationDate);
			this.expiryDate = expiryDate;
		}

		public static int getExpiration() {
			return EXPIRATION;
		}
	   
	    
	     
	
}
