package com.viacom.test.vimn.common.omniture;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.util.CommandExecutorUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.BrandDataFactory;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.viacom.test.vimn.common.omniture.OmnitureConstants.PageName.MAIN_SCREEN_PREFIX;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.PageName.MAIN_SCREEN_PREFIX_FOR_PREV_PAGE_NAME;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.PageName.SHOW_TITLE_PREFIX;

public class OmnitureUtils {

    public static String getDayW() {
        LocalDate date = LocalDate.now();
        DayOfWeek dow = date.getDayOfWeek();
        String dayW = dow.getDisplayName(TextStyle.FULL_STANDALONE,
                Locale.ENGLISH);
        return dayW;
    }

    public static String getHourD(String deviceDate) {
    	Logger.logConsoleMessage("Device Date: " + deviceDate); // Debug lab DeviceDate 
        List<String> deviceDateList = new ArrayList<>(Arrays.asList(deviceDate.split(" ")));
        deviceDateList.removeIf(String::isEmpty);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("H");
        DateTime dt = formatter.parseDateTime(deviceDateList.get(3).split(":")[0]);
        String hourD = formatter.print(dt);
        return hourD;
    }

    public static String getSeriesDetailPageName(String seriesTitle) {
        return SHOW_TITLE_PREFIX + seriesTitle;
    }

    public static String getPrevPagNameFromHome(String seriesPosition) {
        return MAIN_SCREEN_PREFIX + seriesPosition;
    }

    /*
    Hacky method mostly for android executions. Should be removed after defining correct value with Jaldhara and
    update values on both iOS and Android codebases.
     */
    // TODO - GF 27/09/2017 - Remove this method after defining the correct value for Main Screen Prefix
    public static String getPrevPageNameForSwipingCarousel(String seriesPosition) {
        if (seriesPosition.equals("0")) {
            return "N/A";
        } else {
            return MAIN_SCREEN_PREFIX_FOR_PREV_PAGE_NAME + seriesPosition;
        }
    }

    public static String getSeasonValue(String season) {
        return Integer.valueOf(season) > 1 ? "All Seasons" : "1 Season";
    }

    /**
     * Returns the value for AppID parameter (do no confuse with appID parameter!)
     * @return String AppID
     */
    public static String getAppID() {
        String appID = "";
        String omnitureAppName = new BrandDataFactory().getOmnitureAppName();
        if (TestRun.isAndroid()) {
            appID = omnitureAppName + " " + CommandExecutorUtils.getVersionName() + " (" + CommandExecutorUtils.getVersionCode() + ")";
        } else {
        	appID = omnitureAppName + " " + Config.ConfigProps.IOS_APP_VERSION + " (" + CommandExecutorUtils.getVersionCode() + ")";
        }
        return appID;
    }

    public static String getPageName(String version) {
        String omnitureAppName = new BrandDataFactory().getOmnitureAppName();
        String pageName;
        if (TestRun.isAndroid()) {
        	pageName = omnitureAppName + " " + version.replace("v", "");
        } else {
        	pageName = omnitureAppName + "/" + version;
        }
        return pageName;
    }

    public static String getPlayerName() {
        return TestRun.isAndroid() ? "Android Player" : "VMN-iOS-Player"; 
    }

    public static String getAppNameValueForPageCalls() {
        String omnitureAppName = new BrandDataFactory().getOmnitureAppName();
        String osVersion = TestRun.getMobileOS().value();
        return omnitureAppName + " " + osVersion ;
    }

    public static String getBrandID() {
    	return new BrandDataFactory().getOmnitureBrandId() + " " + LocaleDataFactory.getCluster();
    }

    public static String getMgidReport(String mgid) {
        return mgid.replace(new BrandDataFactory().getPlayerMgid(), new BrandDataFactory().getPlayerMgidReport());
    }

    public static String getMgidForLiveCalls(String segmentMgid) {
        return segmentMgid.replace("live", "video");
    }
}
