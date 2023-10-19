package org.java.app.db.auth.config;

import org.java.app.db.auth.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
public class AuthConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers("/login").permitAll()
		.requestMatchers("/categories/create").hasAuthority("SUPERADMIN")
		.requestMatchers("/categories/edit/**").hasAuthority("SUPERADMIN")
		.requestMatchers("/**").hasAnyAuthority("SUPERADMIN", "USER")
		.and().formLogin().defaultSuccessUrl("/")
		.and().logout(logout -> logout.logoutSuccessUrl("/login"));
		
		return http.build();
	}
	
	@Bean
	UserService userDetailsService() {
		return new UserService();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
}
