package com.viacom.test.vimn.common.filters;

import java.util.*;

import com.viacom.test.core.util.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

public class ShortformResults implements Iterable<ShortformResult>, List<ShortformResult> {

	private PropertyResult property;
	private List<ShortformResult> clipResults;
	private List<ShortformResult> musicVideoResults;
	private List<ShortformResult> trailerResults;
	private JSONObject clipJSON;
	private JSONObject musicVideoJSON;
	private JSONObject trailerJSON;
	private JSONObject clipMetadata;
	private JSONObject musicVideoMetadata;
	private JSONObject trailerMetadata;

	/**
	 * Constructor
	 * @param shortformJSON
	 * @param property
	 */
	public ShortformResults(JSONObject shortformJSON, PropertyResult property) {
		this.property = property;
		if (this.property.isSeriesProperty()) {
			this.clipJSON = shortformJSON;
			this.clipMetadata = getShortformMetadata(shortformJSON);
			this.clipResults = convertJSONToListOfShortformResults(this.clipJSON);
		}
		if (this.property.isMovieProperty()) {
			this.trailerJSON = shortformJSON;
			this.trailerMetadata = getShortformMetadata(shortformJSON);
			this.trailerResults = convertJSONToListOfShortformResults(this.trailerJSON);
		}
		if (this.property.isCollectionProperty()) {
			this.musicVideoJSON = shortformJSON;
			this.musicVideoMetadata = getShortformMetadata(shortformJSON);
			this.musicVideoResults = convertJSONToListOfShortformResults(this.musicVideoJSON);
		}
	}

	/**
	 * Converts the shortform json to an array list of shortform results
	 *
	 * @param shortformJSON - the json response from the shortform url
	 * @return a list of the shortform results
	 **/
	private List<ShortformResult> convertJSONToListOfShortformResults(JSONObject shortformJSON) {
		List<ShortformResult> shortformResults = new ArrayList<>();
		try {   // some shortform content contains empty an data object and is throwing null point exceptions
			JSONArray shortformJSONArray = (JSONArray) ((JSONObject) shortformJSON.get("data")).get("items");
			Iterator<JSONObject> iterator = shortformJSONArray.iterator();
			for (int shortformPosition = 0; shortformPosition < shortformJSONArray.size(); shortformPosition++) {
				JSONObject shortformObject = iterator.next();
				shortformResults.add(new ShortformResult(shortformObject, this.property, shortformPosition));
			}
		} catch (NullPointerException e) {
			Logger.logMessage(getProperty().getPropertyTitle() + " is missing shortform data!");
		}
		return shortformResults;
	}

	/**
	 * Overload method that stitches all shortform in each of the Json objects in the list.
	 *
	 * @param shortformJSON
	 * @return a list of shortform results
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private List<ShortformResult> convertJSONToListOfShortformResults(List<JSONObject> shortformJSON) {
		List<ShortformResult> shortformResults = new ArrayList<>();
		Integer shortformPosition = 0;
		try {   // some shortform content contains empty an data object and is throwing null point exceptions
			for (JSONObject resultJSON : shortformJSON) {
				JSONObject dataObj = (JSONObject) resultJSON.get("data");
				JSONArray itemsArr = (JSONArray) dataObj.get("items");
				Iterator<JSONObject> iterator = itemsArr.iterator();
				while (iterator.hasNext()) {
					JSONObject shortformObject = iterator.next();
					shortformResults.add(new ShortformResult(shortformObject, getProperty(), shortformPosition));
					shortformPosition++;
				}
			}
		} catch (NullPointerException e) {
			Logger.logMessage(getProperty().getPropertyTitle() + " is missing shortform data!");
		}
		return shortformResults;
	}

	/**
	 * Gets the metadata from the clips object
	 *
	 * @return metadata from a the clips object
	 **/
	private JSONObject getShortformMetadata(JSONObject clipsJSON) {
		return (JSONObject) clipsJSON.get("metadata");
	}

	/**
	 * Gets the page size from the clips metadata object
	 *
	 * @return page size from the clips metadata object
	 **/
	public Long getClipPageSize() {
		return (Long) ((JSONObject) clipMetadata.get("pagination")).get("pageSize");
	}

	/**
	 * Gets the total number of items from the clips metadata object
	 *
	 * @return total number of items from the clips metadata object
	 **/
	public Long getClipTotalItems() {
		return (Long) ((JSONObject) clipMetadata.get("pagination")).get("totalItems");
	}

