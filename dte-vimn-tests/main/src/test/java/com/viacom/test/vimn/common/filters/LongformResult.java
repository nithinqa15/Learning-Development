package com.viacom.test.vimn.common.filters;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.util.AppLib;
import org.json.simple.JSONObject;

import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

import java.util.HashMap;

public class LongformResult {

    private JSONObject episodeObject;
    private JSONObject fightObject;
    private JSONObject movieObject;
    private PropertyResult property;
    private Integer episodePosition;
    private Integer fightPosition;
    private Integer moviePosition;

    /**
     * Constructor
     * @param longformObject
     * @param property
     * @param longformPosition
     */
    public LongformResult(JSONObject longformObject, PropertyResult property, Integer longformPosition) {
        this.property = property;
        if (this.property.isMovieProperty()) {
        	this.movieObject = longformObject;
            this.moviePosition = longformPosition;
        }
        if (this.property.isFightProperty()) {
        	this.fightObject = longformObject;
            this.fightPosition = longformPosition;
        }
        if (this.property.isSeriesProperty()) {
            this.episodeObject = longformObject;
            this.episodePosition = longformPosition;
        }
    }

    /**
     * Gets the title of the episode
     *
     * @return the title of the episode
     **/
    public String getEpisodeTitle() {
        if (episodeObject.containsKey("title")) {
            return episodeObject.get("title").toString();
        }
        return null;
    }

    /**
     * Gets the description of the episode
     *
     * @return the description of the episode
     **/
    public String getEpisodeDescription() {
        return episodeObject.get("description").toString();
    }

    /**
     * Gets the duration in milliseconds of the episode
     *
     * @return the duration in milliseconds of the episode
     **/
    public Long getEpisodeDurationInMilliSeconds() {
        JSONObject durationObject = (JSONObject) episodeObject.get("duration");
        if (durationObject != null) {
            return Long.parseLong(durationObject.get("milliseconds").toString());
        }
        return null;
    }

    /**
     * Gets the duration in time code of the episode
     *
     * @return the duration in time code of the episode
     **/
    public String getEpisodeDurationInTimeCode() {
        JSONObject durationObject = (JSONObject) episodeObject.get("duration");
        if (durationObject != null) {
            return (String) durationObject.get("timecode");
        }
        return null;
    }

    /**
     * Gets the subtitle of the episode
     *
     * @return the subtitle of the episode
     **/
    public String getEpisodeSubtitle() {
        JSONObject subtitleObject = (JSONObject) episodeObject.get("episode");
        if (subtitleObject != null) {
            return (String) subtitleObject.get("subTitle");
        }
        return null;
    }

    /**
     * Gets the season number of the episode
     *
     * @return the season number of the episode
     **/
    public int getEpisodeSeasonNumber() {
        if (episodeObject.get("seasonNumber") != null) {
            Long seasonNumber = (Long) episodeObject.get("seasonNumber");
            return AppLib.safeLongToInt(seasonNumber);
        } else {
            Logger.logMessage(episodeObject.get("title") + " has a null season number!");
            return 0;
        }
    }

    /**
     * Checks if an episode is new or not
     *
     * @return true if the episode is new, false otherwise
     **/
    public Boolean isNewEpisode() {
        JSONObject ribbonObject = (JSONObject) episodeObject.get("ribbon");
        Boolean newEpisode = false;
        if (ribbonObject != null) {
            if (ribbonObject.get("newEpisode").toString().equals("true")) {
                newEpisode = true;
            }
        }
        return newEpisode;
    }

    /**
     * Gets the episode number of the episode
     *
     * @return the episode number of the episode as a long
     **/
    public int getEpisodeNumber() {
        if (episodeObject.get("episodeNumber") != null) {
            Long episodeNumber = (Long) episodeObject.get("episodeNumber");
            return AppLib.safeLongToInt(episodeNumber);
        }
        Logger.logMessage(episodeObject.get("title") + " has a null episode number!");
        return 0;
    }

