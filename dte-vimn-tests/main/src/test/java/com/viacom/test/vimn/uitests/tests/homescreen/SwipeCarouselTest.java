package com.viacom.test.vimn.uitests.tests.homescreen;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class SwipeCarouselTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	Home home;
	Series series;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter propertyFilter;
    
    //Declare data
    String firstSeriesTitle;
    String lastSeriesTitle;
    Integer swipeMax;
    Integer MaxSeriesItemtoTest = 3;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SwipeCarouselTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	
    	splashChain = new SplashChain();
    	home = new Home();
    	series = new Series();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        
        PropertyResults propertyResults = propertyFilter.propertyFilter();
        PropertyResult firstSeries = propertyResults.getFirstProperty();
        firstSeriesTitle = firstSeries.getPropertyTitle();
        PropertyResult lastSeries = propertyResults.getLastProperty();
        swipeMax = lastSeries.getNumOfSwipes();
        if (swipeMax > MaxSeriesItemtoTest) {
        	lastSeriesTitle = propertyResults.get(MaxSeriesItemtoTest).getPropertyTitle();
        	swipeMax = MaxSeriesItemtoTest;
        } else {
        	lastSeriesTitle = lastSeries.getPropertyTitle();
        }
        
    }
    
    @TestCaseId("66330")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.DEBUG, GroupProps.HOME_SCREEN, GroupProps.SMOKE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void swipeCarouselAndroidTest() {
        
    	splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        home.flickRightToSeriesThumb(lastSeriesTitle, swipeMax);
        home.flickLeftToSeriesThumb(firstSeriesTitle, swipeMax);
        
    }
    
    @TestCaseId("66330")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN , GroupProps.SMOKE})
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void swipeCarouseliOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        
        home.flickRightToSeriesThumb(lastSeriesTitle, swipeMax);
        home.flickBackToFirstSeriesThumb(firstSeriesTitle, swipeMax);
    }
}
