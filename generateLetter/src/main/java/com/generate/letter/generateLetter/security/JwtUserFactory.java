package com.generate.letter.generateLetter.security;

import org.springframework.stereotype.Component;

import com.generate.letter.generateLetter.model.LoginModelDTO;





@Component
public class JwtUserFactory {

	public static JwtUser create(LoginModelDTO user) {
		return new JwtUser( user.getUsername(),user.getPassword()); 
				
	}

	
}
