package com.viacom.test.vimn.common.filters;

import java.util.ArrayList;
import java.util.List;

import com.viacom.test.vimn.common.util.TestRun;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

public class ShortformFilter {
	private PropertyResult property;
	private JSONObject clipsJSON;
	private JSONObject trailersJSON;
	private JSONObject musicVideosJSON;
	private ShortformResults clipResults;
	private ShortformResults trailerResults;
	private ShortformResults musicVideoResults;
	private List<JSONObject> clipsJSONs;
	private List<JSONObject> trailerJSONs;
	private List<JSONObject> musicVideosJSONs;

	/**
	 * Constructor
	 * @param propertyResult
	 */
	public ShortformFilter(PropertyResult propertyResult) {
		this.property = propertyResult;
		if (this.property.isSeriesProperty() || this.property.isFightProperty()) {
			clipsJSON = FilterUtils.getJSONFeed(property.getClipsURL());
			clipResults = convertToShortformResults(clipsJSON);
		}
		if (this.property.isMovieProperty()) {
			trailersJSON = FilterUtils.getJSONFeed(property.getTrailerURL());
			trailerResults = convertToShortformResults(trailersJSON);
		}
		if (this.property.isCollectionProperty()) {
			musicVideosJSON = FilterUtils.getJSONFeed(FeedFactory.getEditorialsFeedURL(property.getPropertyId()));
			musicVideoResults = convertToShortformResults(musicVideosJSON);
		}
	}

	/**
	 * Constructor that accepts Pagination argument.
	 * @param propertyResult
	 * @param pagination
	 */
	public ShortformFilter(PropertyResult propertyResult, Pagination pagination) {
		this.property = propertyResult;
		clipsJSONs = new ArrayList<>();
		trailerJSONs = new ArrayList<>();
		musicVideosJSONs = new ArrayList<>();
		switch (pagination) {
			case PAGINATED:
				if (property.isSeriesProperty() || property.isFightProperty()) {
					property.getPaginatedShortformURLs().forEach(url -> clipsJSONs.add(FilterUtils.getJSONFeed(url)));
					clipResults = convertToShortformResults(clipsJSON);
				}
				if (property.isMovieProperty()) {
					property.getPaginatedShortformURLs().forEach(url -> trailerJSONs.add(FilterUtils.getJSONFeed(url)));
					trailerResults = convertToShortformResults(trailersJSON);
				}
				if (property.isCollectionProperty()) {
					property.getPaginatedShortformURLs().forEach(url -> musicVideosJSONs.add(FilterUtils.getJSONFeed(url)));
				}
				break;
			case NON_PAGINATED:
				if (property.isSeriesProperty() || property.isFightProperty()) {
					clipsJSONs.add(FilterUtils.getJSONFeed(property.getPropertyURL()));
					clipResults = convertToShortformResults(clipsJSON);
				}
				if (property.isMovieProperty()) {
					trailerJSONs.add(FilterUtils.getJSONFeed(property.getPropertyURL()));
					trailerResults = convertToShortformResults(trailersJSON);
				}
				if (property.isCollectionProperty()) {
					musicVideosJSONs.add(FilterUtils.getJSONFeed(property.getPropertyURL()));
					musicVideoResults = convertToShortformResults(musicVideosJSON);
				}
				break;
		}
	}

	/**
	 * Gets clips that are public
	 *
	 * @return public clips
	 **/
	public ShortformFilter withPublicClips() {
		clipResults.removeIf(clipResult -> !clipResult.isPublicClip());
		return this;
	}

	/**
	 * Gets clips that are private
	 *
	 * @return private clips
	 **/
	public ShortformFilter withPrivateClips() {
		clipResults.removeIf(clipResult -> !clipResult.isPrivateClip());
		return this;
	}

	/**
	 * Gets only clips that are public
	 *
	 * @return public clips
	 **/
	public ShortformFilter withPublicClipsOnly() {
		clipResults.removeIf(clipResult -> !clipResult.isPublicClip());
		return this;
	}

