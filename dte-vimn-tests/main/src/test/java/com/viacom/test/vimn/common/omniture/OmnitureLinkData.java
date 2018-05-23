package com.viacom.test.vimn.common.omniture;

import com.viacom.test.vimn.common.util.CommandExecutorUtils;

import java.util.Map;

@SuppressWarnings("unused")
public class OmnitureLinkData {

    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String ACTION_CALL = "actionCall";
    private static final String CLIPREEL_PAGE = "clipreel page";
    //private static final String NO_VIDTITLE = "no-vidTitle"; //revisit after meeting
	private static final String NO_VIDTITLE = "no-vidTitle";
    private static final String VERTICAL_SWIPE = "verticalSwipe";
    private static final String NO_DESTINATION = "no destination";
    private static final String VIDEO_PAGE = "video page";
    private static final String WATCH_EPISODE = "watchEpisode";
    //private static final String SHOWS_PAGE = "show page"; //revisit after meeting
    private static final String CLIP_TAB_CHANGE = "clipTabChange";
    private static final String EPISODE_TAB_CHANGE = "episodeTabChange";
    private static final String SETTINGS_PAGE = "settings page";
    //private static final String NO_FRANCHISE = "no-vidFranchise"; //revisit after meeting
    private static final String NO_FRANCHISE = "no-vidFranchise";
    private static final String SETTINGS = "Settings";
    private static final String HOME_PAGE = "home page";
    private static final String BK_ENGAGE_IMG = "bkEngageImg";
    private static final String BK_ENGAGE_VID = "bkEngageVid";
    private static final String VID_FULL_SCREEN = "vidfullscreen";
    private static final String VID_PLAYBACK = "vidPlayback";
    private static final String APPLE_NOTES = "Apple Notes";
    private static final String SOC_SHARE = "socShare";

    private static Map<String,String> buildGeneralMap() {
        return new OmnitureMap()
                .activity(ACTION_CALL)
                //.hourD(OmnitureUtils.getHourD(TestRun.isIos() ? CommandExecutorUtils.getDeviceDate() : DriverManager.getAndroidDriver().getDeviceTime()))
                //.dayW(OmnitureUtils.getDayW())
                //.brandId(OmnitureUtils.getBrandID()) // MC-6958
                //.appID(OmnitureUtils.getAppID()) // MC-6958
                .build();
    }

    public static Map<String,String> buildVerticalSwipeOnClipsExpectedMap(String seriesTitle, String seriesPosition, String version) {
        return new OmnitureMap()
                .pageName(OmnitureUtils.getPageName(version))
                .vidFranchise(seriesTitle)
                .actPageName(OmnitureUtils.getPrevPagNameFromHome(seriesPosition))
                .pv(TRUE)
                .pageType(CLIPREEL_PAGE)
                //.vidTitle(NO_VIDTITLE) //revisit after meeting
                .actName(VERTICAL_SWIPE)
                .destURL(NO_DESTINATION)
                .mapBuilder()
                .plus(buildGeneralMap());
    }

    public static Map<String,String> buildOmnitureTapWatchEpisodeButtonOnClipsViewExpectedMap(String seriesTitle, String version, String clipTitle, String seriesPosition) {
        return new OmnitureMap()
        		.pageName(OmnitureUtils.getPageName(CommandExecutorUtils.getVersionCode()))
                .pv(FALSE)
                .pageType(VIDEO_PAGE)
                //.vidFranchise(seriesTitle) //revisit after meeting
                //.vidTitle(clipTitle) //revisit after meeting
                .actName(WATCH_EPISODE)
                //.destURL(clipTitle) //revisit after meeting
                //.actPageName(OmnitureUtils.getPrevPagNameFromHome(seriesPosition)) //revisit after meeting
                .mapBuilder()
                .plus(buildGeneralMap());
    }

    public static Map<String,String> buildClipToEpisodeTabChangeExpectedMap(String seriesTitle, String version, String episodeTitle) {
        return new OmnitureMap()
        		//.pageName(OmnitureUtils.getPageName(CommandExecutorUtils.getVersionCode())) //revisit after meeting
                .pv(FALSE)
                //.pageType(SHOWS_PAGE) //revisit after meeting
                .vidFranchise(seriesTitle)
                .vidTitle(episodeTitle)
                .actName(EPISODE_TAB_CHANGE)
                .destURL(episodeTitle)
                .mapBuilder()
                .plus(buildGeneralMap());
    }

    public static Map<String,String> buildEpisodeToClipTabChangeExpectedMap(String seriesTitle, String version, String episodeTitle) {
        return new OmnitureMap()
        		//.pageName(OmnitureUtils.getPageName(CommandExecutorUtils.getVersionCode())) // revisit after meeting
                .pv(FALSE)
                //.pageType(SHOWS_PAGE) // revisit after meeting
                .vidFranchise(seriesTitle)
                //.vidTitle(episodeTitle) //revisit
                .actName(CLIP_TAB_CHANGE)
                //.destURL(episodeTitle) //revisit
                .mapBuilder()
                .plus(buildGeneralMap());
    }

