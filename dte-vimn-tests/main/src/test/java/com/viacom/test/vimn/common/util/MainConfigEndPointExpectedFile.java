package com.viacom.test.vimn.common.util;

import java.util.HashMap;
import java.util.Map;

public class MainConfigEndPointExpectedFile {

	// Declare data
	Map<String, String> expectedMainConfigParamValueMap;
	
	// Reference Doc available here but wait until to get approval on this
	// https://confluence.mtvi.com/pages/viewpage.action?spaceKey=ENT&title=PlayPlex+QA+%7C+Configuration+per+brands+and+regions#508774a0e9f54b69aeac46331c263733
	public Map<String, String> expectedMainConfigParamValueMap(String geo) {
    	
    	expectedMainConfigParamValueMap = new HashMap<String, String>();
    	
    	//Common param listed here
		//expectedMainConfigParamValueMap.put("shortForm", "true");
		expectedMainConfigParamValueMap.put("displayTVEPromptAtStartup", "false");
		//expectedMainConfigParamValueMap.put("searchServiceEnabled", "false");
		//expectedMainConfigParamValueMap.put("searchPredictiveServiceEnabled", "false");
		expectedMainConfigParamValueMap.put("allShowsEnabled", "true");
		//expectedMainConfigParamValueMap.put("pageSize", (TestRun.isParamountApp()||TestRun.isMTVINTLApp()) ? "40" : "25"); 
		expectedMainConfigParamValueMap.put("tveAuthenticationEnabled", "true"); 
		//expectedMainConfigParamValueMap.put("displayLiveTVForAllUsers", "false"); //Override param - Brand Specific
  		expectedMainConfigParamValueMap.put("chromeCastEnabled", "false"); //Override param - Brand Specific
		expectedMainConfigParamValueMap.put("apptentiveEnabled", "false"); //Override param - Brand Specific
		//expectedMainConfigParamValueMap.put("clipPrerollsEnabled", "false"); //Override param - Brand Specific
		
		//Test main config
		switch (TestRun.getContentDomain()) {
		case "Test":
		    break;
		}
		
    	//Brand Specific Toggle
		switch (TestRun.getAppName()) {
    	case "VH1":
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false");
    		expectedMainConfigParamValueMap.put("chromeCastEnabled", "true"); //Override param - Brand Specific
    		expectedMainConfigParamValueMap.put("apptentiveEnabled", "true"); //Override param - Brand Specific
    		//expectedMainConfigParamValueMap.put("clipPrerollsEnabled", "true"); //Override param - Brand Specific
    		break;
    	case "PARAMOUNT":
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false");
    		expectedMainConfigParamValueMap.put("chromeCastEnabled", "true"); //Override param - Brand Specific
    		expectedMainConfigParamValueMap.put("apptentiveEnabled", "true"); //Override param - Brand Specific
    		//expectedMainConfigParamValueMap.put("clipPrerollsEnabled", "true"); //Override param - Brand Specific
    		break;
    	case "CMT":
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false");
    		//expectedMainConfigParamValueMap.put("clipPrerollsEnabled", TestRun.isAndroid() ? "true" : "false"); //Override param - Brand Specific
    		break;
    	case "TVLand":
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false");
    		break;
    	case "BET_INTL":
    		expectedMainConfigParamValueMap.put("appsFlyerEnabled", "true"); //Override param - Brand Specific
    		removeAppsFlyerEnabled();
    		break;
    	case "BET_DOMESTIC":
    		//expectedMainConfigParamValueMap.put("apptentiveEnabled", "true"); //Override param - Brand Specific
    		removeAppsFlyerEnabled();
    		break;
    	case "CC_DOMESTIC":
    		expectedMainConfigParamValueMap.put("chromeCastEnabled", "true"); //Override param - Brand Specific
    		expectedMainConfigParamValueMap.put("apptentiveEnabled", "true"); //Override param - Brand Specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "true");
    	    //expectedMainConfigParamValueMap.put("pageSize", (TestRun.isIos() ? "40" : "25")); // Override param - Brand Specific
    		//expectedMainConfigParamValueMap.put("clipPrerollsEnabled", "true"); //Override param - Brand Specific
    		break;
    	case "MTV_DOMESTIC":
    		expectedMainConfigParamValueMap.put("chromeCastEnabled", "true"); //Override param - Brand Specific
    		expectedMainConfigParamValueMap.put("apptentiveEnabled", "true"); //Override param - Brand Specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "true");
    		//expectedMainConfigParamValueMap.put("clipPrerollsEnabled", "true"); //Override param - Brand Specific
    		break;
    	}
		
		//Region Specific Toggle
    	switch (geo) {
    	case "US":
    		expectedMainConfigParamValueMap.put("moatEnabled", "true"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "false"); //Region specific
    		break;
    	case "AT":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", TestRun.isMTVINTLApp() ? "false" : "true"); //Region & Brand specific //Override param
    		expectedMainConfigParamValueMap.put("tveAuthenticationEnabled", TestRun.isMTVINTLApp() ? "false" : "true"); //Region & Brand specific //Override param
    		//expectedMainConfigParamValueMap.put("shortForm",  TestRun.isMTVINTLApp() ? "false" : "true"); //Region & Brand specific //Override param in UK region
    		break;
    	case "PL":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", TestRun.isMTVINTLApp() ? "false" : "true"); //Region & Brand specific //Override param
    		expectedMainConfigParamValueMap.put("tveAuthenticationEnabled", TestRun.isMTVINTLApp() ? "false" : "true"); //Region & Brand specific //Override param
    		break;
    	case "BR":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		//expectedMainConfigParamValueMap.put("pageSize", "25"); //Override param - Brand Specific
			//expectedMainConfigParamValueMap.put("displayLiveTVForAllUsers", TestRun.isMTVINTLApp() ? "true" : "false"); //Override param - Brand Specific
    		break;
    	case "EC":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		break;
    	case "DE":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		break;
    	case "SV":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		break;
    	case "UY":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		break;
    	case "MX":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		break;
    	case "CO":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		break;
    	case "GT":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		break;
    	case "CL":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		break;
    	case "NL":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", TestRun.isMTVINTLApp() ? "false" : "true"); //Region & Brand specific //Override param
    		expectedMainConfigParamValueMap.put("tveAuthenticationEnabled", TestRun.isMTVINTLApp() ? "false" : "true"); //Region & Brand specific //Override param
    		break;
    	case "PE":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		break;
    	case "VE":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		break;
    	case "CR":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		break;
    	case "BE":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", TestRun.isMTVINTLApp() ? "false" : "true"); //Region & Brand specific //Override param
    		expectedMainConfigParamValueMap.put("tveAuthenticationEnabled", TestRun.isMTVINTLApp() ? "false" : "true"); //Region & Brand specific //Override param
    		//expectedMainConfigParamValueMap.put("pageSize", "25"); //Override param - Brand Specific
    		break;
    	case "AR":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		break;
    	case "PA":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		break;
    	case "DK":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", TestRun.isMTVINTLApp() ? "false" : "true"); //Region specific
			//expectedMainConfigParamValueMap.put("displayLiveTVForAllUsers", "true"); //Region specific
    		break;
    	case "NI":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		break;
    	case "SG":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		break;
    	case "GB":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		//expectedMainConfigParamValueMap.put("clipPrerollsEnabled", TestRun.isBETINTLApp() ? "false" : "true"); //Region & Brand specific //Override param in UK region
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		expectedMainConfigParamValueMap.remove("tveAuthenticationEnabled", "true");
    		break;
    	case "BO":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		break;
    	case "ES":
    		expectedMainConfigParamValueMap.put("moatEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("marketingEnabled", "false"); //Region specific
    		expectedMainConfigParamValueMap.put("backgroundServiceVideoEnabled", "true"); //Region specific
    		//expectedMainConfigParamValueMap.put("pageSize", "25"); //Override param - Brand Specific
			//expectedMainConfigParamValueMap.put("displayLiveTVForAllUsers", "true"); //Region specific
    		break;
		case "NO":
			//expectedMainConfigParamValueMap.put("displayLiveTVForAllUsers", "true"); //Region specific
			break;
    	}
    	
    	return expectedMainConfigParamValueMap;
    }
	
	private void removeAppsFlyerEnabled() {
		if (TestRun.isAndroid())
			expectedMainConfigParamValueMap.remove("appsFlyerEnabled");
	}

}
