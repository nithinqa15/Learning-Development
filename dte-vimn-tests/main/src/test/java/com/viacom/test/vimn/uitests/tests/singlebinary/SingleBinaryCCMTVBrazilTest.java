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
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.ProviderList;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SingleBinaryCCMTVBrazilTest extends BaseTest {
	// Declare page objects/actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AppDataFeed appDataFeed;
	ProviderList providerList;

	// Declare data
	Map<String, String> expectedMainFeedAppValuesMap;
	List<String> expectedProviderList;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public SingleBinaryCCMTVBrazilTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		appDataFeed = new AppDataFeed();
		chromecastChain = new ChromecastChain();
		providerList = new ProviderList();

		expectedMainFeedAppValuesMap = new HashMap<String, String>();
		expectedMainFeedAppValuesMap.put("backgroundServiceVideoEnabled", "true");
		expectedMainFeedAppValuesMap.put("shortForm", "true");
		expectedMainFeedAppValuesMap.put("allShowsEnabled", "true");
		expectedMainFeedAppValuesMap.put("tveAuthenticationEnabled", "true");
		//expectedMainFeedAppValuesMap.put("appsFlyerEnabled", "false"); Sid will check with API team

		expectedProviderList = new ArrayList<String>();
		expectedProviderList.add("Claro");
		expectedProviderList.add("NET");
		expectedProviderList.add("Sky");

	}
	@TestCaseId("44046-44051")
	@Features(GroupProps.SINGLE_BINARY)
	@Test(groups = { GroupProps.FULL, GroupProps.SINGLE_BINARY })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, ParamProps.CC_INTL_APP })
	public void singleBinaryCCMTVBrazilAndroidTest() {

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
	
	@TestCaseId("44046-44051")
	@Features(GroupProps.SINGLE_BINARY)
	@Test(groups = { GroupProps.FULL, GroupProps.SINGLE_BINARY })
	@Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, ParamProps.CC_INTL_APP })
	public void singleBinaryCCMTVBraziliOSTest() {

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
}

