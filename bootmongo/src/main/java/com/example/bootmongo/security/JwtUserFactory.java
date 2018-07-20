package com.example.bootmongo.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.bootmongo.model.Role;
import com.example.bootmongo.model.User;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JwtUserFactory {

	public JwtUser create(User user) {
		return new JwtUser(user.get_id(), user.getEmail(), user.getFirstname(), user.getPassword(),
				 mapToGrantedAuthorities(user.getRoles())); 
				
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(ArrayList<Role> authorities) {
		return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName()))
				.collect(Collectors.toList());
	}
}
