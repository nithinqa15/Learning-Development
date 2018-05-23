package com.viacom.test.vimn.uitests.tests.showdetails;

import java.util.List;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ru.yandex.qatools.allure.annotations.Features;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.SelectSeasonChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.pageobjects.Ads;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class LockUnlockTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
    AutoPlayChain autoPlayChain;
    SelectSeasonChain selectSeasonChain;
    Home home;
    Series series;
    AppDataFeed appDataFeed;
    VideoPlayer videoPlayer;
    Ads ads;
    ChromecastChain chromecastChain;
    HomePropertyFilter propertyFilter;
    LongformResult firstEpisode;

    //Declare data
    String seriesTitle;
    String episodeTitle;
    Integer episodeSeasonNumber;
    Integer numberOfSwipes;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public LockUnlockTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        autoPlayChain = new AutoPlayChain();
        selectSeasonChain = new SelectSeasonChain();
        series = new Series();
        home = new Home();
        videoPlayer = new VideoPlayer();
        appDataFeed = new AppDataFeed();
        chromecastChain = new ChromecastChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        PropertyResult propertyResult = propertyFilter.withEpisodes().withPublicEpisodes().propertyFilter().getFirstProperty();
        seriesTitle = propertyResult.getPropertyTitle();
        numberOfSwipes = propertyResult.getNumOfSwipes();

        LongformResult episodeResult = propertyResult.getLongformFilter().withPublicEpisodesOnly().episodesFilter().getFirstEpisode();
        episodeTitle = episodeResult.getEpisodeTitle();

        firstEpisode = propertyResult.getLongformFilter().episodesFilter().getFirstEpisode();
        episodeSeasonNumber = episodeResult.getEpisodeSeasonNumber();

    }

    @TestCaseId("10913")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.BROKEN, GroupProps.SHOW_DETAILS })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, ParamProps.MTV_DOMESTIC_APP,
            ParamProps.TVLAND_APP, Config.ParamProps.CC_INTL_APP, Config.ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP,
            ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void lockUnlockAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.scrollUpToSeriesTitle(seriesTitle);
        series.seriesTtl(seriesTitle).waitForVisible();
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        if (firstEpisode.isPrivateEpisode()) {
            series.scrollDownToEpisodeTtl(episodeTitle);
            series.tapEpisodePlayBtn(episodeTitle);
        }
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        series.seriesTtl(seriesTitle).lock(5).waitForVisible();
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
    }

    @TestCaseId("10913")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.BROKEN, GroupProps.SHOW_DETAILS  })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, ParamProps.MTV_DOMESTIC_APP,
            ParamProps.TVLAND_APP, Config.ParamProps.CC_INTL_APP, Config.ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP,
            ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void lockUnlockiOSTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.enableAutoPlay();

        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        //Lock method not yet implemented
        series.seriesTtl(seriesTitle).waitForVisible().lock(5).waitForVisible();
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

    }

}