	/**
	 * Gets the page size from the trailer metadata object
	 *
	 * @return page size from the trailer metadata object
	 **/
	public Long getTrailerPageSize() {
		return (Long) ((JSONObject) trailerMetadata.get("pagination")).get("pageSize");
	}

	/**
	 * Gets the total number of items from the trailer metadata object
	 *
	 * @return total number of items from the trailer metadata object
	 **/
	public Long getTrailerTotalItems() {
		return (Long) ((JSONObject) trailerMetadata.get("pagination")).get("totalItems");
	}

	/**
	 * Gets the total number of items from the music video metadata object
	 *
	 * @return total number of items from the music video metadata object
	 **/
	public Long getMusicVideoPageSize() {
		return (Long) ((JSONObject) musicVideoMetadata.get("pagination")).get("pageSize");
	}

	/**
	 * Gets the total number of items from the music video metadata object
	 *
	 * @return total number of items from the music video metadata object
	 **/
	public Long getMusicVideoTotalItems() {
		return (Long) ((JSONObject) musicVideoMetadata.get("pagination")).get("totalItems");
	}

	/**
	 * Gets the clip titles as a list
	 *
	 * @return the clip titles as a list
	 **/
	public List<String> getClipTitles() {
		List<String> clipTitles = new ArrayList<String>();
		for (ShortformResult clipResult : clipResults) {
			clipTitles.add(clipResult.getClipTitle());
		}
		return clipTitles;
	}

	/**
	 * Gets the clip descriptions as a list
	 *
	 * @return the clip descriptions as a list
	 **/
	public List<String> getClipDescriptions() {
		List<String> clipDescriptions = new ArrayList<String>();
		for (ShortformResult clipResult : clipResults) {
			clipDescriptions.add(clipResult.getClipDescription());
		}
		return clipDescriptions;
	}

	/**
	 * Gets the clip durations in milliseconds as a list
	 *
	 * @return the clip durations in milliseconds as a list
	 **/
	public List<Long> getClipDurationsInMilliSeconds() {
		List<Long> clipDurationsInTimeCode = new ArrayList<Long>();
		for (ShortformResult clipResult : clipResults) {
			clipDurationsInTimeCode.add(clipResult.getClipDurationInMilliSeconds());
		}
		return clipDurationsInTimeCode;
	}

	/**
	 * Gets the clip durations in time code as a list
	 *
	 * @return the clip durations in time code as a list
	 **/
	public List<String> getClipDurationsInTimeCode() {
		List<String> clipDurationsInTimeCode = new ArrayList<String>();
		for (ShortformResult clipResult : clipResults) {
			clipDurationsInTimeCode.add(clipResult.getClipDurationInTimeCode());
		}
		return clipDurationsInTimeCode;
	}

	/**
	 * Gets the clip subtitles as a list
	 *
	 * @return the clip subtitles as a list
	 **/
	public List<String> getClipSubtitles() {
		List<String> clipSubtitles = new ArrayList<String>();
		for (ShortformResult clipResult : clipResults) {
			clipSubtitles.add(clipResult.getClipSubtitle());
		}
		return clipSubtitles;
	}

	/**
	 * Gets the episode season number as a list
	 *
	 * @return the episode season number as a list
	 **/
	public List<Integer> getClipSeasonNumbers() {
		List<Integer> clipSeasonNumbers = new ArrayList<>();
		for (ShortformResult clipResult : clipResults) {
			clipSeasonNumbers.add(clipResult.getClipSeasonNumber());
		}
		return clipSeasonNumbers;
	}

	/**
	 * Gets the clip MGIDs as a list
	 *
	 * @return the clip MGIDs as a list
	 **/
	public List<String> getClipMGIDs() {
		List<String> clipMGIDs = new ArrayList<String>();
		for (ShortformResult clipResult : clipResults) {
			clipMGIDs.add(clipResult.getClipMGID());
		}
		return clipMGIDs;
	}

	/**
	 * Gets the clip ids as a list
	 *
	 * @return the clip ids as a list
	 **/
	public List<String> getClipIds() {
		List<String> clipIds = new ArrayList<String>();
		for (ShortformResult clipResult : clipResults) {
			clipIds.add(clipResult.getClipId());
		}
		return clipIds;
	}

	/**
	 * Gets the clip url
	 *
	 * @return the clip url
	 **/
	public String getClipsFeedURL() {
		return FeedFactory.getClipsFeedURL(getPropertyId());
	}

