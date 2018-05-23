package com.viacom.test.vimn.common.filters;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.exceptions.PaginationException;
import com.viacom.test.vimn.uitests.support.feeds.UrlProvider;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PropertyResult {

    // JSON response from property items url
    private JSONObject propertyJSON;
    // the individual json object that represents a single property
    private JSONObject propertyObject;
    private Integer numOfSwipes;
    private String entityType;
    private String subType;
    private Long clipsPageSize;
    private Long clipsTotalItems;
    private Long episodesPageSize;
    private Long episodesTotalItems;
    private Long musicVideosPageSize;
    private Long musicVideosTotalItems;
    private Long fightsPageSize;
    private Long fightsTotalItems;
    private Long trailersPageSize;
    private Long trailersTotalItems;
    private Long moviesPageSize;
    private Long moviesTotalItems;
    private Long numberOfEpisodePages;
    private Long numberOfClipPages;
    private Long numberOfMoviePages;
    private Long numberOfTrailerPages;
    private Long numberOfFightPages;
    private Long numberOfMusicVideoPages;

    /**
     * Constructor
     * @param propertyJSON
     * @param propertyPositionInCarousel
     * @param numOfFeaturedProperty
     */
    public PropertyResult(JSONObject propertyJSON, Integer propertyPositionInCarousel, Integer numOfFeaturedProperty) {
        this.propertyJSON = propertyJSON;
        this.propertyObject = getPropertyObject(propertyJSON, propertyPositionInCarousel, numOfFeaturedProperty);
        this.numOfSwipes = propertyPositionInCarousel;
    }

    /**
     * Constructor that accepts Pagination argument.
     * @param propertyJSON
     * @param propertyPositionInCarousel
     * @param numOfFeaturedProperty
     * @param pagination
     */
    public PropertyResult(JSONObject propertyJSON, Integer propertyPositionInCarousel, Integer numOfFeaturedProperty, Pagination pagination) {
        this.propertyJSON = propertyJSON;
        this.propertyObject = getPropertyObject(propertyJSON, propertyPositionInCarousel, numOfFeaturedProperty);
        this.numOfSwipes = propertyPositionInCarousel;
        this.entityType = setEntityType();
        this.subType = setSubType();

        switch (pagination) {
            case NON_PAGINATED:
                break;
            case PAGINATED:
                this.episodesPageSize = getEpisodePageSize();
                this.clipsPageSize = getClipPageSize();
                this.moviesPageSize = getMoviePageSize();
                this.trailersPageSize = getTrailerPageSize();
                this.fightsPageSize = getFightPageSize();
                this.musicVideosPageSize = getMusicVideoPageSize();
                this.numberOfEpisodePages = setNumberOfEpisodesPages();
                this.numberOfClipPages = setNumberOfClipsPages();
                this.numberOfFightPages = setNumberOfFightsPages();
                this.numberOfMusicVideoPages = setNumberOfMusicVideosPages();
                this.numberOfMoviePages = setNumberOfMoviesPages();
                this.numberOfTrailerPages = setNumberOfTrailersPages();
                break;
        }
    }

    /**
     * @param propertyJSON
     * @param propertyPositionInCarousel
     * @param numOfFeaturedProperty
     * @return propertyObject as a json object
     */
    private JSONObject getPropertyObject(JSONObject propertyJSON, Integer propertyPositionInCarousel, Integer numOfFeaturedProperty) {
        JSONArray propertyItems = (JSONArray) ((JSONObject) propertyJSON.get("data")).get("items");
        if (propertyPositionInCarousel == 0) { // Means this is the featured property
            return (JSONObject) propertyItems.get(propertyPositionInCarousel);
        } else { // Means this is for promo list feed
            return (JSONObject) propertyItems.get(propertyPositionInCarousel - numOfFeaturedProperty);
        }
    }

    /**
     * Gets the title of the property
     *
     * @return the title of the property
     **/
    public String getPropertyTitle() {
        if (propertyObject.containsKey("title")) {
            return propertyObject.get("title").toString();
        }
        return null;
    }

    /**
     * Gets the description of the property
     *
     * @return the description of the property
     **/
    public String getPropertyDetail() {
        return getPropertyDescription();
    }

    /**
     * Gets the ID of the property
     *
     * @return the ID of the property
     **/
    public String getPropertyId() {
        return getPropertyId(propertyObject);
    }

    /**
     * Gets the MGID of the property
     *
     * @return the MGID of the property
     **/
    public String getPropertyMGID() {
        if (propertyObject.containsKey("mgid")) {
            return propertyObject.get("mgid").toString();
        }
        return null;
    }

    /**
     * Gets the description of the property
     *
     * @return the description of the property
     **/
    public String getPropertyDescription() {
        if (propertyObject.containsKey("description")) {
            return propertyObject.get("description").toString();
        }
        return null;
    }

    /**
     * Gets the url of the property NOTE: This field doesn't actually exist within
     * the json response from the promolist. It's is an artifical value created in
     * the PropertyFilter class within the convertToPropertyResult method.
     *
     * TODO Update this so that we can grab it from the url generator
     *
     * @return the url of the property
     **/
    public String getPropertyURL() {
    	 return propertyObject.get("url").toString();
    }

    /**
     * Gets and sets the entitype of a property
     * @return sets the entitype of a property as a string
     */
    private String setEntityType() {
        return propertyObject.get("entityType").toString();
    }

    /**
     * Gets the entitype of a property
     * @return gets the entitype of a property as a string
     */
    public String getEntityType() {
        return entityType;
    }

    /**
     * Gets and sets the subtype of a property
     * @return gets and sets the subtype of a property as a string
     */
    private String setSubType() {
        return propertyObject.get("subType").toString();
    }

    /**
     * Gets the subtype of a property
     * @return sets the subtype of a property as a string
     */
    public String getSubType() {
        return subType;
    }
    
    /**
     * Gets the keyword of the property
     *
     * @return the keyword of the property
     **/
    public String getKeywords() {
        if (propertyObject.containsKey("keywords")) {
            return propertyObject.get("keywords").toString();
        }
        return null;
    }

    /**
     * Lets you know if a property is a video
     *
     * @return true if a property is a video, false otherwise
     **/
    public Boolean isVideoProperty() {
        return entityType.equals("video");
    }

    /**
     * Lets you know if a property is an episode
     *
     * @return true if a property is a episode, false otherwise
     **/
    public Boolean isEpisodeProperty() {
        return entityType.equals("episode");
    }

    /**
     * Lets you know if a property is a movie
     *
     * @return true if a property is a movie, false otherwise
     **/
    public Boolean isMovieProperty() {
        return entityType.equals("movie");
    }

    /**
     * Lets you know if a property is a clip
     *
     * @return true if a property is a clip, false otherwise
     **/
    public Boolean isClipProperty() {
        return entityType.equals("clip");
    }

    /**
     * Lets you know if a property is a fight
     *
     * @return true if a property is a fight, false otherwise
     **/
    public Boolean isFightProperty() {
        return entityType.equals("event"); // fight as an event property
    }

    /**
     * Lets you know if a property is a season
     *
     * @return true if a property is a season, false otherwise
     **/
    public Boolean isSeasonProperty(){
        return entityType.equals("season");
    }

    /**
     * Lets you know if a property is a standup
     *
     * @return true if a property is a standup, false otherwise
     **/
    public Boolean isStandUpProperty() {
        return entityType.equals("stand-up");
    }

    /**
     * Lets you know if a property is a franchise
     *
     * @return true if a property is a franchise, false otherwise
     **/
    public Boolean isFranchiseProperty() {
        return entityType.equals("franchise");
    }

    /**
     * Lets you know if a property is a series
     *
     * @return true if a property is a series, false otherwise
     **/
    public Boolean isSeriesProperty() {
        return entityType.equals("series") ;
    }

    /**
     * Lets you know if a property is an editorial collection
     *
     * @return true if a property is an editorial collection, false otherwise
     **/
    public Boolean isCollectionProperty() {
        return subType.equals("Clip Collection");
    }

    /**
     * Gets a list of paginated episode URLs
     *
     * @return a list of paginated episode URLs
     **/
    public List<String> getPaginatedLongformURLs() {
        List<String> paginatedPropertyURLs = new ArrayList<>();
        for (int i = 1; i <= setNumberOfEpisodesPages(); i++) {
            paginatedPropertyURLs.add(new UrlProvider().getPaginatedEpisodesUrl(getPropertyId(), episodesPageSize, i));
        }
        return paginatedPropertyURLs;
    }

    /**
     * Gets a list of paginated episode URLs
     *
     * @return a list of paginated episode URLs
     **/
    public List<String> getPaginatedShortformURLs() {
        List<String> paginatedPropertyURLs = new ArrayList<>();
        for (int i = 1; i <= setNumberOfClipsPages(); i++) {
            paginatedPropertyURLs.add(new UrlProvider().getPaginatedClipsUrl(getPropertyId(), clipsPageSize, i));
        }
        return paginatedPropertyURLs;
    }

    /**
     * Gets the default order of the property from the feed
     *
     * @return the default order of the property from the feed
     **/
    public String getOrder() {
        if (propertyObject.containsKey("order")) {
            return propertyObject.get("order").toString();
        }
        return null;
    }

    /**
     * Gets the default orderBy of the property from the feed
     *
     * @return the default orderBy of the property from the feed
     **/
    public String getOrderBy() {
        if (propertyObject.containsKey("orderBy")) {
            return propertyObject.get("orderBy").toString();
        }
        return null;
    }

    /**
     * Used to get json objects our current getter functions don't cover
     *
     * @param jsonFieldNames
     *          - list of json field names to get to what we want
     * @return json objects our current getter functions don't cover
     **/
    public Object get(String... jsonFieldNames) {
        JSONObject target = propertyObject;
        for (int i = 0; i < jsonFieldNames.length - 1; i++) {
            String jsonFieldName = jsonFieldNames[i];
            target = (JSONObject) target.get(jsonFieldName);
        }
        Integer lastIndex = jsonFieldNames.length - 1;
        return target.get(jsonFieldNames[lastIndex]);
    }

    /**
     * Gets the all the episodes for the property
     *
     * @return all the episodes for the property
     **/
    public LongformResults getEpisodes() {
        return getLongformFilter().episodesFilter();
    }

    /**
     * Gets the all the clips for the property
     *
     * @return all the clips for the property
     **/
    public ShortformResults getClips() {
        return getShortformFilter().clipsFilter();
    }

    /**
     * Gets the all the movies for the property
     *
     * @return all the movies for the property
     **/
    public LongformResults getMovies() {
        return getLongformFilter().movieFilter();
    }

    /**
     * Gets the all the trailers for the property
     *
     * @return all the trailers for the property
     **/
    public ShortformResults getTrailers() {
        return getShortformFilter().trailersFilter();
    }
    
    /**
     * Gets the all the fights for the property
     *
     * @return all the fights for the property
     **/
    public LongformResults getFights() {
        return getLongformFilter().fightFilter();
    }

    /**
     * Gets the all the music videos for the property
     *
     * @return all the music videos for the property
     **/
    public ShortformResults getMusicVideos() {
        return getShortformFilter().musicVideosFilter();
    }

    /**
     * Gets you the original publish date as a timestamp
     *
     * @return the original publish date as a timestamp
     **/
    public Long getOriginalPublishDateTimestamp(){
        return (Long) this.get("publishDate", "timestamp");
    }

    /**
     * Lets you know if a property has publishdate
     *
     * @return true if a property has a publishdate, false otherwise
     **/
    public Boolean hasPublishDate() {
        return propertyObject.containsKey("publishDate");
    }

    /**
     * Gets you the original air date as a timestamp
     *
     * @return the original air date as a timestamp
     **/
    public Long getOriginalAirDateTimestamp() {
        return (Long) this.get("airDate", "timestamp");
    }

    public String getOriginalAirDateString() {
        return (String) this.get("airDate", "dateString");
    }

    /**
     * Lets you know if a property has airdate
     *
     * @return true if a property has a airdate, false otherwise
     **/
    public Boolean hasAirDate() {
        return propertyObject.containsKey("airDate");
    }

    /**
     * Lets you know if a property has the minimum number of episodes specified in
     * minEpisodesCount
     *
     * @return true if a property has the minimum number of episodes specified in
     *         minEpisodesCount, false otherwise
     **/
    public Boolean hasMinEpisodesCount(Integer minEpisodesCount) {
        return this.getEpisodes().size() >= minEpisodesCount;
    }

    /**
     * Lets you know if a property has the maximum number of episodes specified by
     * maxEpisodesCount
     *
     * @return true if a property has the maximum number of episodes specified by
     *         maxEpisodesCount, false otherwise
     **/
    public Boolean hasMaxEpisodesCount(Integer maxEpisodesCount) {
        return this.getEpisodes().size() <= maxEpisodesCount;
    }

    /**
     * Lets you know if a property has the minimum number of clips specified in
     * minClipsCount
     *
     * @return true if a property has the minimum number of clips specified in
     *         minClipsCount, false otherwise
     **/
    public Boolean hasMinClipsCount(Integer minClipsCount) {
        return this.getClips().size() >= minClipsCount;
    }

    /**
     * Lets you know if a property has the maximum number of clips specified by
     * maxClipsCount
     *
     * @return true if a property has the maximum number of clips specified by
     *         maxClipsCount, false otherwise
     **/
    public Boolean hasMaxClipsCount(Integer maxClipsCount) {
        return this.getClips().size() <= maxClipsCount;
    }

    /**
     * Lets you know if a property has the minimum number of music videos specified in
     * minMusicVideosCount
     *
     * @return true if a property has the minimum number of music videos specified in
     *         minMusicVideoCount, false otherwise
     **/
    public Boolean hasMinMusicVideosCount(Integer minMusicVideoCount) {
        return this.getMusicVideos().size() >= minMusicVideoCount;
    }

    /**
     * Lets you know if a property has the maximum number of music videos specified by
     * maxMusicVideosCount
     *
     * @return true if a property has the maximum number of music videos specified by
     *         maxMusicVideosCount, false otherwise
     **/
    public Boolean hasMaxMusicVideosCount(Integer maxMusicVideosCount) {
        return this.getMusicVideos().size() <= maxMusicVideosCount;
    }

    /**
     * Lets you know if a property has the minimum number of movies specified in
     * minMoviesCount
     *
     * @return true if a property has the minimum number of movies specified in
     *         minMoviesCount, false otherwise
     **/
    public Boolean hasMinMoviesCount(Integer minMoviesCount) {
        return this.getMovies().size() >= minMoviesCount;
    }

    /**
     * Lets you know if a property has the maximum number of movies specified by
     * maxMoviesCount
     *
     * @return true if a property has the maximum number of movies specified by
     *         maxMoviesCount, false otherwise
     **/
    public Boolean hasMaxMoviesCount(Integer maxMoviesCount) {
        return this.getMovies().size() <= maxMoviesCount;
    }

    /**
     * Lets you know if a property has the minimum number of trailers specified in
     * minTrailersCount
     *
     * @return true if a property has the minimum number of trailers specified in
     *         maxTrailerCount, false otherwise
     **/
    public Boolean hasMinTrailersCount(Integer minTrailersCount) {
        return this.getTrailers().size() >= minTrailersCount;
    }

    /**
     * Lets you know if a property has the maximum number of trailers specified by
     * maxMoviesCount
     *
     * @return true if a property has the maximum number of trailers specified by
     *         maxMoviesCount, false otherwise
     **/
    public Boolean hasMaxTrailersCount(Integer maxMoviesCount) {
        return this.getMovies().size() <= maxMoviesCount;
    }

    /**
     * Lets you know if a property has the minimum number of fights specified in
     * minFightsCount
     *
     * @return true if a property has the minimum number of fights specified in
     *         minFightsCount, false otherwise
     **/
    public Boolean hasMinFightsCount(Integer minFightsCount) {
        return this.getFights().size() >= minFightsCount;
    }

    /**
     * Lets you know if a property has the maximum number of fights specified by
     * maxFightsCount
     *
     * @return true if a property has the maximum number of fights specified by
     *         maxFightsCount, false otherwise
     **/
    public Boolean hasMaxFightsCount(Integer maxFightsCount) {
        return this.getFights().size() <= maxFightsCount;
    }

    /**
     * Lets you know if a property has public clips
     *
     * @return true if a property has public clips, false otherwise
     **/
    public Boolean hasPublicClips() {
        return this.getShortformFilter().withPublicClips().clipsFilter().size() > 0;
    }

    /**
     * Lets you know if a property has public episodes
     *
     * @return true if a property has public episodes, false otherwise
     **/
    public Boolean hasPublicEpisodes() {
        return this.getLongformFilter().withPublicEpisodes().episodesFilter().size() > 0;
    }

    /**
     * Lets you know if a property has private clips
     *
     * @return true if a property has private clips, false otherwise
     **/
    public Boolean hasPrivateClips() {
        return this.getShortformFilter().withPrivateClips().clipsFilter().size() > 0;
    }

    /**
     * Lets you know if a property has private episodes
     *
     * @return true if a property has private episodes, false otherwise
     **/
    public Boolean hasPrivateEpisodes() {
        return this.getLongformFilter().withPrivateEpisodes().episodesFilter().size() > 0;
    }

    /**
     * Lets you know if a property has private trailers
     *
     * @return true if a property has private trailers, false otherwise
     **/
    public Boolean hasPrivateTrailers() {
        return this.getShortformFilter().withPrivateTrailers().trailersFilter().size() > 0;
    }

    /**
     * Lets you know if a property has public trailers
     *
     * @return true if a property has public trailers, false otherwise
     **/
    public Boolean hasPublicTrailers() {
        return this.getShortformFilter().withPublicTrailers().trailersFilter().size() > 0;
    }

    /**
     * Lets you know if a property has public movies
     *
     * @return true if a property has public movies, false otherwise
     **/
    public Boolean hasPublicMovies() {
        return this.getLongformFilter().withPublicMovies().movieFilter().size() > 0;
    }


    /**
     * Lets you know if a property has private movies
     *
     * @return true if a property has private movies, false otherwise
     **/
    public Boolean hasPrivateMovies() {
        return this.getLongformFilter().withPrivateMovies().movieFilter().size() > 0;
    }

    /**
     * Lets you know if a property has public music videos
     *
     * @return true if a property has public music videos, false otherwise
     **/
    public Boolean hasPublicMusicVideos() {
        return this.getShortformFilter().withPublicMusicVideos().musicVideosFilter().size() > 0;
    }

    /**
     * Lets you know if a property has public fights
     *
     * @return true if a property has public fights, false otherwise
     **/
    public Boolean hasPublicFights() {
        return this.getLongformFilter().withPublicFights().fightFilter().size() > 0;
    }


    /**
     * Lets you know if a property has private music videos
     *
     * @return true if a property has private music videos, false otherwise
     **/
    public Boolean hasPrivateMusicVideos() {
        return this.getShortformFilter().withPrivateMusicVideos().musicVideosFilter().size() > 0;
    }

    /**
     * Lets you know if a property has private fights
     *
     * @return true if a property has private fights, false otherwise
     **/
    public Boolean hasPrivateFights() {
        return this.getLongformFilter().withPrivateFights().fightFilter().size() > 0;
    }

    /**
     * Lets you know if a property has public clips only
     *
     * @return true if a property has public clips only, false otherwise
     **/
    public Boolean hasPublicClipsOnly() {
        return this.getShortformFilter().withPublicClipsOnly().clipsFilter().size() > 0;
    }

    /**
     * Lets you know if a property has public episodes
     *
     * @return true if a property has public episodes, false otherwise
     **/
    public Boolean hasPublicEpisodesOnly() {
        return this.getLongformFilter().withPublicEpisodesOnly().episodesFilter().size() > 0;
    }

    /**
     * Lets you know if a property has private clips
     *
     * @return true if a property has private clips, false otherwise
     **/
    public Boolean hasPrivateClipsOnly() {
        return this.getShortformFilter().withPrivateClipsOnly().clipsFilter().size() > 0;
    }

    /**
     * Lets you know if a property has private episodes only
     *
     * @return true if a property has private episodes only, false otherwise
     **/
    public Boolean hasPrivateEpisodesOnly() {
        return this.getLongformFilter().withPrivateEpisodesOnly().episodesFilter().size() > 0;
    }

    /**
     * Lets you know if a property has public trailers only
     *
     * @return true if a property has public trailers only, false otherwise
     **/
    public Boolean hasPublicTrailersOnly() {
        return this.getShortformFilter().withPublicTrailersOnly().trailersFilter().size() > 0;
    }

    /**
     * Lets you know if a property has private trailers only
     *
     * @return true if a property has private trailers only, false otherwise
     **/
    public Boolean hasPrivateTrailersOnly() {
        return this.getShortformFilter().withPrivateTrailersOnly().trailersFilter().size() > 0;
    }

    /**
     * Lets you know if a property has trailers only
     *
     * @return true if a property has trailers only, false otherwise
     **/
    public Boolean hasTrailersOnly() {
        return this.getShortformFilter().withTrailersOnly().trailersFilter().size() > 0;
    }

    /**
     * Lets you know if a property has public movies only
     *
     * @return true if a property has public movies only , false otherwise
     **/
    public Boolean hasPublicMoviesOnly() {
        return this.getLongformFilter().withPublicMoviesOnly().movieFilter().size() > 0;
    }


    /**
     * Lets you know if a property has private movies only
     *
     * @return true if a property has private movies only, false otherwise
     **/
    public Boolean hasPrivateMoviesOnly() {
        return this.getLongformFilter().withPrivateMoviesOnly().movieFilter().size() > 0;
    }

    /**
     * Lets you know if a property has public music videos only
     *
     * @return true if a property has public music videos only, false otherwise
     **/
    public Boolean hasPublicMusicVideosOnly() {
        return this.getShortformFilter().withPublicMusicVideosOnly().musicVideosFilter().size() > 0;
    }

    /**
     * Lets you know if a property has public fights only
     *
     * @return true if a property has public fights only, false otherwise
     **/
    public Boolean hasPublicFightsOnly() {
        return this.getLongformFilter().withPublicFightsOnly().fightFilter().size() > 0;
    }


    /**
     * Lets you know if a property has private music videos only
     *
     * @return true if a property has private music videos only, false otherwise
     **/
    public Boolean hasPrivateMusicVideosOnly() {
        return this.getShortformFilter().withPrivateMusicVideossOnly().musicVideosFilter().size() > 0;
    }

    /**
     * Lets you know if a property has private fights only
     *
     * @return true if a property has private fights only, false otherwise
     **/
    public Boolean hasPrivateFightsOnly() {
        return this.getLongformFilter().withPrivateFightsOnly().fightFilter().size() > 0;
    }


    /**
     * Let's you know if a property has a minimum of episode content pages
     * @param numberOfPages
     * @return numberOfEpisodesPages
     */
    public Boolean hasMinimumOfEpisodePages(Integer numberOfPages) {
        if (episodesPageSize == null) {
            throw new PaginationException("Please turn on pagination at constructor level.");
        } else {
            return episodesPageSize >= numberOfPages;
        }
    }

    /**
     * Let's you know if a property has a minimum of clips content pages
     * @param numberOfPages
     * @return
     */
    public boolean hasMinimumOfClipsPages(Integer numberOfPages) {
        if (numberOfEpisodePages == null) {
            throw new PaginationException("Please turn on pagination at constructor level.");
        } else {
            return numberOfClipPages >= numberOfPages;
        }
    }

    /**
     * Let's you know if a property has a minimum of clips content pages
     * @param numberOfPages
     * @return the number of pages of clips content
     */
    public Boolean hasMinimumOfLongformPages(Integer numberOfPages) {
        if (numberOfClipPages == null) {
            throw new PaginationException("Please turn on pagination at constructor level.");
        } else {
            return numberOfClipPages >= numberOfPages;
        }
    }

    /**
     * Let's you know if a property has a minimum of fights content pages
     * @param numberOfPages
     * @return the number of pages of fights content
     */
    public boolean hasMinimumOfFightsPages(Integer numberOfPages) {
        if (numberOfFightPages == null) {
            throw new PaginationException("Please turn on pagination at constructor level.");
        } else {
            return numberOfFightPages >= numberOfPages;
        }
    }

    /**
     * Let's you know if a property has a minimum of music videos content pages
     * @param numberOfPages
     * @return the number of pages of music videos content
     */
    public Boolean hasMinimumOfMusicVideosPages(Integer numberOfPages) {
        if (numberOfMusicVideoPages == null) {
            throw new PaginationException("Please turn on pagination at constructor level.");
        } else {
            return numberOfMusicVideoPages >= numberOfPages;
        }
    }

    /**
     * Let's you know if a property has a minimum of movies content pages
     * @param numberOfPages
     * @return the number of pages of movies content
     */
    public boolean hasMinimumOfMoviesPages(Integer numberOfPages) {
        if (numberOfMoviePages == null) {
            throw new PaginationException("Please turn on pagination at constructor level.");
        } else {
            return numberOfMoviePages >= numberOfPages;
        }
    }

    /**
     * Let's you know if a property has a minimum of trailers content pages
     * @param numberOfPages
     * @return the number of pages of trailers content
     */
    public Boolean hasMinimumOfTrailersPages(Integer numberOfPages) {
        if (numberOfTrailerPages == null) {
            throw new PaginationException("Please turn on pagination at constructor level.");
        } else {
            return numberOfTrailerPages >= numberOfPages;
        }
    }

    /**
     * Lets you know if a property has new episodes
     *
     * @return true if a property has new episodes, false otherwise
     **/
    public Boolean hasNewEpisode() {
        if (propertyObject.containsKey("ribbon")) {
            JSONObject ribbonObject = (JSONObject) propertyObject.get("ribbon"); // New episode ribbon has third priority
            return ribbonObject != null && ribbonObject.containsKey("newEpisode")
                    && (ribbonObject.get("newEpisode").toString()).equals("true")
                    && isNotNewSeries();
        } else {
            Logger.logMessage(propertyObject.get("title") + " does not contain ribbon object");
            return false;
        }
    }

    /**
     * Lets you know if a property is a new property
     *
     * @return true if a property is a new property, false otherwise
     **/
    public Boolean isNewSeries() {
        if (propertyObject.containsKey("ribbon")) {
            JSONObject ribbonObject = (JSONObject) propertyObject.get("ribbon"); // New property ribbon has high priority
            return ribbonObject != null && ribbonObject.containsKey("newSeries")
                    && ribbonObject.get("newSeries").toString().equals("true");
        } else {
            Logger.logMessage(propertyObject.get("title") + " does not contain ribbon object");
            return false;
        }
    }

    /**
     * Lets you know if a series has new seasons
     *
     * @return true if a series has new seasons, false otherwise
     **/
    public Boolean hasNewSeasons() {
        if (propertyObject.containsKey("ribbon")) {
            JSONObject ribbonObject = (JSONObject) propertyObject.get("ribbon");
            return ribbonObject != null && ribbonObject.containsKey("newSeason") // New season ribbon has second priority
                    && ribbonObject.get("newSeason").toString().equals("true");
        } else {
            Logger.logMessage(propertyObject.get("title") + " does not contain ribbon object");
            return false;
        }
    }

    /**
     * Lets you know if a series has no new seasons
     *
     * @return true if a series has no new seasons, false otherwise
     **/
    public Boolean hasNoNewSeasons() {
        return !hasNewSeasons();
    }


    /**
     * Lets you know if a property is not a new property
     *
     * @return true if a property is not a new property, false otherwise
     **/
    public Boolean isNotNewSeries() {
        return !isNewSeries();
    }


    public Boolean hasNewEvent() {
        JSONObject ribbonObject = (JSONObject) propertyObject.get("ribbon");
        return ribbonObject != null && ribbonObject.containsKey("newEvent") // New Event ribbon
                && ribbonObject.get("newEvent").toString().equals("true");
    }

    /**
     * Lets you know if a property is younger by the number of days specified by
     * numDays
     *
     * @param numDays
     *          - number of days the property has to be younger by
     * @return true if a property is younger by the number of days specified by
     *         numDays, false otherwise
     **/
    public Boolean isLessThanNumDaysOld(Integer numDays) {
        // TODO Didn't implement because couldn't find latestEpisode field name in
        // json
        return null;
    }

    /**
     * Lets you know if a property has episodes
     *
     * @return true if a property has episodes, false otherwise
     **/
    public Boolean hasEpisodes() {
        return getlinkObject().containsKey("episode");
    }

    /**
     * Gets the episode URL
     *
     * @return episode URL as a string
     **/
    public String getEpisodeURL() {
        if (getlinkObject().containsKey("episode")){
         	return (String) getlinkObject().get("episode");
         }
        return null;
     }

    /**
     * Gets the clips URL
     *
     * @return clips URL as a string
     **/
    public String getClipsURL() {
        if (getlinkObject().containsKey("video")){
         	return (String) getlinkObject().get("video");
         }
        return null;
     }
    
    /**
     * Lets you know if a property has episodes
     *
     * @return true if a property has episodes, false otherwise
     **/
    public Boolean hasEpisodesOnly() {
        return hasEpisodes() && !hasMovie() && !hasEvent()
                && !hasClips() && !hasTrailer() && !hasPlaylist() && hasMusicVideo();
    }

    /**
     * Lets you know if a property has clips only
     *
     * @return true if a property has clips only, false otherwise
     **/
    public Boolean hasClipsOnly() {
        return hasClips() && !hasEpisodes() && !hasMovie() && !hasPlaylist()
                && !hasEvent() && !hasPlaylist() && !hasMusicVideo();
    }

    /**
     * Lets you know if a property has clips
     *
     * @return true if a property has clips, false otherwise
     **/
    public Boolean hasClips() {
        return getlinkObject().containsKey("video");
    }

    /**
     * Lets you know if a property has background video on the home carousel
     *
     * @return true if a property has background video on the home carousel, false
     *         otherwise
     **/
    public Boolean hasBackgroundVideo() {
        JSONArray videosObject = (JSONArray) propertyObject.get("videos");
        return videosObject.size() > 0;
    }

    /**
     * Lets you know if a property has no background video on the home carousel
     *
     * @return true if a property has no background video on the home carousel,
     *         false otherwise
     **/
    public Boolean hasNoBackgroundVideo() {
        JSONArray videosObject = (JSONArray) propertyObject.get("videos");
        return videosObject.size() == 0;
    }

    /**
     * Lets you know if a property has the property title specified by propertyTitle
     *
     * @param propertyTitle
     * @return true if a property has has the property title specified by propertyTitle,
     *         false otherwise
     **/
    public Boolean hasPropertyTitle(String propertyTitle) {
        if (getPropertyTitle() != null) {
            return getPropertyTitle().equals(propertyTitle);
        }
        return null;
    }

    /**
     * Lets you know if a property has the property id specified by propertyId
     *
     * @param propertyId
     * @return true if a property has the property id specified by propertyId, false
     *         otherwise
     **/
    public Boolean hasPropertyId(String propertyId) {
        if (getPropertyId() != null) {
            return getPropertyId().contains(propertyId);
        }
        return null;
    }

    /**
     * Lets you know if a property has the property mgid specified by property MGID
     *
     * @param propertyMGID
     * @return true if a property has the property mgid specified by propertyMGID, false
     *         otherwise
     **/
    public Boolean hasPropertyMGID(String propertyMGID) {
        if (getPropertyMGID() != null) {
            return getPropertyMGID().contains(propertyMGID);
        }
        return null;
    }

    /**
     * Lets you know if a property has the property detail specified by propertyDetail
     *
     * @param propertyDetail
     * @return true if a property has the property detail specified by propertyDetail,
     *         false otherwise
     **/
    public Boolean hasPropertyDetail(String propertyDetail) {
        return hasPropertyDescription(propertyDetail);
    }

    /**
     * Lets you know if a property has the property description specified by
     * propertyDescription
     *
     * @param propertyDescription
     * @return true if a property has the property description specified by
     *         propertyDescription, false otherwise
     **/
    public Boolean hasPropertyDescription(String propertyDescription) {
        if (getPropertyDescription() != null) {
            return getPropertyDescription().contains(propertyDescription);
        }
        return null;
    }

    /**
     * Lets you know if a property has the number of swipes specified in numOfSwipes
     * Great for getting the property before and after a specified property
     *
     * @param numOfSwipes
     * @return true if a property has the number of swipes specified in numOfSwipes,
     *         false otherwise
     **/
    public Boolean hasNumOfSwipes(Integer numOfSwipes) {
        return getNumOfSwipes().equals(numOfSwipes);
    }

    /**
     * Gets the number of swipes necessary to reach the property in the carousel
     *
     * @return the number of swipes necessary to reach the property in the carousel
     **/
    public Integer getNumOfSwipes() {
        return numOfSwipes;
    }

    /**
     * Get the index of property on the carousel. Useful for omniture tests where we have to provide
     * the property index of a property to generate the Main Screen - Px value.
     * @return integer property index on carousel.
     */
    public Integer getPositionOnCarousel() {
        return numOfSwipes + 1;
    }

    /**
     * Gets the property position in the carousel
     *
     * @return the property position in the carousel
     **/
    public Integer getPropertyPosition() {
        return getNumOfSwipes();
    }

    /**
     * Gets the property id from the mgid of the property object. Note: Just
     * understand that the propertyId is always right before the last ":" character
     * This is an mgid:
     * mgid:arc:property:tvland.com:a2548008-da22-11e6-bfd4-0026b9414f30. Notice how
     * the property id starts right after the last colon?
     *
     * @return the property id from the mgid of the property object.
     **/
    private String getPropertyId(JSONObject propertyObject) {
        if (propertyObject.containsKey("id")) {
            return propertyObject.get("id").toString();
        }
        return null;
    }

    /**
     * Gets the longform filter for the property to further filter the longforms content
     *
     * @return the longform filter for the property to further filter the longform content
     **/
    public LongformFilter getLongformFilter() {
        return new LongformFilter(this);
    }

    /**
     * Gets the shortform filter for the property to further filter the shortform content
     *
     * @return the shortform filter for the property to further filter the episodes
     **/
    public ShortformFilter getShortformFilter() {
        return new ShortformFilter(this);
    }

    /**
     * Gets the property filter for the property to further filter the episodes
     *
     * @return the episodes filter for the property to further filter the episodes
     **/
    public LongformFilter getLongformFilter(Pagination pagination) {
        return new LongformFilter(this, pagination);
    }

    /**
     * Gets the shortform filter for the property to further filter the shortform content
     *
     * @return the shortform filter for the property to further filter the episodes
     **/
    public ShortformFilter getShortformFilter(Pagination pagination) {
        return new ShortformFilter(this, pagination);
    }

    /**
     * Gets the episode filter for the property to further filter the episode
     *
     * @return the episode filter for the property to further filter the episode
     **/
    private Long getEpisodePageSize() {
        return getLongformFilter().episodesFilter().getEpisodePageSize();
    }

    @SuppressWarnings("unused")
	private Long setEpisdodeTotalItems() {
        return getLongformFilter().episodesFilter().getEpisodeTotalItems();
    }

    /**
     * Gets the clips filter for the property to further filter the clips
     *
     * @return the clips filter for the property to further filter the clips
     **/
    private Long getClipPageSize() {
        return getShortformFilter().clipsFilter().getClipPageSize();
    }

    @SuppressWarnings("unused")
	private Long setClipTotalItems() {
        return getShortformFilter().clipsFilter().getClipTotalItems();
    }

    /**
     * Gets the movie filter for the property to further filter the movie
     *
     * @return the movie filter for the property to further filter the movie
     **/
    private Long getMoviePageSize() {
        return getLongformFilter().movieFilter().getMoviePageSize();
    }

    @SuppressWarnings("unused")
	private Long setMovieTotalItems() {
        return getLongformFilter().movieFilter().getMovieTotalItems();
    }

    /**
     * Gets the trailer filter for the property to further filter the trailer
     *
     * @return the trailer filter for the property to further filter the trailer
     **/
    private Long getTrailerPageSize() {
        return getShortformFilter().trailersFilter().getTrailerPageSize();
    }

    @SuppressWarnings("unused")
	private Long setTrailerTotalItems() {
        return getShortformFilter().trailersFilter().getTrailerTotalItems();
    }

    /**
     * Gets the fight filter for the property to further filter the fight
     *
     * @return the fight filter for the property to further filter the fight
     **/
    private Long getFightPageSize() {
        return getLongformFilter().fightFilter().getFightPageSize();
    }

    @SuppressWarnings("unused")
	private Long setFightTotalItems() {
        return getLongformFilter().fightFilter().getFightTotalItems();
    }

    /**
     * Gets the music video filter for the property to further filter the music video
     *
     * @return the music video filter for the property to further filter the music video
     **/
    private Long getMusicVideoPageSize() {
        return getShortformFilter().trailersFilter().getMusicVideoPageSize();
    }

    @SuppressWarnings("unused")
	private Long setMusicVideoTotalItems() {
        return getShortformFilter().trailersFilter().getMusicVideoTotalItems();
    }

    /**
     * Sets the number of pages of episodes a property has
     *
     * @return the number of pages of episodes videos
     **/
    private Long setNumberOfEpisodesPages() {
        return (episodesTotalItems / episodesPageSize) + 1;
    }

    /**
     * Sets the number of pages of clips a property has
     *
     * @return the number of pages of clips videos
     **/
    private Long setNumberOfClipsPages() {
        return (clipsTotalItems / clipsPageSize) + 1;
    }

    /**
     * Sets the number of pages of movies a property has
     *
     * @return the number of pages of movies videos
     **/
    private Long setNumberOfMoviesPages() {
        return (moviesTotalItems / moviesPageSize) + 1;
    }

    /**
     * Sets the number of pages of trailers a property has
     *
     * @return the number of pages of trailers videos
     **/
    private Long setNumberOfTrailersPages() {
        return (trailersTotalItems / trailersPageSize) + 1;
    }

    @SuppressWarnings("unused")
	private Long setMusicVideosPageSize() {
        return (musicVideosTotalItems / musicVideosPageSize) + 1;
    }

    /**
     * Sets the number of pages of fights a property has
     *
     * @return the number of pages of fights videos
     **/
    private Long setNumberOfFightsPages() {
        return (fightsTotalItems / fightsPageSize) + 1;
    }

    /**
     * Sets the number of pages of music videos a property has
     *
     * @return the number of pages of music videos
     **/
    private Long setNumberOfMusicVideosPages() {
        return (musicVideosTotalItems / musicVideosPageSize) + 1;
    }

    /**
     * Gets the entire json reponse from the property items url 
     *
     * @return the entire json reponse from the property items url
     **/
    public JSONObject getPropertyJSONReponse() {
        return this.propertyJSON;
    }

    /**
     * Gets the property object as raw json
     *
     * @return the property object as raw json
     **/
    public JSONObject toJSON() {
        return this.propertyObject;
    }

    /**
     * Gets the PropertyResult in string form
     *
     * @return the PropertyResult in string form
     **/
    public String toString() {
        return propertyObject.toString();
    }

    /**
     * Lets you know if a property has links
     *
     * @return true if a property has a links, false otherwise
     **/
    public Boolean hasLinks() {
        return propertyObject.containsKey("links");
    }

    /**
     * Lets you know if a property has a videoDescriptor key
     *
     * @return true if a property has a videoDescriptor, false otherwise
     **/
    public Boolean hasVideoDesciptor() {
        return propertyObject.containsKey("videoDescriptor");
    }

    /**
     * Lets you know if a property has links object and property link available in it
     *
     * @return true if a property has links object and property link available, false otherwise
     **/
    private JSONObject getlinkObject() {
        if (hasLinks()) {
            return (JSONObject) propertyObject.get("links");
        } else {
            Logger.logMessage(propertyObject.get("title") + " does not contain links object!");
            return propertyObject;
        }
    }

    /**
     * Lets you know if a property has seasons
     *
     * @return true if a property has seasons, false otherwise
     **/
    public Boolean hasSeasons() {
        return getlinkObject().containsKey("season");
    }

    /**
     * Gets the URL of a season
     *
     * @return season URL as a string
     **/
    public String getSeasonsURL() {
        if (getlinkObject().containsKey("season")){
         	return (String) getlinkObject().get("season");
         }
        return null;
     }

    /**
     * Lets you know if a property has playlist
     *
     * @return true if a property has playlist, false otherwise
     **/
    public Boolean hasPlaylist() {
        return getlinkObject().containsKey("playlist");
    }

    /**
     * Gets the URL of a playlist
     *
     * @return playlist URL as a string
     **/
    public String getPlaylistURL() {
        if (getlinkObject().containsKey("playlist")){
         	return (String) getlinkObject().get("playlist");
         }
        return null;
     }

    /**
     * Gets the URL of an extra
     *
     * @return extras URL as a string
     **/
    public String getExtrasURL() {
        if (getlinkObject().containsKey("video")){
         	return (String) getlinkObject().get("video");
         }
        return null;
     }
    
    /**
     * Lets you know if a property has movie
     *
     * @return true if a property has movie, false otherwise
     **/
    public Boolean hasMovie() {
        return getlinkObject().containsKey("movie");
    }

    /**
     * Lets you know if a property has an event
     *
     * @return true if a property has an event, false otherwise
     **/
    public Boolean hasEvent() {
        return getlinkObject().containsKey("playlist");
    }

    /**
     * Gets the URL of a movie
     *
     * @return movie URL as a string
     **/
    public String getMovieURL() {
       if (getlinkObject().containsKey("movie")) {
        	return (String) getlinkObject().get("movie");
        }
       return null;
    }

    /**
     * Gets the URL of a trailer
     *
     * @return trailer URL as a string
     **/
    public String getTrailerURL() {
        if (getlinkObject().containsKey("video")){
         	return (String) getlinkObject().get("video");
         }
        return null;
     }

    /**
     * Lets you know if a EntityType has movie
     *
     * @return true if a property has movie, false otherwise
     **/
    public Boolean hasTrailer() {
    	return getlinkObject().containsKey("video");
    }
    
    /**
     * Lets you know if a property has music videos
     *
     * @return true if a property has music videos, false otherwise
     **/
    public Boolean hasMusicVideo() {
        if (propertyObject.get("subType").toString().equalsIgnoreCase("Clip Collection")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Lets you know if a property has a content rating
     *
     * @return true if a property has content rating, false otherwise
     **/
    public Boolean hasContentRating() {
        return propertyObject.containsKey("contentRating");
    }

    /**
     * Gets the content rating object from s property
     *
     * @return content rating json object
     **/
    public JSONObject getContentRatingObject() {
        return (JSONObject) propertyObject.get("contentRating");
    }

    /**
     * Gets the TVPG rating from a property
     *
     * @return content TVPG rating as a string
     **/
    public String getTVPGContentRating() {
        if (getContentRatingObject() != null) {
            return this.get("contentRating","TV-PG").toString();
        }
        return null;
    }

    /**
     * Lets you know if a property has a ribbon object
     *
     * @return true if a property has ribbon object, false otherwise
     **/
    public Boolean hasRibbonObject() {
        return propertyObject.containsKey("ribbon");
    }

    /**
     * Lets you know if a property has a duration
     *
     * @return true if a property has a duration, false otherwise
     **/
    public Boolean hasDuration() {
        return propertyObject.containsKey("duration");
    }

    /**
     * Lets you know if a property has a duration object
     *
     * @return true if a property has duration object, false otherwise
     **/
    public JSONObject getDurationObject() {
        return (JSONObject)propertyObject.get("duration");
    }

    /**
     * Lets you know if a EntityType has Keywords
     *
     * @return true if a property has Keywords, false otherwise
     **/
    public Boolean hasKeywords() {
    	return propertyObject.containsKey("keywords");
    }
    
    /**
     * Gets the duration and timecode from a property
     *
     * @return gets the duration as a string
     **/
    public String getDuration() {
        if (getDurationObject() != null) {
            return this.get("duration","timecode").toString();
        }
        return null;
    }
}
