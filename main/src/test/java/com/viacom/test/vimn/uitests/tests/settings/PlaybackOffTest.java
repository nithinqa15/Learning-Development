package com.viacom.test.vimn.uitests.tests.settings;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SelectSeasonChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class PlaybackOffTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
    ChromecastChain chromecastChain;
    AutoPlayChain autoPlayChain;
    Home home;
    Series series;
    SelectSeasonChain selectSeasonChain;
    VideoPlayer videoPlayer;
    AlertsChain alertsChain;
    HomePropertyFilter homePropertyFilter;
    
    //Declare data
    String seriesTitle;
    String episodeTitle;
    Integer episodeSeasonNumber;
    Integer numberOfSwipes;
    LongformResult firstEpisode;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public PlaybackOffTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        autoPlayChain = new AutoPlayChain();
        home = new Home();
        series = new Series();
        selectSeasonChain = new SelectSeasonChain();
        videoPlayer = new VideoPlayer();
        alertsChain = new AlertsChain();
        homePropertyFilter = new HomePropertyFilter(NON_PAGINATED);

        PropertyResult propertyResult = homePropertyFilter.withEpisodes().withPublicEpisodes().propertyFilter().getFirstProperty();
        seriesTitle = propertyResult.getPropertyTitle();
        numberOfSwipes = propertyResult.getNumOfSwipes();

        LongformResult episodeResult = propertyResult
                .getLongformFilter()
                .withPublicEpisodesOnly()
                .episodesFilter()
                .getFirstEpisode();
        episodeTitle = episodeResult.getEpisodeTitle();
        episodeSeasonNumber = episodeResult.getEpisodeSeasonNumber();
        firstEpisode = propertyResult.getLongformFilter().episodesFilter().getFirstEpisode();

    }

    @TestCaseId("")
    @Features(GroupProps.SETTINGS)
    @Test(groups = { GroupProps.FULL, GroupProps.SETTINGS })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP,
				  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
				  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP,
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void playbackOffAndroidTest() {

        ProxyUtils.disableTve();

    	splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.disableAutoPlay();
        
        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.scrollUpToSeriesTitle(seriesTitle);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.scrollUpToSeriesTitle(seriesTitle);
        videoPlayer.largePlayBtn().waitForVisible();
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

    }

    @TestCaseId("")
    @Features(GroupProps.SETTINGS)
    @Test(groups = { GroupProps.FULL, GroupProps.SETTINGS  })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP,
				  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
				  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP,
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void playbackOffiOSTest() {
        
    	splashChain.splashAtRest();
    	alertsChain.dismissAlerts();
        autoPlayChain.disableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        videoPlayer.largePlayBtn().waitForPlayerLoad().waitForVisible();
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
    }
    
}
