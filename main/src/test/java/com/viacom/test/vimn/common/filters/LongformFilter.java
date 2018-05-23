package com.viacom.test.vimn.common.filters;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

public class LongformFilter {
    private PropertyResult property;
    private JSONObject episodeJSON;
    private JSONObject movieJSON;
    private JSONObject fightJSON;
    private LongformResults episodeResults;
    private LongformResults movieResults;
    private LongformResults fightResults;
    private List<JSONObject> episodeJSONs;
    private List<JSONObject> movieJSONs;
    private List<JSONObject> fightJSONs;

    /**
     * Constructor
     * @param propertyResult
     */
    public LongformFilter(PropertyResult propertyResult) {
        this.property = propertyResult;
        if (property.isMovieProperty()) {
        	this.movieJSON = FilterUtils.getJSONFeed(property.getMovieURL());
            this.movieResults = convertToLongformResults(movieJSON);
        }
        if (property.isFightProperty()) {
        	this.fightJSON = FilterUtils.getJSONFeed(property.getPlaylistURL());
            this.fightResults = convertToLongformResults(fightJSON);
        }
        if (property.isSeriesProperty()) {
        	this.episodeJSON = FilterUtils.getJSONFeed(property.getEpisodeURL());
            this.episodeResults = convertToLongformResults(episodeJSON);
        }
    }

    /**
     * Constructor that takes paginated argument
     * @param propertyResult
     * @param pagination
     */
    public LongformFilter(PropertyResult propertyResult, Pagination pagination) {
        this.property = propertyResult;
        episodeJSONs = new ArrayList<>();
        movieJSONs = new ArrayList<>();
        fightJSONs = new ArrayList<>();

        switch (pagination) {
            case PAGINATED:
                if (property.isEpisodeProperty()) {
                    property.getPaginatedLongformURLs().forEach(url -> episodeJSONs.add(FilterUtils.getJSONFeed(url)));
                    episodeResults = convertToLongformResults(episodeJSON);
                }
                if (property.isMovieProperty()) {
                    property.getPaginatedLongformURLs().forEach(url -> movieJSONs.add(FilterUtils.getJSONFeed(url)));
                    movieResults = convertToLongformResults(movieJSON);
                }
                if (property.isFightProperty()) {
                    property.getPaginatedLongformURLs().forEach(url -> fightJSONs.add(FilterUtils.getJSONFeed(url)));
                    fightResults = convertToLongformResults(fightJSON);
                }
                break;
            case NON_PAGINATED:
            if (property.isEpisodeProperty()) {
                episodeJSONs.add(FilterUtils.getJSONFeed(property.getPropertyURL()));
                episodeResults = convertToLongformResults(episodeJSON);
            }
            if (property.isMovieProperty()) {
                movieJSONs.add(FilterUtils.getJSONFeed(property.getPropertyURL()));
                movieResults = convertToLongformResults(movieJSON);
            }
            if (property.isFightProperty()) {
                    fightJSONs.add(FilterUtils.getJSONFeed(property.getPropertyURL()));
                fightResults = convertToLongformResults(fightJSON);
            }
            break;
        }
    }

	/**
     * Gets episodes that are public
     *
     * @return public episodes
     **/
    public LongformFilter withPublicEpisodes() {
            episodeResults.removeIf(episodeResult -> !episodeResult.isPublicEpisode());
        return this;
    }

    /**
     * Gets episode that are private
     *
     * @return private episode
     **/
    public LongformFilter withPrivateEpisodes() {
        episodeResults.removeIf(episodeResult -> !episodeResult.isPrivateEpisode());
        return this;
    }

    /**
     * Gets only episodes that are public
     *
     * @return public episodes
     **/
    public LongformFilter withPublicEpisodesOnly() {
        episodeResults.removeIf(episodeResult -> !episodeResult.isPublicEpisode());
        return this;
    }

    /**
     * Gets only episodes that are private
     *
     * @return private episodes
     **/
    public LongformFilter withPrivateEpisodesOnly() {
        episodeResults.removeIf(episodeResult -> !episodeResult.isPrivateEpisode());
        return this;
    }

    /**
     * Gets episode that have related shortform
     *
     * @return episode that have related episodes
     **/
    public LongformFilter withRelatedClips() {
        episodeResults.removeIf(episodeResult -> episodeResult.hasRelatedClips());
        return this;
    }

    /**
     * Gets episode that have no related episodes
     *
     * @return episode that have no related episodes
     **/
    public LongformFilter withNoRelatedClips() {
        episodeResults.removeIf(episodeResult -> episodeResult.hasNoRelatedClips());
        return this;
    }

