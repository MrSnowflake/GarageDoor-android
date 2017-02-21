package com.deepfrozendevelopers.garagedoor;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.deepfrozendevelopers.garagedoor.rest.Nonce;
import com.deepfrozendevelopers.garagedoor.rest.NonceResponse;
import com.deepfrozendevelopers.garagedoor.rest.NonceResponseFactory;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Unlock extends AppCompatActivity {
	public static final String TAG = Unlock.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unlock);
	}

	public void openDoor() {
		Log.d(TAG, "Open door");

		(new RequestNonceTask()).execute();
	}

	private class RequestNonceTask extends AsyncTask<Void, Void, Nonce> {
		@Override
		protected Nonce doInBackground(Void... params) {
			try {
				final String url = "http://rest-service.guides.spring.io/greeting";
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
				Nonce nonce = restTemplate.getForObject(url, Nonce.class);
				return nonce;
			} catch (Exception e) {
				Log.e("MainActivity", e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Nonce nonce) {
			try {
				NonceResponse nonceResponse = NonceResponseFactory.createResponse("SomePassword", nonce.nonce);
				(new OpenDoorTask(() -> {
					Toast.makeText(Unlock.this, "Opening door", Toast.LENGTH_LONG).show();
				})).execute(nonceResponse);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		}
	}

	private class OpenDoorTask extends AsyncTask<NonceResponse, Void, Void> {
		private Runnable callback;

		public OpenDoorTask(Runnable callback) {
			this.callback = callback;
		}

		@Override
		protected Void doInBackground(NonceResponse... params) {
			try {
				final String url = "http://rest-service.guides.spring.io/greeting";
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			} catch (Exception e) {
				Log.e("MainActivity", e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void none) {
			Unlock.this.runOnUiThread(callback);
		}
	}
}
