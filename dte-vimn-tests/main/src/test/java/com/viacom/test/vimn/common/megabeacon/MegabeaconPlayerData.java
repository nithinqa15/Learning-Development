package com.viacom.test.vimn.common.megabeacon;

import com.viacom.test.vimn.common.omniture.OmnitureMap;
import com.viacom.test.vimn.common.util.TestRun;

import java.util.Map;

public class MegabeaconPlayerData {

    private static final String CLIP_START = "clipStart";
    @SuppressWarnings("unused")
	private static final String VOD = "vod";
    private static final String FEP_START = "fepStart";
    private static final String UNLOCKED = "Unlocked";

    public static Map<String, String> buildClipStartPlayerExpectedMap(String seriesTitle, String clipTitle, String mgid,
                                                                      String clipDuration) {
		if (TestRun.isAndroid()) {
			return new OmnitureMap()
					.activity(CLIP_START)
					.contentStatus(UNLOCKED)
					//.vidFranchise(seriesTitle)
					.vidEpTitle(clipTitle)
					.vidMGID(mgid)
					//.vidLength(clipDuration)
					.build();
		} else {
			return new OmnitureMap()
	                .activity(CLIP_START)
	                .contentStatus(UNLOCKED)
	                //.vidFranchise(seriesTitle)
	                .vidLength(clipDuration)
	                .build();
		}
    }

    public static Map<String, String> buildFepStartPlayerExpectedMap(String seriesTitle, String episodeMgid,
                                                                     String segmentMgid, String numberOfSegments,
                                                                     String segmentTitle, String contentType,
                                                                     String playlistTitle) {
        if (TestRun.isAndroid()) {
        	return new OmnitureMap()
                    .activity(FEP_START)
                    .vidFranchise(seriesTitle)
                    .vidEpTitle(playlistTitle)
                    .vidTitle(segmentTitle)
                    .vidMGID(segmentMgid)
                    .epMGID(episodeMgid)
                    .epCount(numberOfSegments)
                    .vidContentType(contentType)
                    .build();
        } else {
        	return new OmnitureMap()
                    .activity(FEP_START)
                    .vidFranchise(seriesTitle)
                    .vidTitle(segmentTitle)
                    .epCount(numberOfSegments)
                    .vidContentType(contentType)
                    .build();
        }
    }
}
