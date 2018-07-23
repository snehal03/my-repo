package com.example.bootmongo.requestModel;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(makeFinal=false , level= AccessLevel.PRIVATE)
public class ChangePasswordRequest {

	@NotNull(message = "Password can not be null")
	String password;
	
	@NotNull(message = "New passsword can not be null")
	String newPassword;
	
	@NotNull(message = "Confirm passsword can not be null")
	String cPassword;
	
	
}
