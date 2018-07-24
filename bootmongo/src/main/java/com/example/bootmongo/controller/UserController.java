package com.example.bootmongo.controller;

import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.bootmongo.constant.HttpStatusCodes;
import com.example.bootmongo.model.User;
import com.example.bootmongo.repo.UserRepo;
import com.example.bootmongo.requestModel.UpdateUserRquest;
import com.example.bootmongo.requestModel.UserRequest;
import com.example.bootmongo.security.JwtTokenUtil;
import com.example.bootmongo.service.UserService;
import com.example.bootmongo.utility.ApplicationUtility;
import com.example.bootmongo.utility.BootMongoUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;

/**
 * 
 * @author snehal
 *
 *User class contains services related to user
 */
@RestController
@Api
@Log
public class UserController {

	@Autowired
	BootMongoUtility bootMongoUtility;

	@Autowired
	ApplicationUtility applicationUtility;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepo;

	/**
	 * 
	 * @param userModel Add user model
	 * @param request Http request
	 * @return Response entity with message
	 */
	@RequestMapping(value = "${api.route.addUser}", method = RequestMethod.POST)
	@ApiOperation(value = "add user", notes = "Add user in application")
	public ResponseEntity<?> addUser(@Valid @RequestBody UserRequest userModel, BindingResult result,
			HttpServletRequest request) {
		if (result.hasErrors()) {
			log.log(Level.SEVERE, "add user validation error");
			return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
					result.getAllErrors().get(0).getDefaultMessage(), null));
		} else {
			if (bootMongoUtility.isValidEmailAddress(userModel.getEmail())) {

				if (userModel.getFirstname() == "" || userModel.getLastname() == "" || userModel.getPassword() == "") {
					return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
							applicationUtility.getMessage("user.invalid.value"), null));
				} else {
					String token = request.getHeader(tokenHeader);
					String email = jwtTokenUtil.getUsernameFromToken(token);
					if (email.isEmpty()) {
						log.log(Level.SEVERE, "invalid credentials");
						return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.UNAUTHORIZED,
								applicationUtility.getMessage("login.invalidcredentail"), null));
					} else {
						User user = userRepo.findUserByEmail(userModel.getEmail());
						if (user == null) {
							String response = userService.addUser(userModel);
							if (response.equalsIgnoreCase("ok")) {
								return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.OK,
										applicationUtility.getMessage("user.add.success"), null));
							} else if (response.equalsIgnoreCase("role error")) {
								return ResponseEntity
										.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.FAILED,
												applicationUtility.getMessage("user.role.failed"), null));
							} else if (response.equalsIgnoreCase("address error")) {
								return ResponseEntity
										.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.FAILED,
												applicationUtility.getMessage("user.address.failed"), null));
							} else {
								return ResponseEntity
										.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.FAILED,
												applicationUtility.getMessage("user.add.failed"), null));
							}
						} else {
							log.log(Level.SEVERE, "user already exist");
							return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.FAILED,
									applicationUtility.getMessage("user.already.exist"), null));
						}
					}

				}
			} else {
				log.log(Level.SEVERE, "invalid email");
				return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
						applicationUtility.getMessage("user.invalid.email"), null));
			}
		}
	}

	@RequestMapping(value = "${api.route.updateUser}", method = RequestMethod.POST)
	@ApiOperation(value = "update user", notes = "update user ")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRquest updateUserModel, BindingResult result,
			HttpServletRequest request) {
		if (result.hasErrors()) {
			log.log(Level.SEVERE, "add user validation error");
			return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
					result.getAllErrors().get(0).getDefaultMessage(), null));
		} else {
			String token = request.getHeader(tokenHeader);
			String email = jwtTokenUtil.getUsernameFromToken(token);
			if (email.isEmpty()) {
				log.log(Level.SEVERE, "invalid credentials");
				return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.UNAUTHORIZED,
						applicationUtility.getMessage("login.invalidcredentail"), null));
			} else {
				User user = userRepo.findOne(updateUserModel.get_id());
				if (user == null) {
					log.log(Level.SEVERE, "user does not exist");
					return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.FAILED,
							applicationUtility.getMessage("user.not.exist"), null));
				}else {
					String response = userService.updateUser(updateUserModel,user);
					if (response.equalsIgnoreCase("ok")) {
						return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.OK,
								applicationUtility.getMessage("user.update.success"), null));
					} else if (response.equalsIgnoreCase("role error")) {
						return ResponseEntity
								.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.FAILED,
										applicationUtility.getMessage("user.role.failed"), null));
					}else {
						return ResponseEntity
								.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.FAILED,
										applicationUtility.getMessage("user.update.failed"), null));
					}
				}
			}
		}
	}
	
	
	@RequestMapping(value="${api.route.deleteUser}/{userId}", method=RequestMethod.POST)
	@ApiOperation(value="Delete User", notes="Delete user")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") String userId , HttpServletRequest request){
		String token = request.getHeader(tokenHeader);
		String email = jwtTokenUtil.getUsernameFromToken(token);
		if (email.isEmpty()) {
			log.log(Level.SEVERE, "invalid credentials");
			return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.UNAUTHORIZED,
					applicationUtility.getMessage("login.invalidcredentail"), null));
		} else {
			User user = userRepo.findOne(userId);
			if (user == null) {
				log.log(Level.SEVERE, "user does not exist");
				return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.FAILED,
						applicationUtility.getMessage("user.not.exist"), null));
			}else{
				String response = userService.deleteUser(user);
				if (response.equalsIgnoreCase("ok")) {
					return ResponseEntity.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.OK,
							applicationUtility.getMessage("user.delete.success"), null));
				} else {
					return ResponseEntity
							.ok(bootMongoUtility.createResponseEntityDTO(HttpStatusCodes.FAILED,
									applicationUtility.getMessage("user.delete.failed"), null));
				}
			}
		}
	}
	
	
	
}
