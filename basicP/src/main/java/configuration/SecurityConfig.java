package configuration;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;

import com.mysql.cj.jdbc.MysqlDataSource;

//@Configuration
@EnableWebSecurity
@Import({ DBContext.class })
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	


	
/*	 @Autowired
	  private DataSource dataSource;

	  @Override
	  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	    auth.jdbcAuthentication().dataSource(dataSource)
	        .usersByUsernameQuery("select login, password, enabled"
	            + " from shop_users where login=?")
	        .authoritiesByUsernameQuery("select username, role"
	            + "from shop_users where login=?")
	        .passwordEncoder(new BCryptPasswordEncoder());
	  }

	  @Override
	  protected void configure(HttpSecurity http) throws Exception {

	    http.authorizeRequests().anyRequest().hasAnyRole("ADMIN", "USER")
	    .and()
	    .httpBasic(); // Authenticate users with HTTP basic authentication
	  }*/
	

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired 
    DataSource dataSource;
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
    	    auth.jdbcAuthentication().dataSource(dataSource)
 			.usersByUsernameQuery(
 			"select login,password, enabled from shop_users where login=?")
 		.authoritiesByUsernameQuery(
 			"select login, role from shop_users where login=?");
    	
   	
    	
 			/* auth.inMemoryAuthentication()
         .passwordEncoder(passwordEncoder)
         .withUser("user").password(passwordEncoder.encode("123456")).roles("USER")
         .and()
         .withUser("admin").password(passwordEncoder.encode("123456")).roles("USER", "ADMIN"); */
    }
 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/login").permitAll()
        .antMatchers("/admin/**").hasRole("ADMIN")
      //  .antMatchers("/**").hasAnyRole("ADMIN", "USER")
        .and().formLogin()
        .and().logout().logoutSuccessUrl("/login").permitAll()
        .and().csrf().disable();
    } 
    
}