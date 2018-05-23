package com.viacom.test.vimn.common.listeners;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.core.lab.GridManager;
import com.viacom.test.core.lab.LabDeviceManager;
import com.viacom.test.core.lab.TestDeviceInfo;
import com.viacom.test.core.report.AllureAttachment;
import com.viacom.test.core.util.Logger;
import com.viacom.test.core.props.AllureScreenshotType;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.driver.CapabilityFactory;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.*;
import com.viacom.test.vimn.common.util.Config.ConfigProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;

import org.apache.commons.lang3.ArrayUtils;
import org.testng.*;
import org.testng.internal.ResultMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.viacom.test.vimn.common.util.Config.GroupProps.TVE;

public class TestListeners extends BaseTest implements IRetryAnalyzer, ITestListener, IInvokedMethodListener {

	private static final int MAX_COUNT = ConfigProps.RERUN_ON_FAILURE_COUNT;
	private Map<String, AtomicInteger> retries = new HashMap<String, AtomicInteger>();
	private IResultMap failedCases = new ResultMap();
	private static String RUN_PROPS = "runProps";
	private static ThreadLocal<String> testID = new ThreadLocal<String>();
	private static ThreadLocal<String> gridSessionIP = new ThreadLocal<String>();
	private static ThreadLocal<String> testName = new ThreadLocal<String>();
	private static ThreadLocal<String> dependencyTestName = new ThreadLocal<String>();
	private static ThreadLocal<Map<String, String>> successfulPhoneTest = ThreadLocal.withInitial(HashMap::new);
	private static ThreadLocal<Map<String, String>> successfulTabletTest = ThreadLocal.withInitial(HashMap::new);

    @Override
    public void onFinish(final ITestContext context) {
		Logger.logConsoleMessage("========TEST FINISHED========");
    }

