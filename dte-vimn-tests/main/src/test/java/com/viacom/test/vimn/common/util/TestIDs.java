package com.viacom.test.vimn.common.util;

import java.util.ArrayList;
import java.util.List;

public class TestIDs {
	
	private static ThreadLocal<String> testId = new ThreadLocal<String>();
	private static List<String> passedTestIds = new ArrayList<String>();
	private static List<String> failedTestIds = new ArrayList<String>();
	private static List<String> skippedTestIds = new ArrayList<String>();
    
	public static void setTestId(String id) {
		testId.set(id);
	}
	
	public static String getTestId() {
		return testId.get();
	}
	
    public static void addPassedTest(String testId) {
    	passedTestIds.add(testId);
    }
    
    public static void addFailedTest(String testId) {
    	failedTestIds.add(testId);
    }

    public static void addSkippedTest(String testId) {
		skippedTestIds.add(testId);
	}
    
    public static List<String> getPassedTestIds() {
    	return passedTestIds;
    }
    
    public static List<String> getFailedTestIds() {
    	return failedTestIds;
    }
}
