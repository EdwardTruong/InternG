package com.example.cache_example_with_ehcache.infrastructure.jwt;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.example.cache_example_with_ehcache.appilication.constant.ContantUnit.MESSENGER_ERROR;
import com.example.cache_example_with_ehcache.appilication.dto.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/*
 * Any URLs have error, this class is calling.
 * I write 2 method : 
 * 	1. form https://stackoverflow.com/questions/63448838/convert-string-to-localdatetime-java-8
 * 	2. create HttpResponse use show field 
 */
@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		log.error("Unauthorized error: {}", authException.getMessage());

		response.setContentType(APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		HttpResponse httpResponse = HttpResponse.builder()
				.timeStamp(new Date())
				.httpStatusCode(HttpStatus.UNAUTHORIZED.value())
				.httpStatus(HttpStatus.UNAUTHORIZED)
				.message(MESSENGER_ERROR.SECURITY_ERROR)
				.build();

		OutputStream outputStream = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();

		mapper.writeValue(outputStream, httpResponse);
		outputStream.flush();
	}
}
