package com.viacom.test.vimn.uitests.support.feeds;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.ConfigProps;
import com.viacom.test.vimn.uitests.support.BrandDataFactory;

import java.util.ArrayList;
import java.util.List;

public class FeedFactory {
    
    private static UrlProvider urlProvider;
    private static BrandDataFactory brandDataFactory;
    private static ThreadLocal<String> appMainFeedURL = new ThreadLocal<>();
    private static ThreadLocal<String> featuredPropertyFeedURL = new ThreadLocal<>();
    private static ThreadLocal<String> liveTvSeriesFeedUrl = new ThreadLocal<>();
    private static ThreadLocal<String> promoListFeedURL = new ThreadLocal<>();
    private static ThreadLocal<String> seriesFeedURL = new ThreadLocal<>();
    private static ThreadLocal<String> episodeFeedURL = new ThreadLocal<>();
    private static ThreadLocal<String> clipsFeedURL = new ThreadLocal<>();
    private static ThreadLocal<String> moviesFeedURL = new ThreadLocal<>();
    private static ThreadLocal<String> trailersFeedURL = new ThreadLocal<>();
    private static ThreadLocal<String> fightsFeedURL = new ThreadLocal<>();
    private static ThreadLocal<String> editorialFeedURL = new ThreadLocal<>();
    private static ThreadLocal<String> liveTvScheduleFeedUrl = new ThreadLocal<>();
    private static ThreadLocal<List<String>> carouselFeed = ThreadLocal.withInitial(ArrayList::new);
    private static ThreadLocal<String> allShowsFeedUrl = new ThreadLocal<>();

    public static void setFeeds() {
        urlProvider = new UrlProvider();
        brandDataFactory = new BrandDataFactory();

        setAppMainFeedURL(urlProvider.getConfigurationUrl());
        setFeaturedSeriesFeedURL(HomeScreen.getFeaturedSeriesUrl());
        setPromoListFeedURL(HomeScreen.getPromoSeriesUrl());
        setCarouselFeed(getFeaturedPropertyFeedURL());
        setCarouselFeed(getPromoListFeedURL());
        setSeriesFeedURL(urlProvider.getSeriesUrl());
        setFightsFeedURL(urlProvider.getFightsUrl());
        setEditorialsFeedURL(urlProvider.getEditorialsUrl());
        setMoviesFeedURL(urlProvider.getMoviesUrl());
        setTrailersFeedURL(urlProvider.getTrailersUrl());
        setEpisodesFeedURL(urlProvider.getEpisodesUrl());
        setClipsFeedURL(urlProvider.getClipsUrl());
        setAllShowsFeedURL(urlProvider.getAllShowsScreenURL());
        if (brandDataFactory.hasLiveTvEnabled()) {
            setLiveTvSeriesFeedUrl(LiveStreamScreen.getLiveStreamDataSourceUrl());
            try {
            	setLiveTvScheduleFeedUrl(urlProvider.getLiveTvUrl(LiveStream.getLiveTvScheduleFeedUrl()));
            } catch (Exception e) {
            	Logger.logConsoleMessage("LiveTvScheduleFeedUrl not available for " + ConfigProps.APP_NAME);
            }         
        }
    }

    public static String getAppMainFeedURL() {
        return appMainFeedURL.get();
    }

    private static void setAppMainFeedURL(String url) {
        appMainFeedURL.set(url);
    }

    public static String getFeaturedPropertyFeedURL() {
        return featuredPropertyFeedURL.get();
    }

    private static void setFeaturedSeriesFeedURL(String url) {
        featuredPropertyFeedURL.set(url);
    }

    public static String getPromoListFeedURL() {
        return promoListFeedURL.get();
    }

    private static void setPromoListFeedURL(String url) {
        promoListFeedURL.set(url);
    }

    public static String getSeriesFeedURL(String seriesID) {
        return seriesFeedURL.get().replace("$inText", seriesID);
    }

    private static void setSeriesFeedURL(String url) {
        seriesFeedURL.set(url);
    }
    
    public static String getEpisodesFeedURL(String seriesID) {
        return episodeFeedURL.get().replace("$inText", seriesID);
    }

    private static void setEpisodesFeedURL(String url) {
        episodeFeedURL.set(url);
    }

    public static String getMoviesFeedURL(String movieID) {
        return moviesFeedURL.get().replace("$inText", movieID);
    }

    private static void setMoviesFeedURL(String url) {
        moviesFeedURL.set(url);
    }

    public static String getTrailersFeedURL(String movieID) {
        return trailersFeedURL.get().replace("$inText", movieID);
    }

    private static void setTrailersFeedURL(String url) {
        trailersFeedURL.set(url);
    }

    public static String getEditorialsFeedURL(String editorialID) {
        return editorialFeedURL.get().replace("$inText", editorialID);
    }

    private static void setEditorialsFeedURL(String url) {
        editorialFeedURL.set(url);
    }

    public static String getFightsFeedURL(String fightID) {
        return fightsFeedURL.get().replace("$inText", fightID);
    }

    private static void setFightsFeedURL(String url) {
        fightsFeedURL.set(url);
    }

    public static String getClipsFeedURL(String seriesID) {
        return clipsFeedURL.get().replace("$inText", seriesID);
    }

    private static void setClipsFeedURL(String url) {
        clipsFeedURL.set(url);
    }

    public static String getLiveTvSeriesFeedUrl() {
        return liveTvSeriesFeedUrl.get();
    }

    private static void setLiveTvSeriesFeedUrl(String url) {
        liveTvSeriesFeedUrl.set(url);
    }

    private static void setCarouselFeed(String url) {
        carouselFeed.get().add(url);
    }

    public static List<String> getCarouselFeed() {
        return carouselFeed.get();
    }

    private static void setLiveTvScheduleFeedUrl(String url) {
        liveTvScheduleFeedUrl.set(url);
    }

    public static String getLiveTvScheduleFeedUrl() {
        return liveTvScheduleFeedUrl.get();
    }
    
    private static void setAllShowsFeedURL(String url) {
		allShowsFeedUrl.set(url);
    }
    
    public static String getAllShowsFeedURL() {
        return allShowsFeedUrl.get();
    }
}
