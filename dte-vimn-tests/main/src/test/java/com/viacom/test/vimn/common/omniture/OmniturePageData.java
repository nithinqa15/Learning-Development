package com.viacom.test.vimn.common.omniture;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.vimn.common.util.CommandExecutorUtils;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.BrandDataFactory;

import java.util.Map;
@SuppressWarnings("unused")
public class OmniturePageData {
    private static final String TRUE = "true";
    //private static final String SHOWS_PAGE = "Show Page"; //revisit after meeting
    private static final String HOME_PAGE = "home page";
    private static final String PAGEVIEW = "pageView";
    //private static final String MAIN_SCREEN_P1 = "MainScreen - P1"; //revisit after meeting
	private static final String MAIN_SCREEN_P1 = "MainScreen - P1";
    private static final String MAIN_LAUNCH_SCREEN = "Main Launch Screen";
    private static final String SHOW_SCREEN = "Show Screen";
    private static final String EPG_SCREEN = "EPG Screen";
    private static final String EPG_PAGE = "EPG page";
    private static final String MAINSCREEN_P1 = "MainScreen - P1";
    private static final String ALL_SHOWS_GRID = "All Shows Grid";
    private static final String SETTINGS = "Settings";
    private static final String IOS_SETTINGS_SCREEN = "Settings Screen";
    private static final String ANDROID_SETTINGS_SCREEN = "Settings";
    private static final String MAIN_SHOWS_SCREEN = "Main Shows Screen";
    private static final String BROWSE_ALL = "browse all";
    private static final String NO_FRANCHISE = "no-franchise";
	private static final String MAIN_SHOWS_PAGE = "Main Shows page";
    private static final String IOS_SETTINGS_PAGE = "Settings page";
    private static final String ANDROID_SETTINGS_PAGE = "settings page";
    private static final String COMPONENT_CHECK = "ComponentCheck";
	private static final String APP_ID = new BrandDataFactory().getOmnitureAppId();
	private static final String APP_NAME = new BrandDataFactory().getOmnitureAppName();

    private static Map<String,String> buildGeneralMap() {
        return new OmnitureMap()
                .activity(PAGEVIEW)
                .pv(TRUE)
                .hourD(OmnitureUtils.getHourD(TestRun.isIos() ? CommandExecutorUtils.getDeviceDate() : DriverManager.getAndroidDriver().getDeviceTime()))
                .dayW(OmnitureUtils.getDayW())
                //.brandId(OmnitureUtils.getBrandID()) //revisit after meeting
                //.appID(OmnitureUtils.getAppID()) //revisit after meeting
                .build();
    }
   
    public static Map<String,String> buildMainLaunchScreenExpectedMap(String seriesTitle, String prevPageName) {
        return new OmnitureMap()
                //.pageName(MAIN_SCREEN_P1) //revisit after meeting
                .channel(MAIN_LAUNCH_SCREEN)
                .pageType(HOME_PAGE)
                //.appId(APP_ID) //revisit after meeting
                //.appName(OmnitureUtils.getAppNameValueForPageCalls()) //revisit after meeting
                //.pageFranchise(seriesTitle)
                //.prevPageName(prevPageName) //revisit after meeting
                .mapBuilder() 
                .plus(buildGeneralMap());
    }
    
    public static Map<String,String> buildMainScreenCarouselExpectedMap(String seriesTitle,
                                                                        String previousSeriesPosition,
                                                                        String seriesPosition) {
        return new OmnitureMap()
                .channel(MAIN_LAUNCH_SCREEN)
                .pageType(HOME_PAGE)
                .pageName(OmnitureUtils.getPrevPagNameFromHome(seriesPosition))
                .appId(APP_ID)
                .prevPageName(OmnitureUtils.getPrevPageNameForSwipingCarousel(previousSeriesPosition))
                //.appName(OmnitureUtils.getAppNameValueForPageCalls()) //revisit after meeting
                //.pageFranchise(seriesTitle) //revisit after meeting
                .mapBuilder()
                .plus(buildGeneralMap());
    }

