package com.example.bootmongo.serviceImpl;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.bootmongo.constant.TokenTypes;
import com.example.bootmongo.exception.BootMongoException;
import com.example.bootmongo.model.User;
import com.example.bootmongo.model.UserToken;
import com.example.bootmongo.repo.UserRepo;
import com.example.bootmongo.repo.UserTokenRepo;
import com.example.bootmongo.requestModel.ChangePasswordRequest;
import com.example.bootmongo.requestModel.ResetPasswordRequest;
import com.example.bootmongo.security.JwtUserDetailsServiceImpl;
import com.example.bootmongo.security.PasswordService;
import com.example.bootmongo.utility.EmailUtility;

@Component
public class PasswordServiceImpl implements PasswordService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	JwtUserDetailsServiceImpl jwtUserDetailsServiceImpl;

	@Autowired
	EmailUtility emailUtility;
	
	@Autowired
	UserTokenRepo userTokenRepo;
	
	@Autowired
	MongoOperations mongoOperations;

	
	@Override
	public boolean changePassword(ChangePasswordRequest resetPasswordRequest, String userId) throws BootMongoException {

		if (resetPasswordRequest.getNewPassword().compareTo(resetPasswordRequest.getCPassword()) != 0) {
			return false;
		} else {
			boolean passValid = checkExistingPassword(resetPasswordRequest, userId);
			if (!passValid) {
				throw new BootMongoException(201, "Existing Password does not match");
			} else {

				Boolean passchange = jwtUserDetailsServiceImpl.changePassword(userId,
						resetPasswordRequest.getNewPassword());
				if (passchange)
					return true;
				else
					throw new BootMongoException(201, "Failed to Change Password");
			}
		}
	}

	public boolean checkExistingPassword(ChangePasswordRequest resetPasswordRequest, String userId) {
		User user = userRepo.findOne(userId);
		return bCryptPasswordEncoder.matches(resetPasswordRequest.getPassword(), user.getPassword());

	}

	@Override
	public boolean forgotPassword(String email, User user) {
		String token = generateTokenForUser(email);
		UserToken userToken = new UserToken();
		userToken.setEmail(email);
		userToken.setTokenType(new TokenTypes().FORGOT_TOKEN);
		userToken.setToken(token);
		userTokenRepo.save(userToken);
		try {
			emailUtility.sendEmail(email, token);
			return true;
		} catch (BootMongoException e) {
			return false;
		}
		
		
	}

	public String generateTokenForUser(String email) {
		Random rand = new Random();
		String token = ""+email.charAt(0)+email.charAt(1);
		token = token + new Date().getTime();
		token = token + rand.nextInt(1000);
		return token;

	}



	@Override
	public void savePassword(ResetPasswordRequest resetPasswordRequest, User user) {
		user.setPassword(bCryptPasswordEncoder.encode(resetPasswordRequest.getCPassword()));
		//userTokenRepo.removeToken(user.getEmail());
		UserToken token = userTokenRepo.getForgotPasswordToken(user.getEmail());
		mongoOperations.remove(token);

	}
}
