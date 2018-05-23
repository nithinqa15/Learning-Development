package com.viacom.test.vimn.uitests.tests.FiltersUnitTest;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.filters.LongformResult;

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

public class LongformFilterTest extends BaseTest {

    //Declare page objects/actions
    private HomePropertyFilter propertyFilter;
    private PropertyResults seriesresults;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public LongformFilterTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        seriesresults = propertyFilter.propertyResults;
    }

    @TestCaseId("")
    @Features(GroupProps.FILTERS_UNIT_TEST)
    @Test(groups = { GroupProps.FULL, GroupProps.FILTERS_UNIT_TEST, GroupProps.SMOKE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void longformFilterAndroidTest() {

    	Logger.logConsoleMessage("Longform Filter Android Test");

		for (PropertyResult s : seriesresults) {
			Logger.logConsoleMessage("===============================================");
			
			Logger.logConsoleMessage("Series Title : " + s.getPropertyTitle());
			Logger.logConsoleMessage("Series ID : " + s.getPropertyId());
			
			if (s.hasEpisodes()) {
				
				for (LongformResult e : s.getEpisodes()) {
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
					
					Logger.logConsoleMessage("Episode Title : " + e.getEpisodeTitle());
					Logger.logConsoleMessage("Episode ID : " + e.getEpisodeId());
					Logger.logConsoleMessage("Episode MGID : " + e.getEpisodeMGID());
					Logger.logConsoleMessage("Is New Episode : " + e.isNewEpisode());
					Logger.logConsoleMessage("Is Public Episode : " + e.isPublicEpisode());
					Logger.logConsoleMessage("Is Private Episode : " + e.isPrivateEpisode());
					Logger.logConsoleMessage("Episode duration : " + e.getEpisodeDurationInTimeCode());
					Logger.logConsoleMessage("Episode Number : " + e.getEpisodeNumber());
					Logger.logConsoleMessage("Episode Position : " + e.getEpisodePosition());
					Logger.logConsoleMessage("Episode URL : " + e.getEpisodeURL());

					if (e.hasEpisodeDescription(e.getEpisodeTitle())) {
						Logger.logConsoleMessage("Episode Description : " + e.getEpisodeDescription());
					}
					if (e.hasEpisodeSeasonNumber(e.getEpisodeSeasonNumber())) {
						Logger.logConsoleMessage("Episode Season Number : " + e.getEpisodeSeasonNumber());
					}
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
				}
			}

			if (s.hasEvent()) {
				for (LongformResult e : s.getFights()) {
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");

					Logger.logConsoleMessage("Fight Title : " + e.getFightTitle());
					Logger.logConsoleMessage("Fight ID : " + e.getFightId());
					Logger.logConsoleMessage("Fight MGID : " + e.getFightMGID());
					Logger.logConsoleMessage("Is Public Fight : " + e.isPublicFight());
					Logger.logConsoleMessage("Is Private Fight : " + e.isPrivateFight());
					Logger.logConsoleMessage("Fight duration : " + e.getFightDurationInTimeCode());
					Logger.logConsoleMessage("Fight duration : " + e.getFightDurationInMilliSeconds());
					Logger.logConsoleMessage("Fight Position : " + e.getFightURL());
					Logger.logConsoleMessage("Fight Position : " + e.getFightPosition());

					if (e.hasFightDescription(e.getFightTitle())) {
						Logger.logConsoleMessage("Fight Description : " + e.getFightDescription());
					}

					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
				}
			}

			if (s.hasMovie()) {
				for (LongformResult e : s.getMovies()) {
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");

					Logger.logConsoleMessage("Movie Title : " + e.getMovieTitle());
					Logger.logConsoleMessage("Movie ID : " + e.getMovieId());
					Logger.logConsoleMessage("Movie MGID : " + e.getMovieMGID());
					Logger.logConsoleMessage("Is Public Movie : " + e.isPublicMovie());
					Logger.logConsoleMessage("Is Private Movie : " + e.isPrivateMovie());
					Logger.logConsoleMessage("Movie Position : " + e.getMovieURL());
					Logger.logConsoleMessage("Movie Position : " + e.getMoviePosition());

					if (e.hasMovieDescription(e.getMovieTitle())) {
						Logger.logConsoleMessage("Movie Description : " + e.getEpisodeDescription());
					}
					if (e.hasMovieDurationInTimeCode(e.getMovieDurationInTimeCode()))
					{
						Logger.logConsoleMessage("Movie duration in timecode : " + e.getMovieDurationInTimeCode());
					}
					if (e.hasMovieDurationInMilliSeconds(e.getMovieDurationInMilliSeconds())) {
						Logger.logConsoleMessage("Movie duration in milliseconds : " + e.getMovieDurationInMilliSeconds());
					}
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
				}
			}
			
			Logger.logConsoleMessage("===============================================");
		}
    	
    }

    @TestCaseId("")
    @Features(GroupProps.FILTERS_UNIT_TEST)
    @Test(groups = { GroupProps.FULL, GroupProps.FILTERS_UNIT_TEST, GroupProps.SMOKE })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS})
    public void longformFilteriOSTest() {

		Logger.logConsoleMessage("Longform Filter iOS Test");

		for (PropertyResult s : seriesresults) {
			Logger.logConsoleMessage("===============================================");

			Logger.logConsoleMessage("Series Title : " + s.getPropertyTitle());
			Logger.logConsoleMessage("Series ID : " + s.getPropertyId());

			if (s.hasEpisodes()) {

				for (LongformResult e : s.getEpisodes()) {
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");

					Logger.logConsoleMessage("Episode Title : " + e.getEpisodeTitle());
					Logger.logConsoleMessage("Episode ID : " + e.getEpisodeId());
					Logger.logConsoleMessage("Episode MGID : " + e.getEpisodeMGID());
					Logger.logConsoleMessage("Is New Episode : " + e.isNewEpisode());
					Logger.logConsoleMessage("Is Public Episode : " + e.isPublicEpisode());
					Logger.logConsoleMessage("Is Private Episode : " + e.isPrivateEpisode());
					Logger.logConsoleMessage("Episode duration : " + e.getEpisodeDurationInTimeCode());
					Logger.logConsoleMessage("Episode Number : " + e.getEpisodeNumber());
					Logger.logConsoleMessage("Episode Position : " + e.getEpisodePosition());
					Logger.logConsoleMessage("Episode URL : " + e.getEpisodeURL());
					Logger.logConsoleMessage("Episode Season Number : " + e.getEpisodeSeasonNumber());

					if (e.hasEpisodeDescription(e.getEpisodeTitle())) {
						Logger.logConsoleMessage("Episode Description : " + e.getEpisodeDescription());
					}

					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
				}
			}

			if (s.hasEvent()) {
				for (LongformResult e : s.getFights()) {
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");

					Logger.logConsoleMessage("Fight Title : " + e.getFightTitle());
					Logger.logConsoleMessage("Fight ID : " + e.getFightId());
					Logger.logConsoleMessage("Fight MGID : " + e.getFightMGID());
					Logger.logConsoleMessage("Is Public Fight : " + e.isPublicFight());
					Logger.logConsoleMessage("Is Private Fight : " + e.isPrivateFight());
					Logger.logConsoleMessage("Fight duration : " + e.getFightDurationInTimeCode());
					Logger.logConsoleMessage("Fight duration : " + e.getFightDurationInMilliSeconds());
					Logger.logConsoleMessage("Fight Position : " + e.getFightURL());
					Logger.logConsoleMessage("Fight Position : " + e.getFightPosition());

					if (e.hasFightDescription(e.getFightTitle())) {
						Logger.logConsoleMessage("Fight Description : " + e.getFightDescription());
					}

					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
				}
			}

			if (s.hasMovie()) {
				for (LongformResult e : s.getMovies()) {
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");

					Logger.logConsoleMessage("Movie Title : " + e.getMovieTitle());
					Logger.logConsoleMessage("Movie ID : " + e.getMovieId());
					Logger.logConsoleMessage("Movie MGID : " + e.getMovieMGID());
					Logger.logConsoleMessage("Is Public Movie : " + e.isPublicMovie());
					Logger.logConsoleMessage("Is Private Movie : " + e.isPrivateMovie());
					Logger.logConsoleMessage("Movie Position : " + e.getMovieURL());
					Logger.logConsoleMessage("Movie Position : " + e.getMoviePosition());

					if (e.hasMovieDescription(e.getMovieTitle())) {
						Logger.logConsoleMessage("Movie Description : " + e.getEpisodeDescription());
					}
					if (e.hasMovieDurationInTimeCode(e.getMovieDurationInTimeCode())) {
						Logger.logConsoleMessage("Movie duration in timecode : " + e.getMovieDurationInTimeCode());
					}
					if (e.hasMovieDurationInMilliSeconds(e.getMovieDurationInMilliSeconds())) {
						Logger.logConsoleMessage("Movie duration in milliseconds : " + e.getMovieDurationInMilliSeconds());
					}
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
				}
			}

			Logger.logConsoleMessage("===============================================");
		}
	}
  }
