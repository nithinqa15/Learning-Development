package com.viacom.test.vimn.uitests.tests.allshows;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoEventContentException;
import com.viacom.test.vimn.common.filters.AllShowsPropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.AllShows;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class FightsContentAllShowsScreenTest extends BaseTest {
	
	SplashChain splashChain;
    AllShows shows;
    Series series;
    ChromecastChain chromecastChain;
    AlertsChain alertsChain;
    AllShowsPropertyFilter allShowsFilter;

    // declare data
    String firstFightTitle;
    Integer numberOfSwipes;
    Boolean hasExtras;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public FightsContentAllShowsScreenTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        shows = new AllShows();
        series = new Series();
        chromecastChain = new ChromecastChain();
        alertsChain = new AlertsChain();
        allShowsFilter = new AllShowsPropertyFilter(NON_PAGINATED);

        PropertyResults fightResults = allShowsFilter.withFight().propertyFilter();
        PropertyResult firstFight = fightResults.getFirstProperty();
        firstFightTitle = firstFight.getPropertyTitle();
        Logger.logConsoleMessage("First Fight title on AllShowsScreen : " + firstFightTitle);
        hasExtras = firstFight.hasClips();
        Logger.logConsoleMessage("First Fight title has extras : " + hasExtras);
        numberOfSwipes = firstFight.getNumOfSwipes();

    }

    @TestCaseId("48076")
    @Features(GroupProps.ALL_SHOWS)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.FIGHTS_CONTENT})
    @Parameters({ ParamProps.ANDROID, ParamProps.PARAMOUNT_APP })
    public void fightsContentAllShowsScreenAndroidTest() {

		if (firstFightTitle != null) {
			splashChain.splashAtRest();
			chromecastChain.dismissChromecast();

			shows.enterAllShows();
			shows.allShowsBackBtn().waitForAllPresent();
			shows.scrollDownToSeriesTile(firstFightTitle);
			shows.tapSeriesTile(firstFightTitle);

			if (hasExtras) {
				series.specialBtn().waitForVisible();
				series.watchXtrasBtn().waitForVisible();
				series.watchXtrasBtn().waitForVisible().tap();
			} else {
				series.specialBtn().waitForNotPresentOrVisible();
				series.specialBtn().waitForNotPresentOrVisible();
			}
		} else {
			String message = "There are no fight content on: " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " in allshowsfeed url : " + FeedFactory.getAllShowsFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoEventContentException(message);
		}
	}

    @TestCaseId("48076")
    @Features(GroupProps.ALL_SHOWS)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN , GroupProps.FIGHTS_CONTENT})
    @Parameters({ ParamProps.IOS, ParamProps.PARAMOUNT_APP })
    public void fightsContentAllShowsScreeniOSTest() {

        if (firstFightTitle != null) {
            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            alertsChain.dismissAlerts();
            shows.enterAllShows();
            
            shows.allShowsBackBtn().waitForAllPresent();
            shows.scrollDownToSeriesTile(firstFightTitle);
            shows.tapSeriesTile(firstFightTitle);
            
            if (hasExtras) {
            	series.specialBtn().waitForVisible();
	        	series.watchXtrasBtn().waitForVisible();
	        	series.watchXtrasBtn().waitForVisible().tap();
            } else {
            	series.specialBtn().waitForVisible();
            	series.specialBtn().waitForVisible().tap();
            }
        } else {
            String message = "There are no fight content on: " + TestRun.getAppName() + " " + TestRun.getLocale()
                    + " in allshowsfeed url : " + FeedFactory.getAllShowsFeedURL() + " so skipping test";
            Logger.logMessage(message);
            throw new NoEventContentException(message);
        }
    }
}