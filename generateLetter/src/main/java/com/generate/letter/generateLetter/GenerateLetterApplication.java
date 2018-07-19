package com.generate.letter.generateLetter;

import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.velocity.VelocityEngineFactory;


@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GenerateLetterApplication {

	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(GenerateLetterApplication.class, args);
		
	}
	
	
	@Bean
    public VelocityEngine getVelocityEngine() throws VelocityException, IOException{
        VelocityEngineFactory factory = new VelocityEngineFactory();
        Properties props = new Properties();
        props.put("resource.loader", "classpath");        
        props.put("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        factory.setVelocityProperties(props);
        
        VelocityContext context = new VelocityContext();
        context.put("numberTool", new NumberTool());
        
        VelocityEngine ve = factory.createVelocityEngine();
        ve.init();
        return ve;      
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}