	/**
	 * Gets the musicVideo titles as a list
	 *
	 * @return the musicVideo titles as a list
	 **/
	public List<String> getMusicVideoTitles() {
		List<String> musicVideoTitles = new ArrayList<String>();
		for (ShortformResult musicVideoResult : musicVideoResults) {
			musicVideoTitles.add(musicVideoResult.getMusicVideoTitle());
		}
		return musicVideoTitles;
	}

	/**
	 * Gets the musicVideo descriptions as a list
	 *
	 * @return the musicVideo descriptions as a list
	 **/
	public List<String> getMusicVideoDescriptions() {
		List<String> musicVideoDescriptions = new ArrayList<String>();
		for (ShortformResult musicVideoResult : musicVideoResults) {
			musicVideoDescriptions.add(musicVideoResult.getMusicVideoDescription());
		}
		return musicVideoDescriptions;
	}

	/**
	 * Gets the musicVideo durations in milliseconds as a list
	 *
	 * @return the musicVideo durations in milliseconds as a list
	 **/
	public List<Long> getMusicVideoDurationsInMilliSeconds() {
		List<Long> musicVideoDurationsInTimeCode = new ArrayList<Long>();
		for (ShortformResult musicVideoResult : musicVideoResults) {
			musicVideoDurationsInTimeCode.add(musicVideoResult.getMusicVideoDurationInMilliSeconds());
		}
		return musicVideoDurationsInTimeCode;
	}

	/**
	 * Gets the musicVideo durations in time code as a list
	 *
	 * @return the musicVideo durations in time code as a list
	 **/
	public List<String> getMusicVideoDurationsInTimeCode() {
		List<String> musicVideoDurationsInTimeCode = new ArrayList<String>();
		for (ShortformResult musicVideoResult : musicVideoResults) {
			musicVideoDurationsInTimeCode.add(musicVideoResult.getMusicVideoDurationInTimeCode());
		}
		return musicVideoDurationsInTimeCode;
	}

	/**
	 * Gets the musicVideo subtitles as a list
	 *
	 * @return the musicVideo subtitles as a list
	 **/
	public List<String> getMusicVideoSubtitles() {
		List<String> musicVideoSubtitles = new ArrayList<String>();
		for (ShortformResult musicVideoResult : musicVideoResults) {
			musicVideoSubtitles.add(musicVideoResult.getMusicVideoSubtitle());
		}
		return musicVideoSubtitles;
	}

	/**
	 * Gets the musicVideo MGIDs as a list
	 *
	 * @return the musicVideo MGIDs as a list
	 **/
	public List<String> getMusicVideoMGIDs() {
		List<String> musicVideoMGIDs = new ArrayList<String>();
		for (ShortformResult musicVideoResult : musicVideoResults) {
			musicVideoMGIDs.add(musicVideoResult.getMusicVideoMGID());
		}
		return musicVideoMGIDs;
	}

	/**
	 * Gets the musicVideo ids as a list
	 *
	 * @return the musicVideo ids as a list
	 **/
	public List<String> getMusicVideoIds() {
		List<String> musicVideoIds = new ArrayList<String>();
		for (ShortformResult musicVideoResult : musicVideoResults) {
			musicVideoIds.add(musicVideoResult.getMusicVideoId());
		}
		return musicVideoIds;
	}

	/**
	 * Gets the musicVideo url
	 *
	 * @return the musicVideo url
	 **/
	public String getMusicVideosFeedURL() {
		return FeedFactory.getEditorialsFeedURL(getPropertyId());
	}

	/**
	 * Gets the trailer titles as a list
	 *
	 * @return the trailer titles as a list
	 **/
	public List<String> getTrailerTitles() {
		List<String> trailerTitles = new ArrayList<String>();
		for (ShortformResult trailerResult : trailerResults) {
			trailerTitles.add(trailerResult.getTrailerTitle());
		}
		return trailerTitles;
	}

	/**
	 * Gets the trailer descriptions as a list
	 *
	 * @return the trailer descriptions as a list
	 **/
	public List<String> getTrailerDescriptions() {
		List<String> trailerDescriptions = new ArrayList<String>();
		for (ShortformResult trailerResult : trailerResults) {
			trailerDescriptions.add(trailerResult.getTrailerDescription());
		}
		return trailerDescriptions;
	}

	/**
	 * Gets the trailer durations in milliseconds as a list
	 *
	 * @return the trailer durations in milliseconds as a list
	 **/
	public List<Long> getTrailerDurationsInMilliSeconds() {
		List<Long> trailerDurationsInTimeCode = new ArrayList<Long>();
		for (ShortformResult trailerResult : trailerResults) {
			trailerDurationsInTimeCode.add(trailerResult.getTrailerDurationInMilliSeconds());
		}
		return trailerDurationsInTimeCode;
	}

