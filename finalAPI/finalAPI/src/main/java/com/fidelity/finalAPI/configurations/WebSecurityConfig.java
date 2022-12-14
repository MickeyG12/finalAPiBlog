/**
 * 
 */
package com.fidelity.finalAPI.configurations;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.fidelity.finalAPI.services.DataUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig { 
		
	@Autowired
	private DataUserDetailsService userDetailService;
	
	//@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	
	@Bean
	DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider =new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailService);
		authProvider.setPasswordEncoder(bCryptPasswordEncoder);
		
		return authProvider;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authenticationProvider(authProvider());
		
		http.authorizeRequests().antMatchers("/login").permitAll()
			.antMatchers("/registration/**").permitAll()
			.antMatchers("/tag/**").hasAuthority("admin")
			.antMatchers("/category/**").hasAnyAuthority("admin")
			.antMatchers("/post/**").hasAnyAuthority("admin", "common")
			.anyRequest().authenticated().and().logout().permitAll();
		
		http.headers().frameOptions().sameOrigin();
		
		return http.build();
	}
	
	@Bean
	public WebSecurityCustomizer customizer() {
		return (web) -> web.ignoring().antMatchers(
				"/images/**",
				"/js/**",
				"/webjars/**"
				);
	}
}
