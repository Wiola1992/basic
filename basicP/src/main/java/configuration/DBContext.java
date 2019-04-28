package configuration;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.mysql.cj.jdbc.MysqlDataSource;

@EnableJpaRepositories("repository")
@ComponentScan({"repository", "model"})
@Configuration
@PropertySource({
   "classpath:database.properties"
})
public class DBContext {

	@Autowired
	Environment env;
	
	@Bean
    public DataSource getDataSource() throws SQLException {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName(env.getProperty("jdbc.serverName"));
		//dataSource.setPort(3306);
		dataSource.setPort(Integer.parseInt(env.getProperty("jdbc.port")));
		dataSource.setDatabaseName(env.getProperty("jdbc.databaseName"));
		dataSource.setUser(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		dataSource.setServerTimezone(env.getProperty("jdbc.serverTimezone"));
		
        return dataSource;
    } 
	
 
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws SQLException {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(getDataSource());
        entityManager.setPackagesToScan("repositiry", "model");
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManager.setJpaProperties(getJpaProperties());
 
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.show_sql", "true");
       
        entityManager.setJpaProperties(jpaProperties);
 
        return entityManager;
    }
    @Bean
    public Properties getJpaProperties() {
    	Properties properties = new Properties();
    	properties.setProperty("hibernate.hbm2ddl.auto", "update");
    	properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
    	
    	return properties;
    }
 
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
 
        return transactionManager;
    }
 
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
