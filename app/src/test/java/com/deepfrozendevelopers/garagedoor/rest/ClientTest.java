package com.deepfrozendevelopers.garagedoor.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Created by maarten.krijn on 2017/02/22.
 */
public class ClientTest {
	private static Client client;

	@BeforeClass
	public static void before() {
		client = ClientFactory.getClient();
	}

	@Test
	public void getNonce() throws Exception {
		Call<Status> nonceCall = client.getNonce();
		Response<Status> response = nonceCall.execute();

		assertNotNull(response.body().nonce);
	}

	@Test
	public void tryOpenSuccess() throws Exception {
		Call<Status> nonceCall = client.getNonce();
		Response<Status> response = nonceCall.execute();

		assertNotNull(response.body().nonce);

		String hash = null;
		try {
			hash = NonceResponseFactory.sha1(response.body().nonce);
		} catch (Exception e) {
			fail();
		}

		response = client.open(hash).execute();
		Status status = response.body();
		assertNotNull(status);
		assertEquals("done", status.status);
	}
	
	@Test
	public void tryOpenFail() throws IOException, JSONException {
		Response<Status> response = client.open("brokenhash").execute();
		assertFalse(response.isSuccessful());

		ObjectMapper mapper = new ObjectMapper();
		Status status = mapper.readValue(response.errorBody().string(), Status.class);

		assertNotNull(status);
		assertEquals("error", status.status);
		assertFalse(Strings.isNullOrEmpty(status.message));
	}
}