    public static Map<String,String> buildNewEpisodeSelectionCallExpectedMap(String seriesTitle, String positionOnCarousel) {
        return new OmnitureMap()
                .pageName(OmnitureUtils.getSeriesDetailPageName(seriesTitle))
                .channel(SHOW_SCREEN)
                .prevPageName(OmnitureUtils.getPrevPageNameForSwipingCarousel(positionOnCarousel))
                .pageFranchise(seriesTitle)
                //.pageType(SHOWS_PAGE) //revisit after meeting
                .appName(OmnitureUtils.getAppNameValueForPageCalls())
                .mapBuilder()
                .plus(buildGeneralMap());
    }

    public static Map<String,String> buildNewSeasonSelectionCallExpectedMap(String seriesTitle, String positionOnCarousel) {
        return new OmnitureMap()
                .pageName(OmnitureUtils.getSeriesDetailPageName(seriesTitle))
                .channel(SHOW_SCREEN)
                .prevPageName(OmnitureUtils.getPrevPageNameForSwipingCarousel(positionOnCarousel))
                .pageFranchise(seriesTitle)
                //.pageType(SHOWS_PAGE) //revisit after meeting
                .appName(OmnitureUtils.getAppNameValueForPageCalls())
                .mapBuilder()
                .plus(buildGeneralMap());
    }

    public static Map<String,String> buildEPGScreenCallExpectedMap(String seriesTitle) {
        return new OmnitureMap()
                .pageName(OmnitureUtils.getSeriesDetailPageName(seriesTitle))
                .channel(EPG_SCREEN)
                .prevPageName(MAINSCREEN_P1)
                .pageFranchise(seriesTitle)
                .pageType(EPG_PAGE)
                .mapBuilder()
                .plus(buildGeneralMap());
    }

    public static Map<String,String> buildSeriesDetailsViewExpectedMap(String seriesTitle, String seriesIndex) {
        return new OmnitureMap()
                //.pageName(OmnitureUtils.getSeriesDetailPageName(seriesTitle)) //revisit after meeting
                .channel(SHOW_SCREEN)
                //.prevPageName(OmnitureUtils.getPrevPageNameForSwipingCarousel(seriesIndex)) //revisit after meeting
                //.pageFranchise(seriesTitle) // Revisit 
                //.showPosition(seriesIndex) //revisit after meeting
                //.pageType(SHOWS_PAGE) //revisit after meeting
                .mapBuilder()
                .plus(buildGeneralMap());
    }
    
    public static Map<String,String> buildMainShowsScreenExpectedMap() {
        return new OmnitureMap()
                //.pageName(ALL_SHOWS_GRID) //revisit after meeting
                .prevPageName(MAINSCREEN_P1)
                //.channel(MAIN_SHOWS_SCREEN) //revisit after meeting
                .pageFranchise(NO_FRANCHISE)
                //.appId(APP_ID)
                //.appName(APP_NAME) //revisit after meeting
                //.pageType(MAIN_SHOWS_PAGE) //revisit after meeting
                .mapBuilder()
                .plus(buildGeneralMap());
    }
    
    public static Map<String,String> buildSettingsScreenExpectedMap() {
        return new OmnitureMap()
                .pageName(SETTINGS) //revisit after meeting
                .prevPageName(MAINSCREEN_P1)
                .channel(TestRun.isIos() ? IOS_SETTINGS_SCREEN : ANDROID_SETTINGS_SCREEN) //revisit after meeting
                //.pageFranchise(NO_FRANCHISE)
                //.appId(APP_ID)
                //.appName(APP_NAME) //revisit after meeting
                .pageType(TestRun.isIos() ? IOS_SETTINGS_PAGE : ANDROID_SETTINGS_PAGE) //revisit after meeting
                .mapBuilder()
                .plus(buildGeneralMap());
    }
    
    public static Map<String,String> buildComponentCheckExpectedMap(String bentoVersion, String tveVersion, String playerVersion) {
        return new OmnitureMap()
        		 //.activity(COMPONENT_CHECK) 
        		.bentoVersion(bentoVersion)
        		.tveVersion(tveVersion)
        		.playerVersion(playerVersion)
                .build();
    }
}