	/**
	 * Gets only clips that are private
	 *
	 * @return private clips
	 **/
	public ShortformFilter withPrivateClipsOnly() {
		clipResults.removeIf(clipResult -> !clipResult.isPrivateClip());
		return this;
	}

	/**
	 * Gets clips that have related episodes
	 *
	 * @return clips that have related episodes
	 **/
	public ShortformFilter withRelatedEpisodes() {
		clipResults.removeIf(clipResult -> !clipResult.hasRelatedEpisodes());
		return this;
	}

	/**
	 * Gets clips that have no related episodes
	 *
	 * @return clips that have no related episodes
	 **/
	public ShortformFilter withNoRelatedEpisodes() {
		clipResults.removeIf(clipResult -> !clipResult.hasNoRelatedEpisodes());
		return this;
	}

	/**
	 * Gets clips that have the clip title specified in clipTitle
	 *
	 * @return clips that have the clip title specified in clipTitle
	 **/
	public ShortformFilter withClipTitle(String clipTitle) {
		clipResults.removeIf(clipResult -> !clipResult.hasClipTitle(clipTitle));
		return this;
	}

	/**
	 * Gets clips that have the clip description specified in clipDescription
	 *
	 * @return clips that have the clip description specified in clipDescription
	 **/
	public ShortformFilter withClipDescription(String clipDescription) {
		clipResults.removeIf(clipResult -> !clipResult.hasClipDescription(clipDescription));
		return this;
	}

	/**
	 * Gets clips that have the clip description specified in clipDescription
	 *
	 * @return clips that have the clip description specified in clipDescription
	 **/
	public ShortformFilter withClipSeasonNumber(int clipSeasonNumber) {
		clipResults.removeIf(clipResult -> !clipResult.hasClipSeasonNumber(clipSeasonNumber));
		return this;
	}

	/**
	 * Gets clips that have the clip duration in milliseconds specified in clipDurationInMilliSeconds
	 *
	 * @return clips that have the clip duration in milliseconds specified in clipDurationInMilliSeconds
	 **/
	public ShortformFilter withClipDurationInMilliSeconds(Long clipDurationInMilliSeconds) {
		clipResults.removeIf(clipResult -> !clipResult.hasClipDurationInMilliSeconds(clipDurationInMilliSeconds));
		return this;
	}

	/**
	 * Gets clips that have the clip duration in time code specified in clipDurationInTimeCode
	 *
	 * @return clips that have the clip duration in time code specified in clipDurationInTimeCode
	 **/
	public ShortformFilter withClipDurationInTimeCode(String clipDurationInTimeCode) {
		clipResults.removeIf(clipResult -> !clipResult.hasClipDurationInTimeCode(clipDurationInTimeCode));
		return this;
	}

	/**
	 * Gets clips that have the clip mgid specified in clipMGID
	 *
	 * @return clips that have the clip mgid specified in clipMGID
	 **/
	public ShortformFilter withClipMGID(String clipMGID) {
		clipResults.removeIf(clipResult -> !clipResult.hasClipMGID(clipMGID));
		return this;
	}

	public ShortformResults clipsFilter() {
		return clipResults;
	}

	/**
	 * Gets trailers that are public
	 *
	 * @return public trailers
	 **/
	public ShortformFilter withPublicTrailers() {
		trailerResults.removeIf(trailerResult -> !trailerResult.isPublicTrailer());
		return this;
	}

	/**
	 * Gets trailers that are private
	 *
	 * @return private trailers
	 **/
	public ShortformFilter withPrivateTrailers() {
		trailerResults.removeIf(trailerResult -> !trailerResult.isPrivateTrailer());
		return this;
	}

	/**
	 * Gets only trailers that are public
	 *
	 * @return public trailers
	 **/
	public ShortformFilter withPublicTrailersOnly() {
		trailerResults.removeIf(trailerResult -> !trailerResult.isPublicTrailer());
		return this;
	}

