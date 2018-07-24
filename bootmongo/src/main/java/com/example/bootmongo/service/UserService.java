package com.example.bootmongo.service;

import org.springframework.stereotype.Service;

import com.example.bootmongo.model.User;
import com.example.bootmongo.requestModel.UpdateUserRquest;
import com.example.bootmongo.requestModel.UserRequest;

@Service
public interface UserService {

	public String addUser(UserRequest userModel);
	
	public String updateUser(UpdateUserRquest userModel, User user);

	public String deleteUser(User user);
}
