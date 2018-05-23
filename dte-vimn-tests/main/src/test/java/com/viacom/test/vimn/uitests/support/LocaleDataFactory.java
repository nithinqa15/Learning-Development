package com.viacom.test.vimn.uitests.support;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.common.util.Config.ConfigProps;

public class LocaleDataFactory {

	public LocaleDataFactory() {

	}

	private static JSONObject getJSONData() {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;

		try {
			Object obj = parser.parse(new FileReader(ConfigProps.LOCALEDATA_FILE_PATH));
			jsonObject = (JSONObject) obj;
		} catch (Exception e) {
			Assert.fail("Failed to get locale data from json file.", e);
		}
		return jsonObject;
	}

	public static String getRegionIPAddress() {

		JSONObject json = getJSONData();
		JSONObject localeObj = (JSONObject) json.get(TestRun.getLocale());

		if(localeObj == null){
			Logger.logMessage("The locale object is null");
		}
		Logger.logMessage("Locale Country Name " + ": " + localeObj.get("country").toString());
		return localeObj.get("ipAddress").toString();

	}

	public static HashMap<String, String> getDefaultProviderData() {

		HashMap<String, String> providerData = new HashMap<String, String>();
		JSONObject json = getJSONData();
		JSONObject localeObj = (JSONObject) json.get(TestRun.getLocale());
		JSONObject defaultProviderObj = (JSONObject) localeObj.get("defaultProvider");
		providerData.put("name", defaultProviderObj.get("name").toString());
		if (defaultProviderObj.containsKey("username") && defaultProviderObj.containsKey("password")) {
			providerData.put("username", defaultProviderObj.get("username").toString());
			providerData.put("password", defaultProviderObj.get("password").toString());
		}
		if (defaultProviderObj.containsKey("noAuthZUsername") && defaultProviderObj.containsKey("noAuthZPassword")) {
			providerData.put("noAuthZUsername", defaultProviderObj.get("noAuthZUsername").toString());
			providerData.put("noAuthZPassword", defaultProviderObj.get("noAuthZPassword").toString());
		}
		if (defaultProviderObj.containsKey("invalidUsername") && defaultProviderObj.containsKey("invalidPassword")) {
			providerData.put("invalidUsername", defaultProviderObj.get("invalidUsername").toString());
			providerData.put("invalidPassword", defaultProviderObj.get("invalidPassword").toString());
		}

		return providerData;

	}

	public static Boolean hasProvider(String providerName) {
		JSONObject json = getJSONData();
		JSONObject localeObj = (JSONObject) json.get(TestRun.getLocale());
		return localeObj.containsKey(providerName);
	}

	public static HashMap<String, String> getProviderData(String providerName) {

		HashMap<String, String> providerData = new HashMap<String, String>();
		JSONObject json = getJSONData();
		JSONObject localeObj = (JSONObject) json.get(TestRun.getLocale());
		JSONObject providerObj = (JSONObject) localeObj.get(providerName.toLowerCase());
		providerData.put("name", providerObj.get("name").toString());
		if (providerObj.containsKey("username") && providerObj.containsKey("password")) {
			providerData.put("username", providerObj.get("username").toString());
			providerData.put("password", providerObj.get("password").toString());
		}
		if (providerObj.containsKey("noAuthZUsername") && providerObj.containsKey("noAuthZPassword")) {
			providerData.put("noAuthZUsername", providerObj.get("noAuthZUsername").toString());
			providerData.put("noAuthZPassword", providerObj.get("noAuthZPassword").toString());
		}
		if (providerObj.containsKey("invalidUsername") && providerObj.containsKey("invalidPassword")) {
			providerData.put("invalidUsername", providerObj.get("invalidUsername").toString());
			providerData.put("invalidPassword", providerObj.get("invalidPassword").toString());
		}
		return providerData;

	}

	public static Boolean isMTV() {

		JSONObject json = getJSONData();
		JSONObject localeObj = (JSONObject) json.get(TestRun.getLocale());
		return Boolean.parseBoolean(localeObj.get("isMTV").toString());

	}

	public static Boolean isCC() {

		JSONObject json = getJSONData();
		JSONObject localeObj = (JSONObject) json.get(TestRun.getLocale());
		return Boolean.parseBoolean(localeObj.get("isCC").toString());

	}

	public static Boolean isTVLand() {

		JSONObject json = getJSONData();
		JSONObject localeObj = (JSONObject) json.get(TestRun.getLocale());
		return Boolean.parseBoolean(localeObj.get("isTVLand").toString());

	}

	public static Boolean isBET() {

		JSONObject json = getJSONData();
		JSONObject localeObj = (JSONObject) json.get(TestRun.getLocale());
		return Boolean.parseBoolean(localeObj.get("isBET").toString());

	}

	public static Boolean isCMT() {

		JSONObject json = getJSONData();
		JSONObject localeObj = (JSONObject) json.get(TestRun.getLocale());
		return Boolean.parseBoolean(localeObj.get("isCMT").toString());

	}

	public static Boolean isVH1() {

		JSONObject json = getJSONData();
		JSONObject localeObj = (JSONObject) json.get(TestRun.getLocale());
		return Boolean.parseBoolean(localeObj.get("isVH1").toString());

	}
	
	public static Boolean isParamount() {

		JSONObject json = getJSONData();
		JSONObject localeObj = (JSONObject) json.get(TestRun.getLocale());
		return Boolean.parseBoolean(localeObj.get("isParamount").toString());

	}

	public static String getCountryName() {

		JSONObject json = getJSONData();
		JSONObject localeObj = (JSONObject) json.get(TestRun.getLocale());
		return localeObj.get("country").toString();
	}

	public static String getCluster() {

		JSONObject json = getJSONData();
		JSONObject localeObj = (JSONObject) json.get(TestRun.getLocale());
		return localeObj.get("cluster").toString();
	}

	public static String getIsoGeoCode() {

		JSONObject json = getJSONData();
		JSONObject localeObj = (JSONObject) json.get(TestRun.getLocale());
		return localeObj.get("isoGeoCode").toString();
	}
	
	public static List<String> getRegionList(String Brand) {
		List<String> availableRegionList = new ArrayList<String>();
		JSONObject json = getJSONData();
		String s = json.keySet().toString();
		
		//Collect all region key 
		List<String> allRegionKeyList = new ArrayList<String>(Arrays.asList(s.replace("[", "").replace("]","").replaceAll("\\s+","").split(",")));
		
		for (int i=0; i<allRegionKeyList.size(); i++) {
			JSONObject localeObj = (JSONObject) json.get(allRegionKeyList.get(i));
			if (Boolean.parseBoolean(localeObj.get(Brand).toString())) {
				//Collect only brand specific region key
				availableRegionList.add(allRegionKeyList.get(i));
			} 
		}
		return availableRegionList;
	}
	
	public static String getGeoCode(String RegionKey) {
		JSONObject json = getJSONData();
		JSONObject localeObj = (JSONObject) json.get(RegionKey);
		return localeObj.get("isoGeoCode").toString();
	}
	
	public static String getCountry(String RegionKey) {
		JSONObject json = getJSONData();
		JSONObject localeObj = (JSONObject) json.get(RegionKey);
		return localeObj.get("country").toString();
	}

}