    /**
     * Gets episodes that have the clips title specified in episode
     *
     * @return episode that have the episode title specified in episodeTitle
     **/
    public LongformFilter withEpisodeTitle(String episodeTitle) {
        episodeResults.removeIf(episodeResult -> episodeResult.hasEpisodeTitle(episodeTitle));
        return this;
    }

    /**
     * Gets episode that have the episode description specified in episodeDescription
     *
     * @return episode that have the episode description specified in episodeDescription
     **/
    public LongformFilter withEpisodeDescription(String episodeDescription) {
        episodeResults.removeIf(episodeResult -> episodeResult.hasEpisodeDescription(episodeDescription));
        return this;
    }

    /**
     * Gets episode that have the episode description specified in episodeDescription
     *
     * @return episode that have the episode description specified in episodeDescription
     **/
    public LongformFilter withEpisodeSeasonNumber(int episodeSeasonNumber) {
        episodeResults.removeIf(episodeResult -> episodeResult.hasEpisodeSeasonNumber(episodeSeasonNumber));
        return this;
    }

    /**
     * Gets episode that have the episode duration in milliseconds specified in episodeDurationInMilliSeconds
     *
     * @return episode that have the episode duration in milliseconds specified in episodeDurationInMilliSeconds
     **/
    public LongformFilter withEpisodeDurationInMilliSeconds(Long episodeDurationInMilliSeconds) {
        episodeResults.removeIf(episodeResult -> episodeResult.hasEpisodeDurationInMilliSeconds(episodeDurationInMilliSeconds));
        return this;
    }

    /**
     * Gets episode that have the episode duration in time code specified in episodeDurationInTimeCode
     *
     * @return episode that have the episode duration in time code specified in episodeDurationInTimeCode
     **/
    public LongformFilter withEpisodeDurationInTimeCode(String episodeDurationInTimeCode) {
        episodeResults.removeIf(episodeResult -> episodeResult.hasMovieDurationInTimeCode(episodeDurationInTimeCode));
        return this;
    }

    /**
     * Gets episode that have the episode mgid specified in episodeMGID
     *
     * @return episode that have the episode mgid specified in episodeMGID
     **/
    public LongformFilter episodesWithMGID(String episodeMGID) {
        episodeResults.removeIf(episodeResult -> episodeResult.hasEpisodeMGID(episodeMGID));
        return this;
    }


    public LongformResults episodesFilter() {
        return episodeResults;
    }

    /**
     * Gets fights that are public
     *
     * @return public fights
     **/
    public LongformFilter withPublicFights() {
        fightResults.removeIf(fightResult -> !fightResult.isPublicFight());
        return this;
    }

    /**
     * Gets fights that are private
     *
     * @return private fights
     **/
    public LongformFilter withPrivateFights() {
        fightResults.removeIf(fightResult -> !fightResult.isPrivateFight());
        return this;
    }

    /**
     * Gets only fights that are public
     *
     * @return public fights
     **/
    public LongformFilter withPublicFightsOnly() {
        fightResults.removeIf(fightResult -> !fightResult.isPublicFight());
        return this;
    }

    /**
     * Gets only fights that are private
     *
     * @return private fights
     **/
    public LongformFilter withPrivateFightsOnly() {
        fightResults.removeIf(fightResult -> fightResult.isPublicFight());
        return this;
    }


    /**
     * Gets fights that have the clips title specified in episode
     *
     * @return fight that have the fight title specified in episodeTitle
     **/
    public LongformFilter withFightTitle(String fightTitle) {
        fightResults.removeIf(fightResult -> fightResult.hasFightTitle(fightTitle));
        return this;
    }

    /**
     * Gets fights that have the fights description specified in fightDescription
     *
     * @return fights that have the fights description specified in fightDescription
     **/
    public LongformFilter withFightDescription(String fightDescription) {
        fightResults.removeIf(fightResult -> fightResult.hasFightDescription(fightDescription));
        return this;
    }

    /**
     * Gets fight that have the fight duration in milliseconds specified in fighturationInMilliSeconds
     *
     * @return fight that have the fight duration in milliseconds specified in fightDurationInMilliSeconds
     **/
    public LongformFilter withFightDurationInMilliSeconds(Long fightDurationInMilliSeconds) {
        fightResults.removeIf(fightResult -> fightResult.hasFightDurationInMilliSeconds(fightDurationInMilliSeconds));
        return this;
    }

