package com.deepfrozendevelopers.garagedoor.rest;

import android.util.Base64;

import com.deepfrozendevelopers.garagedoor.Settings;
import com.google.common.base.Strings;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by maarten.krijn on 2017/02/21.
 */

public class NonceResponseFactory {
	private static String convertToHex(byte[] data) {
		StringBuilder buf = new StringBuilder();
		for (byte b : data) {
			int halfbyte = (b >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
				halfbyte = b & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	public static String sha1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		text = Settings.SHA_KEY + text + Settings.SHA_KEY;

		byte[] textBytes = text.getBytes("UTF-8");
		md.update(textBytes, 0, textBytes.length);
		byte[] sha1hash = md.digest();
		return convertToHex(sha1hash);
	}
}
