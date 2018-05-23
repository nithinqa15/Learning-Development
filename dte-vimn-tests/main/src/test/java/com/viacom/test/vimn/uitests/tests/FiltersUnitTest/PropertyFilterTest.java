package com.viacom.test.vimn.uitests.tests.FiltersUnitTest;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
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

public class PropertyFilterTest extends BaseTest {

    //Declare page objects/actions
    HomePropertyFilter propertyFilter;
    PropertyResults propertyResults;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public PropertyFilterTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        propertyResults = propertyFilter.propertyResults;
    }

    @TestCaseId("")
    @Features(GroupProps.FILTERS_UNIT_TEST)
    @Test(groups = { GroupProps.FULL, GroupProps.FILTERS_UNIT_TEST, GroupProps.SMOKE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void propertyFilterAndroidTest() {

		Logger.logConsoleMessage("Property iOS Test");

		for (PropertyResult s : propertyResults) {
			Logger.logConsoleMessage("===============================================");

			Logger.logConsoleMessage("Property Title : " + s.getPropertyTitle());
			Logger.logConsoleMessage("Property ID : " + s.getPropertyId());
			Logger.logConsoleMessage("Property Description : " + s.getPropertyDescription());
			Logger.logConsoleMessage("Property Detail : " + s.getPropertyDetail());
			Logger.logConsoleMessage("EntityType : " + s.getEntityType());
			Logger.logConsoleMessage("Property MGID : " + s.getPropertyMGID());
			Logger.logConsoleMessage("Property URl : " + s.getPropertyURL());
			Logger.logConsoleMessage("Position in Carousel : " + s.getPositionOnCarousel());
			Logger.logConsoleMessage("Property Position : " + s.getPropertyPosition());
			Logger.logConsoleMessage("Has Link : " + s.hasLinks());
			Logger.logConsoleMessage("Has Series Property : " + s.isSeriesProperty());
			Logger.logConsoleMessage("Has Movie Property : " + s.isMovieProperty());
			Logger.logConsoleMessage("Has Event Property : " + s.isFightProperty());
			Logger.logConsoleMessage("Has Content Rating : " + s.hasContentRating());
			Logger.logConsoleMessage("Content Rating : " + s.getTVPGContentRating());

			if (s.isMovieProperty()) {
				Logger.logConsoleMessage("Has Movies : " + s.hasMovie());
				Logger.logConsoleMessage("Movie URL : " + s.getMovieURL());
				Logger.logConsoleMessage("Has Trailer : " + s.hasTrailer());
				Logger.logConsoleMessage("Trailer URL : " + s.getTrailerURL());
				Logger.logConsoleMessage("Has Duration : " + s.hasDuration());
				Logger.logConsoleMessage("Duration : " + s.getDuration());
			}

			if (s.isFightProperty()) {
				Logger.logConsoleMessage("Has Playlists : " + s.hasPlaylist());
				Logger.logConsoleMessage("Playlist URL : " + s.getPlaylistURL());
				Logger.logConsoleMessage("Extras URL : " + s.getExtrasURL());
			}

			if (s.isSeriesProperty()) {
				Logger.logConsoleMessage("Is New Series : " + s.isNewSeries());
				Logger.logConsoleMessage("Has New Season : " + s.hasNewSeasons());
				Logger.logConsoleMessage("Has New Episode : " + s.hasNewEpisode());
				Logger.logConsoleMessage("Has Episodes : " + s.hasEpisodes());
				Logger.logConsoleMessage("Episode URL : " + s.getEpisodeURL());
				Logger.logConsoleMessage("Has Clips : " + s.hasClips());
				Logger.logConsoleMessage("Clips URL : " + s.getClipsURL());
				Logger.logConsoleMessage("Has Seasons : " + s.hasSeasons());
				Logger.logConsoleMessage("Seasons URL : " + s.getSeasonsURL());

				if (s.hasEpisodes()) {
					Logger.logConsoleMessage("Has Public Episode : " + s.hasPublicEpisodes());
					Logger.logConsoleMessage("Has Private Episode : " + s.hasPrivateEpisodes());
				} else {
					Logger.logConsoleMessage("++++++++++++++++++++++++++" + s.getPropertyTitle() + " " + s.getEntityType() + " doesn't have an episodes" + "+++++++++++++++++++++++++++++++");
				}
			}

			if (s.hasPublishDate()) {
				Logger.logConsoleMessage("Original Publish Date TimeStamp : " + s.getOriginalPublishDateTimestamp());
			}

			if (s.hasAirDate()) {
				Logger.logConsoleMessage("Original Air Date TimeStamp : " + s.getOriginalAirDateTimestamp());
				Logger.logConsoleMessage("Original Air Date String : " + s.getOriginalAirDateString());
			}

			Logger.logConsoleMessage("===============================================");
		}
    	
    }

    @TestCaseId("")
    @Features(GroupProps.FILTERS_UNIT_TEST)
    @Test(groups = { GroupProps.FULL, GroupProps.FILTERS_UNIT_TEST, GroupProps.SMOKE })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS})
    public void propertyFilteriOSTest() {

    	Logger.logConsoleMessage("Property iOS Test");
    	
		for (PropertyResult s : propertyResults) {
			Logger.logConsoleMessage("===============================================");
			
			Logger.logConsoleMessage("Property Title : " + s.getPropertyTitle());
			Logger.logConsoleMessage("Property ID : " + s.getPropertyId());
			Logger.logConsoleMessage("Property Description : " + s.getPropertyDescription());
			Logger.logConsoleMessage("Property Detail : " + s.getPropertyDetail());
			Logger.logConsoleMessage("EntityType : " + s.getEntityType());
			Logger.logConsoleMessage("Property MGID : " + s.getPropertyMGID());
			Logger.logConsoleMessage("Property URl : " + s.getPropertyURL());
			Logger.logConsoleMessage("Position in Carousel : " + s.getPositionOnCarousel());
			Logger.logConsoleMessage("Property Position : " + s.getPropertyPosition());
			Logger.logConsoleMessage("Has Link : " + s.hasLinks());
			Logger.logConsoleMessage("Has Series Property : " + s.isSeriesProperty());
			Logger.logConsoleMessage("Has Movie Property : " + s.isMovieProperty());
			Logger.logConsoleMessage("Has Event Property : " + s.isFightProperty());
			Logger.logConsoleMessage("Has Content Rating : " + s.hasContentRating());
			Logger.logConsoleMessage("Content Rating : " + s.getTVPGContentRating());
			
			if (s.isMovieProperty()) {
				Logger.logConsoleMessage("Has Movies : " + s.hasMovie());
				Logger.logConsoleMessage("Movie URL : " + s.getMovieURL());
				Logger.logConsoleMessage("Has Trailer : " + s.hasTrailer());
				Logger.logConsoleMessage("Trailer URL : " + s.getTrailerURL());
				Logger.logConsoleMessage("Has Duration : " + s.hasDuration());
				Logger.logConsoleMessage("Duration : " + s.getDuration());
			}
			
			if (s.isFightProperty()) {
				Logger.logConsoleMessage("Has Playlists : " + s.hasPlaylist());
				Logger.logConsoleMessage("Playlist URL : " + s.getPlaylistURL());
				Logger.logConsoleMessage("Extras URL : " + s.getExtrasURL());
			}
			
			if (s.isSeriesProperty()) {
				Logger.logConsoleMessage("Is New Series : " + s.isNewSeries());
				Logger.logConsoleMessage("Has New Season : " + s.hasNewSeasons());
				Logger.logConsoleMessage("Has New Episode : " + s.hasNewEpisode());
				Logger.logConsoleMessage("Has Episodes : " + s.hasEpisodes());
				Logger.logConsoleMessage("Episode URL : " + s.getEpisodeURL());
				Logger.logConsoleMessage("Has Clips : " + s.hasClips());
				Logger.logConsoleMessage("Clips URL : " + s.getClipsURL());
				Logger.logConsoleMessage("Has Seasons : " + s.hasSeasons());
				Logger.logConsoleMessage("Seasons URL : " + s.getSeasonsURL());
				
				if (s.hasEpisodes()) {
					Logger.logConsoleMessage("Has Public Episode : " + s.hasPublicEpisodes());
					Logger.logConsoleMessage("Has Private Episode : " + s.hasPrivateEpisodes());
				} else {
					Logger.logConsoleMessage("++++++++++++++++++++++++++" + s.getPropertyTitle() + " " + s.getEntityType() + " doesn't have an episodes" + "+++++++++++++++++++++++++++++++");
				}
			}
			
			if (s.hasPublishDate()) {
				Logger.logConsoleMessage("Original Publish Date TimeStamp : " + s.getOriginalPublishDateTimestamp());
			}
			
			if (s.hasAirDate()) {
				Logger.logConsoleMessage("Original Air Date TimeStamp : " + s.getOriginalAirDateTimestamp());
				Logger.logConsoleMessage("Original Air Date String : " + s.getOriginalAirDateString());
			}

			Logger.logConsoleMessage("===============================================");
		}
    }
}