package com.viacom.test.vimn.uitests.tests.heartbeat;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.heartbeat.HeartbeatLogUtils;
import com.viacom.test.vimn.common.heartbeat.HeartbeatPlayerData;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.MrssDataFeed;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;

import java.util.HashMap;
import java.util.Map;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_PLAYLIST_START;

public class HeartbeatFeStartTest extends BaseTest {

    SplashChain splashChain;
    Home home;
    Series series;
    ChromecastChain chromecastChain;
    AutoPlayChain autoPlayChain;
    VideoPlayer videoPlayer;
    MrssDataFeed mrssDataFeed;
    HomePropertyFilter propertyFilter;

    String seriesTitle;
    String episodeTitle;
    Integer numberOfSwipes;
    String episodeId;
    Map<String, String> expectedMap;

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public HeartbeatFeStartTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        home = new Home();
        series = new Series();
        chromecastChain = new ChromecastChain();
        autoPlayChain = new AutoPlayChain();
        videoPlayer = new VideoPlayer();
        expectedMap = new HashMap<>();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        PropertyResult seriesResult = propertyFilter.withEpisodes().propertyFilter().getFirstProperty();
        seriesTitle = seriesResult.getPropertyTitle();
        numberOfSwipes = seriesResult.getNumOfSwipes();
        episodeId = seriesResult.getLongformFilter().episodesFilter().getEpisodeIds().get(0);
        episodeTitle = seriesResult.getLongformFilter().episodesFilter().getEpisodeTitles().get(0);

    }

    //@TestCaseId("35935")
    @Features(Config.GroupProps.HEARTBEAT)
    @Test(groups = { Config.GroupProps.FULL, Config.GroupProps.BENTO_SMOKE })
    @Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.TVLAND_APP, Config.ParamProps.CC_INTL_APP, Config.ParamProps.CC_DOMESTIC_APP,
            Config.ParamProps.MTV_INTL_APP, Config.ParamProps.MTV_DOMESTIC_APP, Config.ParamProps.CMT_APP,
            Config.ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP, Config.ParamProps.BET_DOMESTIC_APP })
    public void heartbeatFeStartPlayerCallAndroidTest() {
        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.enableAutoPlay();
        ProxyUtils.disableTve();
        ProxyUtils.disableAds();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.scrollUpToSeriesTitle(seriesTitle);
		if (series.fullEpisodesBtn().isVisible()) { // Btn not available for Episode only series
	        series.fullEpisodesBtn().waitForVisible().tap();
	    }
        if (series.allSeasonsBtn().isVisible()) { // Btn available, available seasons > 1
        	series.allSeasonsBtn().waitForVisible().tap();
        }
        if (series.episodePlayBtn(episodeTitle).isVisible()) { // Btn available if any other episode playing by default
        	series.episodePlayBtn(episodeTitle).waitForVisible().tap();
        } 

        mrssDataFeed = new MrssDataFeed(episodeId);
        String episodeMgid = mrssDataFeed.getEpisodeMgid();
        String segmentMgid = mrssDataFeed.getFirstSegmentMgid();
        String numberOfSegments = String.valueOf(mrssDataFeed.getNumberOfSegments());
        String segmentTitle = mrssDataFeed.getFirstSegmentTitle();
        String playlistTitle = mrssDataFeed.getPlaylistTitle();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

        expectedMap = HeartbeatPlayerData.buildFepStartPlayerExpectedMap(segmentTitle, playlistTitle, segmentMgid, episodeMgid, numberOfSegments);
        Map<String, String> actualMap = HeartbeatLogUtils.getActualMap("s:meta:v.activity", EXPECTED_PARAM_PLAYLIST_START);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }
    
    //@TestCaseId("35935")
    @Features(Config.GroupProps.HEARTBEAT)
    @Test(groups = { Config.GroupProps.FULL, Config.GroupProps.BENTO_SMOKE })
    @Parameters({ Config.ParamProps.IOS, Config.ParamProps.TVLAND_APP, Config.ParamProps.CC_INTL_APP, Config.ParamProps.CC_DOMESTIC_APP,
            Config.ParamProps.MTV_INTL_APP, Config.ParamProps.MTV_DOMESTIC_APP, Config.ParamProps.CMT_APP,
            Config.ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP, Config.ParamProps.BET_DOMESTIC_APP })
    public void heartbeatFeStartPlayerCalliOSTest() {
        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.enableAutoPlay();
        ProxyUtils.disableTve();
        ProxyUtils.disableAds();

		home.enterSeriesView(seriesTitle, numberOfSwipes);
		if (series.fullEpisodesBtn().isVisible()) { // Btn not available for Episode only series
	        series.fullEpisodesBtn().waitForVisible().tap();
	    }
        if (series.allSeasonsBtn().isVisible()) { // Btn available, available seasons > 1
        	series.allSeasonsBtn().waitForVisible().tap();
        }
        if (series.episodePlayBtn(episodeTitle).isVisible()) { // Btn available if any other episode playing by default
        	series.episodePlayBtn(episodeTitle).waitForVisible().tap();
        } 

        mrssDataFeed = new MrssDataFeed(episodeId);
        String episodeMgid = mrssDataFeed.getEpisodeMgid();
        String segmentMgid = mrssDataFeed.getFirstSegmentMgid();
        String numberOfSegments = String.valueOf(mrssDataFeed.getNumberOfSegments());
        String segmentTitle = mrssDataFeed.getFirstSegmentTitle();
        String playlistTitle = mrssDataFeed.getPlaylistTitle();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

        expectedMap = HeartbeatPlayerData.buildFepStartPlayerExpectedMap(segmentTitle, playlistTitle, segmentMgid, episodeMgid, numberOfSegments);
        Map<String, String> actualMap = HeartbeatLogUtils.getActualMap("s:meta:v.activity", EXPECTED_PARAM_PLAYLIST_START);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }
}