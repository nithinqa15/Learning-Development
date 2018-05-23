package com.viacom.test.vimn.uitests.support.feeds;

import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.BrandDataFactory;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;
@SuppressWarnings("unused")
public class UrlProvider {

    private static final String FEED_BASE_URL = setFeedBaseURL();
    private static final String MAIN_CONFIG_FEED_BASE_URL = "$baseUrl/feeds/networkapp/intl/main/$apiVersion/?key=networkapp1.0&brand=$brand&platform=$platform&region=$region&version=$appVersion";
    private static final String SERIES_ITEM_FEED_BASE_URL = "$baseUrl/feeds/networkapp/intl/property/$apiVersion/mgid:arc:series:$domain:$inText?&key=networkapp1.0&platform=$platform&brand=$brand&region=$region&version=$appVersion";
    private static final String PAGINATED_SERIES_ITEM_FEED_BASE_URL = "$baseUrl/feeds/networkapp/intl/property/$apiVersion/mgid:arc:series:$domain:$inText?&pageNumber=$pageNumber&pageSize=$pageSize&key=networkapp1.0&brand=$brand&platform=$platform&region=$region&version=$appVersion";
    private static final String PAGINATED_CLIPS_ITEM_FEED_BASE_URL = "$baseUrl/feeds/networkapp/intl/property/items/$apiVersion/mgid:arc:series:$domain:$inText?types=video&pageNumber=$pageNumber&pageSize=$pageSize&key=networkapp1.0&brand=$brand&platform=$platform&region=$region&version=$appVersion";
    private static final String CLIPS_ITEM_FEED_BASE_URL = "$baseUrl/feeds/networkapp/intl/property/items/$apiVersion/mgid:arc:series:$domain:$inText?types=video&key=networkapp1.0&platform=$platform&brand=$brand&region=$region&version=$appVersion";
    private static final String EPISODES_ITEM_FEED_BASE_URL = "$baseUrl/feeds/networkapp/intl/property/items/$apiVersion/mgid:arc:series:$domain:$inText?types=episode&key=networkapp1.0&platform=$platform&brand=$brand&region=$region&version=$appVersion";
    private static final String MOVIES_ITEM_FEED_BASE_URL = "$baseUrl/feeds/networkapp/intl/property/items/$apiVersion/mgid:arc:movie:$domain:$inText?types=movie&key=networkapp1.0&platform=$platform&brand=$brand&region=$region&version=$appVersion";
    private static final String TRAILERS_ITEM_FEED_BASE_URL = "$baseUrl/feeds/networkapp/intl/property/items/$apiVersion/mgid:arc:movie:$domain:$inText?types=movie&key=networkapp1.0&platform=$platform&brand=$brand&region=$region&version=$appVersion";
    private static final String FIGHTS_ITEM_FEED_BASE_URL = "$baseUrl/feeds/networkapp/intl/property/items/$apiVersion/mgid:arc:event:$domain:$inText?types=playlist&key=networkapp1.0&platform=$platform&brand=$brand&region=$region&version=$appVersion";
    private static final String EDITORIALS_ITEM_FEED_BASE_URL = "$baseUrl/feeds/networkapp/intl/promolist/$apiVersion/mgid:arc:editorial:$domain:$inText?key=networkapp1.0&platform=$platform&brand=$brand&region=$region&version=$appVersion";
    private static final String PAGINATED_EPISODES_ITEM_FEED_BASE_URL = "$baseUrl/feeds/networkapp/intl/property/items/$apiVersion/mgid:arc:series:$domain:$inText?types=episode&pageNumber=$pageNumber&pageSize=$pageSize&key=networkapp1.0&brand=$brand&platform=$platform&region=$region&version=$appVersion";
    private static final String PLAYLISTS_ITEM_FEED_BASE_URL = "$baseUrl/feeds/networkapp/intl/property/items/$apiVersion/mgid:arc:movie:$domain:$inText?types=playlist&key=networkapp1.0&platform=$platform&brand=$brand&region=$region&version=$appVersion";
    private static final String PAGINATED_PLAYLISTS_ITEM_FEED_BASE_URL = "$baseUrl/feeds/networkapp/intl/property/items/$apiVersion/mgid:arc:movie:$domain:$inText?types=playlist&pageNumber=$pageNumber&pageSize=$pageSize&key=networkapp1.0&brand=$brand&platform=$platform&region=$region&version=$appVersion";
    
