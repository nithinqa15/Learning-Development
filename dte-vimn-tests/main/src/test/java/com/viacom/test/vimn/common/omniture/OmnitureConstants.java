package com.viacom.test.vimn.common.omniture;

public interface OmnitureConstants {

    String REPORT_URL_PATTERN = "viacom.sc.omtrdc.net";

    interface ExpectedParams {
        String EXPECTED_PARAM_SHOW_DETAILS_VIEW_iOS = "Show Page";
        String EXPECTED_PARAM_SHOW_DETAILS_VIEW = "shows page"; // revisit after meeting
        String EXPECTED_PARAM_ALL_SHOW_SCREEN = "All Shows Grid";
        String EXPECTED_PARAM_HOME_MAIN_SCREEN = "Main Launch Screen";
        String EXPECTED_PARAM_UNSUPPORTED_COUNTRY = "unsupportedCountryScreen";
        String EXPECTED_PARAM_COUNTRY_CHECK = "userCountryCheck";
        String EXPECTED_PARAM_EPG_SCREEN = "EPG Screen";
        String EXPECTED_PARAM_HORIZONTAL_SWIPE = "horizontalSwipe";
        String EXPECTED_PARAM_VERTICAL_SWIPE = "verticalSwipe";
        String EXPECTED_PARAM_SWIPE_FROM_HOME_TO_CLIP_REEL = "clipReelSwipe";
        String EXPECTED_PARAM_CLIP_TO_EPISODE_TAB_CHANGE = "episodeTabChange";
        String EXPECTED_PARAM_EPISODE_TO_CLIP_TAB_CHANGE = "clipTabChange";
        String EXPECTED_PARAM_BACKGROUND_IMAGE_ENGAGEMENT_ON_HOME_SCREEN = "bkEngageImg";
        String EXPECTED_PARAM_BACKGROUND_VIDEO_ENGAGEMENT_ON_HOME_SCREEN = "bkEngageVid";
        String EXPECTED_PARAM_TAP_WATCH_EPISODE_BUTTON_CLIPS_VIEW = "watchEpisode";
        String EXPECTED_PARAM_CELL_IOS_VID_PLAYBACK_OFF = "PlaybackSettingsOff";  
        String EXPECTED_PARAM_CELL_ANDROID_VID_PLAYBACK_OFF = "playbacksettingsOff";
        String EXPECTED_PARAM_CELL_IOS_VID_PLAYBACK_ON = "PlaybackSettingsOn";
        String EXPECTED_PARAM_CELL_ANDROID_VID_PLAYBACK_ON = "playbacksettingsOn";
        String EXPECTED_PARAM_AUTOPLAY_TOGGLE_ON = "vidautoplayOn";
        String EXPECTED_PARAM_AUTOPLAY_TOGGLE_OFF = "vidautoplayOff";
        String EXPECTED_PARAM_AUTOPLAYCLIPS_TOGGLE_ON = "clipautoplayOn";
        String EXPECTED_PARAM_AUTOPLAYCLIPS_TOGGLE_OFF = "clipautoplayOff";
        String EXPECTED_PARAM_CLIP_START = "clipStart";
        String EXPECTED_PARAM_PLAYLIST_START = "fepStart";
        String EXPECTED_PARAM_AD_START = "adStart";
        String EXPECTED_PARAM_LIVE_STREAM_START = "liveStreamStart";
        String EXPECTED_PARAM_TVE_AUTHENTICATION_STATUS = "userAuthCheck";
        String EXPECTED_PARAM_TVE_AUTH_START = "tveAuthStart";
        String EXPECTED_PARAM_VIDEO_FULL_SCREEN = "vidfullscreen";
        String EXPECTED_PARAM_VIDEO_PLAY_BEGIN = "vidPlayback";
        String EXPECTED_PARAM_TVE_SIGN_IN_COMPLETED = "tveSignComplete";
        String EXPECTED_PARAM_MVPD_SELECTED = "tveMVPDPick";
        String EXPECTED_PARAM_TVE_MVPD_NOT_LISTED = "tveMVPDnotListed";
        String EXPECTED_PARAM_SHARE = "socShare";
        String EXPECTED_PARAM_CONTENT_STATUS_LOCKED = "LOCKED";
        String EXPECTED_PARAM_CONTENT_STATUS_UNLOCKED = "UNLOCKED";
        String EXPECTED_PARAM_INSTALL_EVENT = "InstallEvent";
        String EXPECTED_PARAM_SETTINGS_SCREEN = "Settings";
        String EXPECTED_PARAM_COMPONENT_CHECK = "componentCheck";
        String EXPECTED_PARAM_SETTINGS_PAGENAME = "pageName";
    }

    interface PageName {
        String MAIN_SCREEN_PREFIX = "MainScreen - P";
        String MAIN_SCREEN_PREFIX_Android = "Main screen - P";
        String MAIN_SCREEN_PREFIX_FOR_PREV_PAGE_NAME = "MainScreen - P";
        String SHOW_TITLE_PREFIX = "Show/";
    }
}