package model;

import java.sql.Date;
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
public class VerificationToken {

	 private static final int EXPIRATION = 60* 60 * 24;
	 
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="token_id")
	    private Long tokenId;
	     
	    private String token;
	   
	    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	    @JoinColumn(nullable = false, name = "user_id")
	    private User user;
	     
	   private Date expiryDate;
	    
	    public VerificationToken() {
			
		}
	    public VerificationToken(User user, Date registrationDate) {
			this.user = user;
			token = UUID.randomUUID().toString();
			expiryDate = calculateExpiryDate(registrationDate);
		}

	/*	private Date calculateExpiryDate(int expiryTimeInMinutes) {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(new Timestamp(cal.getTime().getTime()));
	        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
	        return new Date(cal.getTime().getTime());
	    }*/
	    
	    private Date calculateExpiryDate (Date registrationDate) {
	    	Calendar calendar = Calendar.getInstance();
	    	calendar.setTime(registrationDate);
	    	calendar.add(Calendar.DATE, 1);
	    	
	    	 return new Date(calendar.getTimeInMillis());
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

		public Date getExpiryDate() {
			return expiryDate;
		}
		
		public void setExpiryDate(Date registrationDate) {
			Date expiryDate = calculateExpiryDate(registrationDate);
			this.expiryDate = expiryDate;
		}

		public static int getExpiration() {
			return EXPIRATION;
		}
	   
	    
	     
	
}
