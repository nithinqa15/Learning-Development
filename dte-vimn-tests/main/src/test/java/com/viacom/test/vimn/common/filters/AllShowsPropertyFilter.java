package com.viacom.test.vimn.common.filters;

import org.json.simple.JSONObject;

import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

public class AllShowsPropertyFilter extends PropertyFilter {

	public AllShowsPropertyFilter(Pagination pagination) {
		String allShowsPropertyURL = FeedFactory.getAllShowsFeedURL();
		JSONObject allShowsPropertyJSON = FilterUtils.getJSONFeed(allShowsPropertyURL);
		PropertyResults allShowsPropery = convertToPropertyResults(allShowsPropertyJSON, 0, pagination);
		propertyResults = allShowsPropery;
	}
}
