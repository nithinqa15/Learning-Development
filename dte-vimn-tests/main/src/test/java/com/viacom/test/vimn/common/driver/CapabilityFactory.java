package com.viacom.test.vimn.common.driver;

import java.util.ArrayList;
import java.util.List;

import com.viacom.test.core.lab.LabDeviceManager;

import com.viacom.test.vimn.common.listeners.TestListeners;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;
import com.viacom.test.core.driver.DriverFactory;
import com.viacom.test.core.props.MQEDriverCaps;
import com.viacom.test.core.props.ProxyType;
import com.viacom.test.core.proxy.ProxyFactory;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.common.util.Config.ConfigProps;

public class CapabilityFactory {

    public static void setCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // retrieve options from config file
        String platform = "MAC";
        String appPackageID = "";
        String androidLaunchActivity = "";

        // set the app capability
        appPackageID = TestRun.getAppPackageID();
        androidLaunchActivity = TestRun.getAndroidLaunchActivity();

        // set desired capabilities
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, TestRun.getMobileOS().value());
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "null"); //required but not used
        capabilities.setCapability(CapabilityType.PLATFORM, platform);
        capabilities.setCapability(CapabilityType.BROWSER_NAME, TestRun.getMobileOS().value());
        String serverCommandTimeout = ConfigProps.SERVER_COMMAND_TIMEOUT;
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, serverCommandTimeout);
        capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, "true");
        capabilities.setCapability(MQEDriverCaps.MQE_PROXY_TYPE.value(), ProxyType.BMP_REST.value());

        // set extra capabilities needed for Android
        if (TestRun.isAndroid()) {
            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackageID);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, androidLaunchActivity);
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "null");
            // TODO - GF 14/09/2017 - Remove excluded devices when https://jira.mtvi.com/browse/QELAB-460 is solved.
            capabilities.setCapability("excludedDevices", "RQ3002VHB0,"
                    + "7a8346c522b718cf,"
                    + "ce0517150998e02803,"
                    + "HT72V0200375,"
                    + "6128001080,"
                    + "RQ3002JDTG,"
                    + "637f4185e2366709,"
                    + "ZX1G22X4P7");
        }

        // set extra capabilities for iOS
        // [XCUITest] The capabilities 'autoAcceptAlerts' and 'autoDismissAlerts' do not work for XCUITest-based tests.
        if (TestRun.isIos()) { 
            capabilities.setCapability(MobileCapabilityType.APP, appPackageID);
            capabilities.setCapability("screenshotWaitTimeout", ConfigProps.IOS_SCREENSHOT_WAIT);
            capabilities.setCapability("sendKeyStrategy", "setValue");
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "null");
            capabilities.setCapability("processArguments", "{\"args\": [\"--ui-tests\"]}");
            capabilities.setCapability("automationName", "XCUITest");
            capabilities.setCapability("setTargetDeviceVersions", "10.1.1,10.2,10.0.1,10.0.2,10.3.2,10.2,1,10.3.3");
            //https://jira.mtvi.com/browse/QELAB-746 (iPhoneX as well), https://jira.mtvi.com/browse/QELAB-841
            capabilities.setCapability("excludedDevices", "111daca3603a5278df0416cd8def074711628ebb,"
            		+ "e33eb2653f3e02ad66b3bbb8443b186136d2e8e8,"
            		+ "5209fefd2d860e92ce530ce0299134ff6b868a18,"
            		+ "931e0f87e19b52ad837134316647a17edd81ec27,"
            		+ "45372f31a338439e3978162945655792185dc6f2,"
            		+ "69676371d72601d914b7b805f4dd365aa30ca794,"
            		+ "fbaef5bb9fd923ec03df1c45e53a67b5a6790db0,"
            		+ "41ee50a09abc282f8435c2b06dbf7212c2ae24e1,"
            		+ "82724342c7a40df3063fb08e536549cff94a088a,"
            		+ "0ba057171813b9485adc73d4884cf5a102ef71ec,"
            		+ "4b10ed18b66dbfd6f8044fe95c215f87d9a247c0,"
            		+ "5021a01668350dc347ba522e71c7546aa3f0e114"); //Excluded iOS 9.x devices // https://jira.mtvi.com/browse/QELAB-862
        }

        // Set extra capability for tests that depends on a previous test execution
        if (TestListeners.isDependableOnMethod()) {
            capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
            if (ConfigProps.RUN_AS_FACTORY) {
                //Forces execution in the same device as dependency method ran
                String testName = TestListeners.getDependencyTestName();
                String deviceId = TestListeners.getDeviceIdToRunDependableTest(testName);
                capabilities.setCapability("excludedDevices", LabDeviceManager.getAllDeviceIDs(TestRun.getMobileOS()).remove(deviceId));
            }
        }

        // enable lab app install
        if (ConfigProps.RUN_AS_FACTORY) {
            DriverFactory.enableLabAppInstall(false, appPackageID);
        }

        // set the local debug proxy and region proxy
        if (!ConfigProps.RUN_AS_FACTORY) {
        	if (ProxyFactory.isProxyPortInUse(ConfigProps.DEBUG_PROXY_PORT)) {
             	TestRun.killProxyPortInUse(ConfigProps.DEBUG_PROXY_PORT);
            }
            DriverFactory.setLocalDebugProxyPort(ConfigProps.DEBUG_PROXY_PORT);
        }

        List<String> allRequestFilters = new ArrayList<>();
        List<String> allResponseFilters = new ArrayList<>();

        allRequestFilters.add(ProxyUtils.addRegionHeader(LocaleDataFactory.getRegionIPAddress()));

        if (TestListeners.getTestName().contains("UnsupportedCountry")) {
            allResponseFilters.add(ProxyUtils.rewriteCountryCode());
        }
        
        //Set rewrite testing vs Live
        if (TestRun.isLiveContentDomain()) {
        	allRequestFilters.add(ProxyUtils.setURLRewrite("testing.api.playplex.viacom.vmn.io","api.playplex.viacom.com", "api.playplex.viacom.com/geo-info"));
        } else {
        	allRequestFilters.add(ProxyUtils.setURLRewrite("api.playplex.viacom.com","testing.api.playplex.viacom.vmn.io", "api.playplex.viacom.com/geo-info"));
        }

        allResponseFilters.add(ProxyUtils.disableChromecast());
        //TO-DO - Add condition for DisplayLiveTVForAllUsers test cases
        allResponseFilters.add(ProxyUtils.disableDisplayLiveTVForAllUsers());

        DriverFactory.setRequestRewritesAtStartup(allRequestFilters);
        DriverFactory.setResponseRewritesAtStartup(allResponseFilters);

        // ensure the devices in the lab have an active connection at driver startup
        DriverFactory.enableProxyCheckAtAppiumStartup();

        // start the session
        DriverFactory.initiateAppiumDriver(TestRun.getMobileOS(), TestRun.getDeviceCategory(), capabilities);
    }
}