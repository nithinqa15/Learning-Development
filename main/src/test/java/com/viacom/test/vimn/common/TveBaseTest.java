package com.viacom.test.vimn.common;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SelectSeasonChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Alerts;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Keyboard;
import com.viacom.test.vimn.uitests.pageobjects.Navigate;
import com.viacom.test.vimn.uitests.pageobjects.SelectProvider;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.pageobjects.SignIn;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.pageobjects.XFinity;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.ProviderEnabled;
import com.viacom.test.vimn.uitests.support.ProviderManager;

public class TveBaseTest extends BaseTest {

    //Declare page objects/actions
    protected static SplashChain splashChain;
    protected static LoginChain loginChain;
    protected static SelectSeasonChain selectSeasonChain;
    protected static AutoPlayChain autoPlayChain;
    protected static Home home;
    protected static Series series;
    protected static SelectProvider selectProvider;
    protected static SignIn signIn;
    protected static VideoPlayer videoPlayer;
    protected static Navigate navigate;
    protected static AppDataFeed appDataFeed;
    protected static Keyboard keyboard;
    protected static ChromecastChain chromecastChain;
    protected static AlertsChain alertschain;
    protected static Settings settings;
    protected static SettingsMenu settingsMenu;
    protected static ProviderEnabled providerEnabled;
    protected static XFinity xFinity;
    protected static Alerts alerts;
    protected static ProviderManager providerManager;
    protected static HomePropertyFilter propertyFilter;

    //Declare data
    protected static String seriesTitle;
    protected static String episodeTitle;
    protected static Integer episodeSeasonNumber;
    protected static Integer numberOfSwipes;
    protected String username;
    protected String password;
    protected String providerName;

    protected void initializePageObjects() {

        splashChain = new SplashChain();
        loginChain = new LoginChain();
        autoPlayChain = new AutoPlayChain();
        selectSeasonChain = new SelectSeasonChain();
        home = new Home();
        series = new Series();
        selectProvider = new SelectProvider();
        signIn = new SignIn();
        videoPlayer = new VideoPlayer();
        navigate = new Navigate();
        appDataFeed = new AppDataFeed();
        keyboard = new Keyboard();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
        settings = new Settings();
        settingsMenu = new SettingsMenu();
        providerEnabled = new ProviderEnabled();
        xFinity = new XFinity();
        alerts = new Alerts();
        providerManager = new ProviderManager();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
    }

    protected void tveSetupTest() {

        initializePageObjects();

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

}