	/**
	 * Gets the trailer durations in time code as a list
	 *
	 * @return the trailer durations in time code as a list
	 **/
	public List<String> getTrailerDurationsInTimeCode() {
		List<String> trailerDurationsInTimeCode = new ArrayList<String>();
		for (ShortformResult trailerResult : trailerResults) {
			trailerDurationsInTimeCode.add(trailerResult.getTrailerDurationInTimeCode());
		}
		return trailerDurationsInTimeCode;
	}

	/**
	 * Gets the trailer subtitles as a list
	 *
	 * @return the trailer subtitles as a list
	 **/
	public List<String> getTrailerSubtitles() {
		List<String> trailerSubtitles = new ArrayList<String>();
		for (ShortformResult trailerResult : trailerResults) {
			trailerSubtitles.add(trailerResult.getTrailerSubtitle());
		}
		return trailerSubtitles;
	}


	/**
	 * Gets the trailer MGIDs as a list
	 *
	 * @return the trailer MGIDs as a list
	 **/
	public List<String> getTrailerMGIDs() {
		List<String> trailerMGIDs = new ArrayList<String>();
		for (ShortformResult trailerResult : trailerResults) {
			trailerMGIDs.add(trailerResult.getTrailerMGID());
		}
		return trailerMGIDs;
	}

	/**
	 * Gets the trailer ids as a list
	 *
	 * @return the trailer ids as a list
	 **/
	public List<String> getTrailerIds() {
		List<String> trailerIds = new ArrayList<String>();
		for (ShortformResult trailerResult : trailerResults) {
			trailerIds.add(trailerResult.getTrailerId());
		}
		return trailerIds;
	}

	/**
	 * Gets the trailer url
	 *
	 * @return the trailer url
	 **/
	public String getTrailersFeedURL() {
		return FeedFactory.getTrailersFeedURL(getPropertyId());
	}

	/**
	 * Gets the parent property of the clips results
	 *
	 * @return the parent property of the clips results
	 **/
	public PropertyResult getParentProperty() {
		return getProperty();
	}

	/**
	 * Gets the property of the clips results
	 *
	 * @return the property of the clips results
	 **/
	public PropertyResult getProperty() {
		return property;
	}

	/**
	 * Gets the property title of the clips results
	 *
	 * @return the property title of the clips results
	 **/
	public String getPropertyTitle() {
		return property.getPropertyTitle();
	}

	/**
	 * Gets the property id of the clips results
	 *
	 * @return the property id of the clips results
	 **/
	public String getPropertyId() {
		return property.getPropertyId();
	}

	/**
	 * Gets the property mgid of the clips results
	 *
	 * @return the property mgid of the clips results
	 **/
	public String getSeriesMGID() {
		return property.getPropertyMGID();
	}

