package com.viacom.test.vimn.uitests.tests.allshows;

import java.awt.image.BufferedImage;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Ads;
import com.viacom.test.vimn.uitests.pageobjects.AllShows;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class NoProgressBarOnAllShowTest extends BaseTest {

	// Declare page objects/actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AllShows allShows;
	Home home;
	Series series;
	VideoPlayer videoPlayer;
	Ads ads;
	HomePropertyFilter homePropertyFilter;

	// Declare data
	String seriesTitle;
	String episodeTitle;
	Integer numberOfSwipes;
	LongformResult firstEpisode;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public NoProgressBarOnAllShowTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setUpTest() {

		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		allShows = new AllShows();
		ads = new Ads();
		series = new Series();
		videoPlayer = new VideoPlayer();
		home = new Home();
		homePropertyFilter = new HomePropertyFilter(NON_PAGINATED);

		PropertyResult propertyResult = homePropertyFilter.withEpisodes().withPublicEpisodes().propertyFilter().getFirstProperty();
        LongformResult episodeResult = propertyResult.getLongformFilter().withPublicEpisodesOnly().episodesFilter().getFirstEpisode();
        episodeTitle = episodeResult.getEpisodeTitle();
		seriesTitle = propertyResult.getPropertyTitle();

		firstEpisode = propertyResult.getLongformFilter().episodesFilter().getFirstEpisode();
		
	}

	@TestCaseId("10885")
	@Features(GroupProps.ALL_SHOWS)
	@Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS })
	@Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
	public void noProgressBarOnAllShowAndroidTest() {

        ProxyUtils.disableAds();
        ProxyUtils.disableTve();

		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();

		allShows.enterAllShows();
		allShows.scrollDownToSeriesTile(seriesTitle);
		
		BufferedImage prevAllShowsImage = allShows.seriesTile(seriesTitle).waitForVisible().takeScreenshotOfThisInteract();

		allShows.seriesTile(seriesTitle).waitForVisible().tap();
		series.seriesTtl(seriesTitle).waitForViewLoad();
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);
		videoPlayer.playerCtr().pause(30000).goBack();
		BufferedImage currAllShowsImage = allShows.seriesTile(seriesTitle).waitForVisible().takeScreenshotOfThisInteract();
		Assert.assertTrue(Interact.areScreenshotsEqual(prevAllShowsImage, currAllShowsImage));

		allShows.exitAllShows();
		home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
		home.progressBar(seriesTitle).waitForVisible();
	}
	
	@TestCaseId("10885")
	@Features(GroupProps.ALL_SHOWS)
	@Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS })
	@Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
	public void noProgressBarOnAllShowiOSTest() {

		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();

		allShows.enterAllShows();
		allShows.scrollDownToSeriesTile(seriesTitle);
		
		BufferedImage prevAllShowsImage = allShows.seriesTile(seriesTitle).waitForVisible().takeScreenshotOfThisInteract();

		allShows.seriesTile(seriesTitle).waitForVisible().tap();
		if (!ads.learnMoreBtn().isVisible()) {
			videoPlayer.playerCtr().waitForPresent().tapCenter();
		}
		ads.waitForAd(0);

		videoPlayer.verifyVideoIsPlayingByScreenshots(1);
		videoPlayer.playerCtr().pause(30000);
		series.backBtn().waitForVisible().tap();
		BufferedImage currAllShowsImage = allShows.seriesTile(seriesTitle).waitForVisible().takeScreenshotOfThisInteract();
		Assert.assertTrue(Interact.areScreenshotsEqual(prevAllShowsImage, currAllShowsImage));
		
		allShows.exitAllShows();
		home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
		home.progressBar(seriesTitle).waitForVisible();
	}
}
