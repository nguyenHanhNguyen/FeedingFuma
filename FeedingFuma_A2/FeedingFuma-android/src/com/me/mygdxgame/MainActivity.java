package com.me.mygdxgame;

import java.util.Arrays;
import java.util.List;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {

//	GoogleAccountCredential credential;
	private static final String PREF_ACCOUNT_NAME = "sept.ntln.3";
	static final List<String> SCOPES = Arrays.asList(
			"https://www.googleapis.com/auth/userinfo.profile",
			"https://www.googleapis.com/auth/userinfo.email",
			"https://www.googleapis.com/auth/datastore");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ntn
		/*credential = GoogleAccountCredential.usingOAuth2(this, SCOPES);
		SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
		credential.setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME,
				null));
		*///FumaDatastore gds = new FumaDatastore(credential);
		// ntn-done
		
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = true;

		initialize(new FeedingFuma(), cfg);

	}
}