package com.viacom.test.vimn.common.omniture;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.core.proxy.ProxyRESTManager;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.util.Config.ConfigProps;
import com.viacom.test.vimn.common.exceptions.DriverTimeoutException;
import com.viacom.test.vimn.common.util.TestRun;

import de.sstoehr.harreader.model.HarPostDataParam;
import de.sstoehr.harreader.model.HarEntry;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import io.appium.java_client.AppiumDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class OmnitureLogUtils {
	
	//Maximum try count for omniture log retrieval // International App = 60 Sec, Domestic App = 40 Sec
	public static int maximumLogRetrievalTry = (TestRun.isCCINTLApp() || TestRun.isMTVINTLApp() || TestRun.isBETINTLApp()) ? 3 : 2;
	public static long startTime;
	public static long endTime; 
	public static int waitTime;
	public static boolean IsURLPatternFoundInProxyLog = false;

    private static List<HarPostDataParam> postData;

    /**
     * Returns a list of omniture calls matching a unique value respective to the
     * omniture call under test.
     * @param additionalCheck unique identifier value that's present on the call
     * @return list of HarPostDataParams
     */
	@SuppressWarnings("rawtypes")
	private static List<HarPostDataParam> getPostData(String additionalCheck) {
		startTime = System.currentTimeMillis();
		new FluentWait<AppiumDriver>(DriverManager.getAppiumDriver())
				.withTimeout(Integer.parseInt(ConfigProps.SERVER_COMMAND_TIMEOUT)*2, TimeUnit.SECONDS)
				.pollingEvery(ConfigProps.POLLING_TIME, TimeUnit.MILLISECONDS).until(new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver input) {
						Boolean success = false;
						postData = new ArrayList<>();
						String urlPattern = OmnitureConstants.REPORT_URL_PATTERN;
						List<HarEntry> logEntries = ProxyRESTManager.getLogEntries();
						if (!logEntries.isEmpty()) {
							for (int i = logEntries.size() - 1; i >= 0; i--) {
	                            HarEntry entry = logEntries.get(i);
								try {
									if (entry.getRequest().getUrl().contains(urlPattern)) {
										IsURLPatternFoundInProxyLog = true;
										List<HarPostDataParam> dataParams = entry.getRequest().getPostData().getParams();
										dataParams.forEach((data) -> {
											if (data.getValue().contains(additionalCheck)) {
												postData = entry.getRequest().getPostData().getParams();
											}
										});
										if (!postData.isEmpty()) {
											success = true;
											break;
										}
									} 
								} catch (NullPointerException e) {
									// ignore as bmp throws null pointer exception in the event a request/response
									// doesn't have the piece of data in the har we're checking for... known bmp bug...
								}
							}
							endTime = System.currentTimeMillis();
							waitTime = (int) (endTime-startTime)/1000;
							DriverManager.getAppiumDriver().getDeviceTime(); //To prevent Driver TimeoutException
							//To check url Pattern available
							if (!IsURLPatternFoundInProxyLog && postData.isEmpty() && waitTime >= ConfigProps.MAX_WAIT_TIME) { //Condition check before timeout
								Assert.fail(urlPattern + " Not found in proxy log");
							}
							//To check unique identifier available
							if (IsURLPatternFoundInProxyLog && postData.isEmpty() && waitTime >= ConfigProps.MAX_WAIT_TIME) { //Condition check before timeout
								Assert.fail(urlPattern + " found in proxy log but " + additionalCheck + " Not available in log entry");
							} 
						} else {
							//To check proxy setup
							Assert.fail("Proxy Log not found, Local Run? - Check your proxy setup OR MQE Lab Run? - "
									+ "Proxy spin up issue, Please check proxy and bmp log");
						}
						return success;
					}
				});
        return postData;
    }
	
	/**
     * Returns a list of omniture calls matching a unique key-value respective to the
     * omniture call under test.
     * @param unique identifier key-value that's present on the call
     * @return list of HarPostDataParams
     */
	@SuppressWarnings({ "rawtypes" })
	private static List<HarPostDataParam> getPostData(String Key, String Value) {
		startTime = System.currentTimeMillis();
		new FluentWait<AppiumDriver>(DriverManager.getAppiumDriver())
				.withTimeout(Integer.parseInt(ConfigProps.SERVER_COMMAND_TIMEOUT)*2, TimeUnit.SECONDS)
				.pollingEvery(ConfigProps.POLLING_TIME, TimeUnit.MILLISECONDS).until(new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver input) {
						Boolean success = false;
						postData = new ArrayList<>();
						String urlPattern = OmnitureConstants.REPORT_URL_PATTERN;
						List<HarEntry> logEntries = ProxyRESTManager.getLogEntries();
						if (!logEntries.isEmpty()) {
						for (int i = logEntries.size() - 1; i >= 0; i--) {
                            HarEntry entry = logEntries.get(i);
							try {
								if (entry.getRequest().getUrl().contains(urlPattern)) {
									IsURLPatternFoundInProxyLog = true;
									List<HarPostDataParam> dataParams = entry.getRequest().getPostData().getParams();
									dataParams.forEach((data) -> {
										if (data.getName().contains(Key) && data.getValue().contains(Value)) {
											postData = entry.getRequest().getPostData().getParams();
										}
									});
									if (!postData.isEmpty()) {
										success = true;
										break;
									}
								}
							} catch (NullPointerException e) {
								// ignore as bmp throws null pointer exception in the event a request/response
								// doesn't have the piece of data in the har we're checking for... known bmp bug...
							}
						}
						endTime = System.currentTimeMillis();
						waitTime = (int) (endTime-startTime)/1000;
						DriverManager.getAppiumDriver().getDeviceTime(); //To prevent Driver TimeoutException
						//To check url Pattern available
						if (!IsURLPatternFoundInProxyLog && postData.isEmpty() && waitTime >= ConfigProps.MAX_WAIT_TIME) { //Condition check before timeout
							Assert.fail(urlPattern + " Not found in proxy log");
						}
						//To check unique identifier available
						if (IsURLPatternFoundInProxyLog && postData.isEmpty() && waitTime >= ConfigProps.MAX_WAIT_TIME) { //Condition check before timeout
							Assert.fail(urlPattern + " found in proxy log but " + Key + "-" + Value + " Not available in log entry");
						} 
					} else {
						//To check proxy setup
						Assert.fail("Proxy Log not found, Local Run? - Check your proxy setup OR MQE Lab Run? - "
								+ "Proxy spin up issue, Please check proxy and bmp log");
					}
						return success;
					}
				});
        return postData;
    }

    /**
     * Returns a map containing the actual values of the omniture call under test.
     * @param additionalCheck unique identifier value that's present on the call
     * @return actual map
     */
    public static Map<String, String> getActualMap(String additionalCheck) {
        Map<String, String> actualMap = new HashMap<>();
        List<HarPostDataParam> params = null;
        for (int i=0; i<maximumLogRetrievalTry; i++) {
	    	try {
				  params = getPostData(additionalCheck);
		       	  if (!params.isEmpty()) {
		       		  break;
		       	  } 
	    	} catch (AssertionError | Exception e) {
	    		if (e instanceof TimeoutException || e instanceof WebDriverException) {
	    			Logger.logMessage("#########  Test Skipped due to driver Timeout #########");
	    			throw new DriverTimeoutException("#########  Test Skipped due to driver Timeout during omniture log retrieval #########");
	    		} else if (i == maximumLogRetrievalTry-1 && e instanceof AssertionError) {
	    			throw e;
	    		} else {
	    			Logger.logMessage("Log retrieval TRY : " + i + " : Expected log entry NOT found " + e.getCause());
	    		}
	    	}
        }
        for (HarPostDataParam param : params) {
            String key = param.getName();
            String value = param.getValue();
            actualMap.put(key, value);
        }
        return actualMap;
    }
    
    /**
     * Returns a map containing the actual Key-values of the omniture call under test.
     * @param unique identifier key-value that's present on the call
     * @return actual map
     */
    public static Map<String, String> getActualMap(String Key, String Value) {
        Map<String, String> actualMap = new HashMap<>();
        List<HarPostDataParam> params = null;
        for (int i=0; i<maximumLogRetrievalTry; i++) {
	    	try {
	    		params = getPostData(Key, Value);
				if (!params.isEmpty()) {
					break;
				} 
	    	} catch (AssertionError | Exception e) {
	    		if (e instanceof TimeoutException || e instanceof WebDriverException) {
	    			Logger.logMessage("#########  Test Skipped due to driver Timeout #########");
	    			throw new DriverTimeoutException("#########  Test Skipped due to driver Timeout during omniture log retrieval #########");
	    		} else if (i == maximumLogRetrievalTry-1 && e instanceof AssertionError) {
	    			throw e;
	    		} else {
	    			Logger.logMessage("Log retrieval TRY : " + i + " : Expected log entry NOT found " + e.getCause());
	    		}
	    	}
        }
        for (HarPostDataParam param : params) {
            String key = param.getName();
            String value = param.getValue();
            actualMap.put(key, value);
        }
        return actualMap;
    }

    public synchronized List<HarEntry> getAllOmnitureCallsFromLog() {
        try {
            this.wait(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<HarEntry> omnitureCalls = new ArrayList<>();
        String urlPattern = OmnitureConstants.REPORT_URL_PATTERN;
        List<HarEntry> logEntries = ProxyRESTManager.getLogEntries();
        Iterator<HarEntry> iterator = logEntries.iterator();
        while (iterator.hasNext()) {
            HarEntry entry = iterator.next();
            try {
                if (entry.getRequest().getUrl().contains(urlPattern)) {
                    omnitureCalls.add(entry);
                }
            } catch (NullPointerException e) {
                // ignore as bmp throws null pointer exception in the event a request/response doesn't
                // have the piece of data in the har we're checking for... known bmp bug...
            }
        }
        return omnitureCalls;
    }
    
    public static boolean expectedParamPresent(String additionalCheck) {
        try {
        	@SuppressWarnings("unused")
			List<HarPostDataParam> params = getPostData(additionalCheck);
        	return true;
        } catch (AssertionError | TimeoutException e) { //Catch timeout for checking expected Param
            return false;
        }
    }
}
