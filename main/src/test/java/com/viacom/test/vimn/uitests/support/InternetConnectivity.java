package com.viacom.test.vimn.uitests.support;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.viacom.test.core.util.Logger;

public class InternetConnectivity {

	public static boolean isInternetAvailable() {
		try {
			final URL url = new URL("http://www.google.com");
			final URLConnection conn = url.openConnection();
			conn.connect();
			return true;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			return false;
		}
	}

	public static void waitForInternetAvailable(Integer numOfSeconds) {
		Integer numOfWaits = 0;
		while (!isInternetAvailable() && numOfWaits < numOfSeconds) {
			try {
				Thread.sleep(1000);
				Logger.logMessage("Pause for '1000' milliseconds.");
				numOfWaits++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}