package com.viacom.test.vimn.uitests.support;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.viacom.test.core.util.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import com.viacom.test.vimn.common.proxy.ProxyLogUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;

public class AppDataFeed {

    public AppDataFeed() {

    }

	private JSONObject getJSONFeed(String url) {
		
		trustAllHosts();
		JSONObject object = null;
		if (url.contains("http")) {
			try {
				URL jsonURL = new URL(url);
				HttpURLConnection httprequest = (HttpURLConnection) jsonURL.openConnection();
				httprequest.setRequestMethod("GET");
				httprequest.connect();
				InputStream input = httprequest.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}
				input.close();

				String responseBody = sb.toString();
				JSONParser parser = new JSONParser();
				object = (JSONObject) parser.parse(responseBody);
			} catch (Exception e) {
				Assert.fail("Failed to parse json response.", e);
			}
		} else {
			try {
				URL jsonURL = new URL(url);
				HttpsURLConnection httpsrequest = (HttpsURLConnection) jsonURL.openConnection();
				httpsrequest.setRequestMethod("GET");
				httpsrequest.connect();
				InputStream input = httpsrequest.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}
				input.close();

				String responseBody = sb.toString();
				JSONParser parser = new JSONParser();
				object = (JSONObject) parser.parse(responseBody);
			} catch (Exception e) {
				Assert.fail("Failed to parse json response.", e);
			}
		}

		return object;
	}

    /**********************************************************************************************
     * Gets a list of all the show titles from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Brandon Clark created November 15, 2015
     * @version 1.0.0 November 15, 2015
     * @return List of show titles.
     ***********************************************************************************************/
    public List<String> getPropertyTitles(String url) {
        List<String> showTitles = new ArrayList<String>();

        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject showObject = iterator.next();
            showTitles.add((String) showObject.get("title"));
        }
        return showTitles;
    }

    /**********************************************************************************************
     * Gets specific series data from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @param SHOW TITLE - {@link String} - The series title.
     * @author Brandon Clark created November 15, 2015
     * @version 1.0.0 November 15, 2015
     * @version 2.0.0 October 31, 2016
     * @return HashMap<String, String - A series Title, ID, and Description.
     ***********************************************************************************************/
    public HashMap<String, String> getSeriesDetails(String url, String showTitle) {
        HashMap<String, String> showData = new HashMap<String, String>();

        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject showObj = iterator.next();
            if (showObj.get("title").toString().equals(showTitle)) {
                String replaceString = "";
                if (TestRun.isMTVINTLApp()) {
                    if (TestRun.getLocale().equals("pt_br")) {
                        replaceString = "mgid:arc:series:mtv.com.br:";
                    } else if (TestRun.getLocale().equals("en_sg")) {
                        replaceString = "mgid:arc:series:mtvasia.com:";
                    } else {
                        replaceString = "mgid:arc:series:mtvla.com:";
                    }
                } else if (TestRun.isTVLandApp()) {
                    replaceString = "mgid:arc:series:tvland.com:";
                } else if (TestRun.isCCINTLApp()) {
                    if (TestRun.getLocale().equals("pt_br")) {
                        replaceString = "mgid:arc:series:comedycentral.com.br:";
                    } else if (TestRun.getLocale().equals("en_sg")) {
                        replaceString = "mgid:arc:series:comedycentralasia.com:";
                    } else {
                        replaceString = "mgid:arc:series:comedycentral.la:";
                    }
                } else if (TestRun.isBETINTLApp()) {
                    replaceString = "mgid:arc:series:bet.intl:";
                } else if (TestRun.isCMTApp()) {
                    replaceString = "mgid:arc:series:cmt.com:";
                } else if (TestRun.isVH1App()) {
                    replaceString = "mgid:arc:series:vh1play.com:";
                }
                showData.put("SeriesID", showObj.get("id").toString().replace(replaceString, ""));
                showData.put("Description", showObj.get("description").toString());
                break;
            }

        }
        return showData;
    }

    /**********************************************************************************************
     * Gets all series ID's from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @param SHOW TITLE - {@link String} - The series title.
     * @author Brandon Clark created November 15, 2015
     * @version 1.0.0 November 15, 2015
     * @version 2.0.0 October 31, 2016
     * @version 3.0.0 November 16, 2017
     * @return List<String> - A series Title, ID, and Description.
     ***********************************************************************************************/
    public List<String> getSeriesIds(String url) {
        List<String> seriesIDs = new ArrayList<String>();

        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject showObj = iterator.next();
            String seriesID = showObj.get("id").toString();
            seriesIDs.add(seriesID.split(":")[4].trim());
        }
        return seriesIDs;
    }

    /**********************************************************************************************
     * Gets a list of all the episode titles for a series from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Brandon Clark created November 15, 2015
     * @version 1.0.0 November 15, 2015
     * @return List of episode titles.
     ***********************************************************************************************/
    public List<String> getEpisodeTitles(String url) {
        List<String> episodeTitles = new ArrayList<String>();

        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            episodeTitles.add((String) episodeObject.get("title"));
        }
        return episodeTitles;
    }

    /**********************************************************************************************
     * Gets a list of all the clip titles for a series from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi created April 25, 2015
     * @version 1.0.0 April 25, 2015
     * @return List of episode titles.
     ***********************************************************************************************/
    public List<String> getClipTitles(String url) {
        List<String> clipTitles = new ArrayList<String>();
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        if (!itemsArr.isEmpty()) {
            Iterator<JSONObject> iterator = itemsArr.iterator();
            while (iterator.hasNext()) {
                JSONObject clipObject = iterator.next();
                if (clipObject.get("type").toString().equals("clip")) {
                    clipTitles.add((String) clipObject.get("title"));
                }
            }
        } else {
            JSONObject contextObj = (JSONObject) json.get("context");
            JSONObject reportingObj = (JSONObject) contextObj.get("reporting");
            String seriesName = reportingObj.get("pageName").toString().split("/")[2];
            Logger.logMessage(seriesName + " has no short form.");
        }
        return clipTitles;
    }

    /**********************************************************************************************
     * Gets a list of all the episode titles for a series from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @param Season Number - {@link Long} - The desired season number
     * @author Brandon Clark created November 15, 2015
     * @version 1.0.0 November 15, 2015
     * @return List of episode titles.
     ***********************************************************************************************/
    public List<String> getEpisodeTitlesBySeason(String url, Integer seasonNum) {
        List<String> episodeTitles = new ArrayList<String>();

        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            Long seasonNumber = (Long) episodeObject.get("seasonNumber");
            if (seasonNumber.intValue() == seasonNum) {
                episodeTitles.add((String) episodeObject.get("title"));
            }
        }
        return episodeTitles;
    }

    /**********************************************************************************************
     * Gets a list of all the episode titles for a series sorted by episode number from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi created April 5, 2016
     * @version 1.0.0 April 5, 2016
     * @return List of episode titles.
     ***********************************************************************************************/
    public List<String> getSortedEpisodeTitlesbyEpisodeNumberInAscendingOrder(String url) {
        List<String> episodeTitles = new ArrayList<String>();

        HashMap<String, String> allTitles = new HashMap<>();
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            allTitles.put((String) episodeObject.get("episodeNumber"), (String) episodeObject.get("title"));
        }
        List<Integer> episodeNumbers = new ArrayList<>();
        for (String key : allTitles.keySet()) {
            episodeNumbers.add(Integer.parseInt(key));
        }
        Collections.sort(episodeNumbers);
        HashMap<Integer, String> sortedMap = new HashMap<>();
        for (Integer i = 0; i <= episodeNumbers.size() - 1; i++) {
            sortedMap.put(episodeNumbers.get(i), allTitles.get(episodeNumbers.get(i).toString()));
        }
        episodeTitles.addAll(sortedMap.values());
        return episodeTitles;
    }

    /**********************************************************************************************
     * Overload method taking seasonNumber as parameter.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Gabriel Fioretti created July 14, 2017
     * @version 1.0.0 July 14, 2017
     * @return List of episode titles.
     ***********************************************************************************************/
    public List<String> getSortedEpisodeTitlesbyEpisodeNumberInAscendingOrder(String url, Long seasonNumber) {
        List<String> episodeTitles = new ArrayList<String>();

        HashMap<String, String> allTitles = new HashMap<>();
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("seasonNumber").equals(seasonNumber)) {
                allTitles.put((String) episodeObject.get("episodeNumber"), (String) episodeObject.get("title"));
            }
        }
        List<Integer> episodeNumbers = new ArrayList<>();
        for (String key : allTitles.keySet()) {
            episodeNumbers.add(Integer.parseInt(key));
        }
        Collections.sort(episodeNumbers);
        HashMap<Integer, String> sortedMap = new HashMap<>();
        for (Integer i = 0; i <= episodeNumbers.size() - 1; i++) {
            sortedMap.put(episodeNumbers.get(i), allTitles.get(episodeNumbers.get(i).toString()));
        }
        episodeTitles.addAll(sortedMap.values());
        return episodeTitles;
    }

    /**********************************************************************************************
     * Gets a list of all the episode titles for a series sorted by episode number in descending
     * order from the series data feed.
     *
     * @param URL - {@link String} - The series feed url to query.
     * @author Gabriel Fioretti created July 14, 2017
     * @version 1.0.0 July 14, 2017
     * @return List of episode titles.
     ***********************************************************************************************/
    public List<String> getSortedEpisodeTitlesbyEpisodeNumberInDescendingOrder(String url, Long seasonNumber) {
        List<String> episodeTitles = new ArrayList<String>();

        HashMap<String, String> allTitles = new HashMap<>();
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("seasonNumber").equals(seasonNumber)) {
                allTitles.put((String) episodeObject.get("episodeNumber"), (String) episodeObject.get("title"));
            }
        }
        List<Integer> episodeNumbers = new ArrayList<>();
        for (String key : allTitles.keySet()) {
            episodeNumbers.add(Integer.parseInt(key));
        }
        Collections.sort(episodeNumbers);
        TreeMap<Integer, String> sortedMap = new TreeMap<>();
        for (Integer i = 0; i <= episodeNumbers.size() - 1; i++) {
            sortedMap.put(episodeNumbers.get(i), allTitles.get(episodeNumbers.get(i).toString()));
        }
        episodeTitles.addAll(sortedMap.descendingMap().values());

        return episodeTitles;
    }

    /**********************************************************************************************
     * Gets a list of all the episode titles for a series sorted by original air date in descending
     * order from the series data feed.
     *
     * @param URL - {@link String} - The series feed url to query.
     * @author Gabriel Fioretti created July 14, 2017
     * @version 1.0.0 July 14, 2017
     * @return List of episode titles.
     ***********************************************************************************************/
    public List<String> getSortedEpisodeTitlesByOriginalAirDateInDescendingOrder(String url, Long seasonNumber) {
        List<String> episodeTitles = new ArrayList<String>();

        HashMap<String, String> allTitles = new HashMap<>();
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("seasonNumber").equals(seasonNumber)) {
                JSONObject originalAirDateObj = (JSONObject) episodeObject.get("originalAirDate");
                allTitles.put(String.valueOf((Long) originalAirDateObj.get("timestamp")), (String) episodeObject.get("title"));
            }
        }
        List<Long> episodeNumbers = new ArrayList<>();
        for (String key : allTitles.keySet()) {
            episodeNumbers.add(Long.parseLong(key));
        }
        Collections.sort(episodeNumbers);
        TreeMap<Long, String> sortedMap = new TreeMap<>();
        for (Integer i = 0; i <= episodeNumbers.size() - 1; i++) {
            sortedMap.put(episodeNumbers.get(i), allTitles.get(episodeNumbers.get(i).toString()));
        }
        episodeTitles.addAll(sortedMap.descendingMap().values());

        return episodeTitles;
    }

    /**********************************************************************************************
     * Gets a list of all the episode titles for a series sorted by original air date in ascending
     * order from the series data feed.
     *
     * @param URL - {@link String} - The series feed url to query.
     * @author Gabriel Fioretti created July 14, 2017
     * @version 1.0.0 July 14, 2017
     * @return List of episode titles.
     ***********************************************************************************************/
    public List<String> getSortedEpisodeTitlesByOriginalAirDateInAscendingOrder(String url, Long seasonNumber) {
        List<String> episodeTitles = new ArrayList<String>();

        HashMap<String, String> allTitles = new HashMap<>();
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("seasonNumber").equals(seasonNumber)) {
                JSONObject originalAirDateObj = (JSONObject) episodeObject.get("originalAirDate");
                allTitles.put(String.valueOf((Long) originalAirDateObj.get("timestamp")), (String) episodeObject.get("title"));
            }
        }
        List<Long> episodeNumbers = new ArrayList<>();
        for (String key : allTitles.keySet()) {
            episodeNumbers.add(Long.parseLong(key));
        }
        Collections.sort(episodeNumbers);
        TreeMap<Long, String> sortedMap = new TreeMap<>();
        for (Integer i = 0; i <= episodeNumbers.size() - 1; i++) {
            sortedMap.put(episodeNumbers.get(i), allTitles.get(episodeNumbers.get(i).toString()));
        }
        episodeTitles.addAll(sortedMap.values());

        return episodeTitles;
    }

    /**********************************************************************************************
     * Gets a list of all the episode titles for a series sorted by original publish date in
     * descending order from the series data feed.
     *
     * @param URL - {@link String} - The series feed url to query.
     * @author Gabriel Fioretti created July 14, 2017
     * @version 1.0.0 July 14, 2017
     * @return List of episode titles.
     ***********************************************************************************************/
    public List<String> getSortedEpisodeTitlesByOriginalPublishDateInDescendingOrder(String url, Long seasonNumber) {
        List<String> episodeTitles = new ArrayList<String>();

        HashMap<String, String> allTitles = new HashMap<>();
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("seasonNumber").equals(seasonNumber)) {
                JSONObject originalAirDateObj = (JSONObject) episodeObject.get("originalPublishDate");
                allTitles.put(String.valueOf((Long) originalAirDateObj.get("timestamp")), (String) episodeObject.get("title"));
            }
        }
        List<Long> episodeNumbers = new ArrayList<>();
        for (String key : allTitles.keySet()) {
            episodeNumbers.add(Long.parseLong(key));
        }
        Collections.sort(episodeNumbers);
        TreeMap<Long, String> sortedMap = new TreeMap<>();
        for (Integer i = 0; i <= episodeNumbers.size() - 1; i++) {
            sortedMap.put(episodeNumbers.get(i), allTitles.get(episodeNumbers.get(i).toString()));
        }
        episodeTitles.addAll(sortedMap.descendingMap().values());

        return episodeTitles;
    }

    /**********************************************************************************************
     * Gets a list of all the episode titles for a series sorted by original publish date in
     * ascending order from the series data feed.
     *
     * @param URL - {@link String} - The series feed url to query.
     * @author Gabriel Fioretti created July 14, 2017
     * @version 1.0.0 July 14, 2017
     * @return List of episode titles.
     ***********************************************************************************************/
    public List<String> getSortedEpisodeTitlesByOriginalPublishDateInAscendingOrder(String url, Long seasonNumber) {
        List<String> episodeTitles = new ArrayList<String>();

        HashMap<String, String> allTitles = new HashMap<>();
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("seasonNumber").equals(seasonNumber)) {
                JSONObject originalAirDateObj = (JSONObject) episodeObject.get("originalPublishDate");
                allTitles.put(String.valueOf((Long) originalAirDateObj.get("timestamp")), (String) episodeObject.get("title"));
            }
        }
        List<Long> episodeNumbers = new ArrayList<>();
        for (String key : allTitles.keySet()) {
            episodeNumbers.add(Long.parseLong(key));
        }
        Collections.sort(episodeNumbers);
        TreeMap<Long, String> sortedMap = new TreeMap<>();
        for (Integer i = 0; i <= episodeNumbers.size() - 1; i++) {
            sortedMap.put(episodeNumbers.get(i), allTitles.get(episodeNumbers.get(i).toString()));
        }
        episodeTitles.addAll(sortedMap.values());

        return episodeTitles;
    }

    /**********************************************************************************************
     * Gets a the current season of a series from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @param Season Number - {@link Long} - The desired season number
     * @author Brandon Clark created November 15, 2015 
     * @version 1.0.0 November 15, 2015
     * @return List of episode titles.
     ***********************************************************************************************/
    public Integer getCurrentSeriesSeason(String url) {
        List<Long> episodeSeasons = new ArrayList<Long>();

        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            episodeSeasons.add((Long) episodeObject.get("seasonNumber"));
        }
        Long max = Collections.max(episodeSeasons);
        return max.intValue();
    }

    /**********************************************************************************************
     * Determines if a series has a minimum episode count from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Brandon Clark created November 15, 2015 
     * @version 1.0.0 November 15, 2015
     * @return Boolean.
     ***********************************************************************************************/
    public Boolean hasMinEpisodeCount(String url, Integer minCount) {
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        if (itemsArr.size() >= minCount) {
            return true;
        }
        return false;
    }

    /**********************************************************************************************
     * Determines the total number of episodes for a season from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi, created March 20, 2016
     * @version 1.0.0 March 20, 2016
     * @return  episode count
     ***********************************************************************************************/
    public Integer getEpisodeCount(String url) {
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Integer episodeCount = itemsArr.size();
        return episodeCount;
    }

    /**********************************************************************************************
     * Gets the most recent publicly available episode for a series from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Brandon Clark created November 15, 2015 
     * @version 1.0.0 November 15, 2015
     * @return episode title.
     ***********************************************************************************************/
    public String getFirstPublicEpisodeTitle(String url) {
        String episode = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("authRequired").toString().equals("false")) {
                episode = (String) episodeObject.get("title");
                break;
            }
        }
        return episode;
    }

    /**********************************************************************************************
     * Gets the most recent publicly available clip for a series from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi created April 25, 2015
     * @version 1.0.0 April 25, 2015
     * @return episode title.
     ***********************************************************************************************/
    public String getFirstPublicClipTitle(String url) {
        String clip = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject clipObject = iterator.next();
            if (clipObject.get("type").toString().equals("clip")) {
                clip = (String) clipObject.get("title");
                break;
            }
        }
        return clip;
    }

    /**********************************************************************************************
     * Gets the second recent publicly available episode for a series from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi, created March 20, 2016
     * @version 1.0.0  March 20, 2016
     * @return String episode title.
     ***********************************************************************************************/
    public String getSecondPublicEpisodeTitle(String url) {
        String episode = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        ArrayList<String> episodeTitles = new ArrayList<>();
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("authRequired").toString().equals("false")) {
                episode = (String) episodeObject.get("title");
                episodeTitles.add(episode);
                if (episodeTitles.size() == 2) {
                    break;
                }
            }
        }
        return episode;
    }


    /**********************************************************************************************
     * Gets the episode's parent series from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Brandon Clark created December 15, 2015 
     * @version 1.0.0 December 15, 2015
     * @return series title.
     ***********************************************************************************************/
    public String getEpisodesParentSeriesTitle(String url) {
        String seriesTitle = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        if (!itemsArr.isEmpty()) {
            JSONObject episodeObj = (JSONObject) itemsArr.get(0);
            JSONObject parentEntityObj = (JSONObject) episodeObj.get("parentEntity");
            if (parentEntityObj.get("entityType").toString().equals("series")) {
                seriesTitle = parentEntityObj.get("title").toString();
            }
        } else {
            JSONArray seasonsArr = (JSONArray) dataObj.get("seasons");
            if (seasonsArr != null && !seasonsArr.isEmpty()) {
                Iterator<JSONObject> iterator = seasonsArr.iterator();
                while (iterator.hasNext()) {
                    JSONObject seriesObj = iterator.next();
                    JSONObject parentEntityObj = (JSONObject) seriesObj.get("parentEntity");
                    seriesTitle = parentEntityObj.get("title").toString();
                    Logger.logMessage(seriesTitle + " has no episodes in Items. Maybe a content issue?");
                    break;
                }
            }
        }
        return seriesTitle;
    }

    /**********************************************************************************************
     * Gets the most recent privately available episode for a series from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Brandon Clark created November 15, 2015 
     * @version 1.0.0 November 15, 2015
     * @return episode title.
     ***********************************************************************************************/
    public String getFirstPrivateEpisodeTitle(String url) {
        String episode = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("authRequired").toString().equals("true")) {
                episode = (String) episodeObject.get("title");
                break;
            }
        }
        return episode;
    }

    /**********************************************************************************************
     * Gets the second privately available episode for a series from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi, created March 20, 2016
     * @version 1.0.0  March 20, 2016
     * @return String episode title.
     ***********************************************************************************************/
    public String getSecondPrivateEpisodeTitle(String url) {
        String episode = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        ArrayList<String> episodeTitles = new ArrayList<>();
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("authRequired").toString().equals("true")) {
                episode = (String) episodeObject.get("title");
                episodeTitles.add(episode);
                if (episodeTitles.size() == 2) {
                    break;
                }
            }
        }
        return episode;
    }


    /**********************************************************************************************
     * Gets the Series Description from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi created January 30, 2017
     * @version 1.0.0 January 30, 2017
     * @return series description.
     ***********************************************************************************************/
    public String getSeriesDescription(String url, String title) {
        String description = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject seriesObject = iterator.next();
            if (seriesObject.get("title").toString().equals(title)) {
                description = (String) seriesObject.get("description");
                break;
            }
        }
        return description;
    }


    /**********************************************************************************************
     * Gets the Episode Description from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Brandon Clark created November 15, 2015 
     * @version 1.0.0 November 15, 2015
     * @return episode description.
     ***********************************************************************************************/
    public String getEpisodeDescription(String url, String title) {
        String description = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("title").toString().equals(title)) {
                description = (String) episodeObject.get("description");
                break;
            }
        }
        return description;
    }

    /**********************************************************************************************
     * Gets the Clip Description from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi created April 25, 2015
     * @version 1.0.0 April 25, 2015
     * @return clip description.
     ***********************************************************************************************/
    public String getClipDescription(String url, String title) {
        String description = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject clipObject = iterator.next();
            if (clipObject.get("title").toString().equals(title)) {
                description = (String) clipObject.get("description");
                break;
            }
        }
        return description;
    }

    /**********************************************************************************************
     * Gets the Episode Season from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Brandon Clark created November 15, 2015 
     * @version 1.0.0 November 15, 2015
     * @version 2.0.0 March 21, 2016
     * @version 3.0.0 July 21, 2016
     * @return episode season number.
     ***********************************************************************************************/
    public Integer getEpisodeSeason(String url, String title) {
        Long season = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("title").toString().equals(title)) {
                season = (Long) episodeObject.get("seasonNumber");
            }
        }
        try {
            // Throws an exception if season is null
            return season.intValue();
        } catch (Exception e) {
            return null;
        }
    }

    /**********************************************************************************************
     * Gets all episodes season numbers from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi created April 28, 2016
     * @version 1.0.0 November April 28, 2016
     * @return episodes seasons
     ***********************************************************************************************/
    public List<Long> getAllEpisodesSeasonNumbers(String url) {
        List<Long> seasons = new ArrayList<>();
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            Long season = (Long) episodeObject.get("seasonNumber");
            if (!seasons.contains(season)) { seasons.add(season); }
        }
        return seasons;
    }

    /**********************************************************************************************
     * Gets a series with a new episode tag from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Brandon Clark created November 15, 2015 
     * @version 1.0.0 November 15, 2015
     * @return Series title associated with a new episode.
     ***********************************************************************************************/
    public String getPropertyTitleWithNewEpisode(String url) {
        String showTitle = null;

        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject showObject = iterator.next();
            JSONObject ribbonObject = (JSONObject) showObject.get("ribbon");
            if (ribbonObject != null) {
                if (ribbonObject.get("newEpisode").toString().equals("true")) {
                    showTitle = (String) showObject.get("title");
                    break;
                }
            }
        }
        return showTitle;
    }

    /**********************************************************************************************
     * Gets an old series with a new episode tag from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi, July 13, 2016
     * @version 1.0.0 July 13, 2016
     * @return Series title associated with a new episode.
     ***********************************************************************************************/
    public String getOldSeriesTitleWithNewEpisode(String url) {
        String showTitle = null;

        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject showObject = iterator.next();
            JSONObject ribbonObject = (JSONObject) showObject.get("ribbon");
            if (ribbonObject != null) {
                if (ribbonObject.get("newEpisode").toString().equals("true")) {
                    showTitle = (String) showObject.get("title");
                    // new series note is displayed if new series and has new episodes
                    if (ribbonObject.get("newSerie").toString().equals("false")) {
                        break;
                    }
                }
            }
        }
        return showTitle;
    }

    /**********************************************************************************************
     * Gets the name of the first new episode of a series from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi, July 14, 2016
     * @version 1.0.0 July 14, 2016
     * @return Episode title of new episode.
     ***********************************************************************************************/
    public String getFirstNewEpisodeOfSeries(String url) {
        String episodeTitle = null;

        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject showObject = iterator.next();
            JSONObject ribbonObject = (JSONObject) showObject.get("ribbon");
            if (ribbonObject != null) {
                if (ribbonObject.get("newEpisode").toString().equals("true")) {
                    episodeTitle = (String) showObject.get("title");
                    break;
                }
            }
        }
        return episodeTitle;
    }

    /**********************************************************************************************
     * Gets a series with a new series tag from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi, July 13, 2016
     * @version 1.0.0 July 13, 2016
     * @return Series title associated with a new series.
     ***********************************************************************************************/
    public String getNewSeriesTitle(String url) {
        String showTitle = null;

        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject showObject = iterator.next();
            JSONObject ribbonObject = (JSONObject) showObject.get("ribbon");
            if (ribbonObject != null) {
                if (ribbonObject.get("newSerie").toString().equals("true")) {
                    showTitle = (String) showObject.get("title");
                    break;
                }
            }
        }
        return showTitle;
    }

    /**********************************************************************************************
     * Gets a series with a new season tag from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi, September 23, 2016
     * @version 1.0.0 September 23, 2016
     * @return Series title associated with a new series.
     ***********************************************************************************************/
    public String getNewSeasonSeriesTitle(String url) {
        String showTitle = null;

        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
		Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject showObject = iterator.next();
            JSONObject ribbonObject = (JSONObject) showObject.get("ribbon");
            if (ribbonObject != null) {
                if (ribbonObject.get("newSeason").toString().equals("true")) {
                    showTitle = (String) showObject.get("title");
                    break;
                }
            }
        }
        return showTitle;
    }

    /**********************************************************************************************
     * Gets a series with a new episode tag from the application data feed
     * that is less than a week old.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi, September 30, 2016
     * @version 1.0.0 September 30, 2016
     * @return Series title associated with a new series.
     ***********************************************************************************************/
    public String getPropertyTitleWithNewEpisodeLessThanAWeekOld(String url) {
        String showTitle = null;
        String date = null;
        Integer i = 1;
        Long aWeekInMilliseconds = 604800000L;
        Long publishedDateInMilliseconds = null;
        Long currentDateInMilliseconds = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
		Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject showObject = iterator.next();
            try {
            JSONArray latestEpisodesArray = (JSONArray) showObject.get("latestEpisodes");
            if(!latestEpisodesArray.isEmpty()){
            for (Object object : latestEpisodesArray) {
                String episode = object.toString();
                Pattern pattern = Pattern.compile("date(.*?),");
                Matcher matcher = pattern.matcher(episode);
                while(matcher.find())
                {
                    date = matcher.group(1);
                    if (i == 2) { // second date is originalPublishDate
                        break;
                    }
                    i++;
                }
            }
            String[] dateArray = date.split(" ");
            String shortenedDate = dateArray[0].replace("\"", "").replace(":", "").replace("-", "");
          
                DateFormat format = new SimpleDateFormat("yyyyMMdd");
                Date publishDate = format.parse(shortenedDate);
                publishedDateInMilliseconds = publishDate.getTime();
                Date currentDate = new Date();
                currentDateInMilliseconds = currentDate.getTime();
            }
            JSONObject ribbonObject = (JSONObject) showObject.get("ribbon");
            if (ribbonObject != null) {
                if (ribbonObject.get("newSeason").toString().equals("false")) {
                    if (ribbonObject.get("newEpisode").toString().equals("true")) {
                        if (currentDateInMilliseconds - publishedDateInMilliseconds <= aWeekInMilliseconds) {
                            showTitle = (String) showObject.get("title");
                            break;
                        }
                    }
                }
            }
            } catch (Exception e) {
                Logger.logConsoleMessage("Latest Episodes parameter not available in feed");
                break;
            }
        }
        return showTitle;
    }

    /**********************************************************************************************

     * Gets the current live tv show from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Brandon Clark created November 15, 2015 
     * @version 1.0.0 November 15, 2015
     * @return Current live tv show.
     ***********************************************************************************************/
    public HashMap<String, String> getCurrentLiveTVDetails(String url) {
        HashMap<String, String> liveTVDetails = new HashMap<String, String>();
        Date today = new Date();
        long todayEpoch = today.getTime() / 1000;

        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray tvschedulesArr = (JSONArray) dataObj.get("tvschedules");
        for (int i = 0; i < tvschedulesArr.size(); i++) {
            JSONObject showObject = (JSONObject) tvschedulesArr.get(i);
            String broadcastDate = showObject.get("broadcastDate").toString();
            if (Long.parseLong(broadcastDate) > todayEpoch) {
                JSONObject previousShowObject = (JSONObject) tvschedulesArr.get(i);
                liveTVDetails.put("SeriesTitle", previousShowObject.get("seriesTitle").toString());
                String episodeTitle = null;
                try {
                    episodeTitle = previousShowObject.get("episodeTitle").toString();
                } catch (NullPointerException e) {
                    // ignore
                }

                if (episodeTitle != null) {
                    liveTVDetails.put("EpisodeTitle", previousShowObject.get("episodeTitle").toString());
                }

                liveTVDetails.put("Description", previousShowObject.get("description").toString());

                DateFormat sdf = new SimpleDateFormat("h:mm a");
                String showEpoch = previousShowObject.get("broadcastDate").toString();
                long seconds = Long.parseLong(showEpoch) * 1000;
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(seconds);
                liveTVDetails.put("BroadcastTime", sdf.format(calendar.getTime()));
                break;
            }
        }

        return liveTVDetails;
    }

    /**********************************************************************************************
     * Gets the duration of an episode in milliseconds from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi created March 31, 2016
     * @version 1.0.0 March 31, 2016
     * @return Duration of episode in milliseconds
     ***********************************************************************************************/
    public Long getEpisodeDuration(String url, String title) {
        Long duration = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("title").toString().equals(title)) {
                HashMap<String, Long> durationMap = (HashMap<String, Long>) episodeObject.get("duration");
                duration = durationMap.get("milliseconds");
                break;
            }
        }
        return duration;
    }

    /**********************************************************************************************
     * Gets the season number for a clip from the application data feed
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Helen Cheng October 25th, 2017
     * @version 1.0.0 March 31, 2016
     * @return Season number as a Long
     ***********************************************************************************************/
    public Long getClipSeasonNumber(String url, String title) {
        Long seasonNumber = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject clipObject = iterator.next();
            if (clipObject.get("type").toString().equals("clip")) {
                if (clipObject.get("title").toString().equals(title)) {
                    HashMap<String, Long> seasonNumberMap = (HashMap<String, Long>) clipObject.get("season");
                    seasonNumber = seasonNumberMap.get("seasonNumber");
                    break;
                }
            }
        }
        return seasonNumber;
    }

    /**********************************************************************************************
     * Gets the episode number for a clip from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Helen Cheng October 25th, 2017
     * @version 1.0.0 March 31, 2016
     * @return Episode number as a string
     ***********************************************************************************************/
    public String getClipEpisodeNumber(String url, String title) {
        String episodeNumber = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject clipObject = iterator.next();
            if (clipObject.get("type").toString().equals("clip")) {
                if (clipObject.get("title").toString().equals(title)) {
                    HashMap<String, String> episodeNumberMap = (HashMap<String, String>) clipObject.get("episode");
                    episodeNumber = episodeNumberMap.get("episodeNumber");
                    break;
                }
            }
        }
        return episodeNumber;
    }

    /**********************************************************************************************
     * Gets the duration of an clip in milliseconds from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi created April 20, 2016
     * @version 1.0.0 April 20, 2016
     * @return Duration of episode in milliseconds
     ***********************************************************************************************/
    public Long getClipDuration(String url, String title) {
        Long duration = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("type").toString().equals("clip")) {
                if (episodeObject.get("title").toString().equals(title)) {
                    HashMap<String, Long> durationMap = (HashMap<String, Long>) episodeObject.get("duration");
                    duration = durationMap.get("milliseconds");
                    break;
                }
            }
        }
        return duration;
    }

    public long getClipDurationInSeconds(String url, String title) {

        Float duration = Float.valueOf(this.getClipDuration(url, title));

        return (long) Math.round(duration / 1000);
    }

    /**********************************************************************************************
     * Gets duration with min and sec formatting.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Helen Cheng created September 6, 2017
     * @return duration of clip with min and sec appended
     ***********************************************************************************************/
    public String getClipDurationFormatted(String url, String title) {
        String durationFormatted;
        String timeCode;
        String[] timeCodeSplit;

        timeCode = this.getClipDurationInTimecode(url, title);
        timeCodeSplit = timeCode.split(":");

        // Seconds cannot have leading '0's
        if(timeCodeSplit[1].charAt(0) == '0') {
            timeCodeSplit[1] = timeCodeSplit[1].substring(1);
        }

        if (timeCodeSplit[0].equals("0")) {
            durationFormatted = timeCodeSplit[1] + " sec";
        }
        else {
            durationFormatted = timeCodeSplit[0] + " min, " + timeCodeSplit[1] + " sec";
        }

        return durationFormatted;
    }

    /**********************************************************************************************
     * Gets duration with min and sec formatting.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Helen Cheng created September 6, 2017
     * @return duration of clip with min and sec appended
     ***********************************************************************************************/
    public String getEntireClipDuration(String url, String title) {

        Long seasonNumber;
        String episodeNumber;
        String subtitleFormatted;
        String durationFormatted;

        durationFormatted = this.getClipDurationFormatted(url, title);

        seasonNumber = this.getClipSeasonNumber(url, title);
        episodeNumber = this.getClipEpisodeNumber(url, title);

        if (seasonNumber == null && episodeNumber == null){
            return " (" + durationFormatted + ")";
        }
        else if (seasonNumber != null && episodeNumber == null) {
            subtitleFormatted = "Season " + seasonNumber;
        }
        else {
            subtitleFormatted = "Season " + seasonNumber + ", Episode " + episodeNumber;
        }

        return subtitleFormatted + " (" + durationFormatted + ")";
    }

    /**********************************************************************************************
     * Gets the number of an episode from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi created April 5, 2016
     * @version 1.0.0 April 5, 2016
     * @return number of an episode
     ***********************************************************************************************/
    public String getEpisodeNumber(String url, String title) {
        String episodeNumber = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("title").toString().equals(title)) {
                episodeNumber = (String) episodeObject.get("episodeNumber");
                break;
            }
        }
        return episodeNumber;
    }

    /**********************************************************************************************
     * Gets a list of episode numbers from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi created April 5, 2016
     * @version 1.0.0 April 5, 2016
     * @return sorted list episode numbers
     ***********************************************************************************************/
    public List<String> getSortedEpisodeNumbersAsc(String url) {
        List<String> episodeNumbers = new ArrayList<>();
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            episodeNumbers.add((String) episodeObject.get("episodeNumber"));
        }
        Collections.sort(episodeNumbers);
        return episodeNumbers;
    }
    /**********************************************************************************************
     * Gets a list of episode numbers from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi created April 5, 2016
     * @version 1.0.0 April 5, 2016
     * @return sorted list episode numbers
     ***********************************************************************************************/
    public List<String> getSortedEpisodeNumbersDesc(String url) {
        List<String> episodeNumbers = new ArrayList<>();
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            episodeNumbers.add((String) episodeObject.get("episodeNumber"));
        }
        Collections.sort(episodeNumbers);
        Collections.reverse(episodeNumbers);
        return episodeNumbers;
    }

    /**********************************************************************************************
     * Gets a sorted list of seasons for a series
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi created April 25, 2016
     * @version 1.0.0 April 25, 2016
     * @return sorted list of Seasons
    ***********************************************************************************************/
    public List<Long> getSortedSeasonsOfSeriesAsc(String url) {
        List<Long> seasonNumbers = new ArrayList<>();
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray seasonsArr = (JSONArray) dataObj.get("seasons");
        Iterator<JSONObject> iterator = seasonsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject seasonObject = iterator.next();
            seasonNumbers.add((Long) seasonObject.get("seasonNumber"));
        }
        Collections.sort(seasonNumbers);
        return seasonNumbers;
    }

    /**********************************************************************************************
     * Gets a sorted list of seasons for a series in descending order
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi created April 25, 2016
     * @version 1.0.0 April 25, 2016
     * @return sorted list of Seasons
     ***********************************************************************************************/
    public List<Long> getSortedSeasonsOfSeriesDesc(String url) {
        List<Long> seasonNumbers = new ArrayList<>();
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray seasonsArr = (JSONArray) dataObj.get("seasons");
        Iterator<JSONObject> iterator = seasonsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject seasonObject = iterator.next();
            seasonNumbers.add((Long) seasonObject.get("seasonNumber"));
        }
        Collections.sort(seasonNumbers);
        Collections.reverse(seasonNumbers);
        return seasonNumbers;
    }

    /**********************************************************************************************
     * Gets the most recent publicly available episode for a series from the application data feed
     * shortened by a character limit.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @param characterLimit = {@link Integer} - the number of characters of the title to return
     * @author Vincent Barresi created August 29, 2016
     * @version 1.0.0 August 29, 2016
     * @return shortened episode title
     ***********************************************************************************************/
    public String getShortenedFirstPublicEpisodeTitle(String url, Integer characterLimit) {
        String episode = "";
        String shortenedEpisodeTitle = "";
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("authRequired").toString().equals("false")) {
                episode = (String) episodeObject.get("title");
                if (episode.toCharArray().length > characterLimit) {
                    shortenedEpisodeTitle = episode.substring(0, Math.min(episode.length(), characterLimit));
                    break;
                }
            }
        }
        return shortenedEpisodeTitle;
    }

    /**********************************************************************************************
     * Gets the most recent private available episode for a series from the application data feed
     * shortened by a character limit.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @param characterLimit = {@link Integer} - the number of characters of the title to return
     * @author Vincent Barresi created August 29, 2016
     * @version 1.0.0 August 29, 2016
     * @return shortened episode title
     ***********************************************************************************************/
    public String getShortenedFirstPrivateEpisodeTitle(String url, Integer characterLimit) {
        String episode = "";
        String shortenedEpisodeTitle = "";
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("authRequired").toString().equals("true")) {
                episode = (String) episodeObject.get("title");
                if (episode.toCharArray().length > characterLimit) {
                    shortenedEpisodeTitle = episode.substring(0, Math.min(episode.length(), characterLimit));
                    break;
                }
            }
        }
        return shortenedEpisodeTitle;
    }

    /**********************************************************************************************
     * Determines if app has Short Form content available or not
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Gabriel Fioretti created November 11, 2016
     * @version 1.0.0 November 11, 2016
     * @return Boolean
     ***********************************************************************************************/
    public Boolean isShortFormEnabled(String url) {
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONObject appConfigurationObj = (JSONObject) dataObj.get("appConfiguration");
        return Boolean.parseBoolean(appConfigurationObj.get("shortForm").toString());
    }

    /**********************************************************************************************
     * Determines if app has Background Video enabled or not
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Gabriel Fioretti created November 11, 2016
     * @version 1.0.0 November 11, 2016
     * @return Boolean
     ***********************************************************************************************/
    public Boolean isBackgroundVideoServiceEnabled(String url) {
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONObject appConfigurationObj = (JSONObject) dataObj.get("appConfiguration");
        return Boolean.parseBoolean(appConfigurationObj.get("backgroundServiceVideoEnabled").toString());
    }

    /**********************************************************************************************
     * Gets the duration of an clip in timecode from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Gabriel Fioretti created November 15, 2016
     * @version 1.0.0 November 15, 2016
     * @return Duration of episode in timecode (String)
     ***********************************************************************************************/
    public String getClipDurationInTimecode(String url, String title) {
        String duration = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("type").toString().equals("clip")) {
                if (episodeObject.get("title").toString().equals(title)) {
                    HashMap<String, String> durationMap = (HashMap<String, String>) episodeObject.get("duration");
                    duration = durationMap.get("timecode");
                    break;
                }
            }
        }
        return duration;
    }

    /**********************************************************************************************
     * Gets the duration of a clip in milliseconds from the application data feed and return it
     * as a date formatted string.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Gabriel Fioretti created November 15, 2016
     * @version 1.0.0 November 15, 2016
     * @return Duration of episode in timecode (String)
     ***********************************************************************************************/
    public String getClipDurationInMillisToTimecode(String url, String title) {
        Long duration = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("type").toString().equals("clip")) {
                if (episodeObject.get("title").toString().equals(title)) {
                    HashMap<String, Long> durationMap = (HashMap<String, Long>) episodeObject.get("duration");
                    duration = durationMap.get("milliseconds");
                    break;
                }
            }
        }
        return (new SimpleDateFormat("mm:ss")).format(new Date(duration));
    }

    /**********************************************************************************************
     * Gets the most recent privately available episode for a series from the application data feed
     * that does not contain special characters.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi created November 16, 2015
     * @version 1.0.0 November 17, 2016
     * @return episode title.
     ***********************************************************************************************/
    public String getFirstPrivateEpisodeTitleWithoutCharacters(String url) {
        String episode = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("authRequired").toString().equals("true")) {
                episode = (String) episodeObject.get("title");
                if (!episode.contains("'")) {
                    break;
                }
            }
        }
        return episode;
    }

    /**********************************************************************************************
     * Gets the most recent publicly available episode for a series from the application data feed
     * that does not contain special characters.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Gabriel Fioretti created November 21, 2015
     * @version 1.0.0 November 21, 2016
     * @return episode title.
     ***********************************************************************************************/
    public String getFirstPublicEpisodeTitleWithoutCharacters(String url) {
        String episode = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("authRequired").toString().equals("false")) {
                episode = (String) episodeObject.get("title");
                if (!episode.contains("'")) {
                    break;
                }
            }
        }
        return episode;
    }

    /**********************************************************************************************
     * Gets a clip that has no related episode from the application data feed and returns
     * string Title
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Gabriel Fioretti created November 23, 2016
     * @version 1.0.0 November 23, 2016
     * @return clip title.
     ***********************************************************************************************/
    public String getClipWithoutRelatedEpisode(String url) {
        String clipTitle = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject clipObject = iterator.next();
            if (clipObject.get("episode").toString().equals("{}")) {
                clipTitle = (String) clipObject.get("title");
            }
            break;
        }
        return clipTitle;
    }

    /**********************************************************************************************
     * Gets a clip that has no related episode from the application data feed and returns
     * string Series Title
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Gabriel Fioretti created November 23, 2016
     * @version 1.0.0 November 23, 2016
     * @return series title.
     ***********************************************************************************************/
    public String getClipWithoutRelatedEpisodeSeriesTitle(String url) {
        String seriesTitle = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject clipObject = iterator.next();
            if (clipObject.get("episode").toString().equals("{}")) {
                JSONObject seasonObj = (JSONObject) clipObject.get("season");
                JSONObject parentEntity = (JSONObject) seasonObj.get("parentEntity");
                seriesTitle = (String) parentEntity.get("title");
            }
            break;
        }
        return seriesTitle;
    }

    /**********************************************************************************************
     * Determines if a specific series has short forms available or not
     *
     * @param URL - {@link String} - ClipsURL feed to query
     * @author Gabriel Fioretti created November 29, 2016
     * @version 1.0.0 November 29, 2016
     * @return Boolean
     ***********************************************************************************************/
    public Boolean hasShortformContent(String url) {
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Integer clipsCount = itemsArr.size();
        return clipsCount.equals(0) ? false : true;
    }

    /**********************************************************************************************
     * Gets the series title from the respective series feed url.
     *
     * @param URL - {@link String} - SeriesURL feed to query
     * @author Gabriel Fioretti created November 29, 2016
     * @version 1.0.0 November 29, 2016
     * @return series title.
     ***********************************************************************************************/
    public String getPropertyTitle(String url) {
        String seriesTitle = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObj = iterator.next();
            JSONObject parentObj = (JSONObject) episodeObj.get("parentEntity");
            seriesTitle = parentObj.get("title").toString();
            if (seriesTitle != null)
                break;
        }
        return seriesTitle;
    }

    /**********************************************************************************************
     * Gets a clips subtitle from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Vincent Barresi created March 8, 2017
     * @version 1.0.0 March 8, 2017
     * @return clip subtitle.
     ***********************************************************************************************/
    public String getClipSubtitle(String url, String title) {
        String subTitle = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject clipObject = iterator.next();
            if (clipObject.get("type").toString().equals("clip")) {
                if (clipObject.get("title").toString().equals(title)) {
                    HashMap<String, String> episodeMap = (HashMap<String, String>) clipObject.get("episode");
                    if(!episodeMap.isEmpty()) {
                        subTitle = episodeMap.get("subTitle").toString();
                    }
                    else {
                        subTitle = null;
                    }
                }
            }
        }
        return subTitle;
    }

    /**********************************************************************************************
     * Gets the episodes arrangement order in series view.
     *
     * @param URL - {@link String} - The series feed url to query.
     * @author Gabriel Fioretti created July 14, 2017
     * @version 1.0.0 July 14, 2017
     * @return order (ascending or descending)
     ***********************************************************************************************/
    public String getOrder(String url) {
        String order = "";
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray seasonsArr = (JSONArray) dataObj.get("seasons");
        if (seasonsArr != null) {
            Iterator<JSONObject> iterator = seasonsArr.iterator();
            while (iterator.hasNext()) {
                JSONObject seasonObj = iterator.next();
                order = seasonObj.get("order").toString();
                Logger.logMessage(order);
                break;
            }
        }
        return order;
    }

    /**********************************************************************************************
     * Gets the episodes order by criteria in series view from respective series feed.
     *
     * @param URL - {@link String} - The series feed url to query.
     * @author Gabriel Fioretti created July 14, 2017
     * @version 1.0.0 July 14, 2017
     * @return order by criteria (episodeNumber or originalAirDate or originalPublishDate)
     ***********************************************************************************************/
    public String getOrderBy(String url) {
        String orderBy = "";
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray seasonsArr = (JSONArray) dataObj.get("seasons");
        if (seasonsArr != null) {
            Iterator<JSONObject> iterator = seasonsArr.iterator();
            while (iterator.hasNext()) {
                JSONObject seasonObj = iterator.next();
                orderBy = seasonObj.get("orderBy").toString();
                Logger.logMessage(orderBy);
            }
        }
        return orderBy;
    }

    public boolean hasSeasonOrderedByPublishDateDescending(String url) {
        String order = "";
        String orderBy = "";
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray seasonsArr = (JSONArray) dataObj.get("seasons");
        if (seasonsArr != null) {
            Iterator<JSONObject> iterator = seasonsArr.iterator();
            while (iterator.hasNext()) {
                JSONObject seasonObj = iterator.next();
                orderBy = seasonObj.get("orderBy").toString();
                order = seasonObj.get("order").toString();
                if (orderBy.equals("originalPublishDate") && order.equals("descending")) {
                    return true;
                }
            }
        }
        return false;
    }

    public Long getSeasonOrderedByPublishDateDescendingNumber(String url) {
        Long seasonNumber = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray seasonsArr = (JSONArray) dataObj.get("seasons");
        if (seasonsArr != null) {
            Iterator<JSONObject> iterator = seasonsArr.iterator();
            while (iterator.hasNext()) {
                JSONObject seasonObj = iterator.next();
                String orderBy = seasonObj.get("orderBy").toString();
                String order = seasonObj.get("order").toString();
                if (orderBy.equals("originalPublishDate") && order.equals("descending")) {
                    seasonNumber = (Long) seasonObj.get("seasonNumber");
                    break;
                }
            }
        }
        return seasonNumber;
    }

    public boolean hasSeasonOrderedByPublishDateAscending(String url) {
        String order = "";
        String orderBy = "";
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray seasonsArr = (JSONArray) dataObj.get("seasons");
        if (seasonsArr != null) {
            Iterator<JSONObject> iterator = seasonsArr.iterator();
            while (iterator.hasNext()) {
                JSONObject seasonObj = iterator.next();
                orderBy = seasonObj.get("orderBy").toString();
                order = seasonObj.get("order").toString();
                if (orderBy.equals("originalPublishDate") && order.equals("ascending")) {
                    return true;
                }
            }
        }
        return false;
    }

    public Long getSeasonOrderedByPublishDateAscendingNumber(String url) {
        Long seasonNumber = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray seasonsArr = (JSONArray) dataObj.get("seasons");
        if (seasonsArr != null) {
            Iterator<JSONObject> iterator = seasonsArr.iterator();
            while (iterator.hasNext()) {
                JSONObject seasonObj = iterator.next();
                String orderBy = seasonObj.get("orderBy").toString();
                String order = seasonObj.get("order").toString();
                if (orderBy.equals("originalPublishDate") && order.equals("ascending")) {
                    seasonNumber = (Long) seasonObj.get("seasonNumber");
                    break;
                }
            }
        }
        return seasonNumber;
    }

    public Boolean hasSeasonOrderedByEpisodeNumberAscending(String url) {
        String order = "";
        String orderBy = "";
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray seasonsArr = (JSONArray) dataObj.get("seasons");
        if (seasonsArr != null) {
            Iterator<JSONObject> iterator = seasonsArr.iterator();
            while (iterator.hasNext()) {
                JSONObject seasonObj = iterator.next();
                orderBy = seasonObj.get("orderBy").toString();
                order = seasonObj.get("order").toString();
                if (orderBy.equals("episodeNumber") && order.equals("ascending")) {
                    return true;
                }
            }
        }
        return false;
    }

    public Long getSeasonOrderedByEpisodeNumberAscendingNumber(String url) {
        Long seasonNumber = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray seasonsArr = (JSONArray) dataObj.get("seasons");
        if (seasonsArr != null) {
            Iterator<JSONObject> iterator = seasonsArr.iterator();
            while (iterator.hasNext()) {
                JSONObject seasonObj = iterator.next();
                String orderBy = seasonObj.get("orderBy").toString();
                String order = seasonObj.get("order").toString();
                if (orderBy.equals("episodeNumber") && order.equals("ascending")) {
                    seasonNumber = (Long) seasonObj.get("seasonNumber");
                    break;
                }
            }
        }
        return seasonNumber;
    }

    public Boolean hasSeasonOrderedByEpisodeNumberDescending(String url) {
        String order = "";
        String orderBy = "";
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray seasonsArr = (JSONArray) dataObj.get("seasons");
        if (seasonsArr != null) {
            Iterator<JSONObject> iterator = seasonsArr.iterator();
            while (iterator.hasNext()) {
                JSONObject seasonObj = iterator.next();
                orderBy = seasonObj.get("orderBy").toString();
                order = seasonObj.get("order").toString();
                if (orderBy.equals("episodeNumber") && order.equals("descending")) {
                    return true;
                }
            }
        }
        return false;
    }

    public Long getSeasonOrderedByEpisodeNumberDescendingNumber(String url) {
        Long seasonNumber = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray seasonsArr = (JSONArray) dataObj.get("seasons");
        if (seasonsArr != null) {
            Iterator<JSONObject> iterator = seasonsArr.iterator();
            while (iterator.hasNext()) {
                JSONObject seasonObj = iterator.next();
                String orderBy = seasonObj.get("orderBy").toString();
                String order = seasonObj.get("order").toString();
                if (orderBy.equals("episodeNumber") && order.equals("descending")) {
                    seasonNumber = (Long) seasonObj.get("seasonNumber");
                    break;
                }
            }
        }
        return seasonNumber;
    }

    public Boolean hasSeasonOrderedByOriginalAirDateDescending(String url) {
        String order = "";
        String orderBy = "";
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray seasonsArr = (JSONArray) dataObj.get("seasons");
        if (seasonsArr != null) {
            Iterator<JSONObject> iterator = seasonsArr.iterator();
            while (iterator.hasNext()) {
                JSONObject seasonObj = iterator.next();
                orderBy = seasonObj.get("orderBy").toString();
                order = seasonObj.get("order").toString();
                if (orderBy.equals("originalAirDate") && order.equals("descending")) {
                    return true;
                }
            }
        }
        return false;
    }

    public Long getSeasonOrderedByOriginalAirDateDescendingNumber(String url) {
        Long seasonNumber = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray seasonsArr = (JSONArray) dataObj.get("seasons");
        if (seasonsArr != null) {
            Iterator<JSONObject> iterator = seasonsArr.iterator();
            while (iterator.hasNext()) {
                JSONObject seasonObj = iterator.next();
                String orderBy = seasonObj.get("orderBy").toString();
                String order = seasonObj.get("order").toString();
                if (orderBy.equals("originalAirDate") && order.equals("descending")) {
                    seasonNumber = (Long) seasonObj.get("seasonNumber");
                    break;
                }
            }
        }
        return seasonNumber;
    }

    public Boolean hasSeasonOrderedByOriginalAirDateAscending(String url) {
        String order = "";
        String orderBy = "";
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray seasonsArr = (JSONArray) dataObj.get("seasons");
        if (seasonsArr != null) {
            Iterator<JSONObject> iterator = seasonsArr.iterator();
            while (iterator.hasNext()) {
                JSONObject seasonObj = iterator.next();
                orderBy = seasonObj.get("orderBy").toString();
                order = seasonObj.get("order").toString();
                if (orderBy.equals("originalAirDate") && order.equals("ascending")) {
                    return true;
                }
            }
        }
        return false;
    }

    public Long getSeasonOrderedByOriginalAirDateAscendingNumber(String url) {
        Long seasonNumber = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray seasonsArr = (JSONArray) dataObj.get("seasons");
        if (seasonsArr != null) {
            Iterator<JSONObject> iterator = seasonsArr.iterator();
            while (iterator.hasNext()) {
                JSONObject seasonObj = iterator.next();
                String orderBy = seasonObj.get("orderBy").toString();
                String order = seasonObj.get("order").toString();
                if (orderBy.equals("originalAirDate") && order.equals("ascending")) {
                    seasonNumber = (Long) seasonObj.get("seasonNumber");
                    break;
                }
            }
        }
        return seasonNumber;
    }

    /**********************************************************************************************
     * Gets a series title with episodes only from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Colin Naspo created July 12, 2017
     * @version 1.0.0 July 12, 2017
     * @return episodes only series title.
     ***********************************************************************************************/
    public String getEpisodesOnlySeriesTitle(String url) {
        String episodesOnlySeriesTitle = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject seriesObject = iterator.next();
            if (seriesObject.get("hasPromos").toString().equals("false") && seriesObject.get("hasSubItems").toString().equals("true")) {
                episodesOnlySeriesTitle = seriesObject.get("title").toString();
                if (episodesOnlySeriesTitle != null)
                    break;
            }
        }
        return episodesOnlySeriesTitle;
    }

    /**********************************************************************************************
     * Gets a series title with clips only from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Colin Naspo created July 12, 2017
     * @version 1.0.0 July 12, 2017
     * @return clips only series title.
     ***********************************************************************************************/
    public String getClipsOnlySeriesTitle(String url) {
        String clipsOnlySeriesTitle = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject seriesObject = iterator.next();
            if (seriesObject.get("hasPromos").toString().equals("true") && seriesObject.get("hasSubItems").toString().equals("false")) {
                clipsOnlySeriesTitle = seriesObject.get("title").toString();
                if (clipsOnlySeriesTitle != null) {
                    break;
                }
            }
        }
        return clipsOnlySeriesTitle;
    }

    public HashMap<String,String> getMgidEpisodesTtlMap(String url) {

        HashMap<String, String> mgidEpisodesTtlMap = new LinkedHashMap<>();
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObj = iterator.next();
            String id = episodeObj.get("id").toString().split(":")[4];
            String episodeTtl = episodeObj.get("title").toString();
            if (!episodeTtl.contains("'")) {
                mgidEpisodesTtlMap.put(episodeTtl, id);
            }
            if (mgidEpisodesTtlMap.size() == 2){
                break;
            }
        }
        return mgidEpisodesTtlMap;
    }

    /**********************************************************************************************
     * Gets the number of seasons that contains episodes from the Series Feed
     *
     * @param url - {@link String} - The series data feed to query.
     * @author Gabriel Fioretti July 21, 2017
     * @version 1.0.0 July 21, 2017
     * @return Number of seasons with episode for a series
     **********************************************************************************************/
    public Integer getNumberOfSeasonsWithEpisodesAvailable(String url) {

        List<Long> seasonsWithEpisode = new ArrayList<>();
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObj = iterator.next();
            Long seasonNumber = (Long) episodeObj.get("seasonNumber");
            if (!seasonsWithEpisode.contains(seasonNumber)) {
                seasonsWithEpisode.add(seasonNumber);
            }
        }
        return seasonsWithEpisode.size();
    }

    /**********************************************************************************************
     * Gets a series title with clips and full episodes from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Colin Naspo created July 12, 2017
     * @version 1.0.0 July 21, 2017
     * @return clips and episodes series title.
     ***********************************************************************************************/
    public String getClipsAndEpisodesSeriesTitle(String url) {
        String clipsAndEpisodesSeriesTitle = null;
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject seriesObject = iterator.next();
            if (seriesObject.get("hasPromos").toString().equals("true") && seriesObject.get("hasSubItems").toString().equals("true")) {
                clipsAndEpisodesSeriesTitle = seriesObject.get("title").toString();
                if (clipsAndEpisodesSeriesTitle != null)
                    break;
            }
        }
        return clipsAndEpisodesSeriesTitle;
    }
    
    /**********************************************************************************************
     * Gets all the series titles with clips and full episodes from the application data feed.
     *
     * @param URL - {@link String} - The data feed url to query.
     * @author Edward Poon created July 28, 2017
     * @version 1.0.0 July 28, 2017
     * @return series titles of series with both clips and episodes .
     ***********************************************************************************************/
    public List<String> getClipsAndEpisodesSeriesTitles(String url) {
        String clipsAndEpisodesSeriesTitle = null;
        List<String> clipsAndEpisodesSeriesTitles = new ArrayList<String>();
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject seriesObject = iterator.next();
            if (seriesObject.get("hasPromos").toString().equals("true") && seriesObject.get("hasSubItems").toString().equals("true")) {
                clipsAndEpisodesSeriesTitle = seriesObject.get("title").toString();
                if (clipsAndEpisodesSeriesTitle != null){
                    clipsAndEpisodesSeriesTitles.add(clipsAndEpisodesSeriesTitle);
                }
            }
        }
        return clipsAndEpisodesSeriesTitles;
    }

    /**********************************************************************************************
     * Determines if a given series has new episode ribbon on home screen
     * @author Gabriel Fioretti July 24, 2017
     * @version 1.0.0 July 24, 2017
     * @param url {@link String} - The series data feed url to query
     * @return Boolean
     **********************************************************************************************/
    public Boolean hasNewEpisodeRibbon(String url) {
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject showObject = iterator.next();
            JSONObject ribbonObject = (JSONObject) showObject.get("ribbon");
            if (ribbonObject != null) {
                if (ribbonObject.get("newEpisode").toString().equals("true") && ribbonObject.get("newSerie").toString().equals("false")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**********************************************************************************************
     * Determines if a given series has new season ribbon on home screen
     * @author Gabriel Fioretti July 25, 2017
     * @version 1.0.0 July 25, 2017
     * @param url {@link String} - The series data feed url to query
     * @return Boolean
     **********************************************************************************************/
    public Boolean hasNewSeasonRibbon(String url) {
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject showObject = iterator.next();
            JSONObject ribbonObject = (JSONObject) showObject.get("ribbon");
            if (ribbonObject != null) {
                if (ribbonObject.get("newSeason").toString().equals("true") && ribbonObject.get("newSerie").toString().equals("false")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Linked hash map of series that have episodes. Each key is the respective series ID and
     * each value is the respective series title.
     * @author Gabriel Fioretti August 1, 2017
     * @version 1.0.0  August 1, 2017
     * @param url - Promo Feed or Featured Series Feed
     * @return seriesIdsAndSeriesTitlesMap
     */
    public Map<String, String> getSeriesIdAndSeriesTitlesWithEpisodesMap(String url) {

        Map<String, String> seriesIdsAndSeriesTitlesMap = new LinkedHashMap<>();
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject seriesObject = iterator.next();
            if (seriesObject.get("hasSubItems").toString().equals("true")) {
                String seriesId = seriesObject.get("id").toString().split(":")[4];
                String seriesTtl = seriesObject.get("title").toString();
                seriesIdsAndSeriesTitlesMap.put(seriesId, seriesTtl);
            }
        }
        return seriesIdsAndSeriesTitlesMap;
    }

    /**
     * Linked hash map of series that have short forms. Each key is the respective series ID and
     * each value is the respective series title.
     * @author Gabriel Fioretti August 1, 2017
     * @version 1.0.0  August 1, 2017
     * @param url - Promo Feed or Featured Series Feed
     * @return seriesIdsAndSeriesTitlesMap
     */
    public Map<String,String> getSeriesIdAndSeriesTitlesWithClipsMap(String url) {

        Map<String, String> seriesIdsAndSeriesTitlesMap = new LinkedHashMap<>();
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject seriesObject = iterator.next();
            if (seriesObject.get("hasPromos").toString().equals("true")) {
                String seriesId = seriesObject.get("id").toString().split(":")[4];
                String seriesTtl = seriesObject.get("title").toString();
                seriesIdsAndSeriesTitlesMap.put(seriesId, seriesTtl);
            }
        }
        return seriesIdsAndSeriesTitlesMap;
    }

    /**
     * Boolean to determine if there are series with background video on home view carousel.
     * @author Gabriel Fioretti August 9, 2017
     * @version 1.0.0  August 9, 2017
     * @param promoSeriesFeedURL / featuredPropertyFeedURL
     * @return Boolean
     */
    public Boolean hasSeriesWithBackgroundVideo(String promoSeriesFeedURL) {
        Boolean hasSeriesWithBackgroundVideo = false;

        JSONObject json = this.getJSONFeed(promoSeriesFeedURL);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject showObject = iterator.next();
            JSONArray videosObj = (JSONArray) showObject.get("videos");
            if (videosObj.size() > 0) {
                hasSeriesWithBackgroundVideo = true;
                break;
            }
        }
        return hasSeriesWithBackgroundVideo;
    }

    /**
     * Returns the first series title that has background video on home view carousel.
     * @author Gabriel Fioretti August 9, 2017
     * @version 1.0.0  August 9, 2017
     * @param promoSeriesFeedURL / featuredPropertyFeedURL
     * @return String seriesTitle
     */
    public String getSeriesWithBackgroundVideo(String promoSeriesFeedURL) {
        String seriesTitle = "";

        JSONObject json = this.getJSONFeed(promoSeriesFeedURL);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject showObject = iterator.next();
            JSONArray videosObj = (JSONArray) showObject.get("videos");
            if (videosObj.size() > 0) {
                seriesTitle = showObject.get("title").toString();
                break;
            }
        }
        return seriesTitle;
    }

    /**
     * Returns the first series title that has no background video on home view carousel.
     * @author Gabriel Fioretti August 9, 2017
     * @version 1.0.0  August 9, 2017
     * @param promoSeriesFeedURL / featuredPropertyFeedURL
     * @return String seriesTitle
     */
    public String getFirstPropertyWithNoBackgroundVideo(String promoSeriesFeedURL) {
        String seriesTitle = "";

        JSONObject json = this.getJSONFeed(promoSeriesFeedURL);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject showObject = iterator.next();
            JSONArray videosObj = (JSONArray) showObject.get("videos");
            if (videosObj.size() == 0) {
                seriesTitle = showObject.get("title").toString();
                break;
            }
        }
        return seriesTitle;
    }

    /**
     * Boolean to determine if series has a clip with related episode.
     * Useful for tests that must interact with Watch Episode button on clips view.
     * @author Gabriel Fioretti August 10, 2017
     * @version 1.0.0  August 10, 2017
     * @param clipsURL feed to query
     * @return Boolean
     */
    public Boolean hasClipWithRelatedEpisode(String clipsURL) {
        Boolean hasClipWithRelatedEpisode = false;
        JSONObject json = this.getJSONFeed(clipsURL);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject clipObject = iterator.next();
            JSONObject episodeObj = (JSONObject) clipObject.get("relatedEntity");
            if (!episodeObj.isEmpty()) {
                hasClipWithRelatedEpisode = true;
                break;
            }
        }
        return hasClipWithRelatedEpisode;
    }

    /**
     * Returns the title of the first clip that has related episode.
     * @author Gabriel Fioretti August 10, 2017
     * @version 1.0.0  August 10, 2017
     * @param clipsURL
     * @return
     */
    public String getClipTitleWithRelatedEpisode(String clipsURL) {
        String clipTitle = "";
        JSONObject json = this.getJSONFeed(clipsURL);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject clipObject = iterator.next();
            JSONObject episodeObj = (JSONObject) clipObject.get("episode");
            if (!episodeObj.isEmpty()) {
                clipTitle = (String) clipObject.get("title");
                break;
            }
        }
        return clipTitle;
    }


    /**
     * Return Mgid for specific clip title from clip feed.
     * @author Gabriel Fioretti August 10, 2017
     * @version 1.0.0  August 10, 2017
     * @param clipsURL
     * @param clipTitle
     * @return
     */
    public String getClipMGID(String clipsURL, String clipTitle) {
        String mgid = "";
        JSONObject json = this.getJSONFeed(clipsURL);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject clipObject = iterator.next();
            if (clipObject.get("title").toString().equals(clipTitle)) {
                mgid = (String) clipObject.get("mgid");
                break;
            }
        }
        return mgid;
    }

    /**
     * Return Episode Id for specific episode title from series feed.
     * @author Gabriel Fioretti August 10, 2017
     * @version 1.0.0  August 10, 2017
     * @param seriesURL
     * @param episodeTitle
     * @return String Episode ID
     */
    public String getEpisodeID(String seriesURL, String episodeTitle) {
        String id = "";
        JSONObject json = this.getJSONFeed(seriesURL);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject episodeObject = iterator.next();
            if (episodeObject.get("title").toString().equals(episodeTitle)) {
                String mgid = (String) episodeObject.get("id");
                id = mgid.split(":")[4].trim();
            }
        }
        return id;
    }
    
    /**
     * Returns appConfiguration Values from mainFeedURL 
     * @author Nithin Reddy August 25, 2017
     * @version 1.0.0  August 25, 2017
     * @param mainFeedURL, appConfiguration key
     * @return 
     */   
    
    public String getmainFeedAppConfigurationValue(String url, String key)   {
        JSONObject json = this.getJSONFeed(url);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONObject appConfigurationObj = (JSONObject) dataObj.get("appConfiguration");
        return appConfigurationObj.get(key).toString();	
    }

    public String getLiveTvId(String liveTVFeedURL) {
        String id = "";

        JSONObject json = this.getJSONFeed(liveTVFeedURL);
        JSONObject dataObj = (JSONObject) json.get("data");
        JSONArray itemsArr = (JSONArray) dataObj.get("items");
        Iterator<JSONObject> iterator = itemsArr.iterator();
        while (iterator.hasNext()) {
            JSONObject itemObj = iterator.next();
            if (itemObj.get("entityType").toString().equals("video")) {
                String mgid = (String) itemObj.get("mgid");
                id = mgid.split(":")[4].trim();
                break;
            }
        }
        return id;
    }

    /**
     * Returns MainFeedHomeScreenTemplateValue from browserMobProxy mainFeedurl response 
     * @author Nithin Reddy August 28, 2017
     * @version 1.0.0  August 28, 2017
     * @param mainFeedresponse 
     * @return 
     */ 
	public String getMainFeedHomeScreenTemplateValue(String responseBody) {
		JSONParser parser = new JSONParser();
		JSONObject object = null;
		try {
			object = (JSONObject) parser.parse(responseBody);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject dataObj = (JSONObject) object.get("data");
		JSONObject appConfigurationObj = (JSONObject) dataObj.get("appConfiguration");
		return appConfigurationObj.get("homeScreenTemplate").toString();
	}
	
	public Map<String, String> getActualMainFeedAppValues(Map<String, String> expectedMap) {
		String mainFeedResponse = ProxyLogUtils.getResponse(Config.StaticProps.REGEX_MAINFEED_URL);
		
		JSONParser parser = new JSONParser();
		JSONObject object = null;
		Map<String, String> actualMap = new HashMap<String,String>();
		try {
			object = (JSONObject) parser.parse(mainFeedResponse);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject dataObj = (JSONObject) object.get("data");
		JSONObject appConfigurationObj = (JSONObject) dataObj.get("appConfiguration");

		for (String key : expectedMap.keySet()) {
			actualMap.put(key, appConfigurationObj.get(key).toString());
		}
		return actualMap;
	}
	
	/**
	 * Trust every server - don't check for any certificate
	 */
	public static void trustAllHosts() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}

			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
