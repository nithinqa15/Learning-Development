package com.viacom.test.vimn.uitests.tests.omniture.tvecalls;

import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.*;

import java.util.HashMap;
import java.util.Map;

import com.viacom.test.vimn.common.proxy.ProxyUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.TveBaseTest;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.omniture.OmnitureTVEData;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureTVEContentAuthenticationStatusCallTest extends TveBaseTest {

    String privateEpisodeTitle;
    Integer privateEpisodeSeasonNumber;
    String publicEpisodeTitle;
    Integer publicEpisodeSeasonNumber;

    Map<String, String> expectedMap;
    Map<String, String> actualMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureTVEContentAuthenticationStatusCallTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	
    	if (seriesTitle == null) {
            super.tveSetupTest();
        }

        PropertyResults allSeriesWithPublicAndPrivateEpisodes = propertyFilter.withEpisodes().withPublicEpisodes().withPrivateEpisodes().propertyFilter();
        PropertyResult firstSeriesWithPublicAndPrivateEpisode = allSeriesWithPublicAndPrivateEpisodes.getFirstProperty();
        seriesTitle = firstSeriesWithPublicAndPrivateEpisode.getPropertyTitle();
        numberOfSwipes = firstSeriesWithPublicAndPrivateEpisode.getNumOfSwipes();

        LongformResult privateEpisode = firstSeriesWithPublicAndPrivateEpisode
                .getLongformFilter()
                .withPrivateEpisodesOnly()
                .episodesFilter()
                .getFirstEpisode();

        privateEpisodeTitle = privateEpisode.getEpisodeTitle();
        privateEpisodeSeasonNumber = privateEpisode.getEpisodeSeasonNumber();

        LongformResult publicEpisode = firstSeriesWithPublicAndPrivateEpisode
                .getLongformFilter()
                .withPublicEpisodesOnly()
                .episodesFilter()
                .getFirstEpisode();

        publicEpisodeTitle = publicEpisode.getEpisodeTitle();
        publicEpisodeSeasonNumber = publicEpisode.getEpisodeSeasonNumber();

        expectedMap = new HashMap<>();
    }

    @TestCaseId("35932")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.OMNITURE, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void omnitureTVEContentAuthenticationStatusCallAndroidTest() {
    	//To-Do
    }

    @TestCaseId("35932")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.OMNITURE, GroupProps.TVE })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void omnitureTVEContentAuthenticationStatusCalliOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        loginChain.loginIfNot();
        ProxyUtils.clearNetworkLogs();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.seriesTtl(seriesTitle).waitForPlayerLoad().waitForPresent();
        selectSeasonChain.navigateToSeason(publicEpisodeSeasonNumber);
        series.seriesTtl(seriesTitle).waitForPlayerLoad().waitForPresent();
        series.tapEpisodePlayBtn(publicEpisodeTitle);
        
        expectedMap = OmnitureTVEData.buildTVEContentAuthenticationStatusExpectedMap("UNLOCKED");
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_CONTENT_STATUS_UNLOCKED);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
        
        expectedMap.clear();
        actualMap.clear();
        ProxyUtils.clearNetworkLogs();

        selectSeasonChain.navigateToSeason(privateEpisodeSeasonNumber);
        series.seriesTtl(seriesTitle).waitForPlayerLoad().waitForPresent();
        series.tapEpisodePlayBtn(privateEpisodeTitle);
        
        expectedMap = OmnitureTVEData.buildTVEContentAuthenticationStatusExpectedMap("LOCKED");
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_CONTENT_STATUS_LOCKED);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }
}
