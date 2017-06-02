package com.pointwest.googletoken.validator.response;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

public class GoogleToken {

	private GoogleIdToken token;

	public GoogleToken(GoogleIdToken token) {
		this.token = token;
	}

	public GoogleIdToken getToken() {
		return token;
	}

	public void setToken(GoogleIdToken token) {
		this.token = token;
	}

}
