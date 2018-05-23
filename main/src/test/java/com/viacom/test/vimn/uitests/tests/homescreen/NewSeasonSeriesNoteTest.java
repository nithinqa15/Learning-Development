package com.viacom.test.vimn.uitests.tests.homescreen;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.LongformResult;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoSeriesWithNewSeasonException;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SelectSeasonChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class NewSeasonSeriesNoteTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
    SelectSeasonChain selectSeasonChain;
	Home home;
	Series series;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter homePropertyFilter;
    
    //Declare data
    String seriesTitleWNote = null;
    String episodeTitle;
    String episodeDescription;
    Integer numberOfSwipes;
    Integer episodeSeasonNumber;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public NewSeasonSeriesNoteTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

    	splashChain = new SplashChain();
        selectSeasonChain = new SelectSeasonChain();
        home = new Home();
    	series = new Series();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
        homePropertyFilter = new HomePropertyFilter(NON_PAGINATED);

        PropertyResult propertyResult = homePropertyFilter.withNewSeasons().propertyFilter().getFirstProperty();
        seriesTitleWNote = propertyResult.getPropertyTitle();
        numberOfSwipes = propertyResult.getNumOfSwipes();

        LongformResult episodeResult = propertyResult.getLongformFilter().episodesFilter().getFirstEpisode();
        episodeTitle = episodeResult.getEpisodeTitle();
        episodeDescription = episodeResult.getEpisodeDescription();
        episodeSeasonNumber = episodeResult.getEpisodeSeasonNumber();

    }
    
    @TestCaseId("11934")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void newSeasonSeriesNoteAndroidTest() {

        if (seriesTitleWNote != null) {
            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            home.flickRightToSeriesThumb(seriesTitleWNote, numberOfSwipes);
            home.newSeasonBtn().waitForVisible().tap();
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.scrollUpToSeriesTitle(seriesTitleWNote);
            series.episodeTtl(episodeTitle).waitForScrolledTo();
        } else {
            String message = "A Series with a New Season Note was not returned from the data feed.";
            Logger.logMessage(message);
            throw new NoSeriesWithNewSeasonException(message);
        }

    }

    @TestCaseId("11934")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void newSeasonSeriesNoteiOSTest() {
        
    	if (seriesTitleWNote == null) {
            String message = "A Series with a New Season Note was not returned from the data feed.";
            Logger.logMessage(message);
            throw new NoSeriesWithNewSeasonException(message);
        } else {

            splashChain.splashAtRest();
            alertschain.dismissAlerts();
            home.enterSeriesWithNewSeason(seriesTitleWNote, numberOfSwipes);

            if (series.fullEpisodesBtn().waitForViewLoad().isVisible()) {
                series.fullEpisodesBtn().waitForVisible().tap();
            }

            series.seriesTtl(seriesTitleWNote).waitForViewLoad().waitForPresent();
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);

            if (series.episodeTtl(episodeTitle).isVisible()) {
                series.episodeTtl(episodeTitle).waitForScrolledTo();
            } else if (series.episodeDescriptionTxt(episodeDescription).isVisible()) {
                series.episodeDescriptionTxt(episodeDescription).waitForScrolledTo();
            } else {
                Assert.fail();
            }
        }
    }
}