    /**
     * Gets the url of the episode
     *
     * @return the url of the episode
     **/
    public String getEpisodeURL() {
        return FeedFactory.getEpisodesFeedURL(getPropertyId());
    }

    /**
     * Gets the MGID of the episode
     *
     * @return the MGID of the episode
     **/
    public String getEpisodeMGID() {
        if (episodeObject.containsKey("mgid")) {
            return episodeObject.get("mgid").toString();
        }
        return null;
    }

    /**
     * Gets the ID of the episode
     *
     * @return the ID of the episode
     **/
    public String getEpisodeId() {
        if (getEpisodeMGID() != null) {
            return getEpisodeMGID().split(":")[4];
        }
        return null;
    }

    /**
     * Gets the property of the episode
     *
     * @return the property of the episode
     **/
    public PropertyResult getProperty() {
        return property;
    }

    /**
     * Gets the property title of the episode
     *
     * @return property title of the episode
     **/
    public String getPropertyTitle() {
        return getProperty().getPropertyTitle();
    }

    /**
     * Gets the property id of the episode
     *
     * @return property id of the episode
     **/
    public String getPropertyId() {
        return getProperty().getPropertyId();
    }

    /**
     * Gets the property mgid of the episode
     *
     * @return property mgid of the episode
     **/
    public String getPropertyMGID() {
        return getProperty().getPropertyMGID();
    }

    /**
     * Used to get json objects our current getter functions don't cover
     *
     * @param jsonFieldNames
     *          - list of json field names to get to what we want
     * @return json objects our current getter functions don't cover
     **/
    public Object get(String... jsonFieldNames) {
    	JSONObject target = null;
    	if (this.property.isSeriesProperty()) {
    		target = episodeObject;
    	} else if (this.property.isMovieProperty()) {
    		target = movieObject;
    	} else if (this.property.isFightProperty()) {
    		target = fightObject;
    	}
        for (int i = 0; i < jsonFieldNames.length - 1; i++) {
            String jsonFieldName = jsonFieldNames[i];
            target = (JSONObject) target.get(jsonFieldName);
        }
        Integer lastIndex = jsonFieldNames.length - 1;
        return target.get(jsonFieldNames[lastIndex]);
    }

    /**
     * Gets the episode position on the list 1 for 1st 2 for 2nd, etc
     *
     * @return the episode position on the list 1 for 1st 2 for 2nd, etc
     **/
    public Integer getEpisodePosition() {
        return episodePosition;
    }

    /**
     * Determines whether the episode is public
     *
     * @return true if the episode is public, false otherwise
     **/
    public Boolean isPublicEpisode() {
        return episodeObject.get("authRequired").toString().equals("false");
    }

    /**
     * Determines whether the episode is private
     *
     * @return true if the episode is private, false otherwise
     **/
    public Boolean isPrivateEpisode() {
        return !isPublicEpisode();
    }

    /**
     * Determines if the episodeObject is related to an episode
     *
     * @return true if the episodeObject is related to an episode, false otherwise
     **/
    public Boolean hasRelatedClips() {
        JSONObject clipsObject = (JSONObject) episodeObject.get("relatedEntity");
        if (clipsObject != null) {
            return episodeObject.keySet().size() > 0;
        }
        return null;
    }

    /**
     * Determines if the episode is related to an shortform
     *
     * @return true if the episode is NOT related to an shortfom
     **/
    public Boolean hasNoRelatedClips() {
        return !hasRelatedClips();
    }

    /**
     * Determines whether the episode has the title specified by episodeTitle
     *
     * @return true if the episode has the title specified by episodeTitle, false
     *         otherwise
     **/
    public Boolean hasEpisodeTitle(String episodeTitle) {
        if (getEpisodeTitle() != null) {
            return getEpisodeTitle().equals(episodeTitle);
        }
        return null;
    }

    /**
     * Determines whether the episode has the description specified by
     * episodeDescription
     *
     * @return true if the episode has the description specified by episodeDescription,
     *         false otherwise
     **/
    public Boolean hasEpisodeDescription(String episodeDescription) {
        if (getEpisodeDescription() != null) {
            return getEpisodeDescription().equals(episodeDescription);
        }
        return null;
    }