	/**
	 * Gets only trailers that are private
	 *
	 * @return private trailers
	 **/
	public ShortformFilter withPrivateTrailersOnly() {
		trailerResults.removeIf(trailerResult -> !trailerResult.isPrivateTrailer());
		return this;
	}

	/**
	 * Gets only trailers
	 *
	 * @return list of trailers
	 **/
	public ShortformFilter withTrailersOnly() {
		trailerResults.removeIf(trailerResult -> !trailerResult.isTrailer());
		return this;
	}

	/**
	 * Gets trailers that have related movies
	 *
	 * @return trailers that have related movies
	 **/
	public ShortformFilter withRelatedMovies() {
		trailerResults.removeIf(trailerResult -> !trailerResult.hasRelatedMovies());
		return this;
	}

	/**
	 * Gets trailers that have no related movies
	 *
	 * @return trailers that have no related movies
	 **/
	public ShortformFilter withNoRelatedMovies() {
		trailerResults.removeIf(trailerResult -> !trailerResult.hasNoRelatedMovies());
		return this;
	}

	/**
	 * Gets trailers that have the trailer title specified in trailerTitle
	 *
	 * @return trailers that have the trailer title specified in trailerTitle
	 **/
	public ShortformFilter withTrailerTitle(String trailerTitle) {
		trailerResults.removeIf(trailerResult -> !trailerResult.hasTrailerTitle(trailerTitle));
		return this;
	}

	/**
	 * Gets trailers that have the trailer description specified in trailerDescription
	 *
	 * @return trailers that have the trailer description specified in trailerDescription
	 **/
	public ShortformFilter withTrailerDescription(String trailerDescription) {
		trailerResults.removeIf(trailerResult -> !trailerResult.hasTrailerDescription(trailerDescription));
		return this;
	}

	/**
	 * Gets trailers that have the trailer duration in milliseconds specified in trailerDurationInMilliSeconds
	 *
	 * @return trailers that have the trailer duration in milliseconds specified in trailerDurationInMilliSeconds
	 **/
	public ShortformFilter withTrailerDurationInMilliSeconds(Long trailerDurationInMilliSeconds) {
		trailerResults.removeIf(trailerResult -> !trailerResult.hasTrailerDurationInMilliSeconds(trailerDurationInMilliSeconds));
		return this;
	}

	/**
	 * Gets trailers that have the trailer duration in time code specified in trailerDurationInTimeCode
	 *
	 * @return trailers that have the trailer duration in time code specified in trailerDurationInTimeCode
	 **/
	public ShortformFilter withTrailerDurationInTimeCode(String trailerDurationInTimeCode) {
		trailerResults.removeIf(trailerResult -> !trailerResult.hasTrailerDurationInTimeCode(trailerDurationInTimeCode));
		return this;
	}

	/**
	 * Gets trailers that have the trailer mgid specified in trailerMGID
	 *
	 * @return trailers that have the trailer mgid specified in trailerMGID
	 **/
	public ShortformFilter withMGID(String trailerMGID) {
		trailerResults.removeIf(trailerResult -> !trailerResult.hasTrailerMGID(trailerMGID));
		return this;
	}

	public ShortformResults trailersFilter() {
		return trailerResults;
	}

	/**
	 * Gets music videos that are public
	 *
	 * @return public music videos
	 **/
	public ShortformFilter withPublicMusicVideos() {
		musicVideoResults.removeIf(musicVideoResult -> !musicVideoResult.isPublicMusicVideo());
		return this;
	}

	/**
	 * Gets music videos that are private
	 *
	 * @return private music videos
	 **/
	public ShortformFilter withPrivateMusicVideos() {
		musicVideoResults.removeIf(musicVideoResult -> !musicVideoResult.isPrivateMusicVideo());
		return this;
	}

