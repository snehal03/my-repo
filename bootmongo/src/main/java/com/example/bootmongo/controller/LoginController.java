package com.example.bootmongo.controller;

import java.util.logging.Level;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.bootmongo.constant.HttpStatusCodes;
import com.example.bootmongo.exception.BootMongoException;
import com.example.bootmongo.requestModel.LoginRequest;
import com.example.bootmongo.service.LoginService;
import com.example.bootmongo.utility.ApplicationUtility;
import com.example.bootmongo.utility.BootMongoUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.java.Log;

@RestController
@Log
@Api
public class LoginController {

	@Autowired
	BootMongoUtility bootMongoUtility;

	@Autowired
	LoginService loginService;

	@Autowired
	ApplicationUtility applicationUtility;

	@RequestMapping(value = "${api.route.login}", method = RequestMethod.POST)
	@ApiOperation(value = "login", notes = "Login with proper email and password")
	public ResponseEntity<?> login(
			@ApiParam("email and password of user") @Valid @RequestBody LoginRequest loginRequest,
			BindingResult result) {

		if (result.hasErrors()) {
			log.log(Level.SEVERE, "validation error, email password not available");
			return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
					result.getAllErrors().get(0).getDefaultMessage(), null));
		} else {
			try {

				if (loginService.getAuthenticatedUser(loginRequest) != null) {
					return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.OK,
							applicationUtility.getMessage("login.success"), loginService.getAuthenticatedUser(loginRequest)));
				} else {
					return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
							applicationUtility.getMessage("login.invalidpassword"), null));
				}

			} catch (BadCredentialsException | UsernameNotFoundException | BootMongoException e) {

				return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
						applicationUtility.getMessage("login.invalidpassword"), null));
			}
		}

	}

}
