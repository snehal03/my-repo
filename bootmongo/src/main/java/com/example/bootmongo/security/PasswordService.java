package com.example.bootmongo.security;

import org.springframework.stereotype.Service;

import com.example.bootmongo.exception.BootMongoException;
import com.example.bootmongo.model.User;
import com.example.bootmongo.requestModel.ChangePasswordRequest;
import com.example.bootmongo.requestModel.ResetPasswordRequest;

@Service
public interface PasswordService {

	public boolean changePassword(ChangePasswordRequest resetPasswordRequest, String userId) throws BootMongoException;

	public boolean forgotPassword(String email, User user);

	public void savePassword(ResetPasswordRequest resetPasswordRequest, User user);
}
