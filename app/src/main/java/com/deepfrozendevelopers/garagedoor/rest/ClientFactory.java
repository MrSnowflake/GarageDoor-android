package com.deepfrozendevelopers.garagedoor.rest;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by maarten.krijn on 2017/02/22.
 */

public class ClientFactory {
	public static Client getClient() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://192.168.0.136/")
				.addConverterFactory(JacksonConverterFactory.create())
				.build();

		Client service = retrofit.create(Client.class);

		return service;
	}
}
