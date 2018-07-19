package com.generate.letter.generateLetter.utility;

public class ResponseEntityDTO {

	int response_code;
	String response_message;
	Object response_body;

	
	public ResponseEntityDTO(int response_code, String response_message, Object response_body) {
		super();
		this.response_code = response_code;
		this.response_message = response_message;
		this.response_body = response_body;
	}
	public ResponseEntityDTO(){
		
	}
	public int getResponse_code() {
		return response_code;
	}

	public void setResponse_code(int response_code) {
		this.response_code = response_code;
	}

	public String getResponse_message() {
		return response_message;
	}

	public void setResponse_message(String response_message) {
		this.response_message = response_message;
	}

	public Object getResponse_body() {
		return response_body;
	}

	public void setResponse_body(Object response_body) {
		this.response_body = response_body;
	}

	@Override
	public String toString() {
		return "ResponseEntityDTO [response_code=" + response_code + ", response_message=" + response_message
				+ ", response_body=" + response_body + "]";
	}

	

	
}
