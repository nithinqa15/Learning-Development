package com.viacom.test.vimn.uitests.tests.FiltersUnitTest;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.filters.ShortformResult;

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

public class ShortformFilterTest extends BaseTest {

    //Declare page objects/actions
    private HomePropertyFilter propertyFilter;
    private PropertyResults seriesresults;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public ShortformFilterTest(String runParams) {
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
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS})
    public void shortformFilterAndroidTest() {

		Logger.logConsoleMessage("Shortform Filter Android Test");

		for (PropertyResult s : seriesresults) {

			Logger.logConsoleMessage("===============================================");

			Logger.logConsoleMessage("Series Title : " + s.getPropertyTitle());
			Logger.logConsoleMessage("Series ID : " + s.getPropertyId());

			if (s.isSeriesProperty() && s.hasClips()) {
				for (ShortformResult c : s.getClips()) {
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
					Logger.logConsoleMessage("Clip Title : " + c.getClipTitle());
					Logger.logConsoleMessage("Clip SubTitle : " + c.getClipSubtitle());
					Logger.logConsoleMessage("Clip ID : " + c.getClipId());
					Logger.logConsoleMessage("Clip MGID : " + c.getClipMGID());
					Logger.logConsoleMessage("Is Public Clip : " + c.isPublicClip());
					Logger.logConsoleMessage("Is Private Clip : " + c.isPrivateClip());
					Logger.logConsoleMessage("Clip Timecode Duration : " + c.getClipDurationInTimeCode());
					Logger.logConsoleMessage("Clip Millisecond Duration : " + c.getClipDurationInMilliSeconds());
					Logger.logConsoleMessage("Clip Episode Number : " + c.getClipEpisodeNumber());
					Logger.logConsoleMessage("Clip URL : " + c.getClipURL());
					Logger.logConsoleMessage("Clip Position : " + c.getClipPosition());

					if (c.hasClipSeasonNumber(c.getClipSeasonNumber())) {
						Logger.logConsoleMessage("Clip Season Number : " + c.getClipSeasonNumber());
					}
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
				}
			}

			if (s.isMovieProperty() && s.hasTrailer()) {
				for (ShortformResult c : s.getTrailers()) {
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
					Logger.logConsoleMessage("Trailer Title : " + c.getTrailerTitle());
					Logger.logConsoleMessage("Trailer SubTitle : " + c.getTrailerSubtitle());
					Logger.logConsoleMessage("Trailer Description : " + c.getTrailerDescription());
					Logger.logConsoleMessage("Trailer ID : " + c.getTrailerId());
					Logger.logConsoleMessage("Trailer MGID : " + c.getTrailerMGID());
					Logger.logConsoleMessage("Public Trailer : " + c.isPublicTrailer());
					Logger.logConsoleMessage("Private Trailer  " + c.isPrivateTrailer());
					Logger.logConsoleMessage("Trailer Timecode Duration : " + c.getTrailerDurationInTimeCode());
					Logger.logConsoleMessage("Trailer Millisecond Duration : " + c.getTrailerDurationInMilliSeconds());
					Logger.logConsoleMessage("Trailer URL : " + c.getTrailerURL());
					Logger.logConsoleMessage("Trailer URL : " + c.getTrailerPosition());
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
				}
			}

			if (s.isFightProperty() && s.hasClips()) {
				for (ShortformResult c : s.getClips()) {
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
					Logger.logConsoleMessage("Clip Title : " + c.getClipTitle());
					Logger.logConsoleMessage("Clip SubTitle : " + c.getClipSubtitle());
					Logger.logConsoleMessage("Clip ID : " + c.getClipId());
					Logger.logConsoleMessage("Clip MGID : " + c.getClipMGID());
					Logger.logConsoleMessage("Is Public Clip : " + c.isPublicClip());
					Logger.logConsoleMessage("Is Private Clip : " + c.isPrivateClip());
					Logger.logConsoleMessage("Clip Timecode Duration : " + c.getClipDurationInTimeCode());
					Logger.logConsoleMessage("Clip Millisecond Duration : " + c.getClipDurationInMilliSeconds());
					Logger.logConsoleMessage("Clip URL : " + c.getClipURL());
					Logger.logConsoleMessage("Clip Position : " + c.getClipPosition());
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
				}
			}

			if (s.isCollectionProperty()) {
				for (ShortformResult c : s.getMusicVideos()) {
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
					Logger.logConsoleMessage("Music Video Title : " + c.getMusicVideoTitle());
					Logger.logConsoleMessage("Music Video SubTitle : " + c.getMusicVideoSubtitle());
					Logger.logConsoleMessage("Music Video Description : " + c.getMusicVideoDescription());
					Logger.logConsoleMessage("Music Video ID : " + c.getMusicVideoId());
					Logger.logConsoleMessage("Music Video MGID : " + c.getMusicVideoMGID());
					Logger.logConsoleMessage("Is Public Music Video : " + c.isPublicMusicVideo());
					Logger.logConsoleMessage("Is Private Music Video : " + c.isPrivateMusicVideo());
					Logger.logConsoleMessage("Music Video Timecode Duration : " + c.getMusicVideoDurationInTimeCode());
					Logger.logConsoleMessage("Music Video Millisecond Duration : " + c.getMusicVideoDurationInMilliSeconds());
					Logger.logConsoleMessage("Music Video URL : " + c.getMusicVideoURL());
					Logger.logConsoleMessage("Music Video Duration : " + c.getMusicVideoPosition());
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
    public void shortformFilteriOSTest() {

    	Logger.logConsoleMessage("Shortform Filter iOS Test");

		for (PropertyResult s : seriesresults) {

			Logger.logConsoleMessage("===============================================");

			Logger.logConsoleMessage("Series Title : " + s.getPropertyTitle());
			Logger.logConsoleMessage("Series ID : " + s.getPropertyId());

			if (s.isSeriesProperty() && s.hasClips()) {
				for (ShortformResult c : s.getClips()) {
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
					Logger.logConsoleMessage("Clip Title : " + c.getClipTitle());
					Logger.logConsoleMessage("Clip SubTitle : " + c.getClipSubtitle());
					Logger.logConsoleMessage("Clip ID : " + c.getClipId());
					Logger.logConsoleMessage("Clip MGID : " + c.getClipMGID());
					Logger.logConsoleMessage("Is Public Clip : " + c.isPublicClip());
					Logger.logConsoleMessage("Is Private Clip : " + c.isPrivateClip());
					Logger.logConsoleMessage("Clip Timecode Duration : " + c.getClipDurationInTimeCode());
					Logger.logConsoleMessage("Clip Millisecond Duration : " + c.getClipDurationInMilliSeconds());
					Logger.logConsoleMessage("Clip Episode Number : " + c.getClipEpisodeNumber());
					Logger.logConsoleMessage("Clip URL : " + c.getClipURL());
					Logger.logConsoleMessage("Clip Season Number : " + c.getClipSeasonNumber());
					Logger.logConsoleMessage("Clip Position : " + c.getClipPosition());
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
				}
			}

			if (s.isMovieProperty() && s.hasTrailer()) {
				for (ShortformResult c : s.getTrailers()) {
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
					Logger.logConsoleMessage("Trailer Title : " + c.getTrailerTitle());
					Logger.logConsoleMessage("Trailer SubTitle : " + c.getTrailerSubtitle());
					Logger.logConsoleMessage("Trailer Description : " + c.getTrailerDescription());
					Logger.logConsoleMessage("Trailer ID : " + c.getTrailerId());
					Logger.logConsoleMessage("Trailer MGID : " + c.getTrailerMGID());
					Logger.logConsoleMessage("Public Trailer : " + c.isPublicTrailer());
					Logger.logConsoleMessage("Private Trailer  " + c.isPrivateTrailer());
					Logger.logConsoleMessage("Trailer Timecode Duration : " + c.getTrailerDurationInTimeCode());
					Logger.logConsoleMessage("Trailer Millisecond Duration : " + c.getTrailerDurationInMilliSeconds());
					Logger.logConsoleMessage("Trailer URL : " + c.getTrailerURL());
					Logger.logConsoleMessage("Trailer URL : " + c.getTrailerPosition());
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
				}
			}

			if (s.isFightProperty() && s.hasClips()) {
				for (ShortformResult c : s.getClips()) {
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
					Logger.logConsoleMessage("Clip Title : " + c.getClipTitle());
					Logger.logConsoleMessage("Clip SubTitle : " + c.getClipSubtitle());
					Logger.logConsoleMessage("Clip ID : " + c.getClipId());
					Logger.logConsoleMessage("Clip MGID : " + c.getClipMGID());
					Logger.logConsoleMessage("Is Public Clip : " + c.isPublicClip());
					Logger.logConsoleMessage("Is Private Clip : " + c.isPrivateClip());
					Logger.logConsoleMessage("Clip Timecode Duration : " + c.getClipDurationInTimeCode());
					Logger.logConsoleMessage("Clip Millisecond Duration : " + c.getClipDurationInMilliSeconds());
					Logger.logConsoleMessage("Clip URL : " + c.getClipURL());
					Logger.logConsoleMessage("Clip Position : " + c.getClipPosition());
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
				}
			}

			if (s.isCollectionProperty()) {
				for (ShortformResult c : s.getMusicVideos()) {
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
					Logger.logConsoleMessage("Music Video Title : " + c.getMusicVideoTitle());
					Logger.logConsoleMessage("Music Video SubTitle : " + c.getMusicVideoSubtitle());
					Logger.logConsoleMessage("Music Video Description : " + c.getMusicVideoDescription());
					Logger.logConsoleMessage("Music Video ID : " + c.getMusicVideoId());
					Logger.logConsoleMessage("Music Video MGID : " + c.getMusicVideoMGID());
					Logger.logConsoleMessage("Is Public Music Video : " + c.isPublicMusicVideo());
					Logger.logConsoleMessage("Is Private Music Video : " + c.isPrivateMusicVideo());
					Logger.logConsoleMessage("Music Video Timecode Duration : " + c.getMusicVideoDurationInTimeCode());
					Logger.logConsoleMessage("Music Video Millisecond Duration : " + c.getMusicVideoDurationInMilliSeconds());
					Logger.logConsoleMessage("Music Video URL : " + c.getMusicVideoURL());
					Logger.logConsoleMessage("Music Video Duration : " + c.getMusicVideoPosition());
					Logger.logConsoleMessage("+++++++++++++++++++++++++++++++++++++++++++++");
				}
			}
			Logger.logConsoleMessage("===============================================");
		}
    }
}