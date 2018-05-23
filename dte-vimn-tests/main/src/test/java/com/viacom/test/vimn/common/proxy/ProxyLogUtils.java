package com.viacom.test.vimn.common.proxy;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.core.proxy.ProxyRESTManager;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.util.Config;

import de.sstoehr.harreader.model.HarEntry;
import de.sstoehr.harreader.model.HarHeader;
import de.sstoehr.harreader.model.HarRequest;
import de.sstoehr.harreader.model.HarResponse;

public class ProxyLogUtils {

	private static String getResponse(HarEntry entry) {
		HarResponse responseEntry = entry.getResponse();
		String response = responseEntry.getContent().getText();
		if (response != null) {
			response = removeCharacters(response);
		}
		return response;
	}

	private static String removeCharacters(String response) {
		String temp = response;

		if (response.contains("\n")) {
			temp = StringUtils.remove(temp, "\n");
		}
		if (response.contains("\t")) {
			temp = StringUtils.remove(temp, "\t");
		}
		if (response.contains("\r")) {
			temp = StringUtils.remove(temp, "\r");
		}

		temp = temp.trim();
		return temp;
	}

	public static String decodeUrl(String unsafeUrl) {
		try {
			if (unsafeUrl.contains("HEX40")) {
				return URLDecoder.decode(unsafeUrl.split("HEX40")[0], "utf-8");
			} else {
				return URLDecoder.decode(unsafeUrl, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static List<String> getResponses(String requestUrl) {
		List<String> responses = new ArrayList<String>();

		for (HarEntry entry : ProxyRESTManager.getLog().getLog().getEntries()) {
			HarRequest req = entry.getRequest();
			String url = decodeUrl(req.getUrl());
            if (url.matches(requestUrl) || url.contains(requestUrl)) {
                responses.add(getResponse(entry));
			}
		}
		return responses;
	}

	public static String getResponse(String requestUrl) {
		List<String> responses = getResponses(requestUrl);
		if (responses.size() > 0) {
			return responses.get(0);
		}
		throw new RuntimeException("Request url " + requestUrl + " not found");
	}
	
	public static List<String> getRequestURLs() {
		List<String> RequestURLs = new ArrayList<String>();

		for (HarEntry entry : ProxyRESTManager.getLog().getLog().getEntries()) {
			HarRequest req = entry.getRequest();
			String url = decodeUrl(req.getUrl());
			RequestURLs.add(url);
		}
			
		return RequestURLs;
	}
	
	public static boolean isRequestUrlFired(String requestUrl) {
		for (String url : getRequestURLs()) {
			if (url.matches(requestUrl) || url.equals(requestUrl)) {
				return true;
			}
		}
		return false;
	}
	
	public static String getRequestURL(String requestUrl) {
		String RequestedURL = null;
		for (String url : getRequestURLs()) {
			if (url.matches(requestUrl) || url.equals(requestUrl)) {
				RequestedURL = url ;
			}
		}
		return RequestedURL;
	}
	
	public static JSONObject parseResponse(String requestURL) {
		JSONObject Object = null;
		try {
			 String response = ProxyLogUtils.getResponse(requestURL);
		     JSONParser parser = new JSONParser();
		     Object = (JSONObject) parser.parse(response);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return Object;
	}
	
    public static List<HarEntry> getLogEntries(String requestPattern) {
        String req;
        List<HarEntry> filteredLogs = Collections.synchronizedList(new ArrayList<HarEntry>());
        for (HarEntry entry : ProxyRESTManager.getLogEntries()) {
            req = entry.getRequest().getUrl();
            if (req.matches(requestPattern) || req.equals(requestPattern) || req.contains(requestPattern)) {
                filteredLogs.add(entry);
            }
        }
        return filteredLogs;
    }
	
    public static String getValueFromResponseHeaderByKey(String pattern, String key) {
        List<HarHeader> headers = ProxyLogUtils.getLogEntries(pattern).get(0).getResponse().getHeaders();
        int headersSize = headers.size();
        for (int i = 0; i < headersSize; i++){
            HarHeader header = headers.get(i);
            if(header.getName().equals(key)){
                return header.getValue();
            }
        }
        throw new NullPointerException("Key: " + key + "was not found in " + pattern);
    }
    
	public static void waitForResponse(String requestUrl, int timeOut) {
		new FluentWait<>(DriverManager.getAppiumDriver()).withTimeout(timeOut, TimeUnit.SECONDS)
				.pollingEvery(Config.ConfigProps.POLLING_TIME, TimeUnit.MILLISECONDS)
				.until((ExpectedCondition<Boolean>) input -> {
					try {
						return getLogEntries(requestUrl).size() != 0 && !getResponse(requestUrl).isEmpty();
					} catch (Exception e) {
						Logger.logMessage(e.getStackTrace() + " some error in waitForResponse " + timeOut);
						return false;
					}
				});
	}
}