    /**
     * Determines whether the episode has the season number specified by
     * episodeSeasonNumber
     *
     * @return true if the the episode has the season number specified by
     *         episodeSeasonNumber, false otherwise
     **/
    public Boolean hasEpisodeSeasonNumber(int episodeSeasonNumber) {
        return new Integer(getEpisodeSeasonNumber()).equals(episodeSeasonNumber);
    }

    /**
     * Determines whether the episode has the duration in milliseconds specified by
     * episodeDurationInMilliSeconds
     *
     * @return true if the episode has the duration in milliseconds specified by
     *         episodeDurationInMilliSeconds, false otherwise
     **/
    public Boolean hasEpisodeDurationInMilliSeconds(Long episodeDurationInMilliSeconds) {
        if (getEpisodeDurationInMilliSeconds() != null) {
            return getEpisodeDurationInMilliSeconds().equals(episodeDurationInMilliSeconds);
        }
        return null;
    }

    /**
     * Determines whether the episode has the duration in timecode specified by
     * episodeDurationInTimeCode
     *
     * @return true if the episode has the duration in timecode specified by
     *         episodeDurationInTimeCode, false otherwise
     **/
    public Boolean hasEpisodeDurationInTimeCode(String episodeDurationInTimeCode) {
        if (getEpisodeDurationInTimeCode() != null) {
            return getEpisodeDurationInTimeCode().equals(episodeDurationInTimeCode);
        }
        return null;
    }

    /**
     * Determines whether the episode has the mgid specified by episodeMGID
     *
     * @return the mgid of the episode
     **/
    public Boolean hasEpisodeMGID(String episodeMGID) {
        if (getEpisodeMGID() != null) {
            return getEpisodeMGID().equals(episodeMGID);
        }
        return null;
    }

    /**
     * Gets the title of the fight
     *
     * @return the title of the fight
     **/
    public String getFightTitle() {
        if (fightObject.containsKey("title")) {
            return fightObject.get("title").toString();
        }
        return null;
    }

    /**
     * Gets the description of the fight
     *
     * @return the description of the fight
     **/
    public String getFightDescription() {
        return fightObject.get("description").toString();
    }

    /**
     * Gets the duration in milliseconds of the fight
     *
     * @return the duration in milliseconds of the fight
     **/
    public Long getFightDurationInMilliSeconds() {
        JSONObject durationObject = (JSONObject) fightObject.get("duration");
        if (durationObject != null) {
            return Long.parseLong(durationObject.get("milliseconds").toString());
        }
        return null;
    }

    /**
     * Gets the duration in time code of the fight
     *
     * @return the duration in time code of the fight
     **/
    public String getFightDurationInTimeCode() {
        JSONObject durationObject = (JSONObject) fightObject.get("duration");
        if (durationObject != null) {
            return (String) durationObject.get("timecode");
        }
        return null;
    }

    /**
     * Gets the subtitle of the fight
     *
     * @return the subtitle of the fight
     **/
    public String getFightSubtitle() {
        JSONObject subtitileObject = (JSONObject) fightObject.get("fight");
        if (subtitileObject != null) {
            return (String) subtitileObject.get("subTitle");
        }
        return null;
    }


    /**
     * Gets the url of the fight
     *
     * @return the url of the fight
     **/
    public String getFightURL() {
        return FeedFactory.getFightsFeedURL(getPropertyId());
    }

    /**
     * Gets the MGID of the fight
     *
     * @return the MGID of the fight
     **/
    public String getFightMGID() {
        if (fightObject.containsKey("mgid")) {
            return fightObject.get("mgid").toString();
        }
        return null;
    }

    /**
     * Gets the ID of the fight
     *
     * @return the ID of the fight
     **/
    public String getFightId() {
        if (getFightMGID() != null) {
            return getFightMGID().split(":")[4];
        }
        return null;
    }

