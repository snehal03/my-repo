package com.example.bootmongo.controller;

import java.util.logging.Level;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.junit.validator.PublicClassValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bootmongo.constant.HttpStatusCodes;
import com.example.bootmongo.exception.BootMongoException;
import com.example.bootmongo.model.User;
import com.example.bootmongo.model.UserToken;
import com.example.bootmongo.repo.UserRepo;
import com.example.bootmongo.repo.UserTokenRepo;
import com.example.bootmongo.requestModel.ChangePasswordRequest;
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

	@Autowired
	UserTokenRepo userTokenRepo;

	@RequestMapping(value = "${api.route.changePassword}", method = RequestMethod.POST)
	@ApiOperation(value = "Reset Password", notes = "Change user password ")
	public ResponseEntity<?> changePassword(@RequestParam(value = "userId") String userId,
			@Valid @RequestBody ChangePasswordRequest resetPasswordRequest, BindingResult result) {
		if (result.hasErrors()) {
			log.log(Level.SEVERE, "Change password with all values ");
			return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
					result.getAllErrors().get(0).getDefaultMessage(), null));
		} else {
			User user = userRepo.findOne(userId);
			if (user != null) {
				boolean passwordChange;
				try {
					passwordChange = passwordService.changePassword(resetPasswordRequest, userId);
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

	@RequestMapping(value = "${api.route.forgotPassword}", method = RequestMethod.GET)
	public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {
		User user = userRepo.findUserByEmail(email);
		if (user == null) {

			return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND,
					applicationUtility.getMessage("user.not.exist"), null));
		} else {

			boolean fPass = passwordService.forgotPassword(email, user);
			if (fPass) {
				return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.OK,
						applicationUtility.getMessage("forgot.password.sucess"), null));
			} else {
				return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.FAILED,
						applicationUtility.getMessage("forgot.password.failure"), null));
			}
		}
	}

	@RequestMapping(value = "${api.route.resetPassword}/{token}/{email:.+}", method = RequestMethod.POST)
	public ResponseEntity<?> resetPassword(@PathVariable("token") String token, @PathVariable("email") String email,
			@Valid @RequestBody ResetPasswordRequest resetPasswordRequest, BindingResult result) {
		if (result.hasErrors()) {
			log.log(Level.SEVERE, "Reset password with all values ");
			return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
					result.getAllErrors().get(0).getDefaultMessage(), null));
		} else {
			User user = userRepo.findUserByEmail(email);
			if (user != null) {
				UserToken dbToken = userTokenRepo.getForgotPasswordToken(email);
				if (dbToken == null) {

					return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND,
							applicationUtility.getMessage("reset.password.token.failure"), null));
				} else {
					if (dbToken.getToken().equals(token)) {
						if (resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getCPassword())) {
							passwordService.savePassword(resetPasswordRequest, user);
							return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.OK,
									applicationUtility.getMessage("reset.password.success"), null));
						} else {

							return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.FAILED,
									applicationUtility.getMessage("reset.not.match"), null));
						}
					} else {
						return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.FAILED,
								applicationUtility.getMessage("reset.password.token.failure"), null));
					}

				}
			} else {
				return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.NOT_FOUND,
						applicationUtility.getMessage("user.not.exist"), null));
			}

		}
	}
}
