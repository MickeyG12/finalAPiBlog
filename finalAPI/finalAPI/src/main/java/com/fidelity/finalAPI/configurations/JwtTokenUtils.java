package com.fidelity.finalAPI.configurations;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtils implements Serializable {

	// Llave privada de nuestra API : Se debe obtener al decodificar el token
	// para verificar la autenticidad del emisor
	private final String secret = "fidelitymkt";
	
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret.getBytes("UTH-8")).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			System.err.println("Error validating token: " + e.getMessage());
			return false;
		}
	}
	
	public String generateToken(UserDetails userDetails) throws UnsupportedEncodingException {
		HashMap<String, Object> claims = new HashMap<>();
		Long minSample =  604800L;
		Date expirationDate = new Date(System.currentTimeMillis() + (minSample*1000));
		return Jwts.builder().setClaims(claims)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, secret.getBytes("UTF-8"))
				.compact();
	}

}
