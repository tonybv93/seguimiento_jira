package com.auth.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private BCryptPasswordEncoder encriptador;
	
	@Autowired
	private DataSource dataSource;	
	private final String USERS_QUERY = "select username, password, enable from usuario where username = ?";
	private final String ROLES_QUERY = "select u.username, r.rol from usuario u inner join usuario_rol ur on (u.id = ur.id_user) inner join rol r on (ur.id_rol = r.id) where u.username =?";

	@Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		  auth.jdbcAuthentication()
		   .usersByUsernameQuery(USERS_QUERY)
		   .authoritiesByUsernameQuery(ROLES_QUERY)
		   .dataSource(dataSource)
		   .passwordEncoder(encriptador);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{

	  http.authorizeRequests()
	   .antMatchers("/").permitAll()
	   .antMatchers("/js/**").permitAll()
	   .antMatchers("/css/**").permitAll()
	   .antMatchers("/vendors/**").permitAll()
	   .antMatchers("/scss/**").permitAll()
	   .antMatchers("/images/**").permitAll()
	   .antMatchers("/").permitAll()
	   .antMatchers("/fonts/**").permitAll()
	   .antMatchers("/login").permitAll()
	   .antMatchers("/registro").permitAll()
	   .antMatchers("/seguimiento/**").hasAuthority("ADMIN")
	   .antMatchers("/acuerdos/**").hasAuthority("ADMIN")
	   .antMatchers("/actas/**").hasAuthority("PROVDES") 
	   .anyRequest().authenticated()
	   .and().csrf().disable()
	   .formLogin().loginPage("/login").failureUrl("/login?error=true")
	   .defaultSuccessUrl("/home")
	   .usernameParameter("username")
	   .passwordParameter("password")
	   .and().logout()
	   .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	   .logoutSuccessUrl("/login")
	   .and().rememberMe()
	   .tokenRepository(persistentTokenRepository())
	   .tokenValiditySeconds(60*60)
	   .and().exceptionHandling().accessDeniedPage("/acceso_denegado");
	 }
	 
	 @Bean
	 public PersistentTokenRepository persistentTokenRepository() {
	  JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
	  db.setDataSource(dataSource);	  
	  return db;
	 }
}
