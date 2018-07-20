package com.example.bootmongo.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bootmongo.exception.BootMongoException;
import com.example.bootmongo.requestModel.LoginRequest;

@Service
public interface LoginService {

	public Object getAuthenticatedUser(LoginRequest loginRequest) throws UsernameNotFoundException ,BootMongoException;
}
