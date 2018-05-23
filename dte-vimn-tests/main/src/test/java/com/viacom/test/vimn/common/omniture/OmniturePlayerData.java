package com.viacom.test.vimn.common.omniture;

import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.BrandDataFactory;

import java.util.Map;

public class OmniturePlayerData {

    private static final String TRUE = "true";
    private static final String NO_FRANCHISE = "NO_FRANCHISE";
    private static final String LIVE_EVENT = "Live Event";
    private static final String NO_SEASON = "NO_SEASONN";
    private static final String NO_EPISODEN = "NO_EPISODEN";
    private static final String NO_LINPUBDATE = "NO_LINPUBDATE";
    //private static final String VIDEO_CLIP = "Video Clip";
    //private static final String UNLOCKED = "UNLOCKED";
    private static final String LOCKED = "Locked";
    private static final String CLIP_START = "clipStart";
    private static final String VOD = "vod";
    private static final String FEP_START = "fepStart";
    private static final String AD_START = "adStart";
    private static final String SHOW_VIDEO = "ShowVideo";
    private static final String LIVE_STREAM_START= "liveStreamStart";
    private static final String LIVE = "live";
    private static final String CONSTANT_LENGTH = "86400";
    private static final String CONSTANT_COUNT = "1";
    private static final String VID_OWNER = new BrandDataFactory().getVidOwner();
    private static final String CHANNEL_NAME = new BrandDataFactory().getChannel();

    private static Map<String, String> buildGeneralMap() {
        return new OmnitureMap()
        		//.pageName(OmnitureUtils.getPageName(CommandExecutorUtils.getVersionCode())) //revisit after meeting
                //.vidOwner(VID_OWNER)
                .vidAppName(OmnitureUtils.getPlayerName())
                //.channel(CHANNEL_NAME)
                .playerName(OmnitureUtils.getPlayerName())
                .view(TRUE)
                //.seasonN(NO_SEASON) //revisit after meeting
                //.episodeN(NO_EPISODEN) //revisit after meeting
                //.linearPubDate(NO_LINPUBDATE)
                .build();
    }

    public static Map<String, String> buildClipStartPlayerExpectedMap(String seriesTitle, String clipTitle, String mgid, String clipDuration) {
        return new OmnitureMap()
                //.mgid(OmnitureUtils.getMgidReport(mgid))
                //.vidLength(clipDuration)
                //.vidFranchise(seriesTitle) //revisit after meeting
                //.vidEpTitle(clipTitle)
                //.epLength(clipDuration)
                //.epMGID(OmnitureUtils.getMgidReport(mgid))
                //.friendlyName(clipTitle)
                //.length(clipDuration)
                //.name(OmnitureUtils.getMgidReport(mgid))
                //.vidContentType(VIDEO_CLIP) //revisit after meeting
                //.contentStatus(UNLOCKED) //revisit after meeting
                .activity(CLIP_START)
                //.epCount(CONSTANT_COUNT) //revisit after meeting
                .contentType(VOD)
                .mapBuilder()
                .plus(buildGeneralMap());
    }

    public static Map<String, String> buildPlaylistStartPlayerExpectedMap(String seriesTitle,
                                                                   String episodeMgid,
                                                                   String segmentMgid,
                                                                   String numberOfSegments,
                                                                   String segmentTitle,
                                                                   String contentType,
                                                                   String playlistTitle) {
    	if (TestRun.isAndroid()) {
    	      return new OmnitureMap()
    	                //.mgid(segmentMgid)
    	                .vidFranchise(seriesTitle)
    	                .vidEpTitle(playlistTitle)
    	                .epMGID(episodeMgid)
    	                .friendlyName(playlistTitle)
    	                .name(episodeMgid)
    	                .epCount(numberOfSegments)
    	                .vidContentType(contentType)
    	                .vidTitle(segmentTitle)
    	                //.contentStatus(UNLOCKED) //revisit after meeting
    	                .activity(FEP_START)
    	                .contentType(VOD)
    	                .mapBuilder()
    	                .plus(buildGeneralMap());
    	} else {
    	      return new OmnitureMap()
    	                //.vidFranchise(seriesTitle)
    	                //.friendlyName(playlistTitle)
    	                .name(episodeMgid)
    	                .epCount(numberOfSegments)
    	                .vidContentType(contentType)
    	                .vidTitle(segmentTitle)
    	                .activity(FEP_START)
    	                .contentType(VOD)
    	                .mapBuilder()
    	                .plus(buildGeneralMap());
    	}
    }

    public static Map<String,String> buildLiveStreamStartPlayerExpectedMap(String seriesTitle, String segmentMgid, String numberOfSegments) {
        if (TestRun.isAndroid()) {
        	return new OmnitureMap()
                    .vidTitle(seriesTitle)
                    //.mgid(segmentMgid)
                    .vidEpTitle(seriesTitle)
                    //.epCount(numberOfSegments)
                    .friendlyName(seriesTitle)
                    //.name(OmnitureUtils.getMgidForLiveCalls(segmentMgid))
                    //.epMGID(OmnitureUtils.getMgidForLiveCalls(segmentMgid))
                    .vidLength(CONSTANT_LENGTH)
                    .vidContentType(LIVE_EVENT)
                    .vidFranchise(NO_FRANCHISE)
                    .epLength(CONSTANT_LENGTH)
                    .contentStatus(LOCKED)
                    .activity(LIVE_STREAM_START)
                    .length(CONSTANT_LENGTH)
                    .contentType(LIVE)
                    .mapBuilder()
                    .plus(buildGeneralMap());
        } else {
        	return new OmnitureMap()
                    .vidTitle(seriesTitle)
                    .friendlyName(seriesTitle)
                    .vidContentType(LIVE_EVENT)
                    .contentStatus(LOCKED)
                    .activity(LIVE_STREAM_START)
                    .contentType(LIVE)
                    .mapBuilder()
                    .plus(buildGeneralMap());
        }
    }
    
    public static Map<String,String> buildAdsStartExpectedMap() {
        return new OmnitureMap()
                .activity(AD_START)
                .mapBuilder()
                .plus(buildGeneralMap());
    }
}
