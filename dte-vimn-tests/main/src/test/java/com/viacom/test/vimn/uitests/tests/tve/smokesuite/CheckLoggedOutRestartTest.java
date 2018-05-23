package com.viacom.test.vimn.uitests.tests.tve.smokesuite;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

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
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class CheckLoggedOutRestartTest extends BaseTest {

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

    //Declare data
    String seriesTitle;
    String episodeTitle;
    Integer episodeSeasonNumber;
    Integer numberOfSwipes;
    String providerName;
	
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public CheckLoggedOutRestartTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	
    	splashChain = new SplashChain();
        loginChain = new LoginChain();
        selectSeasonChain = new SelectSeasonChain();
        home = new Home();
    	loginChain = new LoginChain();
    	settings = new Settings();
    	settingsMenu = new SettingsMenu();
    	selectProvider = new SelectProvider();
    	signIn = new SignIn();
    	chromecastChain = new ChromecastChain();
    	alertschain = new AlertsChain();
    	propertyFilter = new HomePropertyFilter(NON_PAGINATED);
    	series = new Series();
    	
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

    @TestCaseId("C28634")
    @Features(GroupProps.TVE)
    @Test(groups = {  GroupProps.FULL, GroupProps.TVE_SMOKE })
    @Parameters({ ParamProps.ANDROID, ParamProps.CMT_APP,
	  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
	  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
	  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP,
	  	  ParamProps.MTV_INTL_APP, ParamProps.CC_INTL_APP }) 
    public void checkLoggedOutRestartAndroidTest() {

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            loginChain.logoutIfLoggedIn();

            settings.settingsBtn().waitForVisible().tap();
            settingsMenu.signInBtn().waitForVisible().tap();
            loginChain.defaultLogin();
            loginChain.logoutIfLoggedIn();
            
            loginChain.restartApp(); 
            home.seriesThumbBtn(seriesTitle).waitForVisible();
            
            settings.settingsBtn().waitForVisible().tap();
            boolean isSignIn = settingsMenu.signInBtn().isVisible();
            Assert.assertTrue(isSignIn, "SignIn button on settings page is not displayed");
            settingsMenu.backBtn().waitForVisible().tap();
            
            home.enterSeriesView(seriesTitle, numberOfSwipes);
            if (!series.episodeLockBtn(episodeTitle).isVisible(5)) {
            	series.scrollDownToEpisodeTtl(episodeTitle);
            }
            boolean isLock = series.episodeLockBtn(episodeTitle).isVisible();
            Assert.assertTrue(isLock, "Pad Lock is not displaying for private episode");
            series.tapEpisodeLockBtn(episodeTitle);
    }

}