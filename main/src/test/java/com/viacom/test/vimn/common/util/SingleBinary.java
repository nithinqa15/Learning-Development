package com.viacom.test.vimn.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleBinary {
	
	// Declare data
	List<String> expectedProviderList;
	Map<String, String> expectedMainFeedAppValuesMap;
    
    public List<String> expectedLatAmProviderList(String locale) {
    	
    	expectedProviderList = new ArrayList<String>();
    	
    	switch (locale) {
    	case "es_mx":
    		expectedProviderList.add("Dish");
    		expectedProviderList.add("izzi");
    		expectedProviderList.add("Megacable");
    		expectedProviderList.add("Sky");
    		break;
    		
    	case "pt_br":
    		expectedProviderList.add("Claro");
    		expectedProviderList.add("NET");
    		expectedProviderList.add("Sky");
    		break;
    		
    	case "es_ar":
    		expectedProviderList.add("Cablevision");
    		expectedProviderList.add("Colsecor");
    		expectedProviderList.add("DIRECTV");
    		expectedProviderList.add("Telecentro");
    		break;
    		
 		case "es_cl":
 			expectedProviderList.add("Claro");
 			expectedProviderList.add("DIRECTV");
 			break;
 			
		case "es_co":
 			expectedProviderList.add("Claro");
 			expectedProviderList.add("DIRECTV");
 			expectedProviderList.add("Tigo");
 			break;
 			
		case "es_cr":
 			expectedProviderList.add("Cabletica");
 			expectedProviderList.add("Claro");
 			expectedProviderList.add("Tigo");
 			break;
 			
		case "es_sv":
 			expectedProviderList.add("Claro");
 			expectedProviderList.add("Tigo");
 			break;
 			
		case "es_gt":
 			expectedProviderList.add("Claro");
 			expectedProviderList.add("Tigo");
 			break;
 			
		case "es_ni":
 			expectedProviderList.add("Claro");
 			break;
 			
		case "es_hn":
 			expectedProviderList.add("Claro");
 			break;
 			
		case "es_pe":
 			expectedProviderList.add("Claro");
 			expectedProviderList.add("DIRECTV");
 			break;
 			
		case "es_uy":
 			expectedProviderList.add("DIRECTV");
 			break;
 			
		case "es_ec":
 			expectedProviderList.add("DIRECTV");
 			expectedProviderList.add("Claro");
 			break;
 			
		case "es_do":
 			expectedProviderList.add("Claro");
 			break;
 			
		case "es_bo":
 			expectedProviderList.add(""); // TVE provider not available
 			break;
 			
		case "es_ve":
 			expectedProviderList.add("DIRECTV");
 			break;
		}
		
		return expectedProviderList;
    }
    
    public List<String> expectedMTVNorthProviderList(String locale) {
    	
    	expectedProviderList = new ArrayList<String>();
    	
    	switch (locale) {
    	case "no_no":
    		expectedProviderList.add("Get");
			expectedProviderList.add("RiksTV");
    		break;
    		
    	case "da_dk":
    		expectedProviderList.add("Boxer");
			expectedProviderList.add("Stofa");
			expectedProviderList.add("VIMN TVE Guest");
    		break;
		}
		
		return expectedProviderList;
    }
    
    public Map<String, String> expectedMTVNorthMainFeedAppValuesMap(String locale) {
    	
    	expectedMainFeedAppValuesMap = new HashMap<String, String>();
    	
		expectedMainFeedAppValuesMap.put("backgroundServiceVideoEnabled", "false");
		expectedMainFeedAppValuesMap.put("allShowsEnabled", "true");
    	
    	switch (locale) {
    	case "nl_be":
    		expectedMainFeedAppValuesMap.put("shortForm", "true");
			expectedMainFeedAppValuesMap.put("tveAuthenticationEnabled", "false");
    		break;
    		
    	case "nl_nl":
    		expectedMainFeedAppValuesMap.put("shortForm", "true");
			expectedMainFeedAppValuesMap.put("tveAuthenticationEnabled", "false");
    		break;
    		
    	case "no_no":
    		expectedMainFeedAppValuesMap.put("shortForm", "true");
			expectedMainFeedAppValuesMap.put("tveAuthenticationEnabled", "true");
    		break;
    		
 		case "da_dk":
 			expectedMainFeedAppValuesMap.put("shortForm", "true");
			expectedMainFeedAppValuesMap.put("tveAuthenticationEnabled", "true");
 			break;
 			
		case "pl_pl":
			expectedMainFeedAppValuesMap.put("shortForm", "true");
			expectedMainFeedAppValuesMap.put("tveAuthenticationEnabled", "false");
 			break;
 			
		case "de_at":
			expectedMainFeedAppValuesMap.put("shortForm", "false");
			expectedMainFeedAppValuesMap.put("tveAuthenticationEnabled", "false");
 			break;
 			
		case "de_de":
			expectedMainFeedAppValuesMap.put("shortForm", "false");
			expectedMainFeedAppValuesMap.put("tveAuthenticationEnabled", "false");
 			break;
		}
		
		return expectedMainFeedAppValuesMap;
    }
    
    public List<String> expectedMTVCCUKProviderList(String locale) {
    	
    	expectedProviderList = new ArrayList<String>();
    	
    	switch (locale) {
    	case "uk_uk":
    		expectedProviderList.add("Virgin Media"); //Not implemented yet
    		break;
		}
		
		return expectedProviderList;
    }
    
    public List<String> expectedSEAProviderList(String locale) {
    	
    	expectedProviderList = new ArrayList<String>();
    	
    	switch (locale) {
    	case "en_sg":
    		expectedProviderList.add("Singtel");
    		break;
		}
		
		return expectedProviderList;
    }
}
