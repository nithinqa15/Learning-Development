package com.viacom.test.vimn.uitests.tests.homescreen;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoEventContentException;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class FightsContentHomeScreenTest extends BaseTest {

    //declare pageobjects
    SplashChain splashChain;
    Home home;
    Series series;
    ChromecastChain chromecastChain;
    AlertsChain alertsChain;
    HomePropertyFilter propertyFilter;

    // declare data
    String firstFightTitle;
    Integer numberOfSwipes;
    Boolean hasExtras;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public FightsContentHomeScreenTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        home = new Home();
        series = new Series();
        chromecastChain = new ChromecastChain();
        alertsChain = new AlertsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        PropertyResults fightResults = propertyFilter.withFight().propertyFilter();
        PropertyResult firstFight = fightResults.getFirstProperty();
        firstFightTitle = firstFight.getPropertyTitle();
        Logger.logConsoleMessage("First Fight title on homescreen : " + firstFightTitle);
        hasExtras = firstFight.hasClips();
        Logger.logConsoleMessage("First Fight title has extras : " + hasExtras);
        numberOfSwipes = firstFight.getNumOfSwipes();

    }

    @TestCaseId("48075")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.FIGHTS_CONTENT})
    @Parameters({ ParamProps.ANDROID, ParamProps.PARAMOUNT_APP })
    public void fightsContentHomeScreenAndroidTest() {

		if (firstFightTitle != null) {
			splashChain.splashAtRest();
			chromecastChain.dismissChromecast();

			home.flickRightToSeriesThumb(firstFightTitle, numberOfSwipes);

			if (hasExtras) {
				home.watchExtrasBtn().waitForVisible();
				home.specialBtn().waitForVisible();
				home.specialBtn().waitForVisible().tap();
				series.specialBtn().waitForVisible();
				series.watchXtrasBtn().waitForVisible();
			} else {
				home.specialBtn().waitForVisible();
				home.specialBtn().waitForVisible().tap();
				series.specialBtn().waitForNotPresentOrVisible();
				series.watchXtrasBtn().waitForNotPresentOrVisible();
			}
		} else {
			String message = "There are no fight content on: " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoEventContentException(message);
		}
	}

    @TestCaseId("48075")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN , GroupProps.FIGHTS_CONTENT})
    @Parameters({ ParamProps.IOS, ParamProps.PARAMOUNT_APP })
    public void fightsContentHomeScreeniOSTest() {

        if (firstFightTitle != null) {
            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            alertsChain.dismissAlerts();

            home.flickRightToSeriesThumb(firstFightTitle, numberOfSwipes);

            if (hasExtras) {
                home.watchExtrasBtn().waitForVisible();
                home.specialBtn().waitForVisible();
                home.specialBtn().waitForVisible().tap();
                series.specialBtn().waitForVisible();
                series.watchXtrasBtn().waitForVisible();
            } else {
                home.specialBtn().waitForVisible();
                home.specialBtn().waitForVisible().tap();
                series.specialBtn().waitForNotPresentOrVisible();
                series.watchXtrasBtn().waitForNotPresentOrVisible();
            }
        } else {
            String message = "There are no fight content on: " + TestRun.getAppName() + " " + TestRun.getLocale()
                    + " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
            Logger.logMessage(message);
            throw new NoEventContentException(message);
        }
    }
}
