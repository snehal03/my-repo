package com.generate.letter.generateLetter.security;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generate.letter.generateLetter.utility.ResponseEntityDTO;

@Component
public class JwtAuthEntry implements AuthenticationEntryPoint, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public void commence(HttpServletRequest arg0, HttpServletResponse arg1, AuthenticationException arg2)
			throws IOException, ServletException {
		ResponseEntityDTO res = new ResponseEntityDTO();
		res.setResponse_code(201);
		res.setResponse_message("Unauthorzied Access to API");
		arg1.getWriter().print(new ObjectMapper().writeValueAsString(ResponseEntity.ok(res)));
		arg1.setContentType("application/json");
		arg1.setCharacterEncoding("UTF-8");
		arg1.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

	}

}