    /**
     * Gets the fight position on the list 1 for 1st 2 for 2nd, etc
     *
     * @return the fight position on the list 1 for 1st 2 for 2nd, etc
     **/
    public Integer getFightPosition() {
        return fightPosition;
    }

    /**
     * TODO: Conditionals for filtering
     **/

    /**
     * Determines whether the fight is public
     *
     * @return true if the fight is public, false otherwise
     **/
    public Boolean isPublicFight() {
        return fightObject.get("authRequired").toString().equals("false");
    }

    /**
     * Determines whether the fight is private
     *
     * @return true if the fight is private, false otherwise
     **/
    public Boolean isPrivateFight() {
        return !isPublicFight();
    }


    /**
     * Determines whether the fight has the title specified by fightTitle
     *
     * @return true if the fight has the title specified by fightTitle, false
     *         otherwise
     **/
    public Boolean hasFightTitle(String fightTitle) {
        if (getFightTitle() != null) {
            return getFightTitle().equals(fightTitle);
        }
        return null;
    }

    /**
     * Determines whether the fight has the description specified by
     * fightDescription
     *
     * @return true if the fight has the description specified by fightDescription,
     *         false otherwise
     **/
    public Boolean hasFightDescription(String fightDescription) {
        if (getFightDescription() != null) {
            return getFightDescription().equals(fightDescription);
        }
        return null;
    }

    /**
     * Determines whether the fight has the duration in milliseconds specified by
     * fightDurationInMilliSeconds
     *
     * @return true if the fight has the duration in milliseconds specified by
     *         fightDurationInMilliSeconds, false otherwise
     **/
    public Boolean hasFightDurationInMilliSeconds(Long fightDurationInMilliSeconds) {
        if (getFightDurationInMilliSeconds() != null) {
            return getFightDurationInMilliSeconds().equals(fightDurationInMilliSeconds);
        }
        return null;
    }

    /**
     * Determines whether the fight has the duration in timecode specified by
     * fightDurationInTimeCode
     *
     * @return true if the fight has the duration in timecode specified by
     *         fightDurationInTimeCode, false otherwise
     **/
    public Boolean hasFightDurationInTimeCode(String fightDurationInTimeCode) {
        if (getFightDurationInTimeCode() != null) {
            return getFightDurationInTimeCode().equals(fightDurationInTimeCode);
        }
        return null;
    }

    /**
     * Determines whether the fight has the mgid specified by fightMGID
     *
     * @return the mgid of the fight
     **/
    public Boolean hasFightMGID(String fightMGID) {
        if (getFightMGID() != null) {
            return getFightMGID().equals(fightMGID);
        }
        return null;
    }

    /**
     * Determines if the movieObject is related to an trailer
     *
     * @return true if the movieObject is related to an trailer, false otherwise
     **/
    public Boolean hasRelatedTrailers() {
        JSONObject trailerObject = (JSONObject) movieObject.get("relatedEntity");
        if (trailerObject != null) {
            return episodeObject.keySet().size() > 0;
        }
        return null;
    }

    /**
     * Determines if the movie is related to an trailer
     *
     * @return true if the movie is NOT related to an trailer
     **/
    public Boolean hasNoRelatedTrailers() {
        return !hasRelatedTrailers();
    }

    /**
     * Gets the title of the movie
     *
     * @return the title of the movie
     **/
    public String getMovieTitle() {
        if (movieObject.containsKey("title")) {
            return movieObject.get("title").toString();
        }
        return null;
    }

    /**
     * Gets the description of the movie
     *
     * @return the description of the movie
     **/
    public String getMovieDescription() {
        return movieObject.get("description").toString();
    }

    /**
     * Gets the duration in milliseconds of the movie
     *
     * @return the duration in milliseconds of the movie
     **/
    public Long getMovieDurationInMilliSeconds() {
        JSONObject durationObject = (JSONObject) movieObject.get("duration");
        if (durationObject != null) {
            return Long.parseLong(durationObject.get("milliseconds").toString());
        }
        return null;
    }

