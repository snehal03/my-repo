package com.generate.letter.generateLetter.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.generate.letter.generateLetter.model.LoginModelDTO;
import com.generate.letter.generateLetter.security.JwtTokenUtil;
import com.generate.letter.generateLetter.utility.ApplicationUtility;
import com.generate.letter.generateLetter.utility.ResponseEntityDTO;

@RestController
@CrossOrigin("*")
public class LoginController {

	@Autowired
	private ApplicationUtility applicationUtility;

	/** The b crypt password encoder. */
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;


	@RequestMapping(value = "${api.route.login}", method = RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ResponseEntity<?> login(HttpServletRequest request, @Valid @RequestBody LoginModelDTO loginModel,
			BindingResult result) {
		System.out.println("login called");
		ResponseEntityDTO res = new ResponseEntityDTO();
		if (result.hasErrors()) {
			res.setResponse_code(201);
			res.setResponse_message(applicationUtility.getMessage("login.failed"));
			return ResponseEntity.ok(res);
		} else {
		
			try {
				String token  = getAuthenticatedUser(loginModel);
				// if (userFound) {
				res.setResponse_code(200);
				res.setResponse_message(applicationUtility.getMessage("login.success"));
				res.setResponse_body(token);
				return ResponseEntity.ok(res);

			} catch (Exception e) {
				res.setResponse_code(201);
				res.setResponse_message(applicationUtility.getMessage("login.failed"));
				return ResponseEntity.ok(res);
			}

		}

	}

	public String getAuthenticatedUser(LoginModelDTO loginModel) throws Exception {

		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginModel.getUsername().toLowerCase(),
						loginModel.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		
		final LoginModelDTO user = new LoginModelDTO();
		user.setUsername(loginModel.getUsername());
		user.setPassword(loginModel.getPassword());
		final String token = jwtTokenUtil.generateToken(user);

	
	/*	try {
			generateLetterUtility.storeTokenForUser(loginModel.getUsername(), token);
		
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		return token;

	}

	@RequestMapping(value = "encode/password", method = RequestMethod.GET)
	public String encodePassword(@RequestParam("password") String password) {
		if (password != "" && password != null) {
			String loginPassword = bCryptPasswordEncoder.encode(password);
			return loginPassword;
		} else {
			return password;
		}
	}
}
