package com.viacom.test.vimn.common.omniture;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

import com.viacom.test.vimn.common.fluentmapbuilder.FluentMapBuilder;
import com.viacom.test.vimn.common.util.TestRun;

public class OmnitureMap extends FluentMapBuilder {

    private static final String VID_TITLE = "vidTitle";
    private static final String PAGE_NAME = "pageName";
    private static final String VID_FRANCHISE = "vidFranchise";
    private static final String ACT_PAGE_NAME = "actPageName";
    private static final String ACTIVITY = "activity";
    private static final String PV = "pv";
    private static final String PAGE_TYPE = "pageType";
    private static final String ACT_NAME = "actName";
    private static final String DEST_URL = "destURL";
    private static final String HOURD = "hourD";
    private static final String DAYW = "dayW";
    private static final String BRAND_ID = "brandID";
    private static final String APP_ID = "AppID";
    private static final String SOC_METHOD = "socMethod";
    private static final String CHANNEL = "channel";
    private static final String APP_NAME = "appName";
    private static final String APP_CLUSTER = "clusterName";
    private static final String APP_COUNTRY = "countryName";
    private static final String APP_LANGUAGE = "language";
    private static final String CONTENT_BUCKET = "contentBucket";
    private static final String PAGE_FRANCHISE = "pageFranchise";
    private static final String PREV_PAGE_NAME = "prevPageName";
    private static final String APPID = "appID";
    private static final String SHOW_POSITION = "showPosition";
    private static final String VID_OWNER = "vidOwner";
    private static final String VID_APP_NAME = "vidAppName";
    private static final String PLAYER_NAME = "playerName";
    private static final String VIEW = "view";
    private static final String SEASON_N = "seasonN";
    private static final String EPISODE_N = "episodeN";
    private static final String LINEAR_PUB_DATE = "linearPubDate";
    private static final String MGID = "mgid";
    private static final String VID_LENGTH = "vidLength";
    private static final String VID_EP_TITLE = "vidEpTitle";
    private static final String EP_LENGTH = "epLength";
    private static final String EP_MGID = "epMGID";
    private static final String FRIENDLY_NAME = "friendlyName";
    private static final String LENGTH = "length";
    private static final String NAME = "name";
    private static final String VID_CONTENT_TYPE = "vidContentType";
    private static final String EP_COUNT = "epCount";
    private static final String CONTENT_TYPE = "contentType";
    private static final String TVE_USER_STAT = "tveUsrStat";
    private static final String TVE_MVPD = "tveMVPD";
    private static final String CONTENT_STATUS = "contentStatus";
    private static final String TVE_STEP = "tveStep";
    private static final String BENTO_VERSION = "bentoVersion";
    private static final String TVE_VERSION = "tveVersion";
    private static final String PLAYER_VERSION = "playerVersion";
    private static final String VID_MGID = "vidMGID";

    public OmnitureMap vidTitle(final String vidTitleValue) {
        variable(VID_TITLE).value(vidTitleValue);
        return this;
    }

    public OmnitureMap vidMGID(final String vidMGID) {
        variable(VID_MGID).value(vidMGID);
        return this;
    }

    public OmnitureMap pageName(final String pageNameValue) {
        variable(PAGE_NAME).value(pageNameValue);
        return this;
    }

	public OmnitureMap vidFranchise(final String vidFranchiseValue) {
		if (TestRun.isAndroid() && TestRun.isParamountApp()) {
			String seriesTitle = "";
			List<String> seriesTitleWords = Arrays.asList(StringUtils.splitByWholeSeparator(vidFranchiseValue.toLowerCase(), " "));
			for (String str : seriesTitleWords) {
				seriesTitle = seriesTitle + " " + StringUtils.capitalize(str);
			}
			variable(VID_FRANCHISE).value(StringUtils.capitalize(seriesTitle.trim()));
		} else if (TestRun.isAndroid() && TestRun.isMTVDomesticApp()) {
			String seriesTitle = "";
			List<String> seriesTitleWords = Arrays.asList(StringUtils.splitByWholeSeparator(vidFranchiseValue.toLowerCase(), " "));
			seriesTitleWords.removeIf(str -> str.equals("+"));
			int count = 1;
			for (String str : seriesTitleWords) {
				if (count == 2)
					seriesTitle = seriesTitle + " " + StringUtils.capitalize(str).concat(":");
				else
					seriesTitle = seriesTitle + " " + StringUtils.capitalize(str);
				++count;
			}
			variable(VID_FRANCHISE).value(StringUtils.capitalize(seriesTitle.trim()));
		} else {
			variable(VID_FRANCHISE).value(vidFranchiseValue);
		}
		return this;
	}

    public OmnitureMap actPageName(final String actPageNameValue) {
        variable(ACT_PAGE_NAME).value(actPageNameValue);
        return this;
    }

    public OmnitureMap activity(final String activityValue) {
        variable(ACTIVITY).value(activityValue);
        return this;
    }

    public OmnitureMap pv(final String pvValue) {
        variable(PV).value(pvValue);
        return this;
    }

    public OmnitureMap pageType(final String pageTypeValue) {
        variable(PAGE_TYPE).value(pageTypeValue);
        return this;
    }

    public OmnitureMap actName(final String actNameValue) {
        variable(ACT_NAME).value(actNameValue);
        return this;
    }

    public OmnitureMap destURL(final String destURLValue) {
        variable(DEST_URL).value(destURLValue);
        return this;
    }

    public OmnitureMap hourD(final String hourDValue) {
        variable(HOURD).value(hourDValue);
        return this;
    }

