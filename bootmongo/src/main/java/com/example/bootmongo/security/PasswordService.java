package com.example.bootmongo.security;

import org.springframework.stereotype.Service;

import com.example.bootmongo.exception.BootMongoException;
import com.example.bootmongo.requestModel.ResetPasswordRequest;

@Service
public interface PasswordService {

	public boolean forgotPassword(ResetPasswordRequest resetPasswordRequest, String userId) throws BootMongoException;
}
