package com.viacom.test.vimn.common.filters;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.util.AppLib;
import org.json.simple.JSONObject;

import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;


public class ShortformResult {

	private JSONObject clipObject;
	private JSONObject musicVideoObject;
	private JSONObject trailerObject;
	private PropertyResult property;
	private Integer clipPosition;
	private Integer musicVideoPosition;
	private Integer trailerPosition;
	private String videoDescriptor;

	/**
	 * Constructor
	 * @param shortformObject
	 * @param property
	 * @param shortformPosition
	 */
	public ShortformResult(JSONObject shortformObject, PropertyResult property, Integer shortformPosition) {
		this.property = property;
		if (this.property.isSeriesProperty()) {
			this.clipObject = shortformObject;
			this.clipPosition = shortformPosition;
		}
		if (this.property.isMovieProperty()) {
			this.trailerObject = shortformObject;
			this.videoDescriptor = setVideoDescriptor();
			this.trailerPosition = shortformPosition;
		}
		if (this.property.isCollectionProperty()) {
			this.musicVideoObject = shortformObject;
			this.musicVideoPosition = shortformPosition;
		}
	}

	/**
	 * Gets the title of the clip
	 *
	 * @return the title of the clip
	 **/
	public String getClipTitle() {
		String clipTitle = null;
		if (clipObject.containsKey("title")) {
			clipTitle = (String) clipObject.get("title");
		}
		return clipTitle;
	}

	/**
	 * Gets the description of the clip
	 *
	 * @return the description of the clip
	 **/
	public String getClipDescription() {
		return clipObject.get("description").toString();
	}

	/**
	 * Gets the duration in milliseconds of the clip
	 *
	 * @return the duration in milliseconds of the clip
	 **/
	public Long getClipDurationInMilliSeconds() {
		JSONObject durationObject = (JSONObject) clipObject.get("duration");
		if (durationObject != null) {
			return Long.parseLong(durationObject.get("milliseconds").toString());
		}
		return null;
	}

	/**
	 * Gets the duration in time code of the clip
	 *
	 * @return the duration in time code of the clip
	 **/
	public String getClipDurationInTimeCode() {
		JSONObject durationObject = (JSONObject) clipObject.get("duration");
		if (durationObject != null) {
			return (String) durationObject.get("timecode");
		}
		return null;
	}

	/**
	 * Gets the subtitle of the clip
	 *
	 * @return the subtitle of the clip
	 **/
	public String getClipSubtitle() {
		JSONObject clipSubtitleObject = (JSONObject) clipObject.get("clip");
		if (clipSubtitleObject != null) {
			return (String) clipSubtitleObject.get("subTitle");
		}
		return null;
	}

	/**
	 * Gets the season number of the episode
	 *
	 * @return the season number of the episode
	 **/
	public int getClipSeasonNumber() {
		if (clipObject.get("seasonNumber") != null) {
			Long clipSeasonNumber = (Long) clipObject.get("seasonNumber");
			return AppLib.safeLongToInt(clipSeasonNumber);
		} else {
			Logger.logMessage(clipObject.get("title") + " has a null season number!");
			return 0;
		}
	}

	/**
	 * Gets the clip number of the clip
	 *
	 * @return the clip number of the clip
	 **/
	public String getClipEpisodeNumber() {
		JSONObject clipEpisodeNumberObject = (JSONObject) clipObject.get("clip");
		if (clipEpisodeNumberObject != null) {
			return (String) clipEpisodeNumberObject.get("clipNumber");
		}
		return null;
	}

	/**
	 * Gets the url of the clip
	 *
	 * @return the url of the clip
	 **/
	public String getClipURL() {
		return FeedFactory.getClipsFeedURL(getPropertyId());
	}

	/**
	 * Gets the MGID of the clip
	 *
	 * @return the MGID of the clip
	 **/
	public String getClipMGID() {
		if (clipObject.containsKey("mgid")) {
			return clipObject.get("mgid").toString();
		}
		return null;
	}

