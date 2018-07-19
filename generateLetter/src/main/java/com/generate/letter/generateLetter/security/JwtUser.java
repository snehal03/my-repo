package com.generate.letter.generateLetter.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class JwtUser implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final String username;
	private final String password;
	

	public JwtUser(String username, String password) {
		
		this.username = username;
		this.password = password;
		
	}

	

	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	
	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	
	@Override
	public boolean isEnabled() {
		return true;
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String toString() {
		return "JwtUser [username=" + username + ", password=" + password + "]";
	}



}