	/**
	 * Gets only trailers that are public
	 *
	 * @return public trailers
	 **/
	public ShortformFilter withPublicMusicVideosOnly() {
		musicVideoResults.removeIf(musicVideoResult -> !musicVideoResult.isPublicMusicVideo());
		return this;
	}

	/**
	 * Gets only trailers that are private
	 *
	 * @return private trailers
	 **/
	public ShortformFilter withPrivateMusicVideossOnly() {
		musicVideoResults.removeIf(musicVideoResult -> !musicVideoResult.isPrivateMusicVideo());
		return this;
	}

	/**
	 * Gets music videos that have the musicVideo title specified in musicVideoTitle
	 *
	 * @return music videos that have the musicVideo title specified in musicVideoTitle
	 **/
	public ShortformFilter withMusicVideoTitle(String musicVideoTitle) {
		musicVideoResults.removeIf(musicVideoResult -> !musicVideoResult.hasMusicVideoTitle(musicVideoTitle));
		return this;
	}

	/**
	 * Gets music videos that have the musicVideo description specified in musicVideoDescription
	 *
	 * @return music videos that have the musicVideo description specified in musicVideoDescription
	 **/
	public ShortformFilter withMusicVideoDescription(String musicVideoDescription) {
		musicVideoResults.removeIf(musicVideoResult -> !musicVideoResult.hasMusicVideoDescription(musicVideoDescription));
		return this;
	}

	/**
	 * Gets music videos that have the musicVideo duration in milliseconds specified in musicVideoDurationInMilliSeconds
	 *
	 * @return music videos that have the musicVideo duration in milliseconds specified in musicVideoDurationInMilliSeconds
	 **/
	public ShortformFilter witMusicVideoDurationInMilliSeconds(Long musicVideoDurationInMilliSeconds) {
		musicVideoResults.removeIf(musicVideoResult -> !musicVideoResult.hasMusicVideoDurationInMilliSeconds(musicVideoDurationInMilliSeconds));
		return this;
	}

	/**
	 * Gets music videos that have the musicVideo duration in time code specified in musicVideoDurationInTimeCode
	 *
	 * @return music videos that have the musicVideo duration in time code specified in musicVideoDurationInTimeCode
	 **/
	public ShortformFilter withMusicVideoDurationInTimeCode(String musicVideoDurationInTimeCode) {
		musicVideoResults.removeIf(musicVideoResult -> !musicVideoResult.hasMusicVideoDurationInTimeCode(musicVideoDurationInTimeCode));
		return this;
	}

	/**
	 * Gets music videos that have the musicVideo mgid specified in musicVideoMGID
	 *
	 * @return music videos that have the musicVideo mgid specified in musicVideoMGID
	 **/
	public ShortformFilter withMusicVideoMGID(String musicVideoMGID) {
		musicVideoResults.removeIf(musicVideoResult -> !musicVideoResult.hasMusicVideoMGID(musicVideoMGID));
		return this;
	}

	public ShortformResults musicVideosFilter() {
		return musicVideoResults;
	}

	/**
	 * Gets the resulting query of the clips as json NOTE: NEVER return clipsJSON
	 * since that is never altered even after all the filtering. What's filtered
	 * is actually the array list which holds the clips results
	 *
	 * @return the resulting query of the clips as json
	 **/
	public JSONArray toClipsJSONArray() {
		return clipResults.toClipsJSONArray();
	}

	/**
     * Turns the raw JSON that represents the episode objects into a more manageable
     * LongformResults class
     *
	 * @param shortformJSON
     *          - the json form of the longform objects with the data and items flags
     * @return LongformResults from the longform json objects
     **/
	private ShortformResults convertToShortformResults(JSONObject shortformJSON) {
		return new ShortformResults(shortformJSON, property);
	}

	/**
	 * Gets the current result of our filtering so far in string form
	 *
	 * @return current result of our filtering so far in string form
	 **/
	public String toString() {
		return clipResults.toString();
	}

}