    public static Map<String, String> buildCellularVideoPlaybackExpectedMap(String version, String actName) {
        return new OmnitureMap()
                //.pageName(OmnitureUtils.getPageName(version))
                .pv(FALSE)
                .pageType(SETTINGS_PAGE)
                //.vidFranchise(NO_FRANCHISE)  // revisit after meeting
                //.vidTitle(NO_VIDTITLE)  // revisit after meeting
                .actName(actName)
                .destURL(NO_DESTINATION)
                .actPageName(SETTINGS)
                .mapBuilder()
                .plus(buildGeneralMap());
    }

    public static Map<String, String> buildAutoplayExpectedMap(String version, String actName) {
        return new OmnitureMap()
                //.pageName(OmnitureUtils.getPageName(version))
                .pv(FALSE)
                .pageType(SETTINGS_PAGE)
                //.vidFranchise(NO_FRANCHISE)  // revisit after meeting
                //.vidTitle(NO_VIDTITLE)  // revisit after meeting
                .actName(actName)
                .destURL(NO_DESTINATION)
                .actPageName(SETTINGS)
                .mapBuilder()
                .plus(buildGeneralMap());
    }

    public static Map<String, String> buildAutoplayClipsExpectedMap(String version, String actName) {
        return new OmnitureMap()
                //.pageName(OmnitureUtils.getPageName(version))
                .pv(FALSE)
                .pageType(SETTINGS_PAGE)
                //.vidFranchise(NO_FRANCHISE) // revisit after meeting
                //.vidTitle(NO_VIDTITLE) // revisit after meeting
                .actName(actName)
                .destURL(NO_DESTINATION)
                .actPageName(SETTINGS)
                .mapBuilder()
                .plus(buildGeneralMap());
    }

    public static Map<String,String> buildBackgroundImageEngagementHomeScreenExpectedMap(String seriesTitle, String seriesPosition, String version, String ClipTitle) {
        return new OmnitureMap()
        		.pageName(OmnitureUtils.getPageName(CommandExecutorUtils.getVersionCode()))
                .pv(FALSE)
                .pageType(HOME_PAGE)
                .vidFranchise(seriesTitle)
                //.vidTitle(NO_VIDTITLE) //revisit after meeting
                .actName(BK_ENGAGE_IMG)
                .destURL(ClipTitle)
                .actPageName("Show/" + seriesTitle)
                .mapBuilder()
                .plus(buildGeneralMap());
    }

    public static Map<String,String> buildBackgroundVideoEngagementHomeScreenExpectedMap(String seriesTitle, String seriesPosition, String version) {
        return new OmnitureMap()
                .pageName(OmnitureUtils.getPageName(version))
                .pv(FALSE)
                .pageType(HOME_PAGE)
                .vidFranchise(seriesTitle)
                //.vidTitle(NO_VIDTITLE) //revisit after meeting
                .actName(BK_ENGAGE_VID)
                .destURL(NO_DESTINATION)
                .actPageName(OmnitureUtils.getPrevPagNameFromHome(seriesPosition))
                .mapBuilder()
                .plus(buildGeneralMap());
    }

    public static Map<String, String> buildVideoFullScreenExpectedMap(String seriesTitle, String version) {
        return new OmnitureMap()
                //.pageName(OmnitureUtils.getPageName(version)) // revisit after meeting
                .pv(FALSE)
                //.pageType(SHOWS_PAGE) // revisit after meeting
                .vidFranchise(seriesTitle)
                //.vidTitle(NO_VIDTITLE) // revisit after meeting
                //.actName(VID_FULL_SCREEN) // revisit after meeting
                //.destURL(NO_DESTINATION) // revisit after meeting
                //.actPageName("") // revisit after meeting
                .mapBuilder()
                .plus(buildGeneralMap());
    }

    public static Map<String, String> buildVideoPlayFromBeginExpectedMap(String seriesTitle, String episodeTitle) {
        return new OmnitureMap()
        		.pageName(OmnitureUtils.getPageName(CommandExecutorUtils.getVersionCode()))
                .pv(FALSE)
                //.pageType(SHOWS_PAGE) // revisit after meeting
                //.vidFranchise(episodeTitle) // revisit after meeting
                .vidTitle(episodeTitle)
                .actName(VID_PLAYBACK)
                .destURL(episodeTitle)
                //.actPageName("Show/" + episodeTitle) // revisit after meeting
                .mapBuilder()
                .plus(buildGeneralMap());
    }

    public static Map<String, String> buildShareExpectedMap(String seriesTitle, String version) {
        return new OmnitureMap()
                //.pageName(OmnitureUtils.getPageName(version)) // revisit after meeting
                .pv(FALSE)
                .pageType(VIDEO_PAGE)
                .vidFranchise(seriesTitle)
                //.vidTitle(NO_VIDTITLE) // revisit after meeting
                .actName(SOC_SHARE)
                .destURL(seriesTitle)
                //.actPageName("Show/" + seriesTitle) // revisit after meeting
                //.socMethod(APPLE_NOTES) // revisit after meeting
                .mapBuilder()
                .plus(buildGeneralMap());
    }

}
