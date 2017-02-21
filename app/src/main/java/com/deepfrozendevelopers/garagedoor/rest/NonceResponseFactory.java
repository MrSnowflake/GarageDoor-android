package com.deepfrozendevelopers.garagedoor.rest;

import android.util.Base64;

import com.google.common.base.Strings;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by maarten.krijn on 2017/02/21.
 */

public class NonceResponseFactory {
	private static String sha1(String s, String keyString) throws
			UnsupportedEncodingException, NoSuchAlgorithmException,
			InvalidKeyException {

		SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(key);

		byte[] bytes = mac.doFinal(s.getBytes("UTF-8"));

		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}
	public static NonceResponse createResponse(String password, String nonce) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
		if (Strings.isNullOrEmpty(password) || Strings.isNullOrEmpty(nonce))
			throw new IllegalStateException("password and nonce can't be empty");

		String stringToEncrypt = password + nonce;

		String result = sha1(stringToEncrypt, "AndroidBloopady34590");

		return new NonceResponse(result);
	}
}
