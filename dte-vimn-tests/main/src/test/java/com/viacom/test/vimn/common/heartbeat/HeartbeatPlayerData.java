package com.viacom.test.vimn.common.heartbeat;

import java.util.Map;

import com.viacom.test.vimn.common.util.TestRun;

public class HeartbeatPlayerData {

    private static final String CLIP_START = "clipStart";
    @SuppressWarnings("unused")
	private static final String VOD = "vod";
    private static final String FEP_START = "fepStart";
    private static final String START_EVENT_TYPE = "start";
    private static final String CLIP_TYPE = "Video Clip";
    private static final String EPISODE_TYPE = "Full Episode";
    private static final String UNLOCKED = "Unlocked";

    public static Map<String, String> buildClipStartPlayerExpectedMap(String clipTitle, String mgid, String clipDuration) {
    	if (TestRun.isAndroid()) {
            return new HeartbeatMap()
                    .activity(CLIP_START)
                    .eventType(START_EVENT_TYPE)
                    .mediaType(CLIP_TYPE)
                    .contentStatus(UNLOCKED)
                    .vidMgid(mgid)
                    //.vidLength(clipDuration)
                    .vidTitle(clipTitle)
                    .build();
    	} else {
            return new HeartbeatMap()
                    .activity(CLIP_START)
                    .eventType(START_EVENT_TYPE)
                    .mediaType(CLIP_TYPE)
                    .contentStatus(UNLOCKED)
                    .vidLength(clipDuration)
                    .vidTitle(clipTitle)
                    .build();
    	}
    }

    public static Map<String, String> buildFepStartPlayerExpectedMap(String segmentTitle, String episodeTitle,
                                                                     String clipMgid, String epMgid, String epCount) {
        if (TestRun.isAndroid()) {
        	return new HeartbeatMap()
                    .activity(FEP_START)
                    .eventType(START_EVENT_TYPE)
                    .mediaType(EPISODE_TYPE)
                    .vidMgid(clipMgid)
                    .epMgid(epMgid)
                    .epCount(epCount)
                    .vidEpTitle(episodeTitle)
                    .vidTitle(segmentTitle)
                    .build();
        } else {
        	return new HeartbeatMap()
                    .activity(FEP_START)
                    .eventType(START_EVENT_TYPE)
                    .mediaType(EPISODE_TYPE)
                    .epCount(epCount)
                    .vidTitle(segmentTitle)
                    .build();
        }
    }
}