    @Override
    public void onStart(final ITestContext test) {
		Logger.logConsoleMessage("========TEST STARTED========");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(final ITestResult result) {

    }

    @Override
    public void onTestStart(final ITestResult result) {
    	
    	try {
    		testName.set(result.getName());
		} catch (Exception e) {
    		Logger.logConsoleMessage("Failed to set Test Name.");
		}

		// Add dependency method name in case method has one.
		try {
    		dependencyTestName.set(null);
    		List<String> testMethods = Arrays.asList(result.getMethod().getMethodsDependedUpon());
			if (testMethods.size() > 0) {
				for (String testMethod : testMethods) {
					Logger.logConsoleMessage(testMethod);
					dependencyTestName.set(testMethod);
					break;
				}
			}
		} catch (Exception e) {
    		Logger.logConsoleMessage("Failed to set Dependable Test Name");
    		e.printStackTrace();
		}

    	try {

    		// start the appium session
        	CapabilityFactory.setCapabilities();
    	} catch (Exception e) {
    		Logger.logConsoleMessage("Failed to start driver.");
    		e.printStackTrace();
    	}

    	try {
    		// get the session
        	if (ConfigProps.RUN_AS_FACTORY) {
        	    gridSessionIP.set(GridManager.getRunningSessionIP());
        	}
    	} catch (Exception e) {
    		Logger.logConsoleMessage("Failed to get session ip.");
    		e.printStackTrace();
    	}

    	// set bandwidth/latency as desired
    	if (ConfigProps.DEVICE_BANDWIDTH != 0) {
            ProxyUtils.enableBandwidthThrottling(ConfigProps.NETWORK_LATENCY, ConfigProps.DEVICE_BANDWIDTH, ConfigProps.DEVICE_BANDWIDTH);
    	}

    	// set attribute as a test id
    	result.setAttribute(RUN_PROPS, new Object[] { TestRun.getMobileOS().value() + TestRun.getDeviceCategory().value() });

    	try {
    		// set attribute as a test id
        	result.setAttribute(RUN_PROPS, new Object[] { TestRun.getMobileOS().value() + TestRun.getDeviceCategory().value() });

        	// set the test id
        	String id = StaticProps.TEST_ID + " " + result.getMethod().getMethodName() + " "
            	    + "(" + TestRun.getMobileOS().value() + " " + TestRun.getDeviceCategory().value() + ")";
        	testID.set(id.toLowerCase());
        	Logger.logMessage(testID.get());
    	} catch (Exception e) {
    		Logger.logConsoleMessage("Failed to log test ids.");
    		e.printStackTrace();
    	}

        try {
        	// log the test initiation
        	Logger.logMessage("========NEW TEST SESSION========");
        	Logger.logMessage(StaticProps.MOBILE_OS_LOG + TestRun.getMobileOS());
            Logger.logMessage(StaticProps.DEVICE_CATEGORY_LOG + TestRun.getDeviceCategory());
            if (ConfigProps.RUN_AS_FACTORY) {
                Logger.logMessage(StaticProps.DEVICE_NAME_LOG + TestDeviceInfo.getDeviceName());
				Logger.logMessage("Device OS Version: " + TestDeviceInfo.getDeviceOSVersion());
                Logger.logMessage("Grid Session IP: " + gridSessionIP.get());
            }
    	} catch (Exception e) {
    		Logger.logConsoleMessage("Failed to log startup data.");
    		e.printStackTrace();
    	}
    }

	@Override
    public void onTestSuccess(final ITestResult result) {
		try {
			Logger.logConsoleMessage("======SUCCESS======");
			Logger.logConsoleMessage("Test: " + result.getInstanceName() + "." + result.getName());

			if (DriverManager.getAppiumDriver() != null && DriverManager.getAppiumDriver().getSessionId() != null) {
				Logger.logConsoleMessage(StaticProps.MOBILE_OS_LOG + TestRun.getMobileOS());
				Logger.logConsoleMessage("Device Category: " + TestRun.getDeviceCategory().toString());
				Logger.logConsoleMessage("Grid Session IP: " + gridSessionIP.get());
				if (ConfigProps.RUN_AS_FACTORY) {
					Logger.logConsoleMessage(StaticProps.DEVICE_NAME_LOG + TestDeviceInfo.getDeviceName());
				}

				// attach screenshot
				AllureAttachment.attachScreenshot(AllureScreenshotType.SUCCESS.value());

				// attach app xml tree
				if (ConfigProps.ATTACH_APP_XMLTREE_LOGS) {
					AllureAttachment.attachAppXMLTree();
				}

				// attach proxy logs
				if (ConfigProps.ATTACH_APP_PROXY_LOGS) {
					AllureAttachment.attachProxyResults();
				}
			}
		} catch (Exception e) {
			Logger.logConsoleMessage("Failed to attach test artifacts.");
			e.printStackTrace();
		}

		try {
			DriverManager.stopAppiumDriver();
		} catch (Exception e) {
			Logger.logConsoleMessage("Failed to stop driver.");
		}

		try {
			if (result.isSuccess()) {
				// add the test passed id
				TestIDs.addPassedTest(testID.get());
			} else {
				// check if the test should be re-executed based on retry logic
				if (result.getMethod().getRetryAnalyzer() != null && ConfigProps.RERUN_ON_FAILURE) {
					TestListeners testRetryAnalyzer = (TestListeners) result.getMethod().getRetryAnalyzer();
					if (testRetryAnalyzer.getCount(result.getMethod(), result.getAttribute(RUN_PROPS)).intValue() > 0) {
						result.setStatus(ITestResult.FAILURE);
					} else {
						failedCases.addResult(result, result.getMethod());
					}
				}
			}
		} catch (Exception e) {
			Logger.logConsoleMessage("Failed to log test id to results.");
			e.printStackTrace();
		}

		try {
			AppLib.setIncludedGroup(result.getTestContext().getIncludedGroups());
		} catch (Exception e) {
			Logger.logConsoleMessage("Failed to set Included Group.");
			e.printStackTrace();
		}
		
		//Add condition because it throws an error for local run "MQE PSQL lab db connection failed" & clearAppCache method only support for Android
		if (ConfigProps.RUN_AS_FACTORY && TestRun.isAndroid()) {
			try {
				List<String> testGroups = Arrays.asList(result.getMethod().getGroups());
				for (String group : testGroups) {
					if (group.equals(TVE)) {
						Logger.logConsoleMessage("Clearing Device cache.");
						LabDeviceManager.clearAppCache(LabDeviceManager.getDeviceMachineIPAddress(TestDeviceInfo.getDeviceID()), TestRun.getAppPackageID());
						Logger.logConsoleMessage("Device cache is now Clear.");
					}
				}
			} catch (Exception e) {
				Logger.logConsoleMessage("Failed to clear device cache.");
			}
		}

		// Creates a HashMap of test name executed and respective device id pair values where it was executed.
		if (ConfigProps.RUN_AS_FACTORY) {
			try {
				if (TestRun.isPhone()) {
					setSuccessfulPhoneTest(testName.get(), TestDeviceInfo.getDeviceID());
				} else {
					setSuccessfulTabletTest(testName.get(), TestDeviceInfo.getDeviceID());
				}
			} catch (Exception e) {
				e.printStackTrace();
				Logger.logConsoleMessage("Failed to add test name device id pair.");
			}
		}

    }

    @Override
    public void onTestFailure(final ITestResult result) {
		try {
			Logger.logConsoleMessage("======FAILURE======");
			Logger.logConsoleMessage("Test: " + result.getInstanceName() + "." + result.getName());

			if (DriverManager.getAppiumDriver() != null && DriverManager.getAppiumDriver().getSessionId() != null) {
				Logger.logConsoleMessage(StaticProps.MOBILE_OS_LOG + TestRun.getMobileOS());
				Logger.logConsoleMessage("Device Category: " + TestRun.getDeviceCategory().toString());
				Logger.logConsoleMessage("Grid Session IP: " + gridSessionIP.get());
				if (ConfigProps.RUN_AS_FACTORY) {
					Logger.logConsoleMessage(StaticProps.DEVICE_NAME_LOG + TestDeviceInfo.getDeviceName());
				}

				// attach screenshot
				AllureAttachment.attachScreenshot(AllureScreenshotType.FAILURE.value());

				// attach app xml tree
				if (ConfigProps.ATTACH_APP_XMLTREE_LOGS) {
					AllureAttachment.attachAppXMLTree();
				}

				// attach proxy logs
				if (ConfigProps.ATTACH_APP_PROXY_LOGS) {
					AllureAttachment.attachProxyResults();
				}

				// attach logcat
				if (ConfigProps.ATTACH_DEVICE_LOGS) {
					AllureAttachment.attachDeviceLog();
				}
				
				// attach proxy server log only support in Lab
				if (ConfigProps.RUN_AS_FACTORY) {
					AllureAttachment.attachBMPProxyLog();
				}
			}
		} catch (Exception e) {
			Logger.logConsoleMessage("Failed to attach test artifacts.");
			e.printStackTrace();
		}

		try {
			DriverManager.stopAppiumDriver();
		} catch (Exception e) {
			Logger.logConsoleMessage("Failed to stop driver.");
		}

		try {
			if (!result.isSuccess()) {
				// add the test passed id
				TestIDs.addFailedTest(testID.get());
			} else {
				// check if the test should be re-executed based on retry logic
				if (result.getMethod().getRetryAnalyzer() != null && ConfigProps.RERUN_ON_FAILURE) {
					TestListeners testRetryAnalyzer = (TestListeners) result.getMethod().getRetryAnalyzer();
					if (testRetryAnalyzer.getCount(result.getMethod(), result.getAttribute(RUN_PROPS)).intValue() > 0) {
						result.setStatus(ITestResult.FAILURE);
					} else {
						failedCases.addResult(result, result.getMethod());
					}
				}
			}
		} catch (Exception e) {
			Logger.logConsoleMessage("Failed to log test id to results.");
			e.printStackTrace();
		}
		
		//Add condition because it throws an error for local run "MQE PSQL lab db connection failed" & clearAppCache method only support for Android
		if (ConfigProps.RUN_AS_FACTORY && TestRun.isAndroid()) {
			try {
				List<String> testGroups = Arrays.asList(result.getMethod().getGroups());
				for (String group : testGroups) {
					if (group.equals(TVE)) {
						Logger.logConsoleMessage("Clearing Device cache.");
						LabDeviceManager.clearAppCache(LabDeviceManager.getDeviceMachineIPAddress(TestDeviceInfo.getDeviceID()), TestRun.getAppPackageID());
						Logger.logConsoleMessage("Device cache is now Clear.");
					}
				}
			} catch (Exception e) {
				Logger.logConsoleMessage("Failed to clear device cache.");
			}
		}
    }

    @Override
    public void onTestSkipped(final ITestResult result) {
		try {
			Logger.logConsoleMessage("======SKIPPED======");
			Logger.logConsoleMessage("Test: " + result.getInstanceName() + "." + result.getName());
			
			if (DriverManager.getAppiumDriver() != null && DriverManager.getAppiumDriver().getSessionId() != null) {
				Logger.logConsoleMessage(StaticProps.MOBILE_OS_LOG + TestRun.getMobileOS());
				Logger.logConsoleMessage("Device Category: " + TestRun.getDeviceCategory().toString());
				Logger.logConsoleMessage("Grid Session IP: " + gridSessionIP.get());
				if (ConfigProps.RUN_AS_FACTORY) {
					Logger.logConsoleMessage(StaticProps.DEVICE_NAME_LOG + TestDeviceInfo.getDeviceName());
				}

				// attach app xml tree
				if (ConfigProps.ATTACH_APP_XMLTREE_LOGS) {
					AllureAttachment.attachAppXMLTree();
				}

				// attach proxy logs
				if (ConfigProps.ATTACH_APP_PROXY_LOGS) {
					AllureAttachment.attachProxyResults();
				}
			}
		} catch (Exception e) {
			Logger.logConsoleMessage("Failed to attach test artifacts.");
			e.printStackTrace();
		}

		try {
			DriverManager.stopAppiumDriver();
		} catch (Exception e) {
			Logger.logConsoleMessage("Failed to stop driver.");
		}
		TestIDs.addSkippedTest(testID.get());
    }
    
    public boolean retry(ITestResult result) {
		boolean retry = false;
		if (ConfigProps.RERUN_ON_FAILURE) {
			if (getCount(result.getMethod(), result.getAttribute(RUN_PROPS)).intValue() > 0) {
			    Logger.logConsoleMessage("RETRY TEST: " + result.getInstanceName() + "." + result.getName());
			    getCount(result.getMethod(), result.getAttribute(RUN_PROPS)).decrementAndGet();
			    retry = true;
		    } else {
			    Logger.logConsoleMessage("RETRY COMPLETE: " + result.getInstanceName() + "." + result.getName());
		    }
		}
		return retry;
    }
    
    @SuppressWarnings("unused")
	private void teardownTest(ITestResult result) {
    	try {
    		String status = result.isSuccess() ? "SUCCESS" : "FAILURE";
			Logger.logConsoleMessage("======" + status + "======");
			Logger.logConsoleMessage("Test: " + result.getInstanceName() + "." + result.getName());
			
    		if (DriverManager.getAppiumDriver() != null && DriverManager.getAppiumDriver().getSessionId() != null) {
    			Logger.logConsoleMessage(StaticProps.MOBILE_OS_LOG + TestRun.getMobileOS());
    			Logger.logConsoleMessage("Device Category: " + TestRun.getDeviceCategory().toString());
    			Logger.logConsoleMessage("Grid Session IP: " + gridSessionIP.get());
    			if (ConfigProps.RUN_AS_FACTORY) {
    			    Logger.logConsoleMessage(StaticProps.DEVICE_NAME_LOG + TestDeviceInfo.getDeviceName());
    			}
    			
    			// attach screenshot
				AllureAttachment.attachScreenshot(AllureScreenshotType.FAILURE.value());
				AllureAttachment.attachScreenshot(AllureScreenshotType.SUCCESS.value());
				AllureAttachment.attachScreenshot(AllureScreenshotType.IN_PROGRESS.value());
    			
    	        // attach app xml tree
    			if (ConfigProps.ATTACH_APP_XMLTREE_LOGS) {
    				AllureAttachment.attachAppXMLTree();
    			}
    			
    			// attach proxy logs
    			if (ConfigProps.ATTACH_APP_PROXY_LOGS) {
    	            AllureAttachment.attachProxyResults();
    			}
    		}
    	} catch (Exception e) {
    		Logger.logConsoleMessage("Failed to attach test artifacts.");
    		e.printStackTrace();
    	}

    	try {
    		DriverManager.stopAppiumDriver();
    	} catch (Exception e) {
    		Logger.logConsoleMessage("Failed to stop driver.");
    	}
    	
    	try {
    		if (result.isSuccess()) {
    			// add the test passed id
                TestIDs.addPassedTest(testID.get());
    		} else {
    			// check if the test should be re-executed based on retry logic
            	if (result.getMethod().getRetryAnalyzer() != null && ConfigProps.RERUN_ON_FAILURE) {
        		    TestListeners testRetryAnalyzer = (TestListeners) result.getMethod().getRetryAnalyzer();
        		    if (testRetryAnalyzer.getCount(result.getMethod(), result.getAttribute(RUN_PROPS)).intValue() > 0) {
        		        result.setStatus(ITestResult.FAILURE);
        		    } else {
        		        failedCases.addResult(result, result.getMethod());
        		    }
        		} else {
					// add the test failed id
					TestIDs.addFailedTest(testID.get());
				}
    		}
    	} catch (Exception e) {
    		Logger.logConsoleMessage("Failed to log test id to results.");
    		e.printStackTrace();
    	}
    }
    
    private AtomicInteger getCount(ITestNGMethod result, Object attribute) {
	    String id = getId(result, attribute);
	    if (retries.get(id) == null) {
	      retries.put(id, new AtomicInteger(MAX_COUNT));
	    }
	    return retries.get(id);
    }
	
	private String getId(ITestNGMethod result, Object attribute) {
		return result.getConstructorOrMethod().getMethod().toGenericString() 
	    		+ ":" + ArrayUtils.toString(attribute);
	}

	public static synchronized String getTestName() {
    	return testName.get();
	}
	
	public static synchronized String getDependencyTestName() {
    	return dependencyTestName.get();
	}
	
	public static boolean isDependableOnMethod() {
		return getDependencyTestName() != null;
	}

	private static synchronized Map<String, String> getSuccessfulPhoneTest() {
    	return successfulPhoneTest.get();
	}

	private static synchronized void setSuccessfulPhoneTest(String testName, String deviceId) {
    	successfulPhoneTest.get().put(testName, deviceId);
	}

	private static synchronized void setSuccessfulTabletTest(String testName, String deviceId) {
    	successfulTabletTest.get().put(testName, deviceId);
	}

	private static synchronized Map<String, String> getSuccessfulTabletTest() {
		return successfulTabletTest.get();
	}

	public static String getDeviceIdToRunDependableTest(String testName) {
		final String[] deviceID = {""};
    	if (TestRun.isPhone()) {
			getSuccessfulPhoneTest().forEach((key, value) -> {
				if (key.equalsIgnoreCase(testName)) {
					deviceID[0] = value;
				}
			});
		} else {
    		getSuccessfulTabletTest().forEach((key, value) -> {
				if (key.equalsIgnoreCase(testName)) {
					deviceID[0] = value;
				}
			});
		}
		return deviceID[0];
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

	}

	/**
	 * Set tests that are marked as skipped and thrown an exception as failed.
	 *
	 * @param method
	 * @param testResult
	 */
	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		IRetryAnalyzer retryAnalyzer = testResult.getMethod().getRetryAnalyzer();
		if (retryAnalyzer != null) {
			if (testResult.getStatus() == ITestResult.SKIP && testResult.getThrowable() != null && !testResult.getThrowable().toString().contains("Test Skipped due to")) {
				testResult.setStatus(ITestResult.FAILURE);
				Reporter.setCurrentTestResult(testResult);
			}
			if (method.isTestMethod() && testResult.getStatus() == ITestResult.FAILURE && testResult.getThrowable() != null) {
				if (testResult.getThrowable().toString().contains("WebDriverException")) {
					Logger.logMessage("<--- Test mark as skipped due to WebDriverException. Might be MQE lab issue, Please check console log to debug. --->");
					testResult.setStatus(ITestResult.SKIP);
					Reporter.setCurrentTestResult(testResult);
				} else if (testResult.getThrowable().toString().contains("NoSuchSessionException")) {
					Logger.logMessage("<--- Test mark as skipped due to NoSuchSessionException. Might be MQE lab issue, Please check console log to debug. --->");
					testResult.setStatus(ITestResult.SKIP);
					Reporter.setCurrentTestResult(testResult);
				} else if (testResult.getThrowable().toString().contains("ContentException")) {
					Logger.logMessage("<--- Test mark as skipped due to ContentException. Might be a DP issue. Please check with content team --->");
					testResult.setStatus(ITestResult.SKIP);
					Reporter.setCurrentTestResult(testResult);
				} else if (testResult.getThrowable().toString().contains("InternetConnectionException")) {
					Logger.logMessage("<--- Test mark as skipped due to InternetConnectionException. Might be a network issue. Please check your internet connection --->");
					testResult.setStatus(ITestResult.SKIP);
					Reporter.setCurrentTestResult(testResult);
				} else if (testResult.getThrowable().toString().contains("FailedTVELoginException")) {
					Logger.logMessage("<--- Test mark as skipped due to FailedTVELoginException.TVE Login Error OR hidden backend issue --->");
					testResult.setStatus(ITestResult.SKIP);
					Reporter.setCurrentTestResult(testResult);
				}
			} 
		} 
	}
}
