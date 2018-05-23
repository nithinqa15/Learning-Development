package com.viacom.test.vimn.common.util;

public class FBAnalytics {
	
	// Declare data
	String AppIDKey;
    
	//FBAppID key source - https://confluence.mtvi.com/display/TVE/Facebook+and+Twitter+ID-KEY+Configuration
    public String getFBAppIdKey(String appName) {
    	AppIDKey = "";
    	if (TestRun.isParamountApp()) {
    		AppIDKey = TestRun.isIos() ? "1882545375396667" : "817489998411274";
    	} else if (TestRun.isVH1App()) {
    		AppIDKey = TestRun.isIos() ? "151668731210" : "442034362858746";
    	} else if (TestRun.isCMTApp()) {
    		AppIDKey = TestRun.isIos() ? "139487352735740" : "1968914709989337";
    	} else if (TestRun.isTVLandApp()) {
    		AppIDKey = TestRun.isIos() ? "117743591595814" : "2026380417597604";
    	} else if (TestRun.isMTVDomesticApp()) {
    		AppIDKey = TestRun.isIos() ? "122150691493" : "1257926634354166";
    	} else if (TestRun.isMTVINTLApp()) {
    		AppIDKey = TestRun.isIos() ? "122150691493" : "1257926634354166";
    	} else if (TestRun.isCCDomesticApp()) {
    		AppIDKey = TestRun.isIos() ? "143385531530" : "331661220640768";
    	} else if (TestRun.isCCINTLApp()) {
    		AppIDKey = TestRun.isIos() ? "143385531530" : "331661220640768";
    	} else if (TestRun.isBETDomesticApp()) {
    		AppIDKey = TestRun.isIos() ? "186402491400362" : "1433068296810326";
    	}
		return AppIDKey; 
    }
}
