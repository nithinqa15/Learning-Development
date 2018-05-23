package com.viacom.test.vimn.uitests.tests.omniture.linkcalls;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_HORIZONTAL_SWIPE;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import de.sstoehr.harreader.model.HarEntry;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureNoHorizontalSwipingBehaviourOnClipTest extends BaseTest {

    Home home;
    Clips clips;
    SplashChain splashChain;
    ChromecastChain chromecastChain;
    OmnitureLogUtils omnitureLogUtils;
    HomePropertyFilter propertyFilter;

    String firstSeriesWithClipsTitle;
    String lastSeriesWithClipsTitle;
    String firstClipTitleFromFirstSeries;
    String firstClipTitleFromLastSeries;
    Integer numberOfSwipesToFirstSeries;
    Integer numberOfSwipesToLastSeries;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureNoHorizontalSwipingBehaviourOnClipTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        home = new Home();
        clips = new Clips();
        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        omnitureLogUtils = new OmnitureLogUtils();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        PropertyResults propertyResults = propertyFilter.withClips().propertyFilter();

        PropertyResult firstSeriesWithClips = propertyResults.getFirstProperty();
        firstSeriesWithClipsTitle = firstSeriesWithClips.getPropertyTitle();
        numberOfSwipesToFirstSeries = firstSeriesWithClips.getNumOfSwipes();
        firstClipTitleFromFirstSeries = firstSeriesWithClips
                .getClips()
                .getFirstClip()
                .getClipTitle();

        PropertyResult lastSeriesWithClips = propertyResults.getLastProperty();
        lastSeriesWithClipsTitle = lastSeriesWithClips.getPropertyTitle();
        numberOfSwipesToLastSeries = lastSeriesWithClips.getNumOfSwipes() - numberOfSwipesToFirstSeries;
        firstClipTitleFromLastSeries = lastSeriesWithClips
                .getClips()
                .getFirstClip()
                .getClipTitle();

    }

    @TestCaseId("")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.CLIPS, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void omnitureNoHorizontalSwipingBehaviourOnClipAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        home.flickRightToSeriesThumb(firstSeriesWithClipsTitle, numberOfSwipesToFirstSeries);
        home.enterClipsByTappingOnBackground();

        ProxyUtils.clearNetworkLogs();

        clips.clipTitle(firstClipTitleFromFirstSeries).waitForVisible();
        clips.clipTitle(firstClipTitleFromFirstSeries).flickToPreviousSeriesFromSeriesView().waitForVisible();

        List<HarEntry> omnitureCalls = omnitureLogUtils.getAllOmnitureCallsFromLog();
        Omniture.assertCallIsNotPresent(EXPECTED_PARAM_HORIZONTAL_SWIPE, omnitureCalls);


    }
}
