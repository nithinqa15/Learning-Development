package com.viacom.test.vimn.uitests.tests.singlebinary;

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
import com.viacom.test.vimn.common.util.SingleBinary;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.ProviderList;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SingleBinaryMTVNorthTest extends BaseTest {
	// Declare page objects/actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AppDataFeed appDataFeed;
	ProviderList providerList;
	SingleBinary singleBinary;
	AlertsChain alertsChain;

	// Declare data
	Map<String, String> expectedMainFeedAppValuesMap;
	Map<String, String> expectedNoProviderValuesMap;
	List<String> expectedProviderList;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public SingleBinaryMTVNorthTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		appDataFeed = new AppDataFeed();
		chromecastChain = new ChromecastChain();
		providerList = new ProviderList();
		singleBinary = new SingleBinary();
		alertsChain = new AlertsChain();

		expectedMainFeedAppValuesMap = singleBinary.expectedMTVNorthMainFeedAppValuesMap(TestRun.getLocale());
	
		if (TestRun.getLocale().equals("no_no") || TestRun.getLocale().equals("da_dk") ) {
			expectedProviderList = singleBinary.expectedMTVNorthProviderList(TestRun.getLocale());
		} else {
			expectedNoProviderValuesMap = new HashMap<String, String>();
			expectedNoProviderValuesMap.put("status", "error");
			expectedNoProviderValuesMap.put("errorCode", "not_supported_locale");
			expectedNoProviderValuesMap.put("responseStatusCode", "400");
		}
	}

	@TestCaseId("44047")
	@Features(GroupProps.SINGLE_BINARY)
	@Test(groups = { GroupProps.FULL, GroupProps.SINGLE_BINARY })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP })
	public void singleBinaryMTVNorthAndroidTest() {

		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();

		SoftAssert observable = new SoftAssert();

		Map<String, String> actualMap = appDataFeed.getActualMainFeedAppValues(expectedMainFeedAppValuesMap);
		for (String key : expectedMainFeedAppValuesMap.keySet()) {
			String actualValue = actualMap.get(key);
			String expectedValue = expectedMainFeedAppValuesMap.get(key);
			Logger.logMessage("Testing the key: " + key);
			Logger.logMessage("Expected Value: " + expectedValue + " || Actual Value: " + actualValue);
			observable.assertEquals(actualValue, expectedValue);
		}

		if (TestRun.getLocale().equals("no_no") || TestRun.getLocale().equals("da_dk")) {
			List<String> actualproviders = providerList.getActualProviders();
			Logger.logMessage("Testing Providers:");
			Logger.logMessage("Expected Providers: " + expectedProviderList);
			Logger.logMessage("Actual Providers: " + actualproviders);
			for (String provider : expectedProviderList) {
				observable.assertTrue(actualproviders.contains(provider), provider + " is not present");
			}
		} else {
			Map<String, String> actualNoProviderMap = providerList.getNoProviderValues(expectedNoProviderValuesMap);
			for (String key : expectedNoProviderValuesMap.keySet()) {
				String actualValue = actualNoProviderMap.get(key);
				String expectedValue = expectedNoProviderValuesMap.get(key);
				Logger.logMessage("Testing the key: " + key);
				Logger.logMessage("Expected Value: " + expectedValue + " || Actual Value: " + actualValue);
				observable.assertEquals(actualValue, expectedValue);
			}
		}

		observable.assertAll();
	}
	
	@TestCaseId("44047")
	@Features(GroupProps.SINGLE_BINARY)
	@Test(groups = { GroupProps.FULL, GroupProps.SINGLE_BINARY })
	@Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP })
	public void singleBinaryMTVNorthiOSTest() {

		splashChain.splashAtRest();
		alertsChain.dismissAlerts();

		SoftAssert observable = new SoftAssert();

		Map<String, String> actualMap = appDataFeed.getActualMainFeedAppValues(expectedMainFeedAppValuesMap);
		for (String key : expectedMainFeedAppValuesMap.keySet()) {
			String actualValue = actualMap.get(key);
			String expectedValue = expectedMainFeedAppValuesMap.get(key);
			Logger.logMessage("Testing the key: " + key);
			Logger.logMessage("Expected Value: " + expectedValue + " || Actual Value: " + actualValue);
			observable.assertEquals(actualValue, expectedValue);
		}

		if (TestRun.getLocale().equals("no_no") || TestRun.getLocale().equals("da_dk")) {
			List<String> actualproviders = providerList.getActualProviders();
			Logger.logMessage("Testing Providers:");
			Logger.logMessage("Expected Providers: " + expectedProviderList);
			Logger.logMessage("Actual Providers: " + actualproviders);
			for (String provider : expectedProviderList) {
				observable.assertTrue(actualproviders.contains(provider), provider + " is not present");
			}
		} else {
			Map<String, String> actualNoProviderMap = providerList.getNoProviderValues(expectedNoProviderValuesMap);
			for (String key : expectedNoProviderValuesMap.keySet()) {
				String actualValue = actualNoProviderMap.get(key);
				String expectedValue = expectedNoProviderValuesMap.get(key);
				Logger.logMessage("Testing the key: " + key);
				Logger.logMessage("Expected Value: " + expectedValue + " || Actual Value: " + actualValue);
				observable.assertEquals(actualValue, expectedValue);
			}
		}

		observable.assertAll();
	}
}
