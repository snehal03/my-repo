package com.example.bootmongo.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Document(collection="role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = true)
@FieldDefaults(level=AccessLevel.PRIVATE , makeFinal=false)
public class Role {
	
	String _id;
	
	String name;

}
