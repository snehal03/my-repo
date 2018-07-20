package com.example.bootmongo.utility;

import org.springframework.stereotype.Component;

import com.example.bootmongo.model.User;
import com.example.bootmongo.responseModel.LoginResponseModel;

@Component
public class ConvertModelToResponse {

	public LoginResponseModel getLoginResponse(User user, String token){
		LoginResponseModel loginResponseModel = new LoginResponseModel();
		loginResponseModel.setEmail(user.getEmail());
		loginResponseModel.setId(user.get_id());
		loginResponseModel.setFirstname(user.getFirstname());
		loginResponseModel.setLastname(user.getLastname());
		loginResponseModel.setToken(token);
		loginResponseModel.setRoles(user.getRoles());
		return loginResponseModel;
	}
}
