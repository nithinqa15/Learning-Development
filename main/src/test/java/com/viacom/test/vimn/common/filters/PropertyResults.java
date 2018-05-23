package com.viacom.test.vimn.common.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.exceptions.ResultException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PropertyResults implements Iterable<PropertyResult>, List<PropertyResult> {
    private List<PropertyResult> PropertyResults;
    private JSONObject propertyJSON;

    /**
     * Constructor
     * @param propertyJSON
     * @param numOfFeaturedProperty
     */
    public PropertyResults(JSONObject propertyJSON, Integer numOfFeaturedProperty) {
        this.propertyJSON = propertyJSON;
        PropertyResults = convertJSONToListOfPropertyResult(this.propertyJSON, numOfFeaturedProperty);
    }

    /**
     * Constructor for Pagination parameter based executions.
     * @param propertyJSON
     * @param numOfFeaturedProperty
     * @param pagination
     */
    public PropertyResults(JSONObject propertyJSON, Integer numOfFeaturedProperty, Pagination pagination) {
        this.propertyJSON = propertyJSON;
        PropertyResults = convertJSONToListOfPropertyResult(this.propertyJSON, numOfFeaturedProperty, pagination);
    }

    /**
     * Gets the property titles as a list
     *
     * @return the property title as a list
     **/
    public List<String> getPropertyTitles() {
        List<String> propertyTitles = new ArrayList<String>();
        for (PropertyResult propertyResult : PropertyResults) {
            propertyTitles.add(propertyResult.getPropertyTitle());
        }
        return propertyTitles;
    }

    /**
     * Gets the property ids as a list
     *
     * @return the property ids as a list
     **/
    public List<String> getPropertyIds() {
        List<String> propertyIds = new ArrayList<String>();
        for (PropertyResult propertyResult : PropertyResults) {
            propertyIds.add(propertyResult.getPropertyId());
        }
        return propertyIds;
    }

    /**
     * Gets the property mgids as a list
     *
     * @return the property mgids as a list
     **/
    public List<String> getPropertyMGIDs() {
        List<String> propertyMGIDs = new ArrayList<String>();
        for (PropertyResult propertyResult : PropertyResults) {
            propertyMGIDs.add(propertyResult.getPropertyMGID());
        }
        return propertyMGIDs;
    }

    /**
     * Gets the property urls as a list
     *
     * @return the property urls as a list
     **/
    public List<String> getPropertyURLs() {
        List<String> propertyURLs = new ArrayList<String>();
        for (PropertyResult propertyResult : PropertyResults) {
            propertyURLs.add(propertyResult.getPropertyURL());
        }
        return propertyURLs;
    }

    /**
     * Gets the property details as a list
     *
     * @return the property details as a list
     **/
    public List<String> getPropertyDetails() {
        return getPropertyDescriptions(); // Alias
    }

    /**
     * Gets the property description as a list
     *
     * @return the property description as a list
     **/
    public List<String> getPropertyDescriptions() {
        List<String> propertyDescriptions = new ArrayList<String>();
        for (PropertyResult propertyResult : PropertyResults) {
            propertyDescriptions.add(propertyResult.getPropertyDescription());
        }
        return propertyDescriptions;
    }

    /**
     * Gets a list of all the longform filters for each property
     *
     * @return the list of all the episodes filters for each property
     **/
    public List<LongformFilter> getLongformFilters() {
        List<LongformFilter> longformFilters = new ArrayList<LongformFilter>();
        for (PropertyResult propertyResult : PropertyResults) {
            longformFilters.add(propertyResult.getLongformFilter());
        }
        return longformFilters;
    }

    /**
     * Gets a list of all the episodes filters for each property
     *
     * @return the list of all the episodes filters for each property
     **/
    public List<ShortformFilter> getShortformFilters() {
        List<ShortformFilter> shortformFilters = new ArrayList<ShortformFilter>();
        for (PropertyResult propertyResult : PropertyResults) {
            shortformFilters.add(propertyResult.getShortformFilter());
        }
        return shortformFilters;
    }

    /**
     * Gets the raw json of the property result set NOTE: NEVER return propertyJSON
     * since that is never altered even after all the filtering. What's filtered
     * is actually the array list which holds the property results
     *
     * @return the raw json of the property result set
     **/
    @SuppressWarnings("unchecked")
	public JSONArray toJSONArray() {
        JSONArray jsonArray = new JSONArray();
        for (PropertyResult propertyResult : PropertyResults) {
            jsonArray.add(propertyResult.toJSON());
        }
        return jsonArray;
    }

    /**
     * Gets the PropertyResults in string form
     *
     * @return the PropertyResults in string form
     **/
    public String toString() {
        return PropertyResults.toString();
    }

    /**
     * Returns an iterator for the property result set
     *
     * @return iterator for property result set
     **/
    @Override
    public Iterator<PropertyResult> iterator() {
        return PropertyResults.iterator();
    }

    /**
     * Orders the PropertyResults according to the field name in ascending or
     * descending order (Might want to deprecate this)
     *
     * @param order
     *          - Get the order from the Order class
     * @param fieldNames
     *          - Get the episode field names from the Order class
     * @return PropertyResults ordered according to the field name in ascending or
     *         descending order
     **/
    public PropertyResults orderBy(String order, String... fieldNames) {
        Collections.sort(PropertyResults, new Comparator<PropertyResult>() {

            @Override
            public int compare(PropertyResult property1, PropertyResult property2) {
                Object property1Value = property1.get(fieldNames);
                Object property2Value = property2.get(fieldNames);

                if (property1Value instanceof Long) {
                    Long property1ValueAsLong = (Long) property1Value;
                    Long property2ValueAsLong = (Long) property2Value;
                    if (order.toUpperCase().equals("ASC")) {
                        return property1ValueAsLong.compareTo(property2ValueAsLong);
                    } else if (order.toUpperCase().equals("DESC")) {
                        return property2ValueAsLong.compareTo(property1ValueAsLong);
                    }
                } else if (property1Value instanceof String && isStringOfNumbers((String) property1Value)) {
                    Integer property1ValueAsInteger = Integer.parseInt((String) property1Value);
                    Integer property2ValueAsInteger = Integer.parseInt((String) property2Value);
                    if (order.toUpperCase().equals("ASC")) {
                        return property1ValueAsInteger.compareTo(property2ValueAsInteger);
                    } else if (order.toUpperCase().equals("DESC")) {
                        return property2ValueAsInteger.compareTo(property1ValueAsInteger);
                    }
                } else if (property1Value instanceof String) {
                    String property1ValueAsString = (String) property1Value;
                    String property2ValueAsString = (String) property2Value;
                    if (order.toUpperCase().equals("ASC")) {
                        return property1ValueAsString.compareTo(property2ValueAsString);
                    } else if (order.toUpperCase().equals("DESC")) {
                        return property2ValueAsString.compareTo(property1ValueAsString);
                    }
                }
                return 0;
            }

        });
        return this;
    }

    /**
     * Converts the property json to an array list of property result
     *
     * @param propertyJSON
     *          the json response from the property items url
     * @param numOfFeaturedProperty
     *          - determines how many items are in the featured property list or 0
     *          if the url is the featured property list
     * @return a list of the property result
     **/
    private List<PropertyResult> convertJSONToListOfPropertyResult(JSONObject propertyJSON, Integer numOfFeaturedProperty) {
        List<PropertyResult> PropertyResults = new ArrayList<PropertyResult>();
        int positionInCarousel = 0;
        try {   // some property content contains empty an data object and is throwing null point exceptions
            JSONArray propertyItems = (JSONArray) ((JSONObject) propertyJSON.get("data")).get("items");
            while (positionInCarousel < propertyItems.size()) {
                PropertyResults.add(new PropertyResult(propertyJSON, positionInCarousel + numOfFeaturedProperty, numOfFeaturedProperty));
                positionInCarousel++;
            }
        }
        catch (NullPointerException e) {
            Logger.logMessage(getPropertyTitles().get(positionInCarousel) + " is missing data!");
        }
        return PropertyResults;
    }

    /**
     * Overload method that accepts Pagination argument. Returns list of property result, with results containing pagination
     * values based on pagination value.
     * @param propertyJSON
     * @param numOfFeaturedProperty
     * @param pagination
     *
     * @return a list of the property result
     */
    private List<PropertyResult> convertJSONToListOfPropertyResult(JSONObject propertyJSON, Integer numOfFeaturedProperty, Pagination pagination) {
        List<PropertyResult> PropertyResults = new ArrayList<>();
        int positionInCarousel = 0;
        try {   // some property content contains empty an data object and is throwing null point exceptions
            JSONArray propertyItems = (JSONArray) ((JSONObject) propertyJSON.get("data")).get("items");
            while (positionInCarousel < propertyItems.size()) {
                PropertyResults.add(new PropertyResult(propertyJSON, positionInCarousel + numOfFeaturedProperty, numOfFeaturedProperty, pagination));
                positionInCarousel++;
            }
        } catch (NullPointerException e) {
            Logger.logMessage(getPropertyTitles().get(positionInCarousel) + " is missing data!");
        }
        return PropertyResults;
    }

    /**
     * Gets the number of property in the resulting query
     *
     * @return the number of property in the resulting query
     **/
    @Override
    public int size() {
        return PropertyResults.size();
    }

    /**
     * Checks to see if the Property Results is empty
     *
     * @return true if the Property Results is empty, false otherwise
     **/
    @Override
    public boolean isEmpty() {
        return PropertyResults.isEmpty();
    }

    /**
     * Checks to see if the PropertyResults contains an PropertyResult
     *
     * @return true if the PropertyResults contains an PropertyResult, false otherwise
     **/
    @Override
    public boolean contains(Object o) {
        return PropertyResults.contains(o);
    }

    /**
     * Returns an Array of PropertyResults
     *
     * @return an Array of PropertyResults
     **/
    @Override
    public Object[] toArray() {
        return PropertyResults.toArray();
    }

    /**
     * Returns an Array of PropertyResults
     *
     * @return an Array of PropertyResults
     **/
    @Override
    public <T> T[] toArray(T[] a) {
        return PropertyResults.toArray(a);
    }

    /**
     * Adds an PropertyResult object to the end of the PropertyResults list returns
     * false if operation fails
     *
     * @return true if you can add an PropertyResult object to the PropertyResults,
     *         false otherwise
     **/
    @Override
    public boolean add(PropertyResult e) {
        return PropertyResults.add(e);
    }

    /**
     * Removes an PropertyResult from the PropertyResults, false if PropertyResult
     * doesn't exist in PropertyResults
     *
     * @return true if PropertyResult exists in PropertyResults and can be removed,
     *         false otherwise
     **/
    @Override
    public boolean remove(Object o) {
        return PropertyResults.remove(o);
    }

    /**
     * Checks to see if the PropertyResults contains the collection specified by c
     * which should be a list of PropertyResults or a collection of PropertyResults
     *
     * @return true if the PropertyResults contains the collection specified by c,
     *         false otherwise
     **/
    @Override
    public boolean containsAll(Collection<?> c) {
        return PropertyResults.containsAll(c);
    }

    /**
     * Adds all the elements in the collection specified by c which should be a
     * list of PropertyResults or a collection of PropertyResults returns false if
     * addAll fails
     *
     * @return true if you can add all the PropertyResults in collection c to the
     *         PropertyResults, false otherwise
     **/
    @Override
    public boolean addAll(Collection<? extends PropertyResult> c) {
        return PropertyResults.addAll(c);
    }

    /**
     * Adds all the elements in the collection specified by c which should be a
     * list of PropertyResults or a collection of PropertyResults returns false if
     * addAll fails
     *
     * @return true if you can add all the PropertyResults in collection c to the
     *         PropertyResults, false otherwise
     **/
    @Override
    public boolean addAll(int index, Collection<? extends PropertyResult> c) {
        return PropertyResults.addAll(index, c);
    }

    /**
     * Remove from PropertyResults the collection of PropertyResult specified by c
     *
     * @return true if you can remove the collection of PropertyResult specified by
     *         c from PropertyResults, false otherwise
     **/
    @Override
    public boolean removeAll(Collection<?> c) {
        return PropertyResults.removeAll(c);
    }

    /**
     * Retains only the PropertyResult in the collection specified by c
     *
     * @return true if retains only the PropertyResult in the collection specified
     *         by c, false otherwise
     **/
    @Override
    public boolean retainAll(Collection<?> c) {
        return PropertyResults.retainAll(c);
    }

    /**
     * Removes all the elements of PropertyResults
     *
     * @author Edward Poon
     * @version 1.0.0 August 25 2017
     * @return true if all the PropertyResults are removed, false otherwise
     **/
    @Override
    public void clear() {
        PropertyResults.clear();
    }

    /**
     * Returns the PropertyResult from the PropertyResults list specified in the index
     *
     * @return the PropertyResult from the PropertyResults list specified in the index
     **/
    @Override
    public PropertyResult get(int index) {
        return PropertyResults.get(index);
    }

    /**
     * Wrapper method to return the first PropertyResult from PropertyResults list
     *
     * @return first PropertyResult from the PropertyResults
     */
    public PropertyResult getFirstProperty() {
        try {
            return PropertyResults.get(0);
        } catch (IndexOutOfBoundsException e) {
            String message = "No Property with the required filters was found in the feeds.";
            Logger.logMessage(message);
            throw new ResultException(message, e);
        }
    }

    /**
     * Wrapper method to return the last PropertyResult from PropertyResults list
     *
     * @return last PropertyResult from the PropertyResults
     */
    public PropertyResult getLastProperty() {
        return PropertyResults.get(PropertyResults.size() - 1);
    }

    /**
     * Replaces the PropertyResult at the specified position in the PropertyResults
     * with PropertyResult specified by element
     *
     * @return the PropertyResult previously at the specified position
     **/
    @Override
    public PropertyResult set(int index, PropertyResult element) {
        return PropertyResults.set(index, element);
    }

    /**
     * Inserts the specified PropertyResult at the specified position in this list.
     * Shifts the PropertyResults currently at that position (if any) and any
     * subsequent elements to the right (adds one to their indices)
     *
     * @return
     **/
    @Override
    public void add(int index, PropertyResult element) {
        PropertyResults.add(index, element);
    }

    /**
     * Removes the PropertyResult at the specified position in this list (optional
     * operation). Shifts any subsequent PropertyResults to the left (subtracts one
     * from their indices). Returns the PropertyResult that was removed from the
     * list.
     *
     * @return the PropertyResult that was removed from the list.
     **/
    @Override
    public PropertyResult remove(int index) {
        return PropertyResults.remove(index);
    }

    /**
     * Returns the index of the first occurrence of the specified PropertyResult in
     * this list, or -1 if this list does not contain the PropertyResult.
     *
     *
     * @return the index of the first occurrence of the specified PropertyResult in
     *         this list, or -1 if this list does not contain the PropertyResult
     **/
    @Override
    public int indexOf(Object o) {
        return PropertyResults.indexOf(o);
    }

    /**
     * Returns the index of the last occurrence of the specified PropertyResult in
     * this list, or -1 if this list does not contain the PropertyResult.
     *
     * @return the index of the last occurrence of the specified PropertyResult in
     *         this list, or -1 if this list does not contain the PropertyResult
     **/
    @Override
    public int lastIndexOf(Object o) {
        return PropertyResults.lastIndexOf(o);
    }

    /**
     * Returns a list iterator over the PropertyResults in this list (in proper
     * sequence)
     *
     * @return a list iterator over the PropertysResults in this list (in proper
     *         sequence)
     **/
    @Override
    public ListIterator<PropertyResult> listIterator() {
        return PropertyResults.listIterator();
    }

    /**
     * Returns a list iterator over the PropertyResults in this list (in proper
     * sequence), starting at the specified position in the list. The specified
     * index indicates the first PropertyResult that would be returned by an initial
     * call to next. An initial call to previous would return the PropertyResult
     * with the specified index minus one.
     *
     * @return a list iterator over the PropertyResults in this list (in proper
     *         sequence), starting at the specified position in the list. The
     *         specified index indicates the first PropertyResult that would be
     *         returned by an initial call to next. An initial call to previous
     *         would return the PropertyResult with the specified index minus one.
     **/
    @Override
    public ListIterator<PropertyResult> listIterator(int index) {
        return PropertyResults.listIterator(index);
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
    public List<PropertyResult> subList(int fromIndex, int toIndex) {
        return PropertyResults.subList(fromIndex, toIndex);
    }

    /**
     * Checks to see if a string is composed primarily of numbers
     *
     * @return true if the string is composed primarily of numbers, false
     *         otherwise
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
     * Gets properties with series
     *
     * @return returns properties with series
     **/
    public PropertyResults withSeries() {
        PropertyResults.removeIf(propertyResult -> !propertyResult.isSeriesProperty());
        return this;
    }

    /**
     * Gets properties with seasons
     *
     * @return returns properties with seasons
     **/
    public PropertyResults withSeason() {
        PropertyResults.removeIf(propertyResult -> !propertyResult.isSeasonProperty());
        return this;
    }

    /**
     * Gets properties with videos
     *
     * @return returns properties with videos
     **/
    public PropertyResults withVideo() {
        PropertyResults.removeIf(propertyResult -> !propertyResult.isVideoProperty());
        return this;
    }

    /**
     * Gets properties with episodes
     *
     * @return returns properties with episdoes
     **/
    public PropertyResults withEpisodes() {
        PropertyResults.removeIf(propertyResult -> !propertyResult.isEpisodeProperty());
        return this;
    }

    /**
     * Gets properties with movies
     *
     * @return returns properties with movies
     **/
    public PropertyResults withMovie() {
        PropertyResults.removeIf(propertyResult -> !propertyResult.isMovieProperty());
        return this;
    }

    /**
     * Gets properties with fights
     *
     * @return returns properties with fights
     **/
    public PropertyResults withFight() {
        PropertyResults.removeIf(propertyResult -> !propertyResult.isFightProperty());
        return this;
    }

    /**
     * Gets properties with music videos
     *
     * @return returns properties with music videos
     **/
    public PropertyResults withMusicVideo() {
        PropertyResults.removeIf(propertyResult -> !propertyResult.isCollectionProperty());
        return this;
    }

    /**
     * Gets properties with standups
     *
     * @return returns properties with standup
     **/
    public PropertyResults withStandup() {
        PropertyResults.removeIf(propertyResult -> !propertyResult.isStandUpProperty());
        return this;
    }
}
