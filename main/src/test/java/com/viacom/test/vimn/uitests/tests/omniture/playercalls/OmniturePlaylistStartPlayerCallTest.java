package com.viacom.test.vimn.uitests.tests.omniture.playercalls;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.PropertyFilter;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.omniture.OmniturePlayerData;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.BrandDataFactory;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.MrssDataFeed;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import java.util.HashMap;
import java.util.Map;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_PLAYLIST_START;

public class OmniturePlaylistStartPlayerCallTest extends BaseTest {

    SplashChain splashChain;
    Home home;
    Series series;
    ChromecastChain chromecastChain;
    AutoPlayChain autoPlayChain;
    VideoPlayer videoPlayer;
    BrandDataFactory brandDataFactory;
    MrssDataFeed mrssDataFeed;
    PropertyFilter homePropertyFilter;
    LongformResult firstEpisode;

    String seriesTitle;
    String episodeTitle;
    Integer numberOfSwipes;
    String episodeId;
    Map<String, String> expectedMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmniturePlaylistStartPlayerCallTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        home = new Home();
        series = new Series();
        homePropertyFilter = new HomePropertyFilter(NON_PAGINATED);
        chromecastChain = new ChromecastChain();
        autoPlayChain = new AutoPlayChain();
        videoPlayer = new VideoPlayer();
        brandDataFactory = new BrandDataFactory();
        expectedMap = new HashMap<>();

        PropertyResult propertyResult = homePropertyFilter
                .withEpisodes()
                .propertyFilter()
                .getFirstProperty();

        firstEpisode = propertyResult
                .getLongformFilter()
                .episodesFilter()
                .getFirstEpisode();

        episodeTitle = firstEpisode.getEpisodeTitle();
        if (episodeTitle != null) {
            seriesTitle = propertyResult.getPropertyTitle();
            numberOfSwipes = propertyResult.getNumOfSwipes();
            episodeId = firstEpisode.getEpisodeId();
        }
        Logger.logMessage("Series Title :" + seriesTitle);
        Logger.logMessage("No of Swipes :" + numberOfSwipes);
        Logger.logMessage("Episode ID :" + episodeId);
        Logger.logMessage("Episode Title :" + episodeTitle);
    }

    @TestCaseId("35935")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS, GroupProps.OMNITURE, GroupProps.BENTO_SMOKE, GroupProps.VIDEO_PLAYER })
    @Parameters({ ParamProps.ANDROID, ParamProps.TVLAND_APP,
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CC_INTL_APP,
				  ParamProps.MTV_DOMESTIC_APP, ParamProps.MTV_INTL_APP,
				  ParamProps.BET_DOMESTIC_APP, ParamProps.CMT_APP,
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void omniturePlaylistStartPlayerCallAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.disableAutoPlay();
        ProxyUtils.disableTve();
        ProxyUtils.disableAds();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.seriesTtl(seriesTitle).waitForViewLoad();      
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
        String contentType = mrssDataFeed.getContentType();
        String playlistTitle = mrssDataFeed.getPlaylistTitle();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

        expectedMap = OmniturePlayerData.buildPlaylistStartPlayerExpectedMap(seriesTitle,
                episodeMgid,
                segmentMgid,
                numberOfSegments,
                segmentTitle,
                contentType,
                playlistTitle);

        Map<String, String> actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_PLAYLIST_START);
        Omniture.assertOmnitureValues(expectedMap, actualMap);

    }

    @TestCaseId("35935")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS, GroupProps.OMNITURE, GroupProps.VIDEO_PLAYER, GroupProps.BENTO_SMOKE })
    @Parameters({ ParamProps.IOS, ParamProps.TVLAND_APP,
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CC_INTL_APP,
				  ParamProps.MTV_DOMESTIC_APP, ParamProps.MTV_INTL_APP,
				  ParamProps.BET_DOMESTIC_APP, ParamProps.CMT_APP,
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void omniturePlaylistStartPlayerCalliOSTest() {
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
		String contentType = mrssDataFeed.getContentType();
		String playlistTitle = mrssDataFeed.getPlaylistTitle();
		
		expectedMap = OmniturePlayerData.buildPlaylistStartPlayerExpectedMap(seriesTitle,
		        episodeMgid,
		        segmentMgid,
		        numberOfSegments,
		        segmentTitle,
		        contentType,
		        playlistTitle);
		
		Map<String, String> actualMap = OmnitureLogUtils.getActualMap("activity", EXPECTED_PARAM_PLAYLIST_START);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
    }
}
