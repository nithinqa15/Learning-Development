package com.viacom.test.vimn.common.heartbeat;

import com.viacom.test.vimn.common.fluentmapbuilder.FluentMapBuilder;

public class HeartbeatMap extends FluentMapBuilder {

    private static final String ACTIVITY = "s:meta:v.activity";
    private static final String VID_LENGTH = "s:meta:v.vidLength";
    private static final String VID_MGID = "s:meta:v.vidMGID";
    private static final String EP_MGID = "s:meta:v.epMGID";
    private static final String EP_COUNT = "s:meta:v.epCount";
    private static final String CONTENT_STAUS = "s:meta:v.contentStatus";
    private static final String VID_TITLE = "s:meta:v.vidTitle";
    private static final String VID_EP_TITLE = "s:meta:v.vidEpTitle";
    private static final String MEDIA_TYPE = "s:meta:a.media.type";
    private static final String EVENT_TYPE = "s:event:type";

    public HeartbeatMap activity(final String activity) {
        variable(ACTIVITY).value(activity);
        return this;
    }

    public HeartbeatMap eventType(final String eventType) {
        variable(EVENT_TYPE).value(eventType);
        return this;
    }

    public HeartbeatMap mediaType(final String mediaType) {
        variable(MEDIA_TYPE).value(mediaType);
        return this;
    }

    public HeartbeatMap contentStatus(final String contentStatus) {
        variable(CONTENT_STAUS).value(contentStatus);
        return this;
    }

    public HeartbeatMap vidMgid(final String mgid) {
        variable(VID_MGID).value(mgid);
        return this;
    }

    public HeartbeatMap epMgid(final String mgid) {
        variable(EP_MGID).value(mgid);
        return this;
    }

    public HeartbeatMap vidLength(final String vidLength) {
        variable(VID_LENGTH).value(vidLength);
        return this;
    }

    public HeartbeatMap vidTitle(final String vidTitle) {
        variable(VID_TITLE).value(vidTitle);
        return this;
    }

    public HeartbeatMap vidEpTitle(final String vidEpTitle) {
        variable(VID_EP_TITLE).value(vidEpTitle);
        return this;
    }

    public HeartbeatMap epCount(final String epCount) {
        variable(EP_COUNT).value(epCount);
        return this;
    }
}
