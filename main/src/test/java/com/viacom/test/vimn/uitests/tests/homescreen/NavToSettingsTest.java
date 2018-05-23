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
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class NavToSettingsTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	Settings settings;
	SettingsMenu settingsMenu;
    Home home;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter propertyFilter;
    
    //Declare data
    String firstSeriesTitle;
    String lastSeriesTitle;
    Integer numberOfSwipes;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public NavToSettingsTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

    	splashChain = new SplashChain();
        settings = new Settings();
        settingsMenu = new SettingsMenu();
        home = new Home();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        PropertyResults propertyResults = propertyFilter.propertyFilter();

        PropertyResult firstSeries = propertyResults.getFirstProperty();
        firstSeriesTitle = firstSeries.getPropertyTitle();

        PropertyResult lastSeries = propertyResults.getLastProperty();
        lastSeriesTitle = lastSeries.getPropertyTitle();
        numberOfSwipes = lastSeries.getNumOfSwipes();
    }
    
    @TestCaseId("11929")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void navToSettingsAndroidTest() {
        
    	splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.privacyPolicyBtn().waitForVisible().goBack();
        home.flickRightToSeriesThumb(lastSeriesTitle, numberOfSwipes);
        
    }
    
    @TestCaseId("11929")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void navToSettingsiOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        
        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.privacyPolicyBtn().waitForVisible();
        settingsMenu.backBtn().waitForVisible().tap();
        home.flickRightToSeriesThumb(lastSeriesTitle, numberOfSwipes);
    }
}
