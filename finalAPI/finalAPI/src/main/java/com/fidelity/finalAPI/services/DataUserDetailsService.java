/**
 * 
 */
package com.fidelity.finalAPI.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fidelitytechnologies.training.blogapp.configuration.JwtUser;
import com.fidelitytechnologies.training.blogapp.model.Role;
import com.fidelitytechnologies.training.blogapp.model.User;

@Service
public class DataUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService user_service;
	
	/**
	 * 
	 */
	public DataUserDetailsService() {
		// TODO Auto-generated constructor stub
	}
	
	private List<GrantedAuthority> getUserAuthority(List<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<>();
		
		for (Role role : userRoles) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}
		
		return new ArrayList<>(roles);
	}	
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = user_service.getUserData(username);
		List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		
		//TODO Sample of role (implement on DB!!)
		// ROLE_ADMIN
		// ROLE_COMMON
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return new JwtUser(user.getUsername(), user.getPassword(), authorities); //buildUserForAuthentication(user, authorities);
	}

}
