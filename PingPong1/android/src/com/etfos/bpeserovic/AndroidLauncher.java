package com.etfos.bpeserovic;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.etfos.bpeserovic.PingPongMultiplayer;

public class AndroidLauncher extends AndroidApplication {

	/*public static AndroidLauncher instance;
	public static AndroidLauncher getInstance() {
		return instance;
	}
	public static PhoneProperties mPhoneProperties;*/


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		//instance = this;
		super.onCreate(savedInstanceState);
		//mPhoneProperties = new PhoneProperties();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useGyroscope = false;
		config.useAccelerometer = true;
		config.useCompass = false;
		initialize(new PingPongMultiplayer(), config);


		//mPhoneProperties.setX(getResources().getDisplayMetrics().widthPixels);
		//mPhoneProperties.setY(getResources().getDisplayMetrics().heightPixels);

		System.out.println("boris size = " + getResources().getDisplayMetrics().heightPixels
				+ " x " + getResources().getDisplayMetrics().widthPixels);
	}
}
