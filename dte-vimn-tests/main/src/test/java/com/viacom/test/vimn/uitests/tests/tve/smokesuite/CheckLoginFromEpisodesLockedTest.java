package com.viacom.test.vimn.uitests.tests.tve.smokesuite;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import com.viacom.test.core.driver.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SelectSeasonChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.SelectProvider;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.pageobjects.SignIn;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class CheckLoginFromEpisodesLockedTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
    SelectSeasonChain selectSeasonChain;
    Home home;
    Series series;
	LoginChain loginChain;
	Settings settings;
	SettingsMenu settingsMenu;
	SelectProvider selectProvider;
	SignIn signIn;
	ChromecastChain chromecastChain;
	AlertsChain alertschain;
    HomePropertyFilter propertyFilter;
    VideoPlayer videoPlayer;

    //Declare data
    String seriesTitle;
    String episodeTitle;
    Integer episodeSeasonNumber;
    Integer numberOfSwipes;
    String providerName;
	
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public CheckLoginFromEpisodesLockedTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	
    	splashChain = new SplashChain();
        loginChain = new LoginChain();
        selectSeasonChain = new SelectSeasonChain();
        home = new Home();
        series = new Series();
    	loginChain = new LoginChain();
    	settings = new Settings();
    	settingsMenu = new SettingsMenu();
    	selectProvider = new SelectProvider();
    	signIn = new SignIn();
    	chromecastChain = new ChromecastChain();
    	alertschain = new AlertsChain();
    	videoPlayer = new VideoPlayer();
    	propertyFilter = new HomePropertyFilter(NON_PAGINATED);
    	
    	providerName = ProviderManager.getDefaultProvider();
    	
        PropertyResults allSeriesWithPrivateEpisodes = propertyFilter.withEpisodes().withPrivateEpisodes().propertyFilter();
        PropertyResult firstSeriesWithPrivateEpisode = allSeriesWithPrivateEpisodes.getFirstProperty();

        seriesTitle = firstSeriesWithPrivateEpisode.getPropertyTitle();
        numberOfSwipes = firstSeriesWithPrivateEpisode.getNumOfSwipes();

        LongformResult privateEpisode = firstSeriesWithPrivateEpisode
                .getLongformFilter()
                .withPrivateEpisodesOnly()
                .episodesFilter()
                .getFirstEpisode();

        episodeTitle = privateEpisode.getEpisodeTitle();
        episodeSeasonNumber = privateEpisode.getEpisodeSeasonNumber();
    	
    }

    @TestCaseId("C58410+C58411+C58412")
    @Features(GroupProps.TVE)
    @Test(groups = {  GroupProps.FULL, GroupProps.TVE_SMOKE })
    @Parameters({ ParamProps.ANDROID, ParamProps.CMT_APP,
	  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
	  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
	  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP,
	  	  ParamProps.MTV_INTL_APP, ParamProps.CC_INTL_APP }) 
    public void checkLoginFromEpisodesLockedAndroidTest() {

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            loginChain.logoutIfLoggedIn();
            
            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.tapEpisodeLockBtn(episodeTitle);
            loginChain.defaultLogin();
            
            series.waitForEpisodeLoad(episodeTitle);
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
            
            boolean isLock = series.episodeLockBtn(episodeTitle).isVisible();
            Assert.assertFalse(isLock, "Pad Lock is displaying for episodes after login");

            DriverManager.getAndroidDriver().navigate().back(); // to ensure app restart on homescreen
            home.seriesThumbBtn(seriesTitle).waitForVisible();

            loginChain.restartApp();
            
            splashChain.splashAtRest();
            home.enterSeriesView(seriesTitle, numberOfSwipes);
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.scrollUpToSeriesTitle(seriesTitle);
            isLock = series.episodeLockBtn(episodeTitle).isVisible();
            Assert.assertFalse(isLock, "Pad Lock is displaying for private episode after login");

    }

}
