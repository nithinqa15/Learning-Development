package com.viacom.test.vimn.uitests.tests.FiltersUnitTest;

import com.viacom.test.vimn.common.filters.AllShowsPropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class AllShowsPropertyFilterTest extends BaseTest {

    //Declare page objects/actions
    AllShowsPropertyFilter propertyFilter;
    PropertyResults seriesresults;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public AllShowsPropertyFilterTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	
    	propertyFilter = new AllShowsPropertyFilter(NON_PAGINATED);
        seriesresults = propertyFilter.propertyResults;

    }

    @TestCaseId("")
    @Features(GroupProps.FILTERS_UNIT_TEST)
    @Test(groups = { GroupProps.FULL, GroupProps.FILTERS_UNIT_TEST, GroupProps.SMOKE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void allShowsPropertyFilterAndroidTest() {
        
    	Logger.logConsoleMessage("All Shows Series Android Test");

		for (PropertyResult s : seriesresults) {
			Logger.logConsoleMessage("===============================================");
			
			Logger.logConsoleMessage("Title : " + s.getPropertyTitle());
			Logger.logConsoleMessage("Series ID : " + s.getPropertyId());
			Logger.logConsoleMessage("Series Description : " + s.getPropertyDescription());
			Logger.logConsoleMessage("Series Detail : " + s.getPropertyDetail());
			Logger.logConsoleMessage("EntityType : " + s.getEntityType());
			Logger.logConsoleMessage("Series MGID : " + s.getPropertyMGID());
			Logger.logConsoleMessage("Series URl : " + s.getPropertyURL());
			Logger.logConsoleMessage("Position in Carousel : " + s.getPositionOnCarousel());
			Logger.logConsoleMessage("Series Position : " + s.getPropertyPosition());
			Logger.logConsoleMessage("Has Episodes : " + s.hasEpisodes());
			Logger.logConsoleMessage("Has Clips : " + s.hasClips());
			Logger.logConsoleMessage("Has Playlists : " + s.hasPlaylist());
			Logger.logConsoleMessage("Has Seasons : " + s.hasSeasons());
			Logger.logConsoleMessage("Is New Series : " + s.isNewSeries());
			Logger.logConsoleMessage("Has New Season : " + s.hasNewSeasons());
			Logger.logConsoleMessage("Has New Episode : " + s.hasNewEpisode());
			Logger.logConsoleMessage("Has Link : " + s.hasLinks());
			Logger.logConsoleMessage("Has Series Property : " + s.isSeriesProperty());
			Logger.logConsoleMessage("Has Movie Property : " + s.isMovieProperty());
			Logger.logConsoleMessage("Has Event Property : " + s.isFightProperty());
			
			if (s.isFightProperty()) {
				Logger.logConsoleMessage("Has New Event : " + s.hasNewEvent());
			}
			
			if (s.hasPublishDate()) {
				Logger.logConsoleMessage("Original Publish Date TimeStamp : " + s.getOriginalPublishDateTimestamp());
			}
			
			if (s.hasAirDate()) {
				Logger.logConsoleMessage("Original Air Date TimeStamp : " + s.getOriginalAirDateTimestamp());
			}
			
			if (s.hasEpisodes()) {
				Logger.logConsoleMessage("Has Public Episode : " + s.hasPublicEpisodes());
				Logger.logConsoleMessage("Has Private Episode : " + s.hasPrivateEpisodes());
			} else {
				Logger.logConsoleMessage("++++++++++++++++++++++++++" + s.getPropertyTitle() + " " + s.getEntityType() + " doesn't have an episodes" + "+++++++++++++++++++++++++++++++");
			}
			
			Logger.logConsoleMessage("===============================================");
		}
    	
    }

    @TestCaseId("")
    @Features(GroupProps.FILTERS_UNIT_TEST)
    @Test(groups = { GroupProps.FULL, GroupProps.FILTERS_UNIT_TEST, GroupProps.SMOKE })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS})
    public void AllShowsPropertyFilteriOSTest() {

    	Logger.logConsoleMessage("All Shows Series iOS Test");
    	
		for (PropertyResult s : seriesresults) {
			Logger.logConsoleMessage("===============================================");
			
			Logger.logConsoleMessage("Title : " + s.getPropertyTitle());
			Logger.logConsoleMessage("Series ID : " + s.getPropertyTitle());
			Logger.logConsoleMessage("Series Description : " + s.getPropertyDescription());
			Logger.logConsoleMessage("Series Detail : " + s.getPropertyDetail());
			Logger.logConsoleMessage("EntityType : " + s.getEntityType());
			Logger.logConsoleMessage("Series MGID : " + s.getPropertyMGID());
			Logger.logConsoleMessage("Series URl : " + s.getPropertyURL());
			Logger.logConsoleMessage("Position in Carousel : " + s.getPositionOnCarousel());
			Logger.logConsoleMessage("Series Position : " + s.getPropertyPosition());
			Logger.logConsoleMessage("Has Episodes : " + s.hasEpisodes());
			Logger.logConsoleMessage("Has Clips : " + s.hasClips());
			Logger.logConsoleMessage("Has Playlists : " + s.hasPlaylist());
			Logger.logConsoleMessage("Has Seasons : " + s.hasSeasons());
			Logger.logConsoleMessage("Is New Series : " + s.isNewSeries());
			Logger.logConsoleMessage("Has New Season : " + s.hasNewSeasons());
			Logger.logConsoleMessage("Has New Episode : " + s.hasNewEpisode());
			Logger.logConsoleMessage("Has Link : " + s.hasLinks());
			Logger.logConsoleMessage("Has Series Property : " + s.isSeriesProperty());
			Logger.logConsoleMessage("Has Movie Property : " + s.isMovieProperty());
			Logger.logConsoleMessage("Has Event Property : " + s.isFightProperty());
			
			if (s.isFightProperty()) {
				Logger.logConsoleMessage("Has New Event : " + s.hasNewEvent());
			}
			
			if (s.hasPublishDate()) {
				Logger.logConsoleMessage("Original Publish Date TimeStamp : " + s.getOriginalPublishDateTimestamp());
			}
			
			if (s.hasAirDate()) {
				Logger.logConsoleMessage("Original Air Date TimeStamp : " + s.getOriginalAirDateTimestamp());
			}

			if (s.hasEpisodes()) {
				Logger.logConsoleMessage("Has Public Episode : " + s.hasPublicEpisodes());
				Logger.logConsoleMessage("Has Private Episode : " + s.hasPrivateEpisodes());
			} else {
				Logger.logConsoleMessage("++++++++++++++++++++++++++" + s.getPropertyTitle() + " " + s.getEntityType() + " doesn't have an episodes" + "+++++++++++++++++++++++++++++++");
			}
			
			Logger.logConsoleMessage("===============================================");
		}
    }
}