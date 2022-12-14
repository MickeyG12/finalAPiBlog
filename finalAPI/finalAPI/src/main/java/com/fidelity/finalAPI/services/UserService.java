/**
 * 
 */
package com.fidelity.finalAPI.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fidelity.finalAPI.model.dto.UserDto;



@Service
public class UserService {

	@Autowired
	private UserRepository user_repository;
	
	//@Autowired
	//private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
	 * 
	 */
	public UserService() {
		// TODO Auto-generated constructor stub
	}	
	
	public UserDto createUser(UserDto newUser) {
		ModelMapper mapper = new ModelMapper();
		User user = mapper.map(newUser, User.class);
		
		// Generate encrypt user password
		//user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		this.user_repository.save(user);
		
		UserDto resul = mapper.map(user, UserDto.class);
		return resul;
	}
	
	public UserDto getUserProfile(Long id) {
		Optional<User> userFound = user_repository.findById(id);
		
		if (userFound.isEmpty()) {			
			return null;
		} else {
			ModelMapper mapper = new ModelMapper();
			UserDto userDTO = mapper.map(userFound.get(), UserDto.class);
			
			return userDTO;
		}
	}
	
	public User getUserData(String username) {
		Optional<User> userFound = user_repository.getByUsername(username);
		
		if (!userFound.isPresent()) {
			return null;
		} else {
			return userFound.get();
		}
		
	}

}