	/**
	 * Gets the ID of the clip
	 *
	 * @return the ID of the clip
	 **/
	public String getClipId() {
		if (getClipMGID() != null) {
			return getClipMGID().split(":")[4];
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
		JSONObject target = clipObject;
		for (int i = 0; i < jsonFieldNames.length - 1; i++) {
			String jsonFieldName = jsonFieldNames[i];
			target = (JSONObject) target.get(jsonFieldName);
		}
		Integer lastIndex = jsonFieldNames.length - 1;
		return target.get(jsonFieldNames[lastIndex]);
	}

	/**
	 * Gets the clip position on the list 1 for 1st 2 for 2nd, etc
	 *
	 * @return the clip position on the list 1 for 1st 2 for 2nd, etc
	 **/
	public Integer getClipPosition() {
		return clipPosition;
	}

	/**
	 * TODO: Conditionals for filtering
	 **/

	/**
	 * Determines whether the clip is public
	 *
	 * @return true if the clip is public, false otherwise
	 **/
	public Boolean isPublicClip() {
		return clipObject.get("authRequired").toString().equals("false");
	}

	/**
	 * Determines whether the clip is private
	 *
	 * @return true if the clip is private, false otherwise
	 **/
	public Boolean isPrivateClip() {
		return !isPublicClip();
	}

	/**
	 * Determines if the clip is related to an clip
	 *
	 * @return true if the clip is related to an clip, false otherwise
	 **/
	public Boolean hasRelatedEpisodes() {
		JSONObject relatedEntityObject = (JSONObject) clipObject.get("relatedEntity");
		if (relatedEntityObject != null) {
			return clipObject.keySet().size() > 0;
		}
		return null;
	}

	/**
	 * Determines if the clip is related to an clip
	 *
	 * @return true if the clip is NOT related to an clip
	 **/
	public Boolean hasNoRelatedEpisodes() {
		return !hasRelatedEpisodes();
	}

	/**
	 * Determines whether the clip has the title specified by clipTitle
	 *
	 * @return true if the clip has the title specified by clipTitle, false
	 *         otherwise
	 **/
	public Boolean hasClipTitle(String clipTitle) {
		if (getClipTitle() != null) {
			return getClipTitle().equals(clipTitle);
		}
		return null;
	}

	/**
	 * Determines whether the clip has the description specified by
	 * clipDescription
	 *
	 * @return true if the clip has the description specified by clipDescription,
	 *         false otherwise
	 **/
	public Boolean hasClipDescription(String clipDescription) {
		if (getClipDescription() != null) {
			return getClipDescription().equals(clipDescription);
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
	public Boolean hasClipSeasonNumber(int episodeSeasonNumber) {
		return new Integer(getClipSeasonNumber()).equals(episodeSeasonNumber);
	}

	/**
	 * Determines whether the clip has the duration in milliseconds specified by
	 * clipDurationInMilliSeconds
	 *
	 * @return true if the clip has the duration in milliseconds specified by
	 *         clipDurationInMilliSeconds, false otherwise
	 **/
	public Boolean hasClipDurationInMilliSeconds(Long clipDurationInMilliSeconds) {
		if (getClipDurationInMilliSeconds() != null) {
			return getClipDurationInMilliSeconds().equals(clipDurationInMilliSeconds);
		}
		return null;
	}

	/**
	 * Determines whether the clip has the duration in timecode specified by
	 * clipDurationInTimeCode
	 *
	 * @return true if the clip has the duration in timecode specified by
	 *         clipDurationInTimeCode, false otherwise
	 **/
	public Boolean hasClipDurationInTimeCode(String clipDurationInTimeCode) {
		if (getClipDurationInTimeCode() != null) {
			return getClipDurationInTimeCode().equals(clipDurationInTimeCode);
		}
		return null;
	}

	/**
	 * Determines whether the clip has the mgid specified by clipMGID
	 *
	 * @return the mgid of the clip
	 **/
	public Boolean hasClipMGID(String clipMGID) {
		if (getClipMGID() != null) {
			return getClipMGID().equals(clipMGID);
		}
		return null;
	}

	/**
	 * Gets the title of the musicVideo
	 *
	 * @return the title of the musicVideo
	 **/
	public String getMusicVideoTitle() {
		if (musicVideoObject.containsKey("title")) {
			return musicVideoObject.get("title").toString();
		}
		return null;
	}

	/**
	 * Gets the description of the musicVideo
	 *
	 * @return the description of the musicVideo
	 **/
	public String getMusicVideoDescription() {
		return musicVideoObject.get("description").toString();
	}

	/**
	 * Gets the duration in milliseconds of the musicVideo
	 *
	 * @return the duration in milliseconds of the musicVideo
	 **/
	public Long getMusicVideoDurationInMilliSeconds() {
		JSONObject durationObject = (JSONObject) musicVideoObject.get("duration");
		if (durationObject != null) {
			return Long.parseLong(durationObject.get("milliseconds").toString());
		}
		return null;
	}

	/**
	 * Gets the duration in time code of the musicVideo
	 *
	 * @return the duration in time code of the musicVideo
	 **/
	public String getMusicVideoDurationInTimeCode() {
		JSONObject durationObject = (JSONObject) musicVideoObject.get("duration");
		if (durationObject != null) {
			return (String) durationObject.get("timecode");
		}
		return null;
	}

	/**
	 * Gets the subtitle of the musicVideo
	 *
	 * @return the subtitle of the musicVideo
	 **/
	public String getMusicVideoSubtitle() {
		JSONObject clipObject = (JSONObject) musicVideoObject.get("clip");
		if (clipObject != null) {
			return (String) clipObject.get("subTitle");
		}
		return null;
	}

	/**
	 * Gets the url of the musicVideo
	 *
	 * @return the url of the musicVideo
	 **/
	public String getMusicVideoURL() {
		return FeedFactory.getEditorialsFeedURL(getPropertyId());
	}

	/**
	 * Gets the MGID of the musicVideo
	 *
	 * @return the MGID of the musicVideo
	 **/
	public String getMusicVideoMGID() {
		if (musicVideoObject.containsKey("mgid")) {
			return musicVideoObject.get("mgid").toString();
		}
		return null;
	}

	/**
	 * Gets the ID of the musicVideo
	 *
	 * @return the ID of the musicVideo
	 **/
	public String getMusicVideoId() {
		if (getMusicVideoMGID() != null) {
			return getMusicVideoMGID().split(":")[4];
		}
		return null;
	}

	/**
	 * Gets the property of the musicVideo
	 *
	 * @return the property of the musicVideo
	 **/
	public PropertyResult getProperty() {
		return property;
	}

	/**
	 * Gets the property title of the musicVideo
	 *
	 * @return property title of the musicVideo
	 **/
	public String getPropertyTitle() {
		return getProperty().getPropertyTitle();
	}

	/**
	 * Gets the property id of the musicVideo
	 *
	 * @return property id of the musicVideo
	 **/
	public String getPropertyId() {
		return getProperty().getPropertyId();
	}

	/**
	 * Gets the property mgid of the musicVideo
	 *
	 * @return property mgid of the musicVideo
	 **/
	public String getPropertyMGID() {
		return getProperty().getPropertyMGID();
	}

	/**
	 * Gets the musicVideo position on the list 1 for 1st 2 for 2nd, etc
	 *
	 * @return the musicVideo position on the list 1 for 1st 2 for 2nd, etc
	 **/
	public Integer getMusicVideoPosition() {
		return musicVideoPosition;
	}

	/**
	 * Determines whether the musicVideo is public
	 *
	 * @return true if the musicVideo is public, false otherwise
	 **/
	public Boolean isPublicMusicVideo() {
		return musicVideoObject.get("authRequired").toString().equals("false");
	}

	/**
	 * Determines whether the musicVideo is private
	 *
	 * @return true if the musicVideo is private, false otherwise
	 **/
	public Boolean isPrivateMusicVideo() {
		return !isPublicMusicVideo();
	}

	/**
	 * Determines whether the musicVideo has the title specified by musicVideoTitle
	 *
	 * @return true if the musicVideo has the title specified by musicVideoTitle, false
	 *         otherwise
	 **/
	public Boolean hasMusicVideoTitle(String musicVideoTitle) {
		if (getMusicVideoTitle() != null) {
			return getMusicVideoTitle().equals(musicVideoTitle);
		}
		return null;
	}

	/**
	 * Determines whether the musicVideo has the description specified by
	 * musicVideoDescription
	 *
	 * @return true if the musicVideo has the description specified by musicVideoDescription,
	 *         false otherwise
	 **/
	public Boolean hasMusicVideoDescription(String musicVideoDescription) {
		if (getMusicVideoDescription() != null) {
			return getMusicVideoDescription().equals(musicVideoDescription);
		}
		return null;
	}

	/**
	 * Determines whether the musicVideo has the duration in milliseconds specified by
	 * musicVideoDurationInMilliSeconds
	 *
	 * @return true if the musicVideo has the duration in milliseconds specified by
	 *         musicVideoDurationInMilliSeconds, false otherwise
	 **/
	public Boolean hasMusicVideoDurationInMilliSeconds(Long musicVideoDurationInMilliSeconds) {
		if (getMusicVideoDurationInMilliSeconds() != null) {
			return getMusicVideoDurationInMilliSeconds().equals(musicVideoDurationInMilliSeconds);
		}
		return null;
	}

	/**
	 * Determines whether the musicVideo has the duration in timecode specified by
	 * musicVideoDurationInTimeCode
	 *
	 * @return true if the musicVideo has the duration in timecode specified by
	 *         musicVideoDurationInTimeCode, false otherwise
	 **/
	public Boolean hasMusicVideoDurationInTimeCode(String musicVideoDurationInTimeCode) {
		if (getMusicVideoDurationInTimeCode() != null) {
			return getMusicVideoDurationInTimeCode().equals(musicVideoDurationInTimeCode);
		}
		return null;
	}

	/**
	 * Determines whether the musicVideo has the mgid specified by musicVideoMGID
	 *
	 * @return the mgid of the musicVideo
	 **/
	public Boolean hasMusicVideoMGID(String musicVideoMGID) {
		if (getMusicVideoMGID() != null) {
			return getMusicVideoMGID().equals(musicVideoMGID);
		}
		return null;
	}

	/**
	 * Gets the title of the trailer
	 *
	 * @return the title of the trailer
	 **/
	public String getTrailerTitle() {
		if (trailerObject.containsKey("title")) {
			return trailerObject.get("title").toString();
		}
		return null;
	}

	/**
	 * Gets the description of the trailer
	 *
	 * @return the description of the trailer
	 **/
	public String getTrailerDescription() {
		return trailerObject.get("description").toString();
	}

	/**
	 * Gets the duration in milliseconds of the trailer
	 *
	 * @return the duration in milliseconds of the trailer
	 **/
	public Long getTrailerDurationInMilliSeconds() {
		JSONObject durationObject = (JSONObject) trailerObject.get("duration");
		if (durationObject != null) {
			return Long.parseLong(durationObject.get("milliseconds").toString());
		}
		return null;
	}

	/**
	 * Gets the duration in time code of the trailer
	 *
	 * @return the duration in time code of the trailer
	 **/
	public String getTrailerDurationInTimeCode() {
		JSONObject durationObject = (JSONObject) trailerObject.get("duration");
		if (durationObject != null) {
			return (String) durationObject.get("timecode");
		}
		return null;
	}

	/**
	 * Gets the subtitle of the trailer
	 *
	 * @return the subtitle of the trailer
	 **/
	public String getTrailerSubtitle() {
		JSONObject clipObject = (JSONObject) trailerObject.get("clip");
		if (clipObject != null) {
			return (String) clipObject.get("subTitle");
		}
		return null;
	}

	/**
	 * Gets the url of the trailer
	 *
	 * @return the url of the trailer
	 **/
	public String getTrailerURL() {
		return property.getTrailerURL();
	}

	/**
	 * Gets the MGID of the trailer
	 *
	 * @return the MGID of the trailer
	 **/
	public String getTrailerMGID() {
		if (trailerObject.containsKey("mgid")) {
			return trailerObject.get("mgid").toString();
		}
		return null;
	}

	/**
	 * Gets the ID of the trailer
	 *
	 * @return the ID of the trailer
	 **/
	public String getTrailerId() {
		if (getTrailerMGID() != null) {
			return getTrailerMGID().split(":")[4];
		}
		return null;
	}

	/**
	 * Gets the trailer position on the list 1 for 1st 2 for 2nd, etc
	 *
	 * @return the trailer position on the list 1 for 1st 2 for 2nd, etc
	 **/
	public Integer getTrailerPosition() {
		return trailerPosition;
	}

	/**
	 * Determines whether the trailer is public
	 *
	 * @return true if the trailer is public, false otherwise
	 **/
	public Boolean isPublicTrailer() {
		return trailerObject.get("authRequired").toString().equals("false");
	}

	/**
	 * Determines whether the trailer is private
	 *
	 * @return true if the trailer is private, false otherwise
	 **/
	public Boolean isPrivateTrailer() {
		return !isPublicTrailer();
	}

	/**
	 * Determines if the trailer is related to an clip
	 *
	 * @return true if the trailer is related to an clip, false otherwise
	 **/
	public Boolean hasRelatedMovies() {
		JSONObject clipObject = (JSONObject) trailerObject.get("relatedEntity");
		if (clipObject != null) {
			return clipObject.keySet().size() > 0;
		}
		return null;
	}

	/**
	 * Determines if the trailer is related to an clip
	 *
	 * @return true if the trailer is NOT related to an clip
	 **/
	public Boolean hasNoRelatedMovies() {
		return !hasRelatedMovies();
	}

	/**
	 * Determines whether the trailer has the title specified by trailerTitle
	 *
	 * @return true if the trailer has the title specified by trailerTitle, false
	 *         otherwise
	 **/
	public Boolean hasTrailerTitle(String trailerTitle) {
		if (getTrailerTitle() != null) {
			return getTrailerTitle().equals(trailerTitle);
		}
		return null;
	}

	/**
	 * Determines whether the trailer has the description specified by
	 * trailerDescription
	 *
	 * @return true if the trailer has the description specified by trailerDescription,
	 *         false otherwise
	 **/
	public Boolean hasTrailerDescription(String trailerDescription) {
		if (getTrailerDescription() != null) {
			return getTrailerDescription().equals(trailerDescription);
		}
		return null;
	}

	/**
	 * Determines whether the trailer has the duration in milliseconds specified by
	 * trailerDurationInMilliSeconds
	 *
	 * @return true if the trailer has the duration in milliseconds specified by
	 *         trailerDurationInMilliSeconds, false otherwise
	 **/
	public Boolean hasTrailerDurationInMilliSeconds(Long trailerDurationInMilliSeconds) {
		if (getTrailerDurationInMilliSeconds() != null) {
			return getTrailerDurationInMilliSeconds().equals(trailerDurationInMilliSeconds);
		}
		return null;
	}

	/**
	 * Determines whether the trailer has the duration in timecode specified by
	 * trailerDurationInTimeCode
	 *
	 * @return true if the trailer has the duration in timecode specified by
	 *         trailerDurationInTimeCode, false otherwise
	 **/
	public Boolean hasTrailerDurationInTimeCode(String trailerDurationInTimeCode) {
		if (getTrailerDurationInTimeCode() != null) {
			return getTrailerDurationInTimeCode().equals(trailerDurationInTimeCode);
		}
		return null;
	}

	/**
	 * Determines whether the trailer has the mgid specified by trailerMGID
	 *
	 * @return the mgid of the trailer
	 **/
	public Boolean hasTrailerMGID(String trailerMGID) {
		if (getTrailerMGID() != null) {
			return getTrailerMGID().equals(trailerMGID);
		}
		return null;
	}

	/**
	 * Gets the video descriptor from the shortform result object
	 *
	 * @return video descriptor as a string
	 *
	 **/
	private String getVideoDescriptor() {
		return videoDescriptor;
	}

	/**
	 * Sets the video descriptor if the shortform is a trailer
	 *
	 * @return the value of the video descriptor as a string
	 **/
	private String setVideoDescriptor() {
		String descriptor = "";
		if (trailerObject.containsKey("videoDescriptor")) {
			descriptor = trailerObject.get("videoDescriptor").toString();
		}
		return descriptor;
	}

	/**
	 * Checks to see if the shortform is a trailer
	 *
	 * @return true if the shortform is a trailer, false otherwise
	 **/
	public Boolean isTrailer() {
		return videoDescriptor.equals("Trailer");
	}

	/**
	 * Gets the shortform object as raw json
	 *
	 * @return the shortform object as raw json
	 **/
	public JSONObject toJSON(JSONObject shortformObject) {
		return shortformObject;
	}

	/**
	 * Gets the current result of our filtering so far in string form
	 *
	 * @return current result of our filtering so far in string form
	 **/
	public String toString(ShortformResults results) {
		return results.toString();
	}
}
