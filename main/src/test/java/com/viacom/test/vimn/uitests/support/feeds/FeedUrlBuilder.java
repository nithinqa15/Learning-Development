package com.viacom.test.vimn.uitests.support.feeds;

import com.viacom.test.vimn.common.util.TestRun;

public class FeedUrlBuilder {

    private static final String PARAM_BASE_URL = "$baseUrl";
    private static final String PARAM_DOMAIN = "$domain";
    private static final String PARAM_API_VERSION = "$apiVersion";
    private static final String PARAM_BRAND = "$brand";
    private static final String PARAM_PLATFORM = "$platform";
    private static final String PARAM_REGION = "$region";
    private static final String PARAM_APP_VERSION = "$appVersion";
    private static final String PARAM_CACHE = "&cacheRefresh=true";
    private static final String PARAM_PAGE_SIZE = "$pageSize";
    private static final String PARAM_PAGE_NUMBER = "$pageNumber";
    private static final String PARAM_SERIESID = "$inText";

    private String urlPattern;
    private String baseUrl;
    private String domain;
    private String platform;
    private String brand;
    private String apiVersion;
    private String region;
    private String appVersion;
    private String pageSize;
    private String pageNumber;
    private String seriesID;

    public FeedUrlBuilder(final String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public FeedUrlBuilder setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public FeedUrlBuilder setDomain(final String domain) {
        this.domain = domain;
        return this;
    }

    public FeedUrlBuilder setPlatform(final String platform) {
        this.platform = platform;
        return this;
    }

    public FeedUrlBuilder setBrand(final String brand) {
    	if (TestRun.isParamountApp()) {
    		this.brand = "paramountnetwork"; //will revisit, https://jira.mtvi.com/browse/MC-6570 
    	} else if (TestRun.isMTVDomesticApp() || TestRun.isMTVINTLApp()) {
            this.brand = "mtv";
    	} else if (TestRun.isCCDomesticApp() || TestRun.isCCINTLApp()) {
    	    this.brand = "cc";
    	} else if (TestRun.isBETDomesticApp() || TestRun.isBETINTLApp()) {
    	    this.brand = "bet";
        } else {
            this.brand = brand;
        }
        return this;
    }

    public FeedUrlBuilder setApiVersion(final String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    public FeedUrlBuilder setRegion(final String region) {
        this.region = region;
        return this;
    }

    public FeedUrlBuilder setAppVersion(final String appVersion) {
        this.appVersion = appVersion;
        return this;
    }

    public FeedUrlBuilder setPageSize(final String pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public FeedUrlBuilder setPageNumber(final String pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public FeedUrlBuilder setSeriesID(final String seriesID) {
        this.seriesID = seriesID;
        return this;
    }

    public String buildConfigFeed() {
        return urlPattern.replace(PARAM_BASE_URL, baseUrl)
                .replace(PARAM_API_VERSION, apiVersion)
                .replace(PARAM_BRAND, brand)
                .replace(PARAM_PLATFORM, platform)
                .replace(PARAM_REGION, region)
                .replace(PARAM_APP_VERSION, appVersion);
    }

    public String buildContentFeed() {
        return urlPattern.replace(PARAM_BASE_URL, baseUrl)
                .replace(PARAM_API_VERSION, apiVersion)
                .replace(PARAM_BRAND, brand)
                .replace(PARAM_DOMAIN, domain)
                .replace(PARAM_PLATFORM, platform)
                .replace(PARAM_REGION, region)
                .replace(PARAM_APP_VERSION, appVersion);
    }

    public String buildPaginatedContentFeed() {
        return urlPattern.replace(PARAM_BASE_URL, baseUrl)
                .replace(PARAM_API_VERSION, apiVersion)
                .replace(PARAM_BRAND, brand)
                .replace(PARAM_DOMAIN, domain)
                .replace(PARAM_PLATFORM, platform)
                .replace(PARAM_REGION, region)
                .replace(PARAM_APP_VERSION, appVersion)
                .replace(PARAM_PAGE_NUMBER, pageNumber)
                .replace(PARAM_PAGE_SIZE, pageSize)
                .replace(PARAM_SERIESID, seriesID);
    }

    public String buildLiveTvFeed() {
        return urlPattern.concat(PARAM_CACHE);
    }

}