    public String getConfigurationUrl() {
        return new FeedUrlBuilder(MAIN_CONFIG_FEED_BASE_URL)
                .setBaseUrl(FEED_BASE_URL )
                .setApiVersion(Config.ConfigProps.API_VERSION)
                .setBrand(TestRun.getAppName().toLowerCase())
                .setPlatform(TestRun.getMobileOS().value().toLowerCase())
                .setRegion(LocaleDataFactory.getIsoGeoCode())
                .setAppVersion(TestRun.getAppVersion())
                .buildConfigFeed();
    }

    public String getSeriesUrl() {
        return new FeedUrlBuilder(SERIES_ITEM_FEED_BASE_URL)
                .setBaseUrl(FEED_BASE_URL )
                .setApiVersion(Config.ConfigProps.API_VERSION)
                .setDomain(new BrandDataFactory().getDomain())
                .setPlatform(TestRun.getMobileOS().value().toLowerCase())
                .setRegion(LocaleDataFactory.getIsoGeoCode())
                .setBrand(TestRun.getAppName().toLowerCase())
                .setAppVersion(TestRun.getAppVersion())
                .buildContentFeed();
    }
    
    public String getEpisodesUrl() {
        return new FeedUrlBuilder(EPISODES_ITEM_FEED_BASE_URL)
                .setBaseUrl(FEED_BASE_URL )
                .setApiVersion(Config.ConfigProps.API_VERSION)
                .setDomain(new BrandDataFactory().getDomain())
                .setPlatform(TestRun.getMobileOS().value().toLowerCase())
                .setRegion(LocaleDataFactory.getIsoGeoCode())
                .setBrand(TestRun.getAppName().toLowerCase())
                .setAppVersion(TestRun.getAppVersion())
                .buildContentFeed();
    }

    public String getMoviesUrl() {
        return new FeedUrlBuilder(MOVIES_ITEM_FEED_BASE_URL)
                .setBaseUrl(FEED_BASE_URL )
                .setApiVersion(Config.ConfigProps.API_VERSION)
                .setDomain(new BrandDataFactory().getDomain())
                .setPlatform(TestRun.getMobileOS().value().toLowerCase())
                .setRegion(LocaleDataFactory.getIsoGeoCode())
                .setBrand(TestRun.getAppName().toLowerCase())
                .setAppVersion(TestRun.getAppVersion())
                .buildContentFeed();
    }

    public String getTrailersUrl() {
        return new FeedUrlBuilder(TRAILERS_ITEM_FEED_BASE_URL)
                .setBaseUrl(FEED_BASE_URL )
                .setApiVersion(Config.ConfigProps.API_VERSION)
                .setDomain(new BrandDataFactory().getDomain())
                .setPlatform(TestRun.getMobileOS().value().toLowerCase())
                .setRegion(LocaleDataFactory.getIsoGeoCode())
                .setBrand(TestRun.getAppName().toLowerCase())
                .setAppVersion(TestRun.getAppVersion())
                .buildContentFeed();
    }

    public String getEditorialsUrl() {
        return new FeedUrlBuilder(EDITORIALS_ITEM_FEED_BASE_URL)
                .setBaseUrl(FEED_BASE_URL )
                .setApiVersion(Config.ConfigProps.API_VERSION)
                .setDomain(new BrandDataFactory().getDomain())
                .setPlatform(TestRun.getMobileOS().value().toLowerCase())
                .setRegion(LocaleDataFactory.getIsoGeoCode())
                .setBrand(TestRun.getAppName().toLowerCase())
                .setAppVersion(TestRun.getAppVersion())
                .buildContentFeed();
    }

