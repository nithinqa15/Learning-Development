package com.viacom.test.vimn.uitests.tests.general;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.AllShows;
import com.viacom.test.vimn.uitests.pageobjects.DevSettingsMenu;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import io.appium.java_client.android.Connection;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class NoConnectionErrorScreenTest extends BaseTest {

	// Declare page objects/actions
	Home home;
    SplashChain splashChain;
    DevSettingsMenu deviceSettingsMenu;
    AllShows allShows;
    AlertsChain alertsChain;
    HomePropertyFilter propertyFilter;
    
    //Declare data
    String firstSeriesTitle;
    
	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public NoConnectionErrorScreenTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		home = new Home();
	    splashChain = new SplashChain();
	    deviceSettingsMenu = new DevSettingsMenu();
	    allShows = new AllShows();
	    alertsChain = new AlertsChain();
	    propertyFilter = new HomePropertyFilter(NON_PAGINATED);
	    
	    PropertyResults propertyResults = propertyFilter.propertyFilter();
        PropertyResult firstSeries = propertyResults.getFirstProperty();
        firstSeriesTitle = firstSeries.getPropertyTitle();
	}

	@TestCaseId("35983")
	@Features(GroupProps.GENERAL)
	@Test(groups = { GroupProps.FULL, GroupProps.GENERAL })
	@Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
	public void noConnectionErrorScreenAndroidTest() {
		
    	DriverManager.getAndroidDriver().setConnection(Connection.AIRPLANE);
		home.ohNoTxt().waitForVisible();
		home.youLostConnectionTxt().waitForVisible();
		home.retryBtn().waitForVisible();
    	DriverManager.getAndroidDriver().setConnection(Connection.WIFI);

	}
    
    @TestCaseId("35983")
	@Features(GroupProps.GENERAL)
	@Test(groups = { GroupProps.BROKEN, GroupProps.GENERAL })
	@Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
	public void noConnectionErrorScreeniOSTest() {
		
    	splashChain.splashAtRest();
    	alertsChain.dismissAlerts();
        
        //Open device settings, turn OFF WiFi & turn ON airplane mode
        home.seriesThumbBtn(firstSeriesTitle).waitForVisible().openDeviceSettingWindow(StaticProps.NORMAL_SCROLL);
        deviceSettingsMenu.enableAirplaneModeiOS();
        deviceSettingsMenu.disableWifiModeiOS();
        
        //Close device settings and verify No connection error screen
        home.seriesThumbBtn(firstSeriesTitle).closeDeviceSettingWindow(StaticProps.NORMAL_SCROLL);
        allShows.allShowsBtn().waitForVisible().tap();
        allShows.allShowsBackBtn().waitForVisible().tap().pause(StaticProps.MEDIUM_PAUSE);
        
        home.ohNoTxt().waitForVisible();
    	home.youLostConnectionTxt().waitForVisible();
        home.retryBtn().waitForVisible();
        
        //Open device settings, turn ON WiFi, turn OFF airplane mode and verify series title visible
        home.seriesThumbBtn(firstSeriesTitle).openDeviceSettingWindow(StaticProps.NORMAL_SCROLL);
        deviceSettingsMenu.enableWifiModeiOS();
        deviceSettingsMenu.disableAirplaneModeiOS();
        home.seriesThumbBtn(firstSeriesTitle).closeDeviceSettingWindow(StaticProps.NORMAL_SCROLL);
        home.seriesThumbBtn(firstSeriesTitle).waitForVisible();
	}
    
	@AfterMethod
	public void enableInternet() {
		if (TestRun.isAndroid()) {
			if (!DriverManager.getAndroidDriver().getConnection().equals(Connection.ALL)) {
				DriverManager.getAndroidDriver().setConnection(Connection.ALL);
			}
		}
	}
}
