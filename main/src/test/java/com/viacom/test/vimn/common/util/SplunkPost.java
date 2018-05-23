package com.viacom.test.vimn.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;

import de.sstoehr.harreader.model.HarEntry;

import com.viacom.test.core.lab.GridManager;
import com.viacom.test.core.lab.LabDeviceManager;
import com.viacom.test.core.props.MobileOS;
import com.viacom.test.core.proxy.ProxyRESTManager;
import com.viacom.test.core.report.SplunkManager;

import com.viacom.test.vimn.common.util.Config.ConfigProps;

public class SplunkPost {
	
	@SuppressWarnings({ "rawtypes" })
	public static void postBeacon() {
		String beaconUrl = TestRun.isAndroid() ? ConfigProps.ANDROID_MEGA_BEACON_URL : ConfigProps.IOS_MEGA_BEACON_URL;
		List<HarEntry> logEntries = ProxyRESTManager.getLogEntries();
		Iterator iterator = logEntries.iterator();
		while (iterator.hasNext()) {
			HarEntry entry = (HarEntry) iterator.next();
			if (entry.getRequest().getUrl().contains(beaconUrl)) {
				String requestBody = entry.getRequest().getPostData().getText();
				if (requestBody.contains("beaconType")) {
					SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MMddyyHHmmssSS");
	        		String dateTime = dateTimeFormat.format(new Date());
	        	    
	        		String sessionIP = GridManager.getRunningSessionIP();
	        		MobileOS mobileOS = TestRun.getMobileOS();
	        		
	        		StringBuilder beaconEntry = new StringBuilder();
	        		beaconEntry.append("{\"dateTime\": " + "\"" + dateTime + "\", ");
	        		beaconEntry.append("\"buildNumber\": " + "\"" + ConfigProps.BUILD_NUMBER + "\", ");
	        		beaconEntry.append("\"testType\": " + "\"Performance\", ");
	        		beaconEntry.append("\"deviceId\": " + "\"" + LabDeviceManager.getDeviceID(sessionIP, mobileOS) + "\", ");
	        		beaconEntry.append("\"mobileConnectionType\": " + "\"Wifi\", ");
	        		beaconEntry.append("\"bandwidthType\": " + "\"Static\", ");
	        		beaconEntry.append("\"bandwidthValue\": " + "\"" + ConfigProps.DEVICE_BANDWIDTH + "\", ");
	        		beaconEntry.append("\"networkLatency\": " + "\"" + ConfigProps.NETWORK_LATENCY + "\", ");
	        		beaconEntry.append("\"osType\": " + "\"" + mobileOS.value() + "\", ");
	        		beaconEntry.append("\"deviceName\": " + "\"" + LabDeviceManager.getDeviceName(sessionIP, mobileOS) + "\", ");
	        		beaconEntry.append("\"osVersion\": " + "\"" + LabDeviceManager.getDeviceOSVersion(sessionIP, mobileOS) + "\", ");
	        		beaconEntry.append("\"deviceComplete\": " + "\"" + mobileOS.value() + " " 
	        		    + LabDeviceManager.getDeviceOSVersion(sessionIP, mobileOS) + " " 
	        		    + LabDeviceManager.getDeviceName(sessionIP, mobileOS) 
	        			+ "\", ");
	        		beaconEntry.append("\"osComplete\": " + "\"" + mobileOS.value() + " " 
    	                + LabDeviceManager.getDeviceOSVersion(sessionIP, mobileOS) + "\", ");
	        		beaconEntry.append(requestBody.replaceFirst("\\{", ""));
	        		
	        		// post to splunk
	        		new SplunkManager().connectToSplunk().setIndex(ConfigProps.SPLUNK_INDEX)
	        				.postEvent(beaconEntry.toString());
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void postHarData() {
		List<HarEntry> logEntries = ProxyRESTManager.getLogEntries();
		Iterator iterator = logEntries.iterator();
		while (iterator.hasNext()) {
			HarEntry entry = (HarEntry) iterator.next();
			
			JSONObject json = new JSONObject();
			json.put("requestUrl", entry.getRequest().getUrl());
			json.put("requestMethod", entry.getRequest().getMethod());
			try {
			    json.put("requestPostDataMimeType", entry.getRequest().getPostData().getMimeType());
			} catch (NullPointerException e) {
				// ignore
			}
			// TODO headers
			// TODO query strings
			json.put("responseStatus", entry.getResponse().getStatus());
			json.put("responseStatusText", entry.getResponse().getStatusText());
			json.put("responseContentSize", entry.getResponse().getContent().getSize());
			json.put("responseContentMimeType", entry.getResponse().getContent().getMimeType());
			json.put("responseSize", entry.getResponse().getBodySize());
			json.put("responseRedirectUrl", entry.getResponse().getRedirectURL());
			json.put("timeTotal", entry.getTime());
			json.put("timeWait", entry.getTimings().getWait());
			json.put("timeBlocked", entry.getTimings().getBlocked());
			json.put("timeConnect", entry.getTimings().getConnect());
			json.put("timeReceive", entry.getTimings().getReceive());
			json.put("timeSend", entry.getTimings().getSend());
			json.put("timeSSL", entry.getTimings().getSsl());
			json.put("timeDNS", entry.getTimings().getDns());
			
			// post to splunk
    		new SplunkManager().connectToSplunk().setIndex(ConfigProps.SPLUNK_INDEX)
    				.postEvent(json.toJSONString());
		}
	}
	
}
