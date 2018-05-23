package com.viacom.test.vimn.uitests.tests.omniture.suiteID;

import com.viacom.test.vimn.common.proxy.ProxyLogUtils;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureDevSuiteIDTest extends BaseTest {

    SplashChain splashChain;
    AlertsChain alertschain;
    ChromecastChain chromecastChain;
    String adbMobileConfigURL;
    String expectedRsids = "viaplayplexdev";
    String actualRsids ;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureDevSuiteIDTest (String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
    }

    @TestCaseId("35867")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS})
    public void omnitureDevSuiteIDAndroidTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        
        if (ProxyLogUtils.isRequestUrlFired(Config.StaticProps.ADB_MOBILE_CONFIG)) {
        	
        	adbMobileConfigURL = ProxyLogUtils.getRequestURL(Config.StaticProps.ADB_MOBILE_CONFIG);
            Logger.logMessage("Checked Omniture Suite ID in: " + adbMobileConfigURL);
            
            JSONObject configObject = ProxyLogUtils.parseResponse(adbMobileConfigURL);
            JSONObject analyticsObject = (JSONObject) configObject.get("analytics");
            actualRsids = (String) analyticsObject.get("rsids");
            
            Assert.assertTrue(expectedRsids.equalsIgnoreCase(actualRsids), "Expected: " + expectedRsids + " but " + "Actual: " + actualRsids);
        } else {
        	throw new SkipException("<---------Skipped ? Not a first launch------>");
        }
    }   
    @TestCaseId("35867")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.OMNITURE })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void omnitureDevSuiteIDiOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        
        if (ProxyLogUtils.isRequestUrlFired(Config.StaticProps.ADB_MOBILE_CONFIG)) {
        	
        	adbMobileConfigURL = ProxyLogUtils.getRequestURL(Config.StaticProps.ADB_MOBILE_CONFIG);
            Logger.logMessage("Checked Omniture Suite ID in: " + adbMobileConfigURL);
            
            JSONObject configObject = ProxyLogUtils.parseResponse(adbMobileConfigURL);
            JSONObject analyticsObject = (JSONObject) configObject.get("analytics");
            actualRsids = (String) analyticsObject.get("rsids");
            
            Assert.assertTrue(expectedRsids.equalsIgnoreCase(actualRsids), "Expected: " + expectedRsids + " but " + "Actual: " + actualRsids);
        } else {
        	throw new SkipException("<---------Skipped ? Not a first launch------>");
        }
    }
}