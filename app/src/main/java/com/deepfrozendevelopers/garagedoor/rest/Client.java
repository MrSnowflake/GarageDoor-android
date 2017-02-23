package com.deepfrozendevelopers.garagedoor.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by maarten.krijn on 2017/02/22.
 */

public interface Client {
	@Headers("Accept: application/json")
	@GET("nonce")
	Call<Status> getNonce();

	@Headers("Accept: application/json")
	@GET("open")
	Call<Status> open(@Query("hash") String hash);
}
