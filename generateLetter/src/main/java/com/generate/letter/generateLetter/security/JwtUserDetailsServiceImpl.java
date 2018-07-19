package com.generate.letter.generateLetter.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.generate.letter.generateLetter.model.LoginModelDTO;


@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {


	@Value("#{'${usernames}'.split(',')}")
	private List<String> usernames;

	@Value("#{'${passwords}'.split(',')}")
	private List<String> passwords;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		String pass = "";
		boolean userFound = false;
		for(int i =0;i<usernames.size();i++){
			if(username.equalsIgnoreCase(usernames.get(i))){
				pass = passwords.get(i);
				userFound = true;
			}
		}
		if(!userFound){
			throw new UsernameNotFoundException(String.format("No user exists with username : %s", username));
		}else{
			LoginModelDTO user = new LoginModelDTO();//userRepo.findUserByEmail(username);
			user.setUsername(username);
			user.setPassword(pass);
	
		   return JwtUserFactory.create(user);
		}
	}

/*
	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}*/

	
}
