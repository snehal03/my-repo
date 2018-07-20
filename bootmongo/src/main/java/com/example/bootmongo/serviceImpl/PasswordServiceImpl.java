package com.example.bootmongo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.bootmongo.constant.HttpStatusCodes;
import com.example.bootmongo.exception.BootMongoException;
import com.example.bootmongo.model.User;
import com.example.bootmongo.repo.UserRepo;
import com.example.bootmongo.requestModel.ResetPasswordRequest;
import com.example.bootmongo.security.JwtUserDetailsServiceImpl;
import com.example.bootmongo.security.PasswordService;

@Component
public class PasswordServiceImpl implements PasswordService{

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Autowired
	JwtUserDetailsServiceImpl jwtUserDetailsServiceImpl;
	
	@Override
	public boolean forgotPassword(ResetPasswordRequest resetPasswordRequest , String userId) throws BootMongoException {
	
		if(resetPasswordRequest.getNewPassword().compareTo(resetPasswordRequest.getCPassword())!=0){
			return false;
		}else{
			boolean passValid = checkExistingPassword(resetPasswordRequest,userId);
			if(!passValid){
				throw new BootMongoException(201 ,"Existing Password does not match");
			}else{
				
				Boolean passchange = jwtUserDetailsServiceImpl.changePassword(userId, resetPasswordRequest.getNewPassword());
				if(passchange)
					return true;
				else 
					throw new BootMongoException(201 ,"Failed to Change Password");	
			}
		}
	}
	
	public boolean checkExistingPassword(ResetPasswordRequest resetPasswordRequest, String userId) {
		User user = userRepo.findOne(userId);
		return bCryptPasswordEncoder.matches(resetPasswordRequest.getPassword(), user.getPassword());

	}

}