    public String getFightsUrl() {
        return new FeedUrlBuilder(FIGHTS_ITEM_FEED_BASE_URL)
                .setBaseUrl(FEED_BASE_URL )
                .setApiVersion(Config.ConfigProps.API_VERSION)
                .setDomain(new BrandDataFactory().getDomain())
                .setPlatform(TestRun.getMobileOS().value().toLowerCase())
                .setRegion(LocaleDataFactory.getIsoGeoCode())
                .setBrand(TestRun.getAppName().toLowerCase())
                .setAppVersion(TestRun.getAppVersion())
                .buildContentFeed();
    }

    public String getClipsUrl() {
        return new FeedUrlBuilder(CLIPS_ITEM_FEED_BASE_URL)
                .setBaseUrl(FEED_BASE_URL )
                .setApiVersion(Config.ConfigProps.API_VERSION)
                .setDomain(new BrandDataFactory().getDomain())
                .setPlatform(TestRun.getMobileOS().value().toLowerCase())
                .setRegion(LocaleDataFactory.getIsoGeoCode())
                .setBrand(TestRun.getAppName().toLowerCase())
                .setAppVersion(TestRun.getAppVersion())
                .buildContentFeed();
    }

    public String getPaginatedSeriesUrl(String seriesId, Long pageSize, Integer pageNumber) {
        return new FeedUrlBuilder(PAGINATED_SERIES_ITEM_FEED_BASE_URL)
                .setBaseUrl(FEED_BASE_URL )
                .setApiVersion(Config.ConfigProps.API_VERSION)
                .setDomain(new BrandDataFactory().getDomain())
                .setPlatform(TestRun.getMobileOS().value().toLowerCase())
                .setRegion(LocaleDataFactory.getIsoGeoCode())
                .setBrand(TestRun.getAppName().toLowerCase())
                .setAppVersion(TestRun.getAppVersion())
                .setPageSize(String.valueOf(pageSize))
                .setPageNumber(String.valueOf(pageNumber))
                .setSeriesID(seriesId)
                .buildPaginatedContentFeed();
    }
    
    public String getPaginatedEpisodesUrl(String seriesId, Long pageSize, Integer pageNumber) {
        return new FeedUrlBuilder(PAGINATED_EPISODES_ITEM_FEED_BASE_URL)
                .setBaseUrl(FEED_BASE_URL )
                .setApiVersion(Config.ConfigProps.API_VERSION)
                .setDomain(new BrandDataFactory().getDomain())
                .setPlatform(TestRun.getMobileOS().value().toLowerCase())
                .setRegion(LocaleDataFactory.getIsoGeoCode())
                .setBrand(TestRun.getAppName().toLowerCase())
                .setAppVersion(TestRun.getAppVersion())
                .setPageSize(String.valueOf(pageSize))
                .setPageNumber(String.valueOf(pageNumber))
                .setSeriesID(seriesId)
                .buildPaginatedContentFeed();
    }

    public String getPaginatedClipsUrl(String seriesId, Long clipsPageSize, Integer pageNumber) {
        return new FeedUrlBuilder(PAGINATED_CLIPS_ITEM_FEED_BASE_URL)
                .setBaseUrl(FEED_BASE_URL )
                .setApiVersion(Config.ConfigProps.API_VERSION)
                .setDomain(new BrandDataFactory().getDomain())
                .setPlatform(TestRun.getMobileOS().value().toLowerCase())
                .setRegion(LocaleDataFactory.getIsoGeoCode())
                .setBrand(TestRun.getAppName().toLowerCase())
                .setAppVersion(TestRun.getAppVersion())
                .setPageSize(String.valueOf(clipsPageSize))
                .setPageNumber(String.valueOf(pageNumber))
                .setSeriesID(seriesId)
                .buildPaginatedContentFeed();
    }

    public String getLiveTvUrl(String liveTvEndPoint) {
        return new FeedUrlBuilder(liveTvEndPoint).buildLiveTvFeed();
    }
    
    public String getAllShowsScreenURL() {
        new AllShowsScreen();
		return AllShowsScreen.getAllShowsUrl();
    }
    
    public static String setFeedBaseURL() {
		return TestRun.isLiveContentDomain() ? "http://api.playplex.viacom.com" : "http://testing.api.playplex.viacom.vmn.io";
    }
}