	/**
	 * Checks to see if a string is composed primarily of numbers
	 *
	 * @return true if the string is composed primarily of numbers, false
	 * otherwise
	 **/
	public Boolean isStringOfNumbers(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!isCharANumber(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks to see if a character is a number
	 *
	 * @return true if the character is a number, false otherwise
	 **/
	public Boolean isCharANumber(char c) {
		return c >= 48 && c <= 57; // Ascii codes for 0-9
	}

	/**
	 * Gets the number of clips, musicVideos, or trailers in the resulting query
	 *
	 * @return the number of clips, musicVideos, or trailers in the resulting query
	 **/
	@Override
	public int size() {
		Integer size = 0;
		if (this.property.isSeriesProperty()) {
			size = clipResults.size();
		} else if (this.property.isCollectionProperty()) {
			size = musicVideoResults.size();
		} else if (this.property.isMovieProperty()) {
			size = trailerResults.size();
		}
		return size;

	}

	/**
	 * Checks to see if the Clip Results is empty
	 *
	 * @return true if the Clip Results is empty, false otherwise
	 **/
	@Override
	public boolean isEmpty() {
		Boolean isEmpty = false;
		if (this.property.isSeriesProperty()) {
			isEmpty = clipResults.isEmpty();
		} else if (this.property.isCollectionProperty()) {
			isEmpty = musicVideoResults.isEmpty();
		} else if (this.property.isMovieProperty()) {
			isEmpty = trailerResults.isEmpty();
		}
		return isEmpty;
	}

	/**
	 * Checks to see if the ShortformResults contains an ShortformResult
	 *
	 * @return true if the ShortformResults contains an ShortformResult, false otherwise
	 **/
	@Override
	public boolean contains(Object o) {
		Boolean contains = false;
		if (this.property.isSeriesProperty()) {
			contains = clipResults.contains(o);
		} else if (this.property.isCollectionProperty()) {
			contains = musicVideoResults.contains(o);
		} else if (this.property.isMovieProperty()) {
			contains = trailerResults.contains(o);
		}
		return contains;
	}

	/**
	 * Returns an Array of ShortformResults
	 *
	 * @return an Array of ShortformResults
	 **/
	@Override
	public Object[] toArray() {
		if (this.property.isSeriesProperty()) {
			return clipResults.toArray();
		} else if (this.property.isCollectionProperty()) {
			return musicVideoResults.toArray();
		} else if (this.property.isMovieProperty()) {
			return trailerResults.toArray();
		}
		return null;
	}

	/**
	 * Returns an Array of ClipResults
	 *
	 * @return an Array of ClipResults
	 * @author Edward Poon
	 * @version 1.0.0 August 25 2017
	 **/
	@Override
	public <T> T[] toArray(T[] a) {
		if (this.property.isSeriesProperty()) {
			return clipResults.toArray(a);
		} else if (this.property.isCollectionProperty()) {
			return musicVideoResults.toArray(a);
		} else if (this.property.isMovieProperty()) {
			return trailerResults.toArray(a);
		}
		return null;
	}

	/**
	 * Adds an ShortformResult object to the end of the ShortformResults list returns false
	 * if operation fails
	 *
	 * @return true if you can add an ShortformResult object to the ShortformResults, false
	 * otherwise
	 **/
	@Override
	public boolean add(ShortformResult e) {
		Boolean add = false;
		if (this.property.isSeriesProperty()) {
			add = clipResults.add(e);
		} else if (this.property.isCollectionProperty()) {
			add = musicVideoResults.add(e);
		} else if (this.property.isMovieProperty()) {
			add = trailerResults.add(e);
		}
		return add;
	}

	/**
	 * Removes an ShortformResult from the ShortformResults, false if ShortformResult doesn't
	 * exist in ShortformResults
	 *
	 * @return true if ShortformResult exists in ShortformResults and can be removed, false
	 * otherwise
	 **/
	@Override
	public boolean remove(Object o) {
		Boolean remove = false;
		if (this.property.isSeriesProperty()) {
			remove = clipResults.remove(o);
		} else if (this.property.isCollectionProperty()) {
			remove = musicVideoResults.remove(o);
		} else if (this.property.isMovieProperty()) {
			remove = trailerResults.remove(o);
		}
		return remove;
	}

	/**
	 * Checks to see if the ShortformResults contains the collection specified by c
	 * which should be a list of ShortformResults or a collection of ShortformResults
	 *
	 * @return true if the ShortformResults contains the collection specified by c,
	 * false otherwise
	 **/
	@Override
	public boolean containsAll(Collection<?> c) {
		Boolean contains = false;
		if (this.property.isSeriesProperty()) {
			contains = clipResults.containsAll(c);
		} else if (this.property.isCollectionProperty()) {
			contains = musicVideoResults.containsAll(c);
		} else if (this.property.isMovieProperty()) {
			contains = trailerResults.containsAll(c);
		}
		return contains;
	}

	/**
	 * Adds all the elements in the collection specified by c which should be a
	 * list of ShortformResults or a collection of ShortformResults returns false if addAll
	 * fails
	 *
	 * @return true if you can add all the ShortformResults in collection c to the
	 * ShortformResults, false otherwise
	 **/
	@Override
	public boolean addAll(Collection<? extends ShortformResult> c) {
		Boolean addAll = false;
		if (this.property.isSeriesProperty()) {
			addAll = clipResults.addAll(c);
		} else if (this.property.isCollectionProperty()) {
			addAll = musicVideoResults.addAll(c);
		} else if (this.property.isMovieProperty()) {
			addAll = trailerResults.addAll(c);
		}
		return addAll;
	}

	/***
	 * Adds all the elements in the collection specified by c which should be a
	 * list of ShortformResults or a collection of ShortformResults returns false if addAll
	 * fails
	 *
	 * @return true if you can add all the ShortformResults in collection c to the
	 *         ShortformResults, false otherwise
	 **/
	@Override
	public boolean addAll(int index, Collection<? extends ShortformResult> c) {
		Boolean addAll = false;
		if (this.property.isSeriesProperty()) {
			addAll = clipResults.addAll(index, c);
		}
		else if(this.property.isCollectionProperty())
		{
			addAll = musicVideoResults.addAll(index, c);
		}
		else if(this.property.isMovieProperty())
		{
			addAll = trailerResults.addAll(index, c);
		}
		return addAll;
	}

	/**
	 * Remove from ShortformResults the collection of ShortformResult specified by c
	 *
	 * @return true if you can remove the collection of ShortformResult specified by c
	 *         from ShortformResults, false otherwise
	 **/
	@Override
	public boolean removeAll(Collection<?> c) {
		Boolean removeAll = false;
		if (this.property.isSeriesProperty()) {
			removeAll = clipResults.removeAll(c);
		}
		else if(this.property.isCollectionProperty())
		{
			removeAll = musicVideoResults.removeAll(c);
		}
		else if(this.property.isMovieProperty())
		{
			removeAll = trailerResults.removeAll(c);
		}
		return removeAll;
	}

	/**]
	 * Retains only the ShortformResult in the collection specified by c
	 *
	 * @return true if retains only the ShortformResult in the collection specified by
	 *         c, false otherwise
	 **/
	@Override
	public boolean retainAll(Collection<?> c) {
		Boolean removeAll = false;
		if (this.property.isSeriesProperty()) {
			removeAll = clipResults.retainAll(c);
		}
		else if(this.property.isCollectionProperty())
		{
			removeAll = musicVideoResults.retainAll(c);
		}
		else if(this.property.isMovieProperty())
		{
			removeAll = trailerResults.retainAll(c);
		}
		return removeAll;
	}

	/**
	 * Removes all the elements of ShortformResults
	 *
	 * @return true if all the ShortformResults are removed, false otherwise
	 **/
	@Override
	public void clear() {
		if (this.property.isSeriesProperty()) {
			clipResults.clear();
		}
		else if(this.property.isCollectionProperty())
		{
			musicVideoResults.clear();
		}
		else if(this.property.isMovieProperty())
		{
			trailerResults.clear();
		}
	}

	/**
	 * Returns the ShortformResults from the ShortformResults list specified in the index
	 *
	 * @return the ShortformResults from the ShortformResults list specified in the index
	 **/
	@Override
	public ShortformResult get(int index) {
		if (this.property.isSeriesProperty()) {
			return clipResults.get(index);
		}
		else if(this.property.isCollectionProperty())
		{
			return clipResults.get(index);
		}
		else if(this.property.isMovieProperty())
		{
			return clipResults.get(index);
		}
		return null;
	}

	/**
	 * Wrapper method to return the first clip from ShortformResults list
	 * @return first clip from the ShortformResults
	 */
	public ShortformResult getFirstClip() {
		return clipResults.get(0);
	}

	/**
	 * Wrapper method to return the last clip from ShortformResults list
	 * 
	 * @return last clip from the ShortformResults
	 */
	public ShortformResult getLastClip() {
		return clipResults.get(clipResults.size() - 1);
	}

	/**
	 * Wrapper method to return the first trailer  from ShortformResults list
	 * @return first trailer from the ShortformResults
	 */
	public ShortformResult getFirstTrailer() {
		return trailerResults.get(0);
	}

	/**
	 * Wrapper method to return the first music video from ShortformResults list
	 * @return first music video from the ShortformResults
	 */
	public ShortformResult getFirstMusicVideo() {
		return musicVideoResults.get(0);
	}

	/**
	 * Replaces the ShortformResults at the specified position in the ShortformResults with
	 * ShortformResults specified by element
	 *
	 * @return the ShortformResults previously at the specified position
	 **/
	@Override
	public ShortformResult set(int index, ShortformResult element) {
		if (this.property.isSeriesProperty()) {
			return clipResults.set(index, element);
		}
		else if(this.property.isCollectionProperty())
		{
			return musicVideoResults.set(index, element);
		}
		else if(this.property.isMovieProperty())
		{
			return trailerResults.set(index, element);
		}
		return null;
	}

	/**
	 * Inserts the specified ShortformResult at the specified position in this list.
	 * Shifts the ShortformResult currently at that position (if any) and any
	 * subsequent elements to the right (adds one to their indices)
	 *
	 **/
	@Override
	public void add(int index, ShortformResult element) {
		if (this.property.isSeriesProperty()) {
			clipResults.add(index, element);
		}
		else if(this.property.isCollectionProperty())
		{
			musicVideoResults.add(index, element);
		}
		else if(this.property.isMovieProperty())
		{
			trailerResults.add(index, element);
		}
	}

	/**
	 * Removes the ShortformResult at the specified position in this list (optional
	 * operation). Shifts any subsequent ShortformResult to the left (subtracts one
	 * from their indices). Returns the ShortformResult that was removed from the list.
	 *
	 * @return the ShortformResult that was removed from the list.
	 **/
	@Override
	public ShortformResult remove(int index) {
		if (this.property.isSeriesProperty()) {
			return clipResults.remove(index);
		}
		else if(this.property.isCollectionProperty())
		{
			return musicVideoResults.remove(index);
		}
		else if(this.property.isMovieProperty())
		{
			return trailerResults.remove(index);
		}
		return null;
	}

	/**
	 * Returns the index of the first occurrence of the specified ShortformResult in
	 * this list, or -1 if this list does not contain the ShortformResult.
	 *
	 *
	 * @return the index of the first occurrence of the specified ShortformResult in
	 *         this list, or -1 if this list does not contain the ShortformResult
	 **/
	@Override
	public int indexOf(Object o) {
		Integer index = 0;
		if (this.property.isSeriesProperty()) {
			index = clipResults.indexOf(o);
		}
		else if(this.property.isCollectionProperty())
		{
			index = musicVideoResults.indexOf(o);
		}
		else if(this.property.isMovieProperty())
		{
			index = trailerResults.indexOf(o);
		}
		return index;
	}

	/**
	 * Returns the index of the last occurrence of the specified ShortformResult in
	 * this list, or -1 if this list does not contain the ShortformResult.
	 *
	 * @return the index of the last occurrence of the specified ShortformResult in
	 *         this list, or -1 if this list does not contain the ShortformResult
	 **/
	@Override
	public int lastIndexOf(Object o) {
		Integer index = 0;
		if (this.property.isSeriesProperty()) {
			index = clipResults.lastIndexOf(o);
		}
		else if(this.property.isCollectionProperty())
		{
			index = musicVideoResults.lastIndexOf(o);
		}
		else if(this.property.isMovieProperty())
		{
			index = trailerResults.lastIndexOf(o);
		}
		return index;
	}

	/**
	 * Returns a list iterator over the ShortformResults in this list (in proper
	 * sequence)
	 *
	 * @return a list iterator over the ShortformResults in this list (in proper
	 *         sequence)
	 **/
	@Override
	public ListIterator<ShortformResult> listIterator() {
		if (this.property.isSeriesProperty()) {
			return clipResults.listIterator();
		}
		else if(this.property.isCollectionProperty())
		{
			return musicVideoResults.listIterator();
		}
		else if(this.property.isMovieProperty())
		{
			return trailerResults.listIterator();
		}
		return null;
	}

	/**
	 * Returns a list iterator over the ShortformResults in this list (in proper
	 * sequence), starting at the specified position in the list. The specified
	 * index indicates the first ClipResult that would be returned by an initial
	 * call to next. An initial call to previous would return the ClipResult with
	 * the specified index minus one.
	 *
	 * @return a list iterator over the ShortformResults in this list (in proper
	 *         sequence), starting at the specified position in the list. The
	 *         specified index indicates the first ClipResult that would be
	 *         returned by an initial call to next. An initial call to previous
	 *         would return the ClipResult with the specified index minus one.
	 **/
	@Override
	public ListIterator<ShortformResult> listIterator(int index) {
		if (this.property.isSeriesProperty()) {
			return clipResults.listIterator(index);
		}
		else if(this.property.isCollectionProperty())
		{
			return musicVideoResults.listIterator(index);
		}
		else if(this.property.isMovieProperty())
		{
			return trailerResults.listIterator(index);
		}
		return null;
	}

	/**
	 * Returns a view of the portion of this list between the specified fromIndex,
	 * inclusive, and toIndex, exclusive. (If fromIndex and toIndex are equal, the
	 * returned list is empty.) The returned list is backed by this list, so
	 * non-structural changes in the returned list are reflected in this list, and
	 * vice-versa. The returned list supports all of the optional list operations
	 * supported by this list.
	 *
	 * @return a view of the specified range within this list
	 **/
	@Override
	public List<ShortformResult> subList(int fromIndex, int toIndex) {
		if (this.property.isSeriesProperty()) {
			return clipResults.subList(fromIndex, toIndex);
		}
		else if(this.property.isCollectionProperty())
		{
			return musicVideoResults.subList(fromIndex, toIndex);
		}
		else if(this.property.isMovieProperty())
		{
			return trailerResults.subList(fromIndex, toIndex);
		}
		return null;
	}

	/**
	 * Returns an iterator for the clip result set
	 *
	 * @return iterator for clip result set
	 **/
	@Override
	public Iterator<ShortformResult> iterator() {
		if (this.property.isSeriesProperty()) {
			return clipResults.iterator();
		}
		else if(this.property.isCollectionProperty())
		{
			return musicVideoResults.iterator();
		}
		else if(this.property.isMovieProperty())
		{
			return trailerResults.iterator();
		}
		return null;
	}

	/**
	 * Orders the shortformResults according to the field name in ascending or
	 * descending order
	 *
	 * @param order
	 *          - Get the order from the Order class
	 * @param results
	 *          - List of results per property type
	 * @param fieldNames
	 *          - Get the shortform field names from the Order class
	 * @return shortformResults ordered according to the field name in ascending or
	 *         descending order
	 **/
	public ShortformResults orderBy(String order, ShortformResults results, String... fieldNames) {
		Collections.sort(results, new Comparator<ShortformResult>() {
			@Override
			public int compare(ShortformResult shortform1, ShortformResult shortform2) {

				Object shortform1Value = shortform1.get(fieldNames);
				Object shortform2Value = shortform2.get(fieldNames);

				if (shortform1Value instanceof Long) {
					Long shortform1ValueAsLong = (Long) shortform1Value;
					Long shortform2ValueAsLong = (Long) shortform2Value;
					if (order.toUpperCase().equals("ASC")) {
						return shortform1ValueAsLong.compareTo(shortform2ValueAsLong);
					} else if (order.toUpperCase().equals("DESC")) {
						return shortform2ValueAsLong.compareTo(shortform1ValueAsLong);
					}
				} else if (shortform1Value instanceof String && isStringOfNumbers((String) shortform1Value)) {
					Integer shortform1ValueAsInteger = Integer.parseInt((String) shortform1Value);
					Integer shortform2ValueAsInteger = Integer.parseInt((String) shortform2Value);
					if (order.toUpperCase().equals("ASC")) {
						return shortform1ValueAsInteger.compareTo(shortform2ValueAsInteger);
					} else if (order.toUpperCase().equals("DESC")) {
						return shortform2ValueAsInteger.compareTo(shortform1ValueAsInteger);
					}
				} else if (shortform1Value instanceof String) {
					String shortform1ValueAsString = (String) shortform1Value;
					String shortform2ValueAsString = (String) shortform2Value;
					if (order.toUpperCase().equals("ASC")) {
						return shortform1ValueAsString.compareTo(shortform2ValueAsString);
					} else if (order.toUpperCase().equals("DESC")) {
						return shortform2ValueAsString.compareTo(shortform1ValueAsString);
					}
				}
				return 0;
			}

		});
		return this;
	}
	
	/***
	 * Gets the raw json of the clip result set NOTE: NEVER return clipJSON
	 * since that is never altered even after all the filtering. What's filtered
	 * is actually the array list which holds the clip results
	 *
	 * @return the raw json of the clip result set
	 **/
	@SuppressWarnings("unchecked")
	public JSONArray toClipsJSONArray() {
		JSONArray jsonArray = new JSONArray();
		for (ShortformResult clipResult : clipResults) {
			jsonArray.add(clipResult.toJSON(this.clipJSON));
		}
		return jsonArray;
	}

	/***
	 * Gets the raw json of the clip result set NOTE: NEVER return clipJSON
	 * since that is never altered even after all the filtering. What's filtered
	 * is actually the array list which holds the clip results
	 *
	 * @return the raw json of the clip result set
	 **/
	@SuppressWarnings("unchecked")
	public JSONArray toMusicVideosJSONArray() {
		JSONArray jsonArray = new JSONArray();
		for (ShortformResult musicVideoResult : musicVideoResults) {
			jsonArray.add(musicVideoResult.toJSON(this.musicVideoJSON));
		}
		return jsonArray;
	}

	/***
	 * Gets the raw json of the clip result set NOTE: NEVER return trailerJSON
	 * since that is never altered even after all the filtering. What's filtered
	 * is actually the array list which holds the trailer results
	 *
	 * @return the raw json of the trailer result set
	 **/
	@SuppressWarnings("unchecked")
	public JSONArray toTrailerJSONArray() {
		JSONArray jsonArray = new JSONArray();
		for (ShortformResult trailerResult : trailerResults) {
			jsonArray.add(trailerResult.toJSON(this.trailerJSON));
		}
		return jsonArray;
	}

	/**
	 * Gets the ClipsResults in string form
	 *
	 * @return the ClipsResults in string form
	 **/
	public String toString(ShortformResults shortformResults) {
		return shortformResults.toString();
	}

}
