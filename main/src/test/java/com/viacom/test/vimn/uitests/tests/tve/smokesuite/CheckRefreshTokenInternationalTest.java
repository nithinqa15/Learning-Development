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
import com.viacom.test.vimn.common.tve.GetAccessToken;
import com.viacom.test.vimn.common.tve.ProviderUtils;
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

public class CheckRefreshTokenInternationalTest extends BaseTest {

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
    public CheckRefreshTokenInternationalTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	
    	splashChain = new SplashChain();
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

    @TestCaseId("C44581")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE_SMOKE })
	@Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.CC_INTL_APP, Config.ParamProps.MTV_INTL_APP })
    public void checkRefreshTokenInternationalAndriodTest() {

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            loginChain.logoutIfLoggedIn();
            
            GetAccessToken.decreaseAccessTokenTTL();

            settings.settingsBtn().waitForVisible().tap();
            settingsMenu.signInBtn().waitForVisible().tap();
            loginChain.defaultLogin();
            
            home.enterSeriesView(seriesTitle, numberOfSwipes);
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.scrollUpToSeriesTitle(seriesTitle);
            series.episodePlayBtn(episodeTitle).waitForPlaylistLoad().pause(3500).tap();

            Assert.assertTrue(ProviderUtils.isRefreshTokenCallPresent(), "RefreshToken request wasn't sent.");     
    }

}
