package com.deepfrozendevelopers.garagedoor.rest;

import com.deepfrozendevelopers.garagedoor.Settings;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

/**
 * Created by maarten.krijn on 2017/02/22.
 */
public class NonceResponseFactoryTest {
	@Test
	public void testHashGeneration() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
		String hash = NonceResponseFactory.sha1("1017369031");

		assertEquals("d52dcb5b12f9992ce892e545d93f4fb42a3aad51", hash);
	}
}