    /**
     * Gets the duration in time code of the movie
     *
     * @return the duration in time code of the movie
     **/
    public String getMovieDurationInTimeCode() {
        JSONObject durationObject = (JSONObject) movieObject.get("duration");
        if (durationObject != null) {
            return (String) durationObject.get("timecode");
        }
        return null;
    }

    /**
     * Gets the subtitle of the movie
     *
     * @return the subtitle of the movie
     **/
    public String getMovieSubtitle() {
        JSONObject subtitleObject = (JSONObject) movieObject.get("movie");
        if (subtitleObject != null) {
            return (String) subtitleObject.get("subTitle");
        }
        return null;
    }

    /**
     * Gets the url of the movie
     *
     * @return the url of the movie
     **/
    public String getMovieURL() {
        return FeedFactory.getMoviesFeedURL(getPropertyId());
    }

    /**
     * Gets the MGID of the movie
     *
     * @return the MGID of the movie
     **/
    public String getMovieMGID() {
        if (movieObject.containsKey("mgid")) {
            return movieObject.get("mgid").toString();
        }
        return null;
    }

    /**
     * Gets the ID of the movie
     *
     * @return the ID of the movie
     **/
    public String getMovieId() {
        if (getMovieMGID() != null) {
            return getMovieMGID().split(":")[4];
        }
        return null;
    }

    /**
     * Gets the movie position on the list 1 for 1st 2 for 2nd, etc
     *
     * @return the movie position on the list 1 for 1st 2 for 2nd, etc
     **/
    public Integer getMoviePosition() {
        return moviePosition;
    }

    /**
     * Determines whether the movie is public
     *
     * @return true if the movie is public, false otherwise
     **/
    public Boolean isPublicMovie() {
        return movieObject.get("authRequired").toString().equals("false");
    }

    /**
     * Determines whether the movie is private
     *
     * @return true if the movie is private, false otherwise
     **/
    public Boolean isPrivateMovie() {
        return !isPublicMovie();
    }

    /**
     * Determines whether the movie has the title specified by movieTitle
     *
     * @return true if the movie has the title specified by movieTitle, false
     *         otherwise
     **/
    public Boolean hasMovieTitle(String movieTitle) {
        if (getMovieTitle() != null) {
            return getMovieTitle().equals(movieTitle);
        }
        return false;
    }

    /**
     * Determines whether the movie has the description specified by
     * movieDescription
     *
     * @return true if the movie has the description specified by movieDescription,
     *         false otherwise
     **/
    public Boolean hasMovieDescription(String movieDescription) {
        if (getMovieDescription() != null) {
            return getMovieDescription().equals(movieDescription);
        }
        return false;
    }

    /**
     * Determines whether the movie has the duration in milliseconds specified by
     * movieDurationInMilliSeconds
     *
     * @return true if the movie has the duration in milliseconds specified by
     *         movieDurationInMilliSeconds, false otherwise
     **/
    public Boolean hasMovieDurationInMilliSeconds(Long movieDurationInMilliSeconds) {
        if (getMovieDurationInMilliSeconds() != null) {
            return getMovieDurationInMilliSeconds().equals(movieDurationInMilliSeconds);
        }
        return false;
    }

    /**
     * Determines whether the movie has the duration in timecode specified by
     * movieDurationInTimeCode
     *
     * @return true if the movie has the duration in timecode specified by
     *         movieDurationInTimeCode, false otherwise
     **/
    public Boolean hasMovieDurationInTimeCode(String movieDurationInTimeCode) {
        if (getMovieDurationInTimeCode() != null) {
            return getMovieDurationInTimeCode().equals(movieDurationInTimeCode);
        }
        return false;
    }

    /**
     * Determines whether the movie has the mgid specified by movieMGID
     *
     * @return the mgid of the movie
     **/
    public Boolean hasMovieMGID(String movieMGID) {
        if (getMovieMGID() != null) {
            return getMovieMGID().equals(movieMGID);
        }
        return false;
    }

    /**
     * Gets the longform object as raw json
     *
     * @return the longform object as raw json
     **/
    public JSONObject toJSON(JSONObject longformObject) {
        return longformObject;
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