    public OmnitureMap dayW(final String dayWValue) {
        variable(DAYW).value(dayWValue);
        return this;
    }

    public OmnitureMap brandId(final String brandIdValue) {
        variable(BRAND_ID).value(brandIdValue);
        return this;
    }

    public OmnitureMap appID(final String appIDValue) {
        variable(APP_ID).value(appIDValue);
        return this;
    }

    public OmnitureMap socMethod(final String socMethodValue) {
        variable(SOC_METHOD).value(socMethodValue);
        return this;
    }

    public OmnitureMap channel(final String channelValue) {
        variable(CHANNEL).value(channelValue);
        return this;
    }

    public OmnitureMap appName(final String appNameValue) {
        variable(APP_NAME).value(appNameValue);
        return this;
    }

    public OmnitureMap appCluster(final String appClusterValue) {
        variable(APP_CLUSTER).value(appClusterValue);
        return this;
    }

    public OmnitureMap appCountry(final String appCountryValue) {
        variable(APP_COUNTRY).value(appCountryValue);
        return this;
    }

    public OmnitureMap appLanguage(final String appLanguageValue) {
        variable(APP_LANGUAGE).value(appLanguageValue);
        return this;
    }

    public OmnitureMap contentBucket(final String contentBucket) {
        variable(CONTENT_BUCKET).value(contentBucket);
        return this;
    }

    public OmnitureMap pageFranchise(final String pageFranchiseValue) {
        variable(PAGE_FRANCHISE).value(pageFranchiseValue);
        return this;
    }

    public OmnitureMap prevPageName(final String prevPageNameValue) {
        variable(PREV_PAGE_NAME).value(prevPageNameValue);
        return this;
    }

    public OmnitureMap appId(final String appIDValue) {
        variable(APPID).value(appIDValue);
        return this;
    }

    public OmnitureMap showPosition(final String showPositionValue) {
        variable(SHOW_POSITION).value(showPositionValue);
        return this;
    }

    public OmnitureMap vidOwner(final String vidOwnerValue) {
        variable(VID_OWNER).value(vidOwnerValue);
        return this;
    }

    public OmnitureMap vidAppName(final String vidAppNameValue) {
        variable(VID_APP_NAME).value(vidAppNameValue);
        return this;
    }

    public OmnitureMap playerName(final String playerNameValue) {
        variable(PLAYER_NAME).value(playerNameValue);
        return this;
    }

    public OmnitureMap view(final String viewValue) {
        variable(VIEW).value(viewValue);
        return this;
    }

    public OmnitureMap seasonN(final String seasonNValue) {
        variable(SEASON_N).value(seasonNValue);
        return this;
    }

    public OmnitureMap episodeN(final String episodeNValue) {
        variable(EPISODE_N).value(episodeNValue);
        return this;
    }

    public OmnitureMap linearPubDate(final String linearPubDateValue) {
        variable(LINEAR_PUB_DATE).value(linearPubDateValue);
        return this;
    }

    public OmnitureMap mgid(final String mgidValue) {
        variable(MGID).value(mgidValue);
        return this;
    }

    public OmnitureMap vidLength(final String vidLengthValue) {
        variable(VID_LENGTH).value(vidLengthValue);
        return this;
    }

    public OmnitureMap vidEpTitle(final String vidEpTitleValue) {
        variable(VID_EP_TITLE).value(vidEpTitleValue);
        return this;
    }

    public OmnitureMap epLength(final String epLengthValue) {
        variable(EP_LENGTH).value(epLengthValue);
        return this;
    }

    public OmnitureMap epMGID(final String epMGIDValue) {
        variable(EP_MGID).value(epMGIDValue);
        return this;
    }

    public OmnitureMap friendlyName(final String friendlyNameValue) {
        variable(FRIENDLY_NAME).value(friendlyNameValue);
        return this;
    }

    public OmnitureMap length(final String lengthValue) {
        variable(LENGTH).value(lengthValue);
        return this;
    }

    public OmnitureMap name(final String nameValue) {
        variable(NAME).value(nameValue);
        return this;
    }

    public OmnitureMap vidContentType(final String vidContentTypeValue) {
        variable(VID_CONTENT_TYPE).value(vidContentTypeValue);
        return this;
    }

    public OmnitureMap contentStatus(final String contentStatusValue) {
        variable(CONTENT_STATUS).value(contentStatusValue);
        return this;
    }

    public OmnitureMap epCount(final String epCountValue) {
        variable(EP_COUNT).value(epCountValue);
        return this;
    }

    public OmnitureMap contentType(final String contentTypeValue) {
        variable(CONTENT_TYPE).value(contentTypeValue);
        return this;
    }

    public OmnitureMap tveUsrStat(final String tveUsrStatValue) {
        variable(TVE_USER_STAT).value(tveUsrStatValue);
        return this;
    }

    public OmnitureMap tveMVPD(final String tveMVPDValue) {
        variable(TVE_MVPD).value(tveMVPDValue);
        return this;
    }

    public OmnitureMap tveStep(final String tveStepValue) {
        variable(TVE_STEP).value(tveStepValue);
        return this;
    }
    
    public OmnitureMap bentoVersion(final String bentoVersionValue) {
        variable(BENTO_VERSION).value(bentoVersionValue);
        return this;
    }
    
    public OmnitureMap tveVersion(final String tveVersionValue) {
        variable(TVE_VERSION).value(tveVersionValue);
        return this;
    }
    
    public OmnitureMap playerVersion(final String playerVersionValue) {
        variable(PLAYER_VERSION).value(playerVersionValue);
        return this;
    }
}
