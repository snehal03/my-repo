package com.example.bootmongo.requestModel;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(makeFinal= false, level=AccessLevel.PRIVATE)
public class LoginRequest {

	@NotNull(message = "Email can not be null")
	String email;
	
	@NotNull(message = "Password can not be null")
	String Password;
}
