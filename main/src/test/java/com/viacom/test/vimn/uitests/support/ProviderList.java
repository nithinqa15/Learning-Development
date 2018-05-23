package com.viacom.test.vimn.uitests.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.viacom.test.vimn.common.proxy.ProxyLogUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;

public class ProviderList {
	
	public ProviderList() {

	}
	
	public List<String> getActualProviders() {
		
		String url;
		String key;
		if (TestRun.getLocale().equals("en_us")) {
			url = Config.StaticProps.EXTENDED_PROVIDERS_LIST;
			key = "primaryList"; // Only MVPD picker list
		} else {
			url = Config.StaticProps.GET_PROVIDERS_LIST;
			key = "primaryList"; // Only MVPD picker list
		}
		
		String providerResponse = ProxyLogUtils.getResponse(url);
		
		List<String> actualproviders = new ArrayList<String>();
		JSONParser parser = new JSONParser();
		JSONObject object = null;
		try {
			object = (JSONObject) parser.parse(providerResponse);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONArray providersListObj = (JSONArray) object.get(key);
		int n = providersListObj.size();
		for (int i = 0; i < n; i++) {
			JSONObject providerObj = (JSONObject) providersListObj.get(i);
			if (!(providerObj.get("displayName") == null))
				actualproviders.add(providerObj.get("displayName").toString());
		}
        return actualproviders;
	}

	public List<String> getActualFeaturesList() {

		String url;
		url = Config.StaticProps.FEATURES_LIST;
		
		String providerResponse = ProxyLogUtils.getResponse(url);
		List<String> actualproviders = new ArrayList<String>();
		JSONParser parser = new JSONParser();
		JSONObject object = null;
		try {
			object = (JSONObject) parser.parse(providerResponse);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONArray providersListObj = (JSONArray) object.get("featuresList");
		int n = providersListObj.size();
		for (int i = 0; i < n; i++) {
			JSONObject providerObj = (JSONObject) providersListObj.get(i);
			if (!(providerObj.get("name") == null))
				actualproviders.add(providerObj.get("name").toString());
		}
		return actualproviders;
	}
	
	public Map<String, String> getNoProviderValues(Map<String, String> expectedMap) {

		JSONParser parser = new JSONParser();
		JSONObject object = null;
		Map<String, String> actualMap = new HashMap<String, String>();
		String noProviderResponse = ProxyLogUtils.getResponse(Config.StaticProps.GET_PROVIDERS_LIST);
		try {
			object = (JSONObject) parser.parse(noProviderResponse);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (String key : expectedMap.keySet()) {
			String actualValue = object.get(key).toString();
			actualMap.put(key, actualValue);
		}
		return actualMap;
	}
}
