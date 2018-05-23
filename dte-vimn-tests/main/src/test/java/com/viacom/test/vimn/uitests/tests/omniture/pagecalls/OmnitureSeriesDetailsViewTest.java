package com.viacom.test.vimn.uitests.tests.omniture.pagecalls;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.*;

import java.util.Map;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.uitests.pageobjects.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmniturePageData;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureSeriesDetailsViewTest extends BaseTest{

    //Declare page objects/actions
    SplashChain splashChain;
    Home home;
    Series series;
    Clips clips;
    VideoPlayer videoPlayer;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    AllShows allShows;
    PropertyFilter propertyFilter;
    Navigate navigate;
    ProgressBar progressBar;

    //Declare data
    String seriesTitle;
    Map<String, String> expectedMap;
    Map<String, String> actualMap;
    Integer numberOfSwipes;
    String positionOnCarousel;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureSeriesDetailsViewTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        home = new Home();
        series = new Series();
        allShows = new AllShows();
        videoPlayer = new VideoPlayer();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        navigate = new Navigate();
        clips = new Clips();
        progressBar = new ProgressBar();

        PropertyResult propertyResult = propertyFilter.withEpisodes().propertyFilter().getFirstProperty();

        seriesTitle = propertyResult.getPropertyTitle();
        positionOnCarousel = String.valueOf(propertyResult.getPositionOnCarousel());
        numberOfSwipes = propertyResult.getNumOfSwipes();

    }

    @TestCaseId("35877")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.BROKEN, GroupProps.SHOW_DETAILS, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void omnitureSeriesDetailsViewOmnitureTest() {

        // Test is broken due to https://jira.mtvi.com/browse/VAA-5420 and https://jira.mtvi.com/browse/VAA-5421
        // TODO - GF - 27/09/2017 validate test and change group to full once https://jira.mtvi.com/browse/VAA-5420 and https://jira.mtvi.com/browse/VAA-5421 are fixed
        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        home.enterSeriesView(seriesTitle, numberOfSwipes);

        expectedMap = OmniturePageData.buildSeriesDetailsViewExpectedMap(seriesTitle, String.valueOf(numberOfSwipes));
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_SHOW_DETAILS_VIEW);
        Omniture.assertOmnitureValues(expectedMap, actualMap);

        series.scrollUpToSeriesTitle(seriesTitle);
        series.seriesTtl(seriesTitle).goBack();

    }

    @TestCaseId("35877")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS, GroupProps.OMNITURE  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void omnitureSeriesDetailsViewIOSTest() {
    	
    	splashChain.splashAtRest();
        alertschain.dismissAlerts();

        home.enterSeriesView(seriesTitle, numberOfSwipes);

        expectedMap = OmniturePageData.buildSeriesDetailsViewExpectedMap(seriesTitle, String.valueOf(numberOfSwipes));
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_SHOW_DETAILS_VIEW);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
        
        progressBar.loadingSpinnerIcn().pause(StaticProps.LARGE_PAUSE).waitForNotPresent();
        videoPlayer.playFromBeginningBtn().waitForNotPresent();
        series.switchToClips();
        clips.clipCloseBtn().waitForVisible().tap();
        ProxyUtils.clearNetworkLogs();

        allShows.allShowsBtn().waitForVisible().tap();
        allShows.scrollDownToSeriesTile(seriesTitle);
        allShows.allShowsSeriesTitleText(seriesTitle).waitForVisible().tap();

        expectedMap.clear();
        actualMap.clear();
        
        expectedMap = OmniturePageData.buildSeriesDetailsViewExpectedMap(seriesTitle, String.valueOf(numberOfSwipes));
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_SHOW_DETAILS_VIEW_iOS);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }
}
