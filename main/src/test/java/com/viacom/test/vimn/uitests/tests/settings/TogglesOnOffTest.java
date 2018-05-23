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
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.CellularToggleChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SelectSeasonChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class TogglesOnOffTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
    LoginChain loginChain;
    AutoPlayChain autoPlayChain;
    CellularToggleChain cellularToggleChain;
    Settings settings;
    SettingsMenu settingsMenu;
    Home home;
    Series series;
    VideoPlayer videoPlayer;
    SelectSeasonChain selectSeasonChain;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter homePropertyFilter;

    //Declare data
    String episodeTitle;
    String seriesTitle;
    Integer numberOfSwipes;
    Integer episodeSeasonNumber;
    LongformResult firstEpisode;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public TogglesOnOffTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        loginChain = new LoginChain();
        autoPlayChain = new AutoPlayChain();
        cellularToggleChain = new CellularToggleChain();
        settings = new Settings();
        settingsMenu = new SettingsMenu();
        home = new Home();
        series = new Series();
        videoPlayer = new VideoPlayer();
        selectSeasonChain = new SelectSeasonChain();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
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

    @TestCaseId("35059-35060")
    @Features(Config.GroupProps.SETTINGS)
    @Test(groups = { Config.GroupProps.FULL })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP,
				  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
				  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP,
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void TogglesOnOffTestAndroidTest() {

        ProxyUtils.disableTve();

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        cellularToggleChain.enableCellular();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.scrollUpToSeriesTitle(seriesTitle);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

        series.episodeTtl(episodeTitle).goBack();
        cellularToggleChain.disableCellular();
        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.scrollUpToSeriesTitle(seriesTitle);
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

        series.episodeTtl(episodeTitle).goBack();
        cellularToggleChain.enableCellular();
        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.scrollUpToSeriesTitle(seriesTitle);
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

        series.episodeTtl(episodeTitle).goBack();
        autoPlayChain.enableAutoPlay();
        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.scrollUpToSeriesTitle(seriesTitle);
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

    }

    // This test will not run for the time being
    @TestCaseId("35059-35060")
    @Features(Config.GroupProps.SETTINGS)
    @Test(groups = { Config.GroupProps.FULL, Config.GroupProps.SETTINGS})
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP,
				  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
				  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP,
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void TogglesOnOffTestiOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        cellularToggleChain.enableCellular();
        autoPlayChain.enableAutoPlay();

        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForScrolledTo();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        series.backBtn().waitForVisible().tap();

        cellularToggleChain.disableCellular();
        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForScrolledTo();
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
        series.backBtn().waitForVisible().tap();

        cellularToggleChain.enableCellular();
        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForScrolledTo();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        series.backBtn().waitForVisible().tap();

        autoPlayChain.enableAutoPlay();
        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForScrolledTo();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

    }

}