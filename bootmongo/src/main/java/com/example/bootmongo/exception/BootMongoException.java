package com.example.bootmongo.exception;

public class BootMongoException extends Exception{

	int code;
	
	String message;

	public BootMongoException(int code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public BootMongoException() {
		
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "MongoBootException [code=" + code + ", message=" + message + "]";
	}
	
	
	
}
