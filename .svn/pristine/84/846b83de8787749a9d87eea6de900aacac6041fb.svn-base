package com.pointwest.googletoken.validator.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.pointwest.googletoken.validator.response.Error;
import com.pointwest.googletoken.validator.response.ErrorCode;
import com.pointwest.googletoken.validator.response.GoogleToken;

@RestController
public class TokenSignInController {

	@Value("${client_id}")
	private String applicationClientId = "";

	private GoogleIdTokenVerifier googleTokenVerifier;
	
	private static final Logger log = LoggerFactory.getLogger(TokenSignInController.class);
	
	@PostConstruct
	public void init() {
		log.info("Initializing Google Token Verifier with client: " + applicationClientId);
		googleTokenVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
				.setAudience(Collections.singletonList(applicationClientId)).build();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/tokensignin")
	public ResponseEntity<Object> validateToken(@RequestParam("idTokenString") String idTokenString) throws GeneralSecurityException, IOException {
		
		GoogleIdToken token = null;
		try{
			token = googleTokenVerifier.verify(idTokenString);
		} catch (GeneralSecurityException | IOException e) {
			return new ResponseEntity<>(new Error(ErrorCode.SERVER_ERROR,
					e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (token == null) {
			log.info("Invalid Google token: " + token);
			return new ResponseEntity<>(new Error(ErrorCode.INVALID_TOKEN,
					"Invalid Google Token"), HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(new GoogleToken(token), HttpStatus.OK);
		}
	}
}
