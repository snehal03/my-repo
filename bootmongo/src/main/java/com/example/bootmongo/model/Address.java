package com.example.bootmongo.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(makeFinal=false, level=AccessLevel.PRIVATE)
public class Address {

	String route;
	
	String landmark;
	
	String city;
	
	String state;
	
	String zip;
	
	String country;
	
}
