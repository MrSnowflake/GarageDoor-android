package com.deepfrozendevelopers.garagedoor.rest;

/**
 * Created by maarten.krijn on 2017/02/21.
 */

public class NonceResponse {
	String response;

	NonceResponse(String response) {
		this.response = response;
	}

	public String getResponse() {
		return response;
	}
}
