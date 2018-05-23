package com.viacom.test.vimn.common.filters;

import org.json.simple.JSONObject;

import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

public class HomePropertyFilter extends PropertyFilter {

	public HomePropertyFilter(Pagination pagination) {
		String featuredPropertyURL = FeedFactory.getFeaturedPropertyFeedURL();
		JSONObject featuredPropertyJSON = FilterUtils.getJSONFeed(featuredPropertyURL);
		PropertyResults featuredProperty = convertToPropertyResults(featuredPropertyJSON, 0, pagination);
		if (TestRun.isVH1App() || TestRun.isCMTApp() || TestRun.isParamountApp()) { // these brands only use featured api for homescreen content
			propertyResults = featuredProperty;
		} else {
			String promoListFeedURL = FeedFactory.getPromoListFeedURL();
			JSONObject promoListFeedseriesJSON = FilterUtils.getJSONFeed(promoListFeedURL);
			PropertyResults promoListSeries = convertToPropertyResults(promoListFeedseriesJSON, featuredProperty.size(), pagination);
			featuredProperty.addAll(promoListSeries);
			propertyResults = featuredProperty;
		}
	}
}
