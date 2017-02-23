package com.deepfrozendevelopers.garagedoor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.deepfrozendevelopers.garagedoor.rest.Client;
import com.deepfrozendevelopers.garagedoor.rest.ClientFactory;
import com.deepfrozendevelopers.garagedoor.rest.NonceResponseFactory;
import com.deepfrozendevelopers.garagedoor.rest.Status;
import com.fasterxml.jackson.databind.ObjectMapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Unlock extends AppCompatActivity {
	public static final String TAG = Unlock.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unlock);
	}

	public void openDoor(View button) {
		Log.d(TAG, "Open door");

		final Client client = ClientFactory.getClient();

		Call<Status> nonceCall = client.getNonce();

		nonceCall.enqueue(new Callback<Status>() {
			@Override
			public void onResponse(Call<Status> call, Response<Status> response) {
				if (response.code() != 200) {
					Toast.makeText(Unlock.this, "Error", Toast.LENGTH_LONG).show();
					Log.e(TAG, "Error");
					return;
				}

				String hash = null;
				try {
					hash = NonceResponseFactory.sha1(response.body().nonce);

					client.open(hash).enqueue(new Callback<Status>() {
						@Override
						public void onResponse(Call<Status> call, Response<Status> response) {
							if (response.code() == 200 && "done".equalsIgnoreCase(response.body().status))
								Toast.makeText(Unlock.this, "Opening", Toast.LENGTH_LONG).show();
							else {
								Toast.makeText(Unlock.this, "Error", Toast.LENGTH_LONG).show();
								Log.e(TAG, response.body().message);
							}
						}

						@Override
						public void onFailure(Call<Status> call, Throwable t) {
							Toast.makeText(Unlock.this, "Error", Toast.LENGTH_LONG).show();

							Log.e(TAG, t.toString());
						}
					});
					Status status = response.body();
				} catch (Exception e) {
					Log.e(TAG, e.toString());
					Toast.makeText(Unlock.this, "Error", Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFailure(Call<Status> call, Throwable t) {
				Log.e(TAG, t.toString());
				Toast.makeText(Unlock.this, "Error", Toast.LENGTH_LONG).show();
			}
		});
	}
}
