package com.example.bootmongo.utility;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Component;

import com.example.bootmongo.constant.HttpStatusCodes;
import com.example.bootmongo.responseModel.ResponseEntityDTO;

import lombok.extern.java.Log;

@Component
@Log
public class BootMongoUtility {

	public ResponseEntityDTO createResponseEntityDTO(HttpStatusCodes httpStatusCodes, String message, Object body) {
		return ResponseEntityDTO.response().withResponseCode(httpStatusCodes).withResponseMessage(message)
				.withResponseBody(body).build();
	}

	public boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();

		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}
}
