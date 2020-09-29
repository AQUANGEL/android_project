package com.vladlozkin.libgdk_protector;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		ActionResolverAndroid actionResolverAndroid = new ActionResolverAndroid(this);
		initialize(new Main(actionResolverAndroid), config);
	}
}
