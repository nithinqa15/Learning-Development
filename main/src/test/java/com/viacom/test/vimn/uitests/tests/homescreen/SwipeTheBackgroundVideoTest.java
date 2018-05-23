package com.viacom.test.vimn.uitests.tests.homescreen;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class SwipeTheBackgroundVideoTest extends BaseTest {

	// Declare page objects/actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AlertsChain alertsChain;
	Home home;
	HomePropertyFilter propertyFilter;

	// Declare data
    String firstSeriesTitle;
    String secondSeriesTitle;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public SwipeTheBackgroundVideoTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {

		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		alertsChain = new AlertsChain();
		home = new Home();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        PropertyResults propertyResults = propertyFilter.propertyFilter();
        PropertyResult firstSeries = propertyResults.getFirstProperty();
        firstSeriesTitle = firstSeries.getPropertyTitle();
        PropertyResult secondSeries = propertyResults.get(1);
        secondSeriesTitle = secondSeries.getPropertyTitle();

    }

	@TestCaseId("11927")
	@Features(GroupProps.HOME_SCREEN)
	@Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN })
	@Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
	public void swipeTheBackgroundVideoAndroidTest() {

		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();

		home.flickRightToSeriesThumb(secondSeriesTitle, 1);
		home.flickLeftToSeriesThumb(firstSeriesTitle, 1);
	}

	@TestCaseId("11927")
	@Features(GroupProps.HOME_SCREEN)
	@Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN })
	@Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
	public void swipeTheBackgroundVideoiOSTest() {

		splashChain.splashAtRest();
		alertsChain.dismissAlerts();
		chromecastChain.dismissChromecast();

		home.flickRightToSeriesThumb(secondSeriesTitle, 1);
		home.flickLeftToSeriesThumb(firstSeriesTitle, 1);

	}


}
