package com.viacom.test.vimn.uitests.tests.allshows;

import com.viacom.test.vimn.common.filters.AllShowsPropertyFilter;
import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.common.filters.PropertyResult;
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
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.AllShows;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class AllShowsAutoplayTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
    ChromecastChain chromecastChain;
    AutoPlayChain autoPlayChain;
    LoginChain loginChain;
    AllShows allShows;
    Series  series;
    Home    home;
    VideoPlayer videoPlayer;
    AlertsChain alertschain;
    AllShowsPropertyFilter allShowsPropertyFilter;

    //Declare Data
    String seriesTitle;
    String episodeTitle;
    LongformResult firstEpisode;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public AllShowsAutoplayTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {
        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        autoPlayChain = new AutoPlayChain();
        allShows = new AllShows();
        series = new Series();
        home = new Home();
        videoPlayer = new VideoPlayer();
        loginChain = new LoginChain();
        alertschain = new AlertsChain();
        allShowsPropertyFilter = new AllShowsPropertyFilter(NON_PAGINATED);

        PropertyResult propertyResult = allShowsPropertyFilter.withEpisodes().withPublicEpisodes().propertyFilter().getFirstProperty();
        seriesTitle = propertyResult.getPropertyTitle();

        LongformResult episodeResult = propertyResult.getLongformFilter().withPublicEpisodesOnly().episodesFilter().getFirstEpisode();
        episodeTitle = episodeResult.getEpisodeTitle();

        firstEpisode = propertyResult.getLongformFilter().episodesFilter().getFirstEpisode();
    }

    @TestCaseId("")
    @Features(GroupProps.ALL_SHOWS)
    @Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS })
    @Parameters({ParamProps.ANDROID, ParamProps.ALL_APPS})
    public void allShowsAutoplayAndroidTest() {

        ProxyUtils.disableTve();

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.disableAutoPlay();

        allShows.enterAllShows();
        allShows.enterSeriesView(seriesTitle);
        series.seriesTtl(seriesTitle).waitForViewLoad();
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
        series.seriesTtl(seriesTitle).goBack();
        allShows.allShowsBackBtn().waitForVisible().goBack();

        autoPlayChain.enableAutoPlay();

        allShows.enterAllShows();
        allShows.enterSeriesView(seriesTitle);
        series.seriesTtl(seriesTitle).waitForViewLoad();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        series.seriesTtl(seriesTitle).goBack();
    }
    
    @TestCaseId("")
    @Features(GroupProps.ALL_SHOWS)
    @Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS  })
    @Parameters({ParamProps.IOS, ParamProps.ALL_APPS})
    public void allShowsAutoplayiOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        autoPlayChain.disableAutoPlay();

        allShows.enterAllShows();
        allShows.enterSeriesView(seriesTitle);

        series.scrollUpToSeriesTitle(seriesTitle);
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
        series.backBtn().waitForVisible().tap();
        allShows.allShowsBackBtn().waitForVisible().tap();

        autoPlayChain.enableAutoPlay();

        allShows.enterAllShows();
        allShows.enterSeriesView(seriesTitle);
        series.scrollUpToSeriesTitle(seriesTitle);
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        series.backBtn().waitForVisible().tap();
    }
}
