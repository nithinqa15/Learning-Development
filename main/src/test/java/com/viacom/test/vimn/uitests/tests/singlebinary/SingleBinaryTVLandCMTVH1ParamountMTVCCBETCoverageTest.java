package com.viacom.test.vimn.uitests.tests.singlebinary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.ProviderList;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SingleBinaryTVLandCMTVH1ParamountMTVCCBETCoverageTest extends BaseTest {
	// Declare page objects/actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AppDataFeed appDataFeed;
	AlertsChain alertsChain;
	ProviderList providerList;

	// Declare data
	Map<String, String> expectedMainFeedAppValuesMap;
	List<String> expectedProviderList;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public SingleBinaryTVLandCMTVH1ParamountMTVCCBETCoverageTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		appDataFeed = new AppDataFeed();
		chromecastChain = new ChromecastChain();
		alertsChain = new AlertsChain();
		providerList = new ProviderList();

		expectedMainFeedAppValuesMap = new HashMap<String, String>();
		expectedMainFeedAppValuesMap.put("backgroundServiceVideoEnabled", "false");
		expectedMainFeedAppValuesMap.put("shortForm", "true");
		expectedMainFeedAppValuesMap.put("allShowsEnabled", "true");
		expectedMainFeedAppValuesMap.put("tveAuthenticationEnabled", "true");
		//expectedMainFeedAppValuesMap.put("appsFlyerEnabled", "false"); //Sid will check with API team (BET special)

		expectedProviderList = new ArrayList<String>();
		expectedProviderList.add("AT&T U-verse");
		expectedProviderList.add("Cox");
		expectedProviderList.add("DIRECTV");
		expectedProviderList.add("MediaCom");
		expectedProviderList.add("Optimum");
		expectedProviderList.add("RCN");
		expectedProviderList.add("Spectrum");
		expectedProviderList.add("Suddenlink");
		expectedProviderList.add("Verizon Fios");
		expectedProviderList.add("WOW!");
		//expectedProviderList.add("XFINITY"); //Elvis disabled
		if (TestRun.isIos()) {
			expectedProviderList.add("Frontier Communications"); //TVE live picker
		} else {
			expectedProviderList.add("Adobe"); //TVE stage picker
		}

	}

	@TestCaseId("44039-44040-44042")
	@Features(GroupProps.SINGLE_BINARY)
	@Test(groups = { GroupProps.FULL, GroupProps.SINGLE_BINARY })
	@Parameters({ ParamProps.ANDROID, ParamProps.CMT_APP, 
				  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP, 
				  ParamProps.MTV_DOMESTIC_APP,ParamProps.TVLAND_APP, 
				  ParamProps.CC_DOMESTIC_APP,ParamProps.VH1_APP })
	public void singleBinaryTVLandCMTVH1ParamountMTVCCBETCoverageAndroidTest() {

		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();
		
		SoftAssert observable = new SoftAssert();
    
		Map<String,String> actualMap = appDataFeed.getActualMainFeedAppValues(expectedMainFeedAppValuesMap);	
		for (String key : expectedMainFeedAppValuesMap.keySet()) {
			String actualValue = actualMap.get(key);
			String expectedValue = expectedMainFeedAppValuesMap.get(key);
			Logger.logMessage("Testing the key: " + key);
			Logger.logMessage("Expected Value: " + expectedValue + " || Actual Value: " + actualValue);
			observable.assertEquals(actualValue, expectedValue);
		}
		
		List<String> actualproviders = providerList.getActualProviders();
		Logger.logMessage("Testing Providers:");
		Logger.logMessage("Expected Providers: " + expectedProviderList);
		Logger.logMessage("Actual Providers: " + actualproviders);
		for (String provider : expectedProviderList) {
			observable.assertTrue(actualproviders.contains(provider), provider + " is not present");
		}
		
		observable.assertAll();
	}
	
	@TestCaseId("44039-44040-44042-44998") 
	@Features(GroupProps.SINGLE_BINARY)
	@Test(groups = { GroupProps.FULL, GroupProps.SINGLE_BINARY })
	@Parameters({ ParamProps.IOS, ParamProps.CMT_APP, 
				  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP, 
				  ParamProps.MTV_DOMESTIC_APP,ParamProps.TVLAND_APP, 
				  ParamProps.CC_DOMESTIC_APP,ParamProps.VH1_APP })
	public void singleBinaryTVLandCMTVH1ParamountMTVCCBETCoverageiOSTest() {

		splashChain.splashAtRest();
		alertsChain.dismissAlerts();
		
		SoftAssert observable = new SoftAssert();
    
		Map<String,String> actualMap = appDataFeed.getActualMainFeedAppValues(expectedMainFeedAppValuesMap);	
		for (String key : expectedMainFeedAppValuesMap.keySet()) {
			String actualValue = actualMap.get(key);
			String expectedValue = expectedMainFeedAppValuesMap.get(key);
			Logger.logMessage("Testing the key: " + key);
			Logger.logMessage("Expected Value: " + expectedValue + " || Actual Value: " + actualValue);
			observable.assertEquals(actualValue, expectedValue);
		}
		
		List<String> actualproviders = providerList.getActualProviders();
		Logger.logMessage("Testing Providers:");
		Logger.logMessage("Expected Providers: " + expectedProviderList);
		Logger.logMessage("Actual Providers: " + actualproviders);
		for (String provider : expectedProviderList) {
			observable.assertTrue(actualproviders.contains(provider), provider + " is not present");
		}
		
		observable.assertAll();
	}
}
