package com.viacom.test.vimn.common.omniture;

import java.util.Map;

import com.viacom.test.vimn.common.util.TestRun;

public class OmnitureTVEData {

	private static final String USER_AUTH_CHECK = "userAuthCheck";
	//private static final String SETTINGS_NOT_AVAILABLE = "TVE:settings:Not available"; //revisit after meeting
	//private static final String SETTINGS_AUTH_REQUEST = "TVE:settings:MVPD Auth-Request"; //revisit after meeting
	private static final String iOS_SETTINGS_AUTH_SUCCESS = "TVE:settings:Successful login";
	private static final String ANDROID_SETTINGS_AUTH_SUCCESS = "TVE:Settings:Successful login";
	private static final String TVE_STEP_START = "TVE:Settings:MVPD Selector";
	private static final String TVE_AUTH_START = "tveAuthStart";
	private static final String TVE_NOT_LISTED = "tveMVPDnotListed";
	private static final String TVE_MVPD_PICK = "tveMVPDPick";
	private static final String TVE_SIGN_IN_COMPLETE = "tveSignComplete";
	private static final String TVE = "TVE";

	private static Map<String, String> buildGeneralMap() {
		return new OmnitureMap()
				//.appID(OmnitureUtils.getAppID()) //revisit after meeting
				.build();
	}

	public static Map<String, String> buildOmnitureTveAuthStartExpectedMap() {
		return new OmnitureMap()
				//.pageName(OmnitureUtils.getPageName(CommandExecutorUtils.getVersionCode())) //revisit after meeting
				//.tveUsrStat(tveUsrStat)
				//.tveMVPD(tveMVPD)
				.channel(TVE)
				.tveStep(TVE_STEP_START)
				.activity(TVE_AUTH_START)
				.mapBuilder()
				.plus(buildGeneralMap());
	}

	public static Map<String, String> buildOmnitureTVEUserAuthenticationStatusExpectedMap(String tveUsrStat, String tveMVPD) {
		return new OmnitureMap()
				//.pageName(OmnitureUtils.getPageName(CommandExecutorUtils.getVersionCode())) //revisit after meeting
				//.tveUsrStat(tveUsrStat)
				//.tveMVPD(tveMVPD)
				.activity(USER_AUTH_CHECK)
				.mapBuilder()
				.plus(buildGeneralMap());
	}

	public static Map<String, String> buildOmnitureSettingsPageMVPDNotListedExpectedMap(String version, String tveMVPD) {
		return new OmnitureMap()
				//.pageName(OmnitureUtils.getPageName(CommandExecutorUtils.getVersionCode())) //revisit after meeting
				//.tveMVPD(tveMVPD)
				//.tveStep(SETTINGS_NOT_AVAILABLE)
				.channel(TVE)
				.activity(TVE_NOT_LISTED)
				.mapBuilder()
				.plus(buildGeneralMap());
	}
	
	public static Map<String, String> buildTVEContentAuthenticationStatusExpectedMap(String contentStatus) {
		return new OmnitureMap()
				//.pageName(OmnitureUtils.getPageName(CommandExecutorUtils.getVersionCode())) //revisit after meeting
				.contentStatus(contentStatus)
				.mapBuilder()
				.plus(buildGeneralMap());
	}
	
	public static Map<String, String> buildOmnitureSettingsPageMVPDSelectedExpectedMap(String version, String tveMVPD) {
		return new OmnitureMap()
				//.pageName(OmnitureUtils.getPageName(CommandExecutorUtils.getVersionCode())) //revisit after meeting
				.tveMVPD(tveMVPD)
				//.tveStep(SETTINGS_AUTH_REQUEST)
				.activity(TVE_MVPD_PICK)
				.channel(TVE)
				.mapBuilder()
				.plus(buildGeneralMap());
	}
	
	public static Map<String, String> buildOmnitureSettingsPageTVELginSuccessfulExpectedMap(String tveMVPD, String tveUSrStat) {
		return new OmnitureMap()
				//.pageName(OmnitureUtils.getPageName(CommandExecutorUtils.getVersionCode())) //revisit after meeting
				.tveMVPD(tveMVPD)
				.tveStep(TestRun.isIos() ? iOS_SETTINGS_AUTH_SUCCESS : ANDROID_SETTINGS_AUTH_SUCCESS)
				.activity(TVE_SIGN_IN_COMPLETE)
				.channel(TVE)
				.tveUsrStat(tveUSrStat)
				.mapBuilder()
				.plus(buildGeneralMap());
	}
}
