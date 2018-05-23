package com.viacom.test.vimn.uitests.tests.homescreen;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class PreviewTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	Home home;
	AppDataFeed appDataFeed;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter propertyFilter;
    
    //Declare data
    String seriesTitle;
    String seriesDescription;
    Integer numberOfSwipes;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public PreviewTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

    	splashChain = new SplashChain();
    	home = new Home();
    	appDataFeed = new AppDataFeed();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        PropertyResult propertyResult = propertyFilter.propertyFilter().getFirstProperty();
        seriesTitle = propertyResult.getPropertyTitle();
        seriesDescription = propertyResult.getPropertyDescription();
        numberOfSwipes = propertyResult.getNumOfSwipes();

    }
    
    @TestCaseId("11930")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.SMOKE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void previewAndroidTest() {
    	
    	splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        
    	home.verifySeriesDescriptionTxtIsVisible(seriesTitle, seriesDescription, numberOfSwipes);
        
    }

    @TestCaseId("11930")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN , GroupProps.SMOKE})
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void previewiOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();

        home.verifySeriesDescriptionTxtIsVisible(seriesTitle, seriesDescription, numberOfSwipes);
    }
}
