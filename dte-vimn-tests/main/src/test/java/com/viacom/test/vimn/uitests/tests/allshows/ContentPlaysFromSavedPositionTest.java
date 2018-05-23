package com.viacom.test.vimn.uitests.tests.allshows;

import com.viacom.test.vimn.common.filters.AllShowsPropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.LongformResult;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.AllShows;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class ContentPlaysFromSavedPositionTest extends BaseTest {

	// Declare page objects/actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AllShows allShows;
	Series series;
	VideoPlayer videoPlayer;
	AllShowsPropertyFilter allShowsPropertyFilter;

	// Declare data
	String seriesTitle;
	String episodeTitle;
	int repeatNumTimes = 6;
	Integer prevProgressTxt;
	Integer currProgressTxt;
	LongformResult firstEpisode;
	
	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public ContentPlaysFromSavedPositionTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setUpTest() {
		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		allShows = new AllShows();
		series = new Series();
		videoPlayer = new VideoPlayer();
		allShowsPropertyFilter = new AllShowsPropertyFilter(NON_PAGINATED);

		PropertyResult propertyResult = allShowsPropertyFilter.withEpisodes().withPublicEpisodes().propertyFilter().getFirstProperty();
        LongformResult episodeResult = propertyResult
                .getLongformFilter()
                .withPublicEpisodesOnly()
                .episodesFilter()
                .getFirstEpisode();
        episodeTitle = episodeResult.getEpisodeTitle();
		seriesTitle = propertyResult.getPropertyTitle();

		firstEpisode = propertyResult.getLongformFilter().episodesFilter().getFirstEpisode();
	}

	@TestCaseId("10882")
	@Features(GroupProps.ALL_SHOWS)
	@Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS })
	@Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
	public void contentPlaysFromSavedPositionAndroidTest() {

		ProxyUtils.disableAds();
		ProxyUtils.disableTve();

		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();

		allShows.enterAllShows();
		allShows.enterSeriesView(seriesTitle);
		series.seriesTtl(seriesTitle).waitForViewLoad();
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);
		series.backBtn().waitForVisible().pause(30000);
		
		while (repeatNumTimes > 0) {
			prevProgressTxt = videoPlayer.getProgressTxtInSeconds();
			series.backBtn().waitForVisible().tap();
			allShows.enterSeriesView(seriesTitle);
			Assert.assertTrue(videoPlayer.playFromBeginningBtn().isVisible(30000));
			currProgressTxt = videoPlayer.getProgressTxtInSeconds();
			Assert.assertTrue(currProgressTxt >= prevProgressTxt && prevProgressTxt + 10 >= currProgressTxt);
			videoPlayer.playFromBeginningBtn().waitForVisible();
			repeatNumTimes--;
		}
	}
	
	@TestCaseId("10882")
	@Features(GroupProps.ALL_SHOWS)
	@Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS })
	@Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
	public void contentPlaysFromSavedPositioniOSTest() {

		ProxyUtils.disableAds();

		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();

		allShows.enterAllShows();
		allShows.enterSeriesView(seriesTitle);
		videoPlayer.verifyVideoIsPlayingByScreenshots(10);
		series.backBtn().waitForVisible().pause(30000);
		
		while (repeatNumTimes > 0) {
			prevProgressTxt = videoPlayer.getProgressTxtInPercentage();
			series.backBtn().waitForVisible().tap();
			allShows.enterSeriesView(seriesTitle);
			currProgressTxt = videoPlayer.getProgressTxtInPercentage();
			Assert.assertTrue(currProgressTxt >= prevProgressTxt && prevProgressTxt + 10 >= currProgressTxt);
			videoPlayer.playFromBeginningBtn().waitForVisible();
			repeatNumTimes--;
		}
	}
}
