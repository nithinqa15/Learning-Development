package com.viacom.test.vimn.uitests.support;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.modules.mediarss.MediaEntryModuleImpl;
import com.rometools.modules.mediarss.types.Category;
import com.rometools.modules.mediarss.types.MediaGroup;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.exceptions.DriverTimeoutException;
import com.viacom.test.vimn.common.proxy.MrssLogUtils;
import com.viacom.test.vimn.common.util.TestRun;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;

public class MrssDataFeed {

    private static final String PLAYLIST_URI_CATEGORY = "urn:mtvn:playlist_uri";
    private static final String ID_CATEGORY = "urn:mtvn:id";
    private static final String CONTENT_TYPE_CATEGORY = "urn:mtvn:content_type";
    private static final String FRANCHISE_CATEGORY = "urn:mtvn:franchise";
    private static final String PLAYLIST_TITLE_CATEGORY = "urn:mtvn:playlist_title";
	//Maximum try count for feed retrieval // International App = 60 Sec, Domestic App = 40 Sec
	public static int maximumRetrievalTry = (TestRun.isCCINTLApp() || TestRun.isMTVINTLApp() || TestRun.isBETINTLApp()) ? 3 : 2;

    private static SyndFeed feed;
    private static String mrssFeedUrl;

    public MrssDataFeed(final String episodeId) {
    	for (int i=0; i<maximumRetrievalTry; i++) {
    		try {
    			mrssFeedUrl = MrssLogUtils.getMrssFeedUrl(episodeId);
    			if (!mrssFeedUrl.isEmpty() || !(mrssFeedUrl == null)) {
    				break;
    			}
    		} catch (AssertionError | Exception e) {
	    		if (e instanceof TimeoutException || e instanceof WebDriverException) {
	    			Logger.logMessage("#########  Test Skipped due to driver Timeout, mrss feed not found #########");
	    			throw new DriverTimeoutException("#########  Test Skipped due to driver Timeout, mrss feed not found #########");
	    		} else if (i == maximumRetrievalTry-1 && e instanceof AssertionError) {
	    			throw e;
	    		} else {
	    			Logger.logMessage("Retrieval TRY : " + i + " : Expected entry NOT found " + e.getCause());
	    		}
    		}
    	}
    	setFeed();
    }

    private static void setFeed() {
        try {
            URL mrssFeed = new URL(mrssFeedUrl);
            HttpURLConnection request = (HttpURLConnection) mrssFeed.openConnection();
            request.setRequestMethod("GET");
            request.connect();
            InputStream stream = request.getInputStream();
            SyndFeedInput input = new SyndFeedInput();
            feed = input.build(new XmlReader(stream));
            stream.close();
            request.disconnect();
        } catch (FeedException | IOException e) {
            e.printStackTrace();
        }
    }

    private List<SyndEntry> getEntries() {
        return feed.getEntries();
    }

    /**
     * Return the value of a given category scheme name from the first
     * item in the mrss feed.
     * @author Gabriel Fioretti August 30, 2017
     * @version 1.0.0  August 30, 2017
     * @param categoryScheme - {@link String} - The category to query
     * @return String value of category
     */
    private String getFirstSegmentMediaCategoryValue(String categoryScheme) {
        String value = "";

        List<SyndEntry> entries = getEntries();
        outerloop:
        for (SyndEntry entry : entries) {
            List<Module> modules = entry.getModules();
            for (Module module : modules) {
                if (module instanceof MediaEntryModuleImpl) {
                    MediaEntryModule mediaEntryModule = (MediaEntryModule) module;
                    MediaGroup[] mediaGroups = mediaEntryModule.getMediaGroups();
                    for (MediaGroup mediaGroup : mediaGroups) {
                        Category[] categories = mediaGroup.getMetadata().getCategories();
                        for (Category category : categories) {
                            if (category.getScheme() != null && category.getScheme().equals(categoryScheme)) {
                                value = category.getValue();
                                break outerloop;
                            }
                        }
                    }
                }
            }
        }
        return value;
    }

    /**
     * Get the series title string from Title tag
     * @author Gabriel Fioretti August 30, 2017
     * @version 1.0.0  August 30, 2017
     * @return String Series Title
     */
    public String getTitle() {
        return feed.getTitle();
    }

    /**
     * Get the episode MGID from the first segment Item Tag
     * @author Gabriel Fioretti August 30, 2017
     * @version 1.0.0  August 30, 2017
     * @return String Episode MGID
     */
    public String getEpisodeMgid() {
        return this.getFirstSegmentMediaCategoryValue(PLAYLIST_URI_CATEGORY);
    }

    /**
     * Return the string from Title tag nested inside the first item.
     * @author Gabriel Fioretti August 30, 2017
     * @version 1.0.0  August 30, 2017
     * @return String Segment Title
     */
    public String getFirstSegmentTitle() {
        String title = "";

        List<SyndEntry> entries = getEntries();
        for (SyndEntry entry : entries) {
            title = entry.getTitle();
            break;
        }
        return title;
    }

    /**
     * Return the first segment Mgid
     * @author Gabriel Fioretti August 30, 2017
     * @version 1.0.0  August 30, 2017
     * @return String Segment MGID
     */
    public String getFirstSegmentMgid() {
        return getFirstSegmentMediaCategoryValue(ID_CATEGORY);
    }

    /**
     * Return the Content Type
     * @author Gabriel Fioretti August 30, 2017
     * @version 1.0.0  August 30, 2017
     * @return String Content Type
     */
    public String getContentType() {
        return getFirstSegmentMediaCategoryValue(CONTENT_TYPE_CATEGORY);
    }

    /**
     * Return the Franchise Name
     * @author Gabriel Fioretti August 30, 2017
     * @version 1.0.0  August 30, 2017
     * @return String Franchise Name
     */
    public String getFranchiseName() {
        return getFirstSegmentMediaCategoryValue(FRANCHISE_CATEGORY);
    }

    /**
     * Return the Playlist Title
     * @author Gabriel Fioretti August 30, 2017
     * @version 1.0.0  August 30, 2017
     * @return String Playlist Title
     */
	public String getPlaylistTitle() {
		if (TestRun.isAndroid() && TestRun.isParamountApp() || TestRun.isMTVDomesticApp()) {
			return getFirstSegmentMediaCategoryValue(PLAYLIST_TITLE_CATEGORY).split(" - ")[1];
		}
		return getFirstSegmentMediaCategoryValue(PLAYLIST_TITLE_CATEGORY);
	}

    /**
     * Return the number of segments that compose the playlist episode
     * @author Gabriel Fioretti August 30, 2017
     * @version 1.0.0  August 30, 2017
     * @return Integer number of segments
     */
    public Integer getNumberOfSegments() {
        return getEntries().size();
    }

}
