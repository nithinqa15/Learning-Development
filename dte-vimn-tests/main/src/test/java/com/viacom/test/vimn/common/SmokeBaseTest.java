package com.viacom.test.vimn.common;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import java.util.List;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.filters.*;
import com.viacom.test.vimn.uitests.actionchains.*;
import com.viacom.test.vimn.uitests.pageobjects.*;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.ProviderEnabled;
import com.viacom.test.vimn.uitests.support.ProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;

public class SmokeBaseTest extends BaseTest {

	// Declare page objects/actions
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
	protected static Alerts alerts;
	protected static ProviderManager providerManager;
	protected static HomePropertyFilter propertyFilter;
	protected static AllShows allShows;
	protected static Clips clips;
	protected static Contact contact;
	protected static LegalNotices legalNotices;
	protected static PrivacyPolicy privacyPolicy;
	protected static ProgressBar progressBar;
	protected static TermsAndConditions termsAndConditions;
	protected static AllShowsPropertyFilter allShowsPropertyFilter;
	protected static PropertyResults AllEventResultsOnHomeScreen;

	// Declare data
	protected static String privateEpisodesSeriesTitle;
	protected static Integer privateEpisodesNumberOfSwipes;
	protected static String publicEpisodesSeriesTitle;
	protected static Integer publicEpisodesNumberOfSwipes;
	protected static String privateEpisodeTitle;
	protected static Integer privateEpisodeSeasonNumber;
	protected static String publicEpisodeTitle;
	protected static Integer publicEpisodeSeasonNumber;
	protected static String firstSeriesTitle;
	protected static String firstMovieTitle;
	protected static String firstFightTitle;
	protected static String firstTitleOnHomeScreen;
	protected static String firstSeriesDescription;
	protected static String allShowsFirstSeriesTitle;
	protected static String allShowsLastSeriesTitle;
	protected static String username;
	protected static String password;
	protected static String usernameInt;
	protected static String passwordInt;
	protected static String providerName;
	protected static Integer seriesPosition;
	protected static Integer moviePosition;
	protected static Integer fightPosition;
	protected static Integer seriesSwips;
	protected static Integer movieSwips;
	protected static Integer fightSwips;
	protected static Boolean seriesHasFullEpisodes;
	protected static Boolean seriesHasExtras;
	protected static Boolean movieHasVideo;
	protected static Boolean movieHasTrailer;
	protected static Boolean fightHasVideo;
	protected static Boolean fightHasExtras;
	protected static String firstEventWithPublicLongformAndShortform;
	protected static Integer EventWithPublicLongformAndShortformPosition;
	protected static Integer EventWithPublicLongformAndShortformSwips;
	protected static String firstEventWithPublicOrPrivateLongformAndShortform;
	protected static Integer EventWithPublicOrPrivateLongformAndShortformPosition;
	protected static Integer EventWithPublicOrPrivateLongformAndShortformSwips;
	protected static String episodeTitle;
	protected static String clipTitle;
	protected static String publicOrPrivateEpisodeTitle;
	protected static String publicOrPrivateClipTitle;
	protected static boolean searchServiceEnabled = false;
	protected static boolean AllShowsEnabled = false;
	protected static Integer NumberOfSeriesAvailableForAllShows ;

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
		alerts = new Alerts();
		providerManager = new ProviderManager();
		propertyFilter = new HomePropertyFilter(NON_PAGINATED);
		allShowsPropertyFilter = new AllShowsPropertyFilter(NON_PAGINATED);
		clips = new Clips();
		contact = new Contact();
		legalNotices = new LegalNotices();
		privacyPolicy = new PrivacyPolicy();
		progressBar = new ProgressBar();
		termsAndConditions = new TermsAndConditions();
		allShows = new AllShows();
	}

	protected void smokeSetupTest() {

		initializePageObjects();
		
		searchServiceEnabled = MainConfig.isSearchServiceEnabled();
        AllShowsEnabled = MainConfig.isAllShowsEnabled();
        Logger.logMessage("Search service enabled: " + searchServiceEnabled);
    	Logger.logMessage("AllShows enabled: " + AllShowsEnabled );
    	
		propertyFilter = new HomePropertyFilter(NON_PAGINATED);
		AllEventResultsOnHomeScreen = propertyFilter.propertyResults;
		firstTitleOnHomeScreen = propertyFilter.propertyFilter().getFirstProperty().getPropertyTitle();
		AllEventResultsOnHomeScreen = propertyFilter.propertyResults;
		firstTitleOnHomeScreen = propertyFilter.propertyFilter().getFirstProperty().getPropertyTitle();

		// get the first content type data on home screen
		propertyFilter = new HomePropertyFilter(NON_PAGINATED);
		AllEventResultsOnHomeScreen = propertyFilter.propertyResults;
		if (!AllEventResultsOnHomeScreen.withSeries().isEmpty()) {
			PropertyResult firstSeriesOnHomeScreen = propertyFilter.withSeries().propertyFilter().getFirstProperty();
			firstSeriesTitle = firstSeriesOnHomeScreen.getPropertyTitle();
			seriesPosition = firstSeriesOnHomeScreen.getPositionOnCarousel();
			seriesSwips = firstSeriesOnHomeScreen.getNumOfSwipes();
			seriesHasFullEpisodes = firstSeriesOnHomeScreen.hasEpisodes();
			seriesHasExtras = firstSeriesOnHomeScreen.hasClips();
			firstSeriesDescription = firstSeriesOnHomeScreen.getPropertyDescription();
		} else {
			Logger.logConsoleMessage("<-------- Series Content Type Not Available On Homescreen So 1 to 4 Check Point :- Skipped  ------>");
		}

		// get the first movie content type data on home screen
		propertyFilter = new HomePropertyFilter(NON_PAGINATED);
		AllEventResultsOnHomeScreen = propertyFilter.propertyResults;
		if (!AllEventResultsOnHomeScreen.withMovie().isEmpty()) { 
			PropertyResult firstMovieOnHomeScreen = propertyFilter.withMovie().propertyFilter().getFirstProperty();
			firstMovieTitle = firstMovieOnHomeScreen.getPropertyTitle();
			moviePosition = firstMovieOnHomeScreen.getPositionOnCarousel();
			movieSwips = firstMovieOnHomeScreen.getNumOfSwipes();
			movieHasTrailer = firstMovieOnHomeScreen.hasTrailer();
			movieHasVideo = firstMovieOnHomeScreen.hasMovie();
		} else {
			Logger.logConsoleMessage("<-------- Movie Content Type Not Available On Homescreen So 5 to 8 Check Point :- Skipped  ------>");
		}
		
		// get the first fight content type data on home screen
		propertyFilter = new HomePropertyFilter(NON_PAGINATED);
		AllEventResultsOnHomeScreen = propertyFilter.propertyResults;
		if (!AllEventResultsOnHomeScreen.withFight().isEmpty()) { 
			PropertyResult firstFightOnHomeScreen = propertyFilter.withFight().propertyFilter().getFirstProperty();
			firstFightTitle = firstFightOnHomeScreen.getPropertyTitle();
			fightPosition = firstFightOnHomeScreen.getPositionOnCarousel();
			fightSwips = firstFightOnHomeScreen.getNumOfSwipes();
			fightHasVideo = firstFightOnHomeScreen.hasPlaylist();
			fightHasExtras = firstFightOnHomeScreen.hasClips();
		} else {
			Logger.logConsoleMessage("<-------- Fight Content Type Not Available On Homescreen So 9 to 12 Check Point :- Skipped  ------>");
		}

		allShowsPropertyFilter = new AllShowsPropertyFilter(NON_PAGINATED);
		PropertyResults allShowsPropertyResults = allShowsPropertyFilter.propertyFilter();
		List<String> seriesTitles = allShowsPropertyResults.getPropertyTitles();
		NumberOfSeriesAvailableForAllShows = seriesTitles.size();
		allShowsFirstSeriesTitle = seriesTitles.get(0);
		allShowsLastSeriesTitle = seriesTitles.get(seriesTitles.size() - 1);
		
		// get the first content type data on home screen with longform/shortform (Public/Private) available 
		try {
			propertyFilter = new HomePropertyFilter(NON_PAGINATED);
			AllEventResultsOnHomeScreen = propertyFilter.propertyResults;
			for (PropertyResult event : AllEventResultsOnHomeScreen) { 
				if ((event.hasEpisodes() && event.hasClips()) || (event.hasMovie() && event.hasTrailer())) {
					if ((event.hasPublicEpisodes() && event.hasPublicClips()) || (event.hasPublicMovies() && event.hasPublicTrailers())) {
						firstEventWithPublicLongformAndShortform = event.getPropertyTitle();
						EventWithPublicLongformAndShortformPosition = event.getPositionOnCarousel();
						EventWithPublicLongformAndShortformSwips = event.getNumOfSwipes();
					    episodeTitle = event.getLongformFilter().withPublicEpisodes().episodesFilter().getFirstEpisode().getEpisodeTitle();
					    clipTitle = event.getShortformFilter().withPublicClips().clipsFilter().getFirstClip().getClipTitle();
						break;
					}
					if ((event.hasEpisodes() && event.hasClips()) || (event.hasMovie() && event.hasTrailer())) {
						firstEventWithPublicOrPrivateLongformAndShortform = event.getPropertyTitle();
						EventWithPublicOrPrivateLongformAndShortformPosition = event.getPositionOnCarousel();
						EventWithPublicOrPrivateLongformAndShortformSwips = event.getNumOfSwipes();
					    publicOrPrivateEpisodeTitle = event.getLongformFilter().episodesFilter().getFirstEpisode().getEpisodeTitle();
					    publicOrPrivateClipTitle = event.getShortformFilter().clipsFilter().getFirstClip().getClipTitle();
						break;
					}
				} else {
					Logger.logConsoleMessage("<-------- " + event.getPropertyTitle() + " doesn't contain Longform & shortform ------>");
				}
			}
		} catch (Exception e) {
			Logger.logConsoleMessage("<-------- Meta data collection Error for VideoPlaybackTogglePlayPauseTest " + e + " ------>");
		}

	}
}
