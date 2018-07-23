package com.example.bootmongo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.bootmongo.constant.TokenTypes;
import com.example.bootmongo.exception.BootMongoException;
import com.example.bootmongo.model.User;
import com.example.bootmongo.model.UserToken;
import com.example.bootmongo.repo.UserRepo;
import com.example.bootmongo.repo.UserTokenRepo;
import com.example.bootmongo.requestModel.LoginRequest;
import com.example.bootmongo.security.JwtTokenUtil;
import com.example.bootmongo.security.JwtUserDetailsServiceImpl;
import com.example.bootmongo.service.LoginService;
import com.example.bootmongo.utility.BootMongoUtility;
import com.example.bootmongo.utility.ConvertModelToResponse;

@Component
public class LoginServiceImpl implements LoginService {


	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private BootMongoUtility bootMongoUtil;
		
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	UserTokenRepo loginTokenRepo;
	
	@Autowired
	ConvertModelToResponse convertModelToResponse;
	
	@Override
	public Object getAuthenticatedUser(LoginRequest loginRequest) throws UsernameNotFoundException ,BootMongoException  {
		if (!bootMongoUtil.isValidEmailAddress(loginRequest.getEmail())) {
			throw new BootMongoException(201, "Invalid email");
		} 
		else {
			final Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail().toLowerCase(),
							loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			final User user = userDetailsService.getUserByEmail(loginRequest.getEmail().toLowerCase());
			final String token = jwtTokenUtil.generateToken(user);

			UserToken userLoginToken = new UserToken();
			userLoginToken.setToken(token);
			userLoginToken.setEmail(user.getEmail());
			userLoginToken.setTokenType(new TokenTypes().LOGIN_TOKEN);
			loginTokenRepo.save(userLoginToken);
			return convertModelToResponse.getLoginResponse(user, token);
		}
		
	}

}