    /**
     * Gets fight that have the fight duration in time code specified in fightDurationInTimeCode
     *
     * @return fight that have the fight duration in time code specified in fightDurationInTimeCode
     **/
    public LongformFilter withFightDurationInTimeCode(String fightDurationInTimeCode) {
        fightResults.removeIf(fightResult -> fightResult.hasFightDurationInTimeCode(fightDurationInTimeCode));
        return this;
    }

    /**
     * Gets fight that have the fight mgid specified in fightMGID
     *
     * @return fight that have the fight mgid specified in fightMGID
     **/
    public LongformFilter fightsWithMGID(String fightMGID) {
        fightResults.removeIf(fightResult -> fightResult.hasFightMGID(fightMGID));
        return this;
    }

    public LongformResults fightFilter() {
        return fightResults;
    }
    
    /**
     * Gets movies that are public
     *
     * @return movie fights
     **/
    public LongformFilter withPublicMovies() {
        movieResults.removeIf(movieResult -> !movieResult.isPublicMovie());
        return this;
    }

    /**
     * Gets movies that are private
     *
     * @return private movies
     **/
    public LongformFilter withPrivateMovies() {
        movieResults.removeIf(movieResult -> !movieResult.isPrivateMovie());
        return this;
    }

    /**
     * Gets only movies that are public
     *
     * @return public movies
     **/
    public LongformFilter withPublicMoviesOnly() {
        movieResults.removeIf(movieResult -> !movieResult.isPublicMovie());
        return this;
    }

    /**
     * Gets only movies that are private
     *
     * @return private movies
     **/
    public LongformFilter withPrivateMoviesOnly() {
        movieResults.removeIf(movieResult -> !movieResult.isPrivateMovie());
        return this;
    }

    /**
     * Gets movie that have related trailers
     *
     * @return movie that have related trailers
     **/
    public LongformFilter withRelatedTrailers() {
        movieResults.removeIf(movieResult -> movieResult.hasNoRelatedTrailers());
        return this;
    }

    /**
     * Gets movie that have no related trailers
     *
     * @return movie that have no related trailers
     **/
    public LongformFilter withNoRelatedTrailer() {
        movieResults.removeIf(movieResult -> movieResult.hasRelatedTrailers());
        return this;
    }

    /**
     * Gets movies that have the movie title specified in movie
     *
     * @return movie that have the movie title specified in movieTitle
     **/
    public LongformFilter withMovieTitle(String movieTitle) {
        movieResults.removeIf(movieResult -> movieResult.hasMovieTitle(movieTitle));
        return this;
    }

    /**
     * Gets movies that have the movies description specified in movieDescription
     *
     * @return movies that have the movies description specified in movieDescription
     **/
    public LongformFilter withMovieDescription(String movieDescription) {
        movieResults.removeIf(movieResult -> movieResult.hasMovieDescription(movieDescription));
        return this;
    }

    /**
     * Gets movie that have the movie duration in milliseconds specified in movieDurationInMilliSeconds
     *
     * @return movie that have the movie duration in milliseconds specified in movieDurationInMilliSeconds
     **/
    public LongformFilter withMovieDurationInMilliSeconds(Long movieDurationInMilliSeconds) {
        movieResults.removeIf(movieResult -> movieResult.hasMovieDurationInMilliSeconds(movieDurationInMilliSeconds));
        return this;
    }

    /**
     * Gets movie that have the movie duration in time code specified in movieDurationInTimeCode
     *
     * @return movie that have the movie duration in time code specified in movieDurationInTimeCode
     **/
    public LongformFilter withMovieDurationInTimeCode(String movieDurationInTimeCode) {
        movieResults.removeIf(movieResult -> movieResult.hasMovieDurationInTimeCode(movieDurationInTimeCode));
        return this;
    }

    /**
     * Gets movie that have the movie mgid specified in movieMGID
     *
     * @return movie that have the movie mgid specified in movieMGID
     **/
    public LongformFilter moviesWithMGID(String movieMGID) {
        movieResults.removeIf(movieResult -> movieResult.hasMovieMGID(movieMGID));
        return this;
    }

    public LongformResults movieFilter() {
        return movieResults;
    }

    /**
     * Turns the raw JSON that represents the episode objects into a more manageable
     * LongformResults class
     *
     * @param longformJSON
     *          - the json form of the longform objects with the data and items flags
     * @return LongformResults from the longform json objects
     **/
    private LongformResults convertToLongformResults(JSONObject longformJSON) {
        return new LongformResults(longformJSON, this.property);
    }

    /**
     * Gets the current result of our filtering so far in string form
     *
     * @return current result of our filtering so far in string form
     **/
    public String toString(LongformResults results) {
        return results.toString();
    }
}
