package com.example.bootmongo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bootmongo.constant.HttpStatusCodes;
import com.example.bootmongo.exception.BootMongoException;
import com.example.bootmongo.model.User;
import com.example.bootmongo.repo.UserRepo;
import com.example.bootmongo.requestModel.ResetPasswordRequest;
import com.example.bootmongo.security.PasswordService;
import com.example.bootmongo.utility.ApplicationUtility;
import com.example.bootmongo.utility.BootMongoUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;

@RestController
@Api
@Log
public class PasswordController {

	@Autowired
	BootMongoUtility bootMongoUtility;

	@Autowired
	UserRepo userRepo;

	@Autowired
	ApplicationUtility applicationUtility;

	@Autowired
	PasswordService passwordService;

	@RequestMapping(value = "${api.route.resetPassword}", method=RequestMethod.POST)
	@ApiOperation(value = "Reset Password", notes = "Reset password of user")
	public ResponseEntity<?> resetPassword(@RequestParam(value = "userId") String userId,
			@Valid @RequestBody ResetPasswordRequest resetPasswordRequest, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
					result.getAllErrors().get(0).getDefaultMessage(), null));
		} else {
			User user = userRepo.findOne(userId);
			if (user != null) {
				boolean passwordChange;
				try {
					passwordChange = passwordService.forgotPassword(resetPasswordRequest ,userId);
					if (passwordChange) {
						return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.OK,
								applicationUtility.getMessage("reset.success"), null));
					} else {
						return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.BAD_REQUEST,
								applicationUtility.getMessage("reset.not.match"), null));
					}
				} catch (BootMongoException e) {
					return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND,
							applicationUtility.getMessage("pass.not.match"), null));
				}
				
			} else {
				return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND,
						applicationUtility.getMessage("user.not.exist"), null));
			}
		}
	}
}
