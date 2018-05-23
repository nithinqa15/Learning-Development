package com.viacom.test.vimn.common.filters;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.jetty.http.HttpStatus;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.uitests.support.AppDataFeed;

public class FilterUtils {
	
	public static JSONObject getJSONFeed(String url) {
		
		if (url.contains("http")) {
			return parseHttpURL(url);
		} else {
			return parseHttpsURL(url);
		}
	}
	
	private static JSONObject parseHttpURL(String url) {
		JSONObject object = null;
		
		try {
			URL jsonURL = new URL(url);
			HttpURLConnection httprequest = (HttpURLConnection) jsonURL.openConnection();
			httprequest.setRequestMethod("GET");
			httprequest.connect();
			InputStream input = httprequest.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			input.close();

			String responseBody = sb.toString();
			JSONParser parser = new JSONParser();
			object = (JSONObject) parser.parse(responseBody);
		} catch (Exception e) {
			Assert.fail("Failed to parse json response.", e);
		}
		
		return object;
	}
	
	private static JSONObject parseHttpsURL(String url) {
		JSONObject object = null;
		AppDataFeed.trustAllHosts();
		
		try {
			URL jsonURL = new URL(url);
			HttpsURLConnection httpsrequest = (HttpsURLConnection) jsonURL.openConnection();
			httpsrequest.setRequestMethod("GET");
			httpsrequest.connect();
			InputStream input = httpsrequest.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			input.close();

			String responseBody = sb.toString();
			JSONParser parser = new JSONParser();
			object = (JSONObject) parser.parse(responseBody);
		} catch (Exception e) {
			Assert.fail("Failed to parse json response.", e);
		}
		
		return object;
	}
	
	public static void checkStatusCode(String url, String screenName) {
		try {
			HttpClient httpclient = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			HttpResponse response = null;
			try {
				response = httpclient.execute(request);
			} catch (HttpHostConnectException httpE) {
				// connection refused
			}
			
			if (response == null || !(response.getStatusLine().getStatusCode() == HttpStatus.OK_200)) {
				Logger.logMessage("Broken URL : " + request.getURI().toString());
				Logger.logMessage(response.getStatusLine().getReasonPhrase());
				Assert.assertTrue(false);
			} else {
				Logger.logMessage(screenName + " :: "+ url + " : END POINT CHECKED : " + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object checkSearchResult(String url) {
		JSONObject statusObject = null;
		try {
			HttpClient httpclient = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			HttpResponse response = null;
			try {
				response = httpclient.execute(request);
			} catch (HttpHostConnectException httpE) {
				// connection refused
			}
			
			if (response == null || !(response.getStatusLine().getStatusCode() == HttpStatus.OK_200)) {
				Logger.logMessage("Broken URL : " + request.getURI().toString());
				Logger.logMessage(response.getStatusLine().getReasonPhrase());
			} else {
				statusObject = (JSONObject) getJSONFeed(url).get("status");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusObject;
	}
}
	
