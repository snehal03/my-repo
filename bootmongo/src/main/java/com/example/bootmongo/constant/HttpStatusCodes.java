package com.example.bootmongo.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public enum HttpStatusCodes {

	OK(200, "OK"),

	VALIDATION_ERROR(201, "Validation Error"),

	REGISTERED(202, "Registered SuccessFully"),

	ALREADY_EXISTS(204, "User Already exists"),

	FAILED(203, "Service Failed"),

	BAD_REQUEST(400, "Bad Request"),

	UNAUTHORIZED(401, "Unauthorized"),

	NOT_FOUND(404, "Request object not found"),

	INTERNAL_SERVER_ERROR(500, "Internal Server Error");

	int value;
	String reasonPhrase;
	
	public static HttpStatusCodes value(int code) {
		
		return HttpStatusCodes.value(code);
	}
	

}

