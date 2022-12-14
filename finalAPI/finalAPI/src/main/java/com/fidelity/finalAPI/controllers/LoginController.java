/**
 * 
 */
package com.fidelity.finalAPI.controllers;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.aspectj.apache.bcel.classfile.Module.Uses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fidelitytechnologies.training.blogapp.configuration.JwtTokenUtils;
import com.fidelitytechnologies.training.blogapp.model.User;
import com.fidelitytechnologies.training.blogapp.model.dto.UserDto;
import com.fidelitytechnologies.training.blogapp.services.DataUserDetailsService;
import com.fidelitytechnologies.training.blogapp.services.UserService;

@RestController
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	public UserService user_service;
	
	@Autowired
	private JwtTokenUtils tokenUtils;
	
	@Autowired
	private DataUserDetailsService jwtUserService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	/**
	 * 
	 */
	public LoginController() {
		// TODO Auto-generated constructor stub
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam("username") String username,
									@RequestParam("password") String password) {
		HashMap<String, Object> responseMap = new HashMap<>();
		
		try {
			Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			if (auth.isAuthenticated()) {
				UserDetails userDetails = jwtUserService.loadUserByUsername(username);
				String token = tokenUtils.generateToken(userDetails);
				responseMap.put("error", false);
				responseMap.put("mensaje", "Login exitoso");
				responseMap.put("token", token);
				return ResponseEntity.ok(responseMap);
			} else {
				responseMap.put("error", true);
				responseMap.put("mensaje", "Usuario o contraseña incorrectos");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
			}
		} catch (BadCredentialsException exc) {
			responseMap.put("error", true);
			responseMap.put("mensaje", "Credenciales invalidas");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
		} catch (DisabledException exc) {
			exc.printStackTrace();
			responseMap.put("error", true);
			responseMap.put("mensaje", "Usuario no habilitado");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
		} catch (Exception exc) {
			exc.printStackTrace();
			responseMap.put("error", true);
			responseMap.put("mensaje", "Error inesperado en la aplicacion");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
		}
	}

	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestParam("user") String user,
			@RequestParam("email") String email,
			@RequestParam("password") String password) throws UnsupportedEncodingException {
		String passwordEnc = new BCryptPasswordEncoder().encode(password);
		UserDto tmp_user = new UserDto();
		tmp_user.setEmail(email);
		tmp_user.setUsername(user);
		tmp_user.setPassword(password);
		
		HashMap<String, Object> responseMap = new HashMap<>();
		UserDetails userDetails = jwtUserService.loadUserByUsername(user);
		String token = tokenUtils.generateToken(userDetails);
		user_service.createUser(tmp_user);
		
		responseMap.put("error", false);
		responseMap.put("username", user);
		responseMap.put("mensaje", "Usuario registrado correctamente");
		responseMap.put("token", token);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
	}
	
	@PostMapping("/registration/user/create")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
		user = user_service.createUser(user);
		
		return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);
	}
	
	

}
