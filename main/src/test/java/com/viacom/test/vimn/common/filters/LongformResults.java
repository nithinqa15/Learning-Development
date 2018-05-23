package com.viacom.test.vimn.common.filters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.viacom.test.core.util.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LongformResults implements Iterable<LongformResult>, List<LongformResult> {

    private PropertyResult property;
    private List<LongformResult> episodeResults;
    private List<LongformResult> fightResults;
    private List<LongformResult> movieResults;
    private JSONObject episodeJSON;
    private JSONObject fightJSON;
    private JSONObject movieJSON;
    private JSONObject episodeMetadata;
    private JSONObject fightMetadata;
    private JSONObject movieMetadata;

    /**
     * Constructor
     * @param longformJSON
     * @param property
     */
    public LongformResults(JSONObject longformJSON, PropertyResult property) {
        this.property = property;
        if (property.isMovieProperty()) {
        	this.movieJSON = longformJSON;
            this.movieMetadata = getLongformsMetadata(movieJSON);
            this.movieResults = convertJSONToListOfLongformResults(movieJSON);
        }
        if (property.isFightProperty()) {
            this.fightJSON = longformJSON;
            this.fightMetadata = getLongformsMetadata(fightJSON);
            this.fightResults = convertJSONToListOfLongformResults(fightJSON);
        }
        if (property.isSeriesProperty()) {
        	this.episodeJSON = longformJSON;
            this.episodeMetadata = getLongformsMetadata(episodeJSON);
            this.episodeResults = convertJSONToListOfLongformResults(episodeJSON);
        }
    }

    /**
     * Converts the longform json to an array list of longform results
     *
     * @param longformResultsAsJSON
     *
     * @return a list of the longform results
     **/
    @SuppressWarnings("unchecked")
	private List<LongformResult> convertJSONToListOfLongformResults(JSONObject longformResultsAsJSON) {
        List<LongformResult> longformResults = new ArrayList<>();
        try {   // some longform content contains empty an data object and is throwing null point exceptions
            JSONObject dataObj = (JSONObject) longformResultsAsJSON.get("data");
            JSONArray itemsArr = (JSONArray) dataObj.get("items");
            Iterator<JSONObject> iterator = itemsArr.iterator();
            Integer longformPosition = 0;
            while (iterator.hasNext()) {
                JSONObject longformObject = iterator.next();
                longformResults.add(new LongformResult(longformObject, this.property, longformPosition));
                longformPosition++;
            }
        } catch (NullPointerException e) {
            Logger.logMessage(getProperty().getPropertyTitle() + " is missing longform data!");
        }
        return longformResults;
    }

    /**
     * Overload method that stitches all longform in each of the Json objects in the list.
     *
     * @param longformJSONs
     * @return a list of longform results
     */
    @SuppressWarnings({ "unchecked", "unused" })
    private List<LongformResult> convertJSONToListOfLongformResults(List<JSONObject> longformJSONs) {
        List<LongformResult> longformResults = new ArrayList<>();
        Integer longformPosition = 0;
        try {   // some longform content contains empty an data object and is throwing null point exceptions
            for (JSONObject resultJSON : longformJSONs) {
                JSONObject dataObj = (JSONObject) resultJSON.get("data");
                JSONArray itemsArr = (JSONArray) dataObj.get("items");
                Iterator<JSONObject> iterator = itemsArr.iterator();
                while (iterator.hasNext()) {
                    JSONObject longformObject = iterator.next();
                    longformResults.add(new LongformResult(longformObject, getProperty(), longformPosition));
                    longformPosition++;
                }
            }
        } catch (NullPointerException e) {
            Logger.logMessage(getProperty().getPropertyTitle() + " is missing longform data!");
        }
        return longformResults;
    }

    /**
     * Gets the metadata from a longform object
     *
     * @param longformJSON
     * @return metadata from a longform object
     **/
    private JSONObject getLongformsMetadata(JSONObject longformJSON) {
        return (JSONObject) longformJSON.get("metadata");
    }

    /**
     * Gets the pagesize from the episode metadata object
     *
     * @return pagesize from a the episode metadata object
     **/
    public Long getEpisodePageSize() {
        return (Long) ((JSONObject) episodeMetadata.get("pagination")).get("pageSize");
    }

    /**
     * Gets the total number of episode items from the episode metadata object
     *
     * @return total number of episode items from a the episode metadata object
     **/
    public Long getEpisodeTotalItems() {
        return (Long) ((JSONObject) episodeMetadata.get("pagination")).get("totalItems");
    }

    /**
     * Gets the pagesize from the movie metadata object
     *
     * @return pagesize from a the movie metadata object
     **/
    public Long getMoviePageSize() {
        return (Long) ((JSONObject) movieMetadata.get("pagination")).get("pageSize");
    }

    /**
     * Gets the total number of movie items from the movie metadata object
     *
     * @return total number of movie items from a the movie metadata object
     **/
    public Long getMovieTotalItems() {
        return (Long) ((JSONObject) movieMetadata.get("pagination")).get("totalItems");
    }

    /**
     * Gets the pagesize from the fights metadata object
     *
     * @return pagesize from a the fights metadata object
     **/
    public Long getFightPageSize() {
        return (Long) ((JSONObject) fightMetadata.get("pagination")).get("pageSize");
    }

    /**
     * Gets the total number of fight items from the fight metadata object
     *
     * @return total number of fight items from a the fight metadata object
     **/
    public Long getFightTotalItems() {
        return (Long) ((JSONObject) fightMetadata.get("pagination")).get("totalItems");
    }

    /**
     * Gets the episode titles as a list
     *
     * @return the episode titles as a list
     **/
    public List<String> getEpisodeTitles() {
        List<String> episodeTitles = new ArrayList<String>();
        for (LongformResult episodeResult : episodeResults) {
            episodeTitles.add(episodeResult.getEpisodeTitle());
        }
        return episodeTitles;
    }

    /**
     * Gets the episode descriptions as a list
     *
     * @return the episode descriptions as a list
     **/
    public List<String> getEpisodeDescriptions() {
        List<String> episodeDescriptions = new ArrayList<String>();
        for (LongformResult episodeResult : episodeResults) {
            episodeDescriptions.add(episodeResult.getEpisodeDescription());
        }
        return episodeDescriptions;
    }

    /**
     * Gets the episode durations in milliseconds as a list
     *
     * @return the episode durations in milliseconds as a list
     **/
    public List<Long> getEpisodeDurationsInMilliSeconds() {
        List<Long> episodeDurationsInTimeCode = new ArrayList<Long>();
        for (LongformResult episodeResult : episodeResults) {
            episodeDurationsInTimeCode.add(episodeResult.getEpisodeDurationInMilliSeconds());
        }
        return episodeDurationsInTimeCode;
    }

    /**
     * Gets the episode durations in time code as a list
     *
     * @return the episode durations in time code as a list
     **/
    public List<String> getEpisodeDurationsInTimeCode() {
        List<String> episodeDurationsInTimeCode = new ArrayList<String>();
        for (LongformResult episodeResult : episodeResults) {
            episodeDurationsInTimeCode.add(episodeResult.getEpisodeDurationInTimeCode());
        }
        return episodeDurationsInTimeCode;
    }

    /**
     * Gets the episode subtitles as a list
     *
     * @return the episode subtitles as a list
     **/
    public List<String> getEpisodeSubtitles() {
        List<String> episodeSubtitles = new ArrayList<String>();
        for (LongformResult episodeResult : episodeResults) {
            episodeSubtitles.add(episodeResult.getEpisodeSubtitle());
        }
        return episodeSubtitles;
    }

    /**
     * Gets the episode season number as a list
     *
     * @return the episode season number as a list
     **/
    public List<Integer> getEpisodeSeasonNumbers() {
        List<Integer> episodeSeasonNumbers = new ArrayList<>();
        for (LongformResult episodeResult : episodeResults) {
            episodeSeasonNumbers.add(episodeResult.getEpisodeSeasonNumber());
        }
        return episodeSeasonNumbers;
    }


    /**
     * Gets the episode MGIDs as a list
     *
     * @return the episode MGIDs as a list
     **/
    public List<String> getEpisodeMGIDs() {
        List<String> episodeMGIDs = new ArrayList<String>();
        for (LongformResult episodeResult : episodeResults) {
            episodeMGIDs.add(episodeResult.getEpisodeMGID());
        }
        return episodeMGIDs;
    }

    /**
     * Gets the episode ids as a list
     *
     * @return the episode ids as a list
     **/
    public List<String> getEpisodeIds() {
        List<String> episodeIds = new ArrayList<String>();
        for (LongformResult episodeResult : episodeResults) {
            episodeIds.add(episodeResult.getEpisodeId());
        }
        return episodeIds;
    }

    /**
     * Gets the episode url
     *
     * @return the episode url
     **/
    public String getEpisodesFeedURL() {
        return property.getEpisodeURL();
    }

    /**
     * Gets the fight titles as a list
     *
     * @return the fight titles as a list
     **/
    public List<String> getFightTitles() {
        List<String> fightTitles = new ArrayList<String>();
        for (LongformResult fightResult : fightResults) {
            fightTitles.add(fightResult.getFightTitle());
        }
        return fightTitles;
    }

    /**
     * Gets the fight descriptions as a list
     *
     * @return the fight descriptions as a list
     **/
    public List<String> getFightDescriptions() {
        List<String> fightDescriptions = new ArrayList<String>();
        for (LongformResult fightResult : fightResults) {
            fightDescriptions.add(fightResult.getFightDescription());
        }
        return fightDescriptions;
    }

    /**
     * Gets the fight durations in milliseconds as a list
     *
     * @return the fight durations in milliseconds as a list
     **/
    public List<Long> getFightDurationsInMilliSeconds() {
        List<Long> fightDurationsInTimeCode = new ArrayList<Long>();
        for (LongformResult fightResult : fightResults) {
            fightDurationsInTimeCode.add(fightResult.getFightDurationInMilliSeconds());
        }
        return fightDurationsInTimeCode;
    }

    /**
     * Gets the fight durations in time code as a list
     *
     * @return the fight durations in time code as a list
     **/
    public List<String> getFightDurationsInTimeCode() {
        List<String> fightDurationsInTimeCode = new ArrayList<String>();
        for (LongformResult fightResult : fightResults) {
            fightDurationsInTimeCode.add(fightResult.getFightDurationInTimeCode());
        }
        return fightDurationsInTimeCode;
    }

    /**
     * Gets the fight subtitles as a list
     *
     * @return the fight subtitles as a list
     **/
    public List<String> getFightSubtitles() {
        List<String> fightSubtitles = new ArrayList<String>();
        for (LongformResult fightResult : fightResults) {
            fightSubtitles.add(fightResult.getFightSubtitle());
        }
        return fightSubtitles;
    }

    /**
     * Gets the fight MGIDs as a list
     *
     * @return the fight MGIDs as a list
     **/
    public List<String> getFightMGIDs() {
        List<String> fightMGIDs = new ArrayList<String>();
        for (LongformResult fightResult : fightResults) {
            fightMGIDs.add(fightResult.getFightMGID());
        }
        return fightMGIDs;
    }

    /**
     * Gets the fight ids as a list
     *
     * @return the fight ids as a list
     **/
    public List<String> getFightIds() {
        List<String> fightIds = new ArrayList<String>();
        for (LongformResult fightResult : fightResults) {
            fightIds.add(fightResult.getFightId());
        }
        return fightIds;
    }

    /**
     * Gets the fight url
     *
     * @return the fight url
     **/
    public String getFightsFeedURL() {
        return property.getPlaylistURL();
    }

    /**
     * Gets the movie titles as a list
     *
     * @return the movie titles as a list
     **/
    public List<String> getMovieTitles() {
        List<String> movieTitles = new ArrayList<String>();
        for (LongformResult movieResult : movieResults) {
            movieTitles.add(movieResult.getMovieTitle());
        }
        return movieTitles;
    }

    /**
     * Gets the movie descriptions as a list
     *
     * @return the movie descriptions as a list
     **/
    public List<String> getMovieDescriptions() {
        List<String> movieDescriptions = new ArrayList<String>();
        for (LongformResult movieResult : movieResults) {
            movieDescriptions.add(movieResult.getMovieDescription());
        }
        return movieDescriptions;
    }

    /**
     * Gets the movie durations in milliseconds as a list
     *
     * @return the movie durations in milliseconds as a list
     **/
    public List<Long> getMovieDurationsInMilliSeconds() {
        List<Long> movieDurationsInTimeCode = new ArrayList<Long>();
        for (LongformResult movieResult : movieResults) {
            movieDurationsInTimeCode.add(movieResult.getMovieDurationInMilliSeconds());
        }
        return movieDurationsInTimeCode;
    }

    /**
     * Gets the movie durations in time code as a list
     *
     * @return the movie durations in time code as a list
     **/
    public List<String> getMovieDurationsInTimeCode() {
        List<String> movieDurationsInTimeCode = new ArrayList<String>();
        for (LongformResult movieResult : movieResults) {
            movieDurationsInTimeCode.add(movieResult.getMovieDurationInTimeCode());
        }
        return movieDurationsInTimeCode;
    }

    /**
     * Gets the movie subtitles as a list
     *
     * @return the movie subtitles as a list
     **/
    public List<String> getMovieSubtitles() {
        List<String> movieSubtitles = new ArrayList<String>();
        for (LongformResult movieResult : movieResults) {
            movieSubtitles.add(movieResult.getMovieSubtitle());
        }
        return movieSubtitles;
    }


    /**
     * Gets the movie MGIDs as a list
     *
     * @return the movie MGIDs as a list
     **/
    public List<String> getMovieMGIDs() {
        List<String> movieMGIDs = new ArrayList<String>();
        for (LongformResult movieResult : movieResults) {
            movieMGIDs.add(movieResult.getMovieMGID());
        }
        return movieMGIDs;
    }

    /**
     * Gets the movie ids as a list
     *
     * @return the movie ids as a list
     **/
    public List<String> getMovieIds() {
        List<String> movieIds = new ArrayList<String>();
        for (LongformResult movieResult : movieResults) {
            movieIds.add(movieResult.getMovieId());
        }
        return movieIds;
    }

    /**
     * Gets the movie url
     *
     * @return the movie url
     **/
    public String getMoviesFeedURL() {
        return property.getMovieURL();
    }

    /**
     * Gets the parent property of the longform results
     *
     * @return the parent property of the longform results
     **/
    public PropertyResult getParentProperty() {
        return getProperty();
    }

    /**
     * Gets the property of the longform results
     *
     * @return the property of the longform results
     **/
    public PropertyResult getProperty() {
        return property;
    }

    /**
     * Gets the property title of the longform results
     *
     * @return the property title of the longform results
     **/
    public String getPropertyTitle() {
        return property.getPropertyTitle();
    }

    /**
     * Gets the property id of the longform results
     *
     * @return the property id of the longform results
     **/
    public String getPropertyId() {
        return property.getPropertyId();
    }

    /**
     * Gets the property mgid of the longform results
     *
     * @return the property mgid of the longform results
     **/
    public String getPropertyMGID() {
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
     * Gets the number of episodes, fights, or movies in the resulting query
     *
     * @return the number of episodes, fights, or movies in the resulting query
     **/
    @Override
    public int size() {
        Integer size = 0;
        if (this.property.isSeriesProperty()) {
            size = episodeResults.size();
        } else if (this.property.isFightProperty()) {
            size = fightResults.size();
        } else if (this.property.isMovieProperty()) {
            size = movieResults.size();
        }
        return size;

    }

    /**
     * Checks to see if the Episode Results is empty
     *
     * @return true if the Episode Results is empty, false otherwise
     **/
    @Override
    public boolean isEmpty() {
        Boolean isEmpty = false;
        if (this.property.isSeriesProperty()) {
            isEmpty = episodeResults.isEmpty();
        } else if (this.property.isFightProperty()) {
            isEmpty = fightResults.isEmpty();
        } else if (this.property.isMovieProperty()) {
            isEmpty = movieResults.isEmpty();
        }
        return isEmpty;
    }

    /**
     * Checks to see if the LongformResults contains an LongformResult
     *
     * @return true if the LongformResults contains an LongformResult, false otherwise
     **/
    @Override
    public boolean contains(Object o) {
        Boolean contains = false;
        if (this.property.isSeriesProperty()) {
            contains = episodeResults.contains(o);
        } else if (this.property.isFightProperty()) {
            contains = fightResults.contains(o);
        } else if (this.property.isMovieProperty()) {
            contains = movieResults.contains(o);
        }
        return contains;
    }

    /**
     * Returns an Array of LongformResults
     *
     * @return an Array of LongformResults
     **/
    @Override
    public Object[] toArray() {
        if (this.property.isSeriesProperty()) {
            return episodeResults.toArray();
        } else if (this.property.isFightProperty()) {
            return fightResults.toArray();
        } else if (this.property.isMovieProperty()) {
            return movieResults.toArray();
        }
        return null;
    }

    /**
     * Returns an Array of LongformResults
     *
     * @version 1.0.0 August 25 2017
     **/
    @Override
    public <T> T[] toArray(T[] a) {
        if (this.property.isSeriesProperty()) {
            return episodeResults.toArray(a);
        } else if (this.property.isFightProperty()) {
            return fightResults.toArray(a);
        } else if (this.property.isMovieProperty()) {
            return movieResults.toArray(a);
        }
        return null;
    }

    /**
     * Adds an LongformResult object to the end of the LongformResults list returns false
     * if operation fails
     *
     * @return true if you can add an LongformResult object to the LongformResults, false
     * otherwise
     **/
    @Override
    public boolean add(LongformResult e) {
        Boolean add = false;
        if (this.property.isSeriesProperty()) {
            add = episodeResults.add(e);
        } else if (this.property.isFightProperty()) {
            add = fightResults.add(e);
        } else if (this.property.isMovieProperty()) {
            add = movieResults.add(e);
        }
        return add;
    }

    /**
     * Removes an LongformResult from the LongformResults, false if LongformResult doesn't
     * exist in LongformResults
     *
     * @return true if LongformResult exists in LongformResults and can be removed, false
     * otherwise
     **/
    @Override
    public boolean remove(Object o) {
        Boolean remove = false;
        if (this.property.isSeriesProperty()) {
            remove = episodeResults.remove(o);
        } else if (this.property.isFightProperty()) {
            remove = fightResults.remove(o);
        } else if (this.property.isMovieProperty()) {
            remove = movieResults.remove(o);
        }
        return remove;
    }

    /**
     * Checks to see if the LongformResults contains the collection specified by c
     * which should be a list of LongformResults or a collection of LongformResults
     *
     * @return true if the LongformResults contains the collection specified by c,
     * false otherwise
     **/
    @Override
    public boolean containsAll(Collection<?> c) {
        Boolean contains = false;
        if (this.property.isSeriesProperty()) {
            contains = episodeResults.containsAll(c);
        } else if (this.property.isFightProperty()) {
            contains = fightResults.containsAll(c);
        } else if (this.property.isMovieProperty()) {
            contains = movieResults.containsAll(c);
        }
        return contains;
    }

    /**
     * Adds all the elements in the collection specified by c which should be a
     * list of LongformResults or a collection of LongformResults returns false if addAll
     * fails
     *
     * @return true if you can add all the LongformResults in collection c to the
     * LongformResults, false otherwise
     **/
    @Override
    public boolean addAll(Collection<? extends LongformResult> c) {
        Boolean addAll = false;
        if (this.property.isSeriesProperty()) {
            addAll = episodeResults.addAll(c);
        } else if (this.property.isFightProperty()) {
            addAll = fightResults.addAll(c);
        } else if (this.property.isMovieProperty()) {
            addAll = movieResults.addAll(c);
        }
        return addAll;
    }

    /***
     * Adds all the elements in the collection specified by c which should be a
     * list of LongformResults or a collection of LongformResults returns false if addAll
     * fails
     *
     * @return true if you can add all the LongformResults in collection c to the
     *         LongformResults, false otherwise
     **/
    @Override
    public boolean addAll(int index, Collection<? extends LongformResult> c) {
        Boolean addAll = false;
        if (this.property.isSeriesProperty()) {
            addAll = episodeResults.addAll(index, c);
        }
        else if(this.property.isFightProperty())
        {
            addAll = fightResults.addAll(index, c);
        }
        else if(this.property.isMovieProperty())
        {
            addAll = movieResults.addAll(index, c);
        }
        return addAll;
    }

    /**
     * Remove from LongformResults the collection of LongformResult specified by c
     *
     * @return true if you can remove the collection of LongformResult specified by c
     *         from LongformResults, false otherwise
     **/
    @Override
    public boolean removeAll(Collection<?> c) {
        Boolean removeAll = false;
        if (this.property.isSeriesProperty()) {
            removeAll = episodeResults.removeAll(c);
        }
        else if(this.property.isFightProperty())
        {
            removeAll = fightResults.removeAll(c);
        }
        else if(this.property.isMovieProperty())
        {
            removeAll = movieResults.removeAll(c);
        }
        return removeAll;
    }

    /**]
     * Retains only the LongformResult in the collection specified by c
     *
     * @return true if retains only the LongformResult in the collection specified by
     *         c, false otherwise
     **/
    @Override
    public boolean retainAll(Collection<?> c) {
        Boolean removeAll = false;
        if (this.property.isSeriesProperty()) {
            removeAll = episodeResults.retainAll(c);
        }
        else if(this.property.isFightProperty())
        {
            removeAll = fightResults.retainAll(c);
        }
        else if(this.property.isMovieProperty())
        {
            removeAll = movieResults.retainAll(c);
        }
        return removeAll;
    }

    /**
     * Removes all the elements of LongformResults
     *
     * @return true if all the LongformResults are removed, false otherwise
     **/
    @Override
    public void clear() {
        if (this.property.isSeriesProperty()) {
            episodeResults.clear();
        }
        else if(this.property.isFightProperty())
        {
            fightResults.clear();
        }
        else if(this.property.isMovieProperty())
        {
            movieResults.clear();
        }
    }

    /**
     * Returns the LongformResults from the LongformResults list specified in the index
     *
     * @return the LongformResults from the LongformResults list specified in the index
     **/
    @Override
    public LongformResult get(int index) {
        if (this.property.isSeriesProperty()) {
            return episodeResults.get(index);
        }
        else if(this.property.isFightProperty())
        {
            return fightResults.get(index);
        }
        else if(this.property.isMovieProperty())
        {
            return movieResults.get(index);
        }
        return null;
    }

    /**
     * Wrapper method to return the first episode from LongformResults list
     * @return first episode from the LongformResults
     */
    public LongformResult getFirstEpisode() {
        return episodeResults.get(0);
    }

    /**
     * Wrapper method to return the second episode from LongformResults list
     * @return first episode from the LongformResults
     */
    public LongformResult getSecondEpisode() {
        return episodeResults.get(1);
    }

    /**
     * Wrapper method to return the first movie from LongformResults list
     * @return first movie from the LongformResults
     */
    public LongformResult getFirstMovie() {
        return movieResults.get(0);
    }

    /**
     * Wrapper method to return the first fight from LongformResults list
     * @return first fight from the LongformResults
     */
    public LongformResult getFirstFight() {
        return fightResults.get(0);
    }

    /**
     * Replaces the LongformResults at the specified position in the LongformResults with
     * LongformResults specified by element
     *
     * @return the LongformResults previously at the specified position
     **/
    @Override
    public LongformResult set(int index, LongformResult element) {
        if (this.property.isSeriesProperty()) {
            return episodeResults.set(index, element);
        }
        else if(this.property.isFightProperty())
        {
            return fightResults.set(index, element);
        }
        else if(this.property.isMovieProperty())
        {
            return movieResults.set(index, element);
        }
        return null;
    }

    /**
     * Inserts the specified LongformResult at the specified position in this list.
     * Shifts the LongformResult currently at that position (if any) and any
     * subsequent elements to the right (adds one to their indices)
     *
     **/
    @Override
    public void add(int index, LongformResult element) {
        if (this.property.isSeriesProperty()) {
            episodeResults.add(index, element);
        }
        else if(this.property.isFightProperty())
        {
            fightResults.add(index, element);
        }
        else if(this.property.isMovieProperty())
        {
            movieResults.add(index, element);
        }
    }

    /**
     * Removes the LongformResult at the specified position in this list (optional
     * operation). Shifts any subsequent LongformResult to the left (subtracts one
     * from their indices). Returns the LongformResult that was removed from the list.
     *
     * @return the LongformResult that was removed from the list.
     **/
    @Override
    public LongformResult remove(int index) {
        if (this.property.isSeriesProperty()) {
            return episodeResults.remove(index);
        }
        else if(this.property.isFightProperty())
        {
            return fightResults.remove(index);
        }
        else if(this.property.isMovieProperty())
        {
            return movieResults.remove(index);
        }
        return null;
    }

    /**
     * Returns the index of the first occurrence of the specified LongformResult in
     * this list, or -1 if this list does not contain the LongformResult.
     *
     *
     * @return the index of the first occurrence of the specified LongformResult in
     *         this list, or -1 if this list does not contain the LongformResult
     **/
    @Override
    public int indexOf(Object o) {
        Integer index = 0;
        if (this.property.isSeriesProperty()) {
            index = episodeResults.indexOf(o);
        }
        else if(this.property.isFightProperty())
        {
            index = fightResults.indexOf(o);
        }
        else if(this.property.isMovieProperty())
        {
            index = movieResults.indexOf(o);
        }
        return index;
    }

    /**
     * Returns the index of the last occurrence of the specified LongformResult in
     * this list, or -1 if this list does not contain the LongformResult.
     *
     * @return the index of the last occurrence of the specified LongformResult in
     *         this list, or -1 if this list does not contain the LongformResult
     **/
    @Override
    public int lastIndexOf(Object o) {
        Integer index = 0;
        if (this.property.isSeriesProperty()) {
            index = episodeResults.lastIndexOf(o);
        }
        else if(this.property.isFightProperty())
        {
            index = fightResults.lastIndexOf(o);
        }
        else if(this.property.isMovieProperty())
        {
            index = movieResults.lastIndexOf(o);
        }
        return index;
    }

    /**
     * Returns a list iterator over the LongformResults in this list (in proper
     * sequence)
     *
     * @return a list iterator over the LongformResults in this list (in proper
     *         sequence)
     **/
    @Override
    public ListIterator<LongformResult> listIterator() {
        if (this.property.isSeriesProperty()) {
            return episodeResults.listIterator();
        }
        else if(this.property.isFightProperty())
        {
            return fightResults.listIterator();
        }
        else if(this.property.isMovieProperty())
        {
            return movieResults.listIterator();
        }
        return null;
    }

    /**
     * Returns a list iterator over the LongformResults in this list (in proper
     * sequence), starting at the specified position in the list. The specified
     * index indicates the first LongformResult that would be returned by an initial
     * call to next. An initial call to previous would return the LongformResult with
     * the specified index minus one.
     *
     * @return a list iterator over the LongformResults in this list (in proper
     *         sequence), starting at the specified position in the list. The
     *         specified index indicates the first LongformResult that would be
     *         returned by an initial call to next. An initial call to previous
     *         would return the LongformResult with the specified index minus one.
     **/
    @Override
    public ListIterator<LongformResult> listIterator(int index) {
        if (this.property.isSeriesProperty()) {
            return episodeResults.listIterator(index);
        }
        else if(this.property.isFightProperty())
        {
            return fightResults.listIterator(index);
        }
        else if(this.property.isMovieProperty())
        {
            return movieResults.listIterator(index);
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
    public List<LongformResult> subList(int fromIndex, int toIndex) {
        
        if (this.property.isSeriesProperty()) {
        	return episodeResults.subList(fromIndex, toIndex);	
    	} else if (this.property.isFightProperty()) {
            return fightResults.subList(fromIndex, toIndex);
        } else if(this.property.isMovieProperty()) {
            return movieResults.subList(fromIndex, toIndex);
        }
        return null;
    }

    /**
     * Returns an iterator for the episode result set
     *
     * @return iterator for episode result set
     **/
    @Override
    public Iterator<LongformResult> iterator() {
        if (this.property.isSeriesProperty()) {
        	return episodeResults.iterator();
    	} else if (this.property.isFightProperty()) {
            return fightResults.iterator();
        } else if(this.property.isMovieProperty()) {
            return movieResults.iterator();
        }
        return null;
    }
    
    /**
     * Orders the longformResults according to the field name in ascending or
     * descending order
     *
     * @param order
     *          - Get the order from the Order class
     * @param results
     *          - List of results per property type
     * @param fieldNames
     *          - Get the longform field names from the Order class
     * @return longformResults ordered according to the field name in ascending or
     *         descending order
     **/
    public LongformResults orderBy(String order, LongformResults results, String... fieldNames) {
        Collections.sort(results, new Comparator<LongformResult>() {
            @Override
            public int compare(LongformResult longform1, LongformResult longform2) {

                Object shortform1Value = longform1.get(fieldNames);
                Object longform2Value = longform2.get(fieldNames);

                if (shortform1Value instanceof Long) {
                    Long shortform1ValueAsLong = (Long) shortform1Value;
                    Long longform2ValueAsLong = (Long) longform2Value;
                    if (order.toUpperCase().equals("ASC")) {
                        return shortform1ValueAsLong.compareTo(longform2ValueAsLong);
                    } else if (order.toUpperCase().equals("DESC")) {
                        return longform2ValueAsLong.compareTo(shortform1ValueAsLong);
                    }
                } else if (shortform1Value instanceof String && isStringOfNumbers((String) shortform1Value)) {
                    Integer shortform1ValueAsInteger = Integer.parseInt((String) shortform1Value);
                    Integer longform2ValueAsInteger = Integer.parseInt((String) longform2Value);
                    if (order.toUpperCase().equals("ASC")) {
                        return shortform1ValueAsInteger.compareTo(longform2ValueAsInteger);
                    } else if (order.toUpperCase().equals("DESC")) {
                        return longform2ValueAsInteger.compareTo(shortform1ValueAsInteger);
                    }
                } else if (shortform1Value instanceof String) {
                    String shortform1ValueAsString = (String) shortform1Value;
                    String longform2ValueAsString = (String) longform2Value;
                    if (order.toUpperCase().equals("ASC")) {
                        return shortform1ValueAsString.compareTo(longform2ValueAsString);
                    } else if (order.toUpperCase().equals("DESC")) {
                        return longform2ValueAsString.compareTo(shortform1ValueAsString);
                    }
                }
                return 0;
            }

        });
        return this;
    }

    /***
     * Gets the raw json of the episode result set NOTE: NEVER return episodeJSON
     * since that is never altered even after all the filtering. What's filtered
     * is actually the array list which holds the episode results
     *
     * @return the raw json of the episode result set
     **/
    @SuppressWarnings("unchecked")
	public JSONArray toEpisodesJSONArray() {
        JSONArray jsonArray = new JSONArray();
        for (LongformResult episodeResult : episodeResults) {
            jsonArray.add(episodeResult.toJSON(this.episodeJSON));
        }
        return jsonArray;
    }

    /***
     * Gets the raw json of the episode result set NOTE: NEVER return episodeJSON
     * since that is never altered even after all the filtering. What's filtered
     * is actually the array list which holds the episode results
     *
     * @return the raw json of the episode result set
     **/
    @SuppressWarnings("unchecked")
	public JSONArray toFightsJSONArray() {
        JSONArray jsonArray = new JSONArray();
        for (LongformResult fightResult : fightResults) {
            jsonArray.add(fightResult.toJSON(this.fightJSON));
        }
        return jsonArray;
    }

    /***
     * Gets the raw json of the episode result set NOTE: NEVER return movieJSON
     * since that is never altered even after all the filtering. What's filtered
     * is actually the array list which holds the movie results
     *
     * @return the raw json of the movie result set
     **/
    @SuppressWarnings("unchecked")
	public JSONArray toMovieJSONArray() {
        JSONArray jsonArray = new JSONArray();
        for (LongformResult movieResult : movieResults) {
            jsonArray.add(movieResult.toJSON(this.movieJSON));
        }
        return jsonArray;
    }

    /**
     * Gets the EpisodesResults in string form
     *
     * @return the EpisodesResults in string form
     **/
    public String toString(LongformResults longformResults) {
        return longformResults.toString();
    }

}
