package com.viacom.test.vimn.common.filters;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PropertyFilter {

    public PropertyResults propertyResults;

    /**
     * This is the constructor that you should use. It combines the featured
     * series with the promo list series
     **/
    protected PropertyFilter() {
    }

    /**
     * Gets a ProprtyFilter with the set of series that has a minimum number of
     * episodes specified by minLongformCount
     *
     * @param minEpisodesCount
     *          - the minimum number of episodes
     * @return PropertyFilter with the set of series that has a minimum number of
     *         episodes specified by minLongformCount
     **/
    public PropertyFilter withMinEpisodesCount(Integer minEpisodesCount) {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasMinEpisodesCount(minEpisodesCount));
        return this;
    }

    /**
     * Gets a PropertyFilter with the set of property that has a maximum number of
     * episodes specified by maxEpisodesCount
     *
     * @param maxEpisodesCount
     *          - the maximum number of episodes
     * @return PropertyFilter with the set of series that has a maximum number of
     *         episodes specified by maxEpisodesCount
     **/
    public PropertyFilter withMaxEpisodesCount(Integer maxEpisodesCount) {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasMaxEpisodesCount(maxEpisodesCount));
        return this;
    }

    /**
     * Gets a ProprtyFilter with the set of property that has a minimum number of
     * fights specified by minTrailers
     *
     * @param minTrailersCount
     *          - the minimum number of trailers
     * @return PropertyFilter with the set of property that has a minimum number of
     *         trailers specified by minTrailers
     **/
    public PropertyFilter withMinTrailersCount(Integer minTrailersCount) {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasMinTrailersCount(minTrailersCount));
        return this;
    }

    /**
     * Gets a PropertyFilter with the set of property that has a maximum number of
     * trailers specified by maxTrailersCount
     *
     * @param maxTrailersCount
     *          - the maximum number of trailers
     * @return PropertyFilter with the set of property that has a maximum number of
     *         trailers specified by maxTrailersCount
     **/
    public PropertyFilter withMaxTrailersCount(Integer maxTrailersCount) {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasMaxTrailersCount(maxTrailersCount));
        return this;
    }


    /**
     * Gets a PropertyFilter with the set of property that has public episodes
     *
     * @return PropertyFilter with the set of property that has public episodes
     **/
    public PropertyFilter withPublicEpisodes() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasPublicEpisodes());
        return this;
    }

    /**
     * Gets a PropertyFilter with the set of property that has private episodes
     *
     * @return PropertyFilter with the set of property that has private episodes
     **/
    public PropertyFilter withPrivateEpisodes() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasPrivateEpisodes());
        return this;
    }

    /**
     * Gets a PropertyFilter with the set of property that has public clips
     *
     * @return PropertyFilter with the set of property that has public clips
     **/
    public PropertyFilter withPublicClips() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasPublicClips());
        return this;
    }

    /**
     * Gets a PropertyFilter with the set of property that has private clips
     *
     * @return PropertyFilter with the set of property that has private clips
     **/
    public PropertyFilter withPrivateClips() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasPublicClips());
        return this;
    }

    /**
     * Gets a PropertyFilter with the set of property that has public movies
     *
     * @return PropertyFilter with the set of property that has public movies
     **/
    public PropertyFilter withPublicMovies() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasPublicMovies());
        return this;
    }

    /**
     * Gets a PropertyFilter with the set of property that has private clips
     *
     * @return PropertyFilter with the set of property that has private clips
     **/
    public PropertyFilter withPrivateMovies() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasPublicMovies());
        return this;
    }

    /**
     * Gets a PropertyFilter with the set of property that has public clips
     *
     * @return PropertyFilter with the set of property that has public clips
     **/
    public PropertyFilter withPublicTrailers() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasPublicTrailers());
        return this;
    }

    /**
     * Gets a PropertyFilter with the set of property that has private clips
     *
     * @return PropertyFilter with the set of property that has private clips
     **/
    public PropertyFilter withPrivateTrailers() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasPublicTrailers());
        return this;
    }

    /**
     * Gets a PropertyFilter with the set of property that has public fights
     *
     * @return PropertyFilter with the set of property that has public fights
     **/
    public PropertyFilter withPublicFights() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasPublicFights());
        return this;
    }

    /**
     * Gets a PropertyFilter with the set of property that has private fights
     *
     * @return PropertyFilter with the set of property that has private fights
     **/
    public PropertyFilter withPrivateFights() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasPublicFights());
        return this;
    }

    /**
     * Gets a PropertyFilter with the set of property that has public music videos
     *
     * @return PropertyFilter with the set of property that has public music videos
     **/
    public PropertyFilter withPublicMusicVideos() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasPublicMusicVideos());
        return this;
    }

    /**
     * Gets a PropertyFilter with the set of property that has private music videos
     *
     * @return PropertyFilter with the set of property that has private music videos
     **/
    public PropertyFilter withPrivateMusicVideos() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasPublicMusicVideos());
        return this;
    }

    /**
     * Gets a PropertyFilter with the set of series that has a minimum of series pages specified
     * @param numberOfPages
     *
     * @return PropertyFilter with the set of series that has a minimum of series pages specified
     */
    public PropertyFilter withPaginatedPropertiesMinimumOf(Integer numberOfPages) {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasMinimumOfLongformPages(numberOfPages));
        return this;
    }

    /**
     * Gets a PropertyFilter with the set of series that has a minimum of clips pages specified
     * @param numberOfPages
     *
     * @return PropertyFilter with the set of series that has a minimum of clips pages specified
     */
    public PropertyFilter withPaginatedClipsMinimumOf(Integer numberOfPages) {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasMinimumOfClipsPages(numberOfPages));
        return this;
    }

    /**
     * Gets a PropertyFilter with the set of series that has new episodes
     *
     * @return PropertyFilter with the set of series that has new episodes
     **/
    public PropertyFilter withNewEpisodes() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasNewEpisode());
        return this;
    }

    /**
     * Gets a PropertyFilter with the set of series that has no new episodes
     *
     * @return PropertyFilter with the set of series that has no new episodes
     **/
    public PropertyFilter withNoNewEpisodes() {
        propertyResults.removeIf(propertyResult -> propertyResult.hasNewEpisode());
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of series that are new
     *
     *
     *
     * @return PropertyFilter that has the set of series that are new
     **/
    public PropertyFilter withNewSeries() {
        propertyResults.removeIf(propertyResult -> !propertyResult.isNewSeries());
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of series that are not new
     *
     * @return PropertyFilter that has the set of series that are not new
     **/
    public PropertyFilter withNoNewSeries() {
        propertyResults.removeIf(propertyResult -> !propertyResult.isNotNewSeries());
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of series that has a new season
     *
     * @return PropertyFilter that has the set of series that has a new season
     **/
    public PropertyFilter withNewSeasons() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasNewSeasons());
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of series with no new seasons
     *
     * @return PropertyFilter that has the set of series with no new seasons
     **/
    public PropertyFilter withNoNewSeasons() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasNoNewSeasons());
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of series that is less than the
     * specified number of days old
     *
     * @param numDays
     *          - the number of days that the series should be younger than
     *
     * @return PropertyFilter that has the set of series that is less than the
     *         specified number of days old
     **/
    public PropertyFilter withLessThanNumDaysOld(int numDays) {
        propertyResults.removeIf(propertyResult -> !propertyResult.isLessThanNumDaysOld(numDays));
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of series that has episodes only
     *
     * @return PropertyFilter that has the set of series that has episodes only
     **/
    public PropertyFilter withEpisodesOnly() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasEpisodesOnly());
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of series that has clips
     *
     * @return PropertyFilter that has the set of series that has clips
     **/
    public PropertyFilter withClips() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasClips());
        return this;
    }
    
    /**
     * Gets a PropertyFilter that has the set of series that has clips
     *
     * @return PropertyFilter that has the set of series that has clips
     **/
    public PropertyFilter withClipsOnly() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasClipsOnly());
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of movies
     *
     * @return PropertyFilter that has the set of movies
     **/
    public PropertyFilter withMoviesOnly() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasMovie());
        return this;
    }

    /**
     * Gets a PropertyFilter that
     *
     * @return PropertyFilter that has the set of series that has clips
     **/
    public PropertyFilter withSeries() {
        propertyResults.removeIf(propertyResult -> !propertyResult.isSeriesProperty());
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of series with seasons
     *
     * @return PropertyFilter that has the set of series with seasons
     **/
    public PropertyFilter withSeason() {
        propertyResults.removeIf(propertyResult -> !propertyResult.isSeasonProperty());
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of series with videos
     *
     * @return PropertyFilter that has the set of series with videos
     **/
    public PropertyFilter withVideo() {
        propertyResults.removeIf(propertyResult -> !propertyResult.isVideoProperty());
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of series with episodes
     *
     * @return PropertyFilter that has the set of series with episodes
     **/
    public PropertyFilter withEpisodes() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasEpisodes());
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of movies
     *
     * @return PropertyFilter that has the set of movies
     **/
    public PropertyFilter withMovie() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasMovie());
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of trailers
     *
     * @return PropertyFilter that has the set of trailers
     **/
    public PropertyFilter withTrailer() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasTrailer());
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of fights
     *
     * @return PropertyFilter that has the set of fights
     **/
    public PropertyFilter withFight() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasEvent());
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of music videos
     *
     * @return PropertyFilter that has the set of music videos
     **/
    public PropertyFilter withMusicVideo() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasMusicVideo());
        return this;
    }

    /**
     * Gets a PropertyFilter that is a standup property
     *
     * @return PropertyFilter that is a standup property
     *
     **/
    public PropertyFilter withStandup() {
        propertyResults.removeIf(propertyResult -> !propertyResult.isStandUpProperty());
        return this;
    }

    /**
     * Gets a PropertyFilter that is a franchise property
     *
     * @return PropertyFilter that is a franchise property
     **/
    public PropertyFilter withFranchise() {
        propertyResults.removeIf(propertyResult -> !propertyResult.isFranchiseProperty());
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of series that has background video
     *
     * @return PropertyFilter that has the set of series that has background video
     **/
    public PropertyFilter withBackgroundVideo() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasBackgroundVideo());
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of series that has background image
     *
     * @return PropertyFilter that has the set of series that has background image
     **/
    public PropertyFilter withBackgroundImage() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasBackgroundVideo());
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of series that has no background video
     *
     * @return PropertyFilter that has the set of series that has no background video
     **/
    public PropertyFilter withNoBackgroundVideo() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasNoBackgroundVideo());
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of series that has no background image
     *
     * @return PropertyFilter that has the set of series that has no background image
     **/
    public PropertyFilter withNoBackgroundImage() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasBackgroundVideo());
        return this;
    }


    /**
     * Gets a PropertyFilter that has the set of series that have the specified
     * series title
     *
     * @param propertyId
     * @return PropertyFilter that has the set of series that have the specified
     *         series title
     **/
    public PropertyFilter withPropertyTitle(String propertyId) {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasPropertyTitle(propertyId));
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of series that have the specified
     * series ID
     *
     * @param propertyId
     * @return PropertyFilter that has the set of series that have the specified
     *         property ID
     **/
    public PropertyFilter withSeriesId(String propertyId) {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasPropertyId(propertyId));
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of series that have the specified
     * series MGID
     *
     * @param propertyMGID
     * @return PropertyFilter that has the set of series that have the specified
     *         series MGID
     **/
    public PropertyFilter withSeriesMGID(String propertyMGID) {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasPropertyMGID(propertyMGID));
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of series that have the specified
     * series detail
     *
     * @param propertyDetail
     * @return PropertyFilter that has the set of series that have the specified
     *         property detail
     **/
    public PropertyFilter withPropertyDetail(String propertyDetail) {
        return withSeriesDescription(propertyDetail);
    }

    /**
     * Gets a PropertyFilter that has the set of series that have the specified
     * series detail
     *
     * @param propertyDescription
     *
     * @return PropertyFilter that has the set of series that have the specified
     *         property detail
     **/
    public PropertyFilter withSeriesDescription(String propertyDescription) {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasPropertyDescription(propertyDescription));
        return this;
    }

    /**
     * Gets a PropertyFilter that has the set of series that have the specified
     * numOfSwipes Great for getting the series before and after a specified
     * series
     *
     * @param numOfSwipes
     *
     * @return a PropertyFilter that has the set of series that have the specified
     *         numOfSwipes
     **/
    public PropertyFilter withNumOfSwipes(Integer numOfSwipes) {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasNumOfSwipes(numOfSwipes));
        return this;
    }

    /**
     * Gets a PropertyFilter that has the Keywords
     *
     * @return PropertyFilter that has the Keywords
     **/
    public PropertyFilter withKeywords() {
        propertyResults.removeIf(propertyResult -> !propertyResult.hasKeywords());
        return this;
    }
    
    /**
     * Orders the propertyResults according to the field name in ascending or
     * descending order (Might want to deprecate this)
     *
     * @param order
     *          - Get the order from the Order class
     * @param fieldNames
     *          - Get the episode field names from the Order class
     * @return propertyResults ordered according to the field name in ascending or
     *         descending order
     **/
    public PropertyFilter orderBy(String order, String... fieldNames) {
        propertyResults.orderBy(order, fieldNames);
        return this;
    }

    /**
     * Gets the results of the query as a PropertyResults
     *
     * @return the results of the query as a PropertyResults
     **/
    public PropertyResults propertyFilter() {
        return propertyResults;
    }

    /********************************************************************
     * TODO: Non-Filtering methods
     *******************************************************************/

    /**
     * Gets the resulting query of the series as json array NOTE: NEVER return
     * propertyJSON since that is never altered even after all the filtering. What's
     * filtered is actually the array list which holds the series results
     *
     * @return the resulting query of the series as json array
     **/
    public JSONArray toJSONArray() {
        return propertyResults.toJSONArray();
    }

    /**
     * Gets the current result of our filtering so far in string form
     *
     * @return current result of our filtering so far in string form
     **/
    public String toString() {
        return propertyResults.toString();
    }

    /**
     * Turns the raw JSON that represents the series objects into a more
     * manageable PropertyResults class
     *
     * @param propertyJSON
     *          - the json form of the series objects with the data and items
     *          flags
     * @return the series objects as a PropertyResults
     **/
    public PropertyResults convertToPropertyResults(JSONObject propertyJSON, Integer numOfFeaturedProperties) {
        return new PropertyResults(propertyJSON, numOfFeaturedProperties);
    }

    /**
     * Overload method that accepts pagination argument.
     * @param propertyJSON
     * @param numOfFeaturedProperties
     * @param pagination
     * @return the paginated or non paginated series objects as a PropertyResults
     */
    public PropertyResults convertToPropertyResults(JSONObject propertyJSON, Integer numOfFeaturedProperties, Pagination pagination) {
        return new PropertyResults(propertyJSON, numOfFeaturedProperties, pagination);
    }

}
