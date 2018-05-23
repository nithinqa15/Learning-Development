package com.viacom.test.vimn.uitests.tests.notifiers;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.core.proxy.ProxyRESTManager;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.DevSettingsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Bala;
import com.viacom.test.vimn.uitests.pageobjects.DevSettingsMenu;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class AcceptBalaTermsAfterReadingTest extends BaseTest {

    //Declare page objects and actions
    SplashChain splashChain;
    Home home;
    Settings settings;
    DevSettingsMenu devSettingsMenu;
    Bala bala;
    AppDataFeed appDataFeed;
    DevSettingsChain devSettingsChain;
    HomePropertyFilter propertyFilter;
    AlertsChain alertsChain;

    //Declare data
    String seriesTitle;
    String balaLatestUpdatedTimeStamp;
    Integer mainConfigRefreshTime = 10;
    Integer appResetTime = 60; // Tablet takes more time to reset the app (approximate 2 min)

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public AcceptBalaTermsAfterReadingTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        home = new Home();
        settings = new Settings();
        devSettingsMenu = new DevSettingsMenu();
        bala = new Bala();
        appDataFeed = new AppDataFeed();
        devSettingsChain = new DevSettingsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        alertsChain = new AlertsChain();

        PropertyResult propertyResult = propertyFilter.propertyFilter().getFirstProperty();
        seriesTitle = propertyResult.getPropertyTitle();
        
        balaLatestUpdatedTimeStamp = MainConfig.balaLatestUpdatedTimeStamp();
    }

    @TestCaseId("35188-35189-35190-35191")
    @Features(GroupProps.NOTIFIERS)
    @Test(groups = {GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.NOTIFIERS})
    @Parameters({ParamProps.ANDROID, ParamProps.TVLAND_APP, ParamProps.CMT_APP, ParamProps.VH1_APP})//Does this need more apps?
    public void setUpAcceptBalaTermsAndroidTest() {

        if (TestRun.isDevBuild()) {
            splashChain.splashAtRest();

            devSettingsChain.launchBala();
            home.seriesTtl(seriesTitle).waitForVisible();
        } else {
            throw new SkipException("Not possible to launch Bala Notifier on QA builds, skipping test.");
        }

    }

    @TestCaseId("35188-35189-35190-35191")
    @Features(GroupProps.NOTIFIERS)
    @Test(groups = {GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.NOTIFIERS}, dependsOnMethods = { "setUpAcceptBalaTermsAndroidTest" })
    @Parameters({ ParamProps.ANDROID, ParamProps.TVLAND_APP, 
			      ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP,
			      ParamProps.MTV_DOMESTIC_APP, ParamProps.CMT_APP,  
			      ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP })
    public void acceptBalaTermsAfterReadingAndroidTest() {

        splashChain.splashAtRest();

        bala.updatedTermsTxt().waitForVisible();
        bala.reviewUpdatesBtn().waitForVisible().tap();

        bala.privacyPolicyBtn().waitForVisible().tap();
        bala.privacyPolicyTxt().waitForVisible();
        bala.backBtn().waitForVisible().tap();

        bala.termsOfUseBtn().waitForScrolledTo().tap();
        bala.termsOfUseTxt().waitForVisible();
        bala.backBtn().waitForVisible().tap();

        bala.changesBtn().waitForScrolledTo().tap();
        bala.changesTxt().waitForVisible();
        bala.backBtn().waitForVisible().tap();

        bala.faqsBtn().waitForScrolledTo().tap();
        bala.faqsTxt().waitForVisible();
        bala.backBtn().waitForVisible().tap();

        bala.acceptAndContinueBtn().waitForScrolledTo().tap();
        home.seriesThumbBtn(seriesTitle).waitForVisible();

    }
    
    @TestCaseId("35188-35189-35190-35191") 
    @Features(GroupProps.NOTIFIERS)
    @Test(groups = {GroupProps.BROKEN, GroupProps.HOME_SCREEN, GroupProps.NOTIFIERS}) // steps cover in other test
    @Parameters({ ParamProps.IOS, ParamProps.TVLAND_APP, 
			      ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP,
			      ParamProps.MTV_DOMESTIC_APP, ParamProps.CMT_APP,  
			      ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP })
    public void acceptBalaTermsAfterReadingiOSTest() {

        splashChain.splashAtRest();
        alertsChain.dismissAlerts();
        
        ProxyRESTManager.applyResponseFilters(ProxyUtils.enableBalaNotifier(balaLatestUpdatedTimeStamp));
        
        bala.waitUntilMainConfigRefreshTime(mainConfigRefreshTime);
        DriverManager.getIOSDriver().resetApp();
        
        bala.waitUntilAppResetTime(appResetTime);
        alertsChain.dismissAlerts();

        bala.updatedTermsTxt().waitForVisible();
        bala.reviewUpdatesBtn().waitForVisible().tap();

        bala.privacyPolicyBtn().waitForVisible().tap();
        bala.privacyPolicyTxt().waitForVisible();
        bala.backBtn().waitForVisible().tap();

        bala.termsOfUseBtn().waitForScrolledTo().tap();
        bala.termsOfUseTxt().waitForVisible();
        bala.backBtn().waitForVisible().tap();

        bala.changesBtn().waitForScrolledTo().tap();
        bala.changesTxt().waitForVisible();
        bala.backBtn().waitForVisible().tap();

        bala.faqsBtn().waitForScrolledTo().tap();
        bala.faqsTxt().waitForVisible();
        bala.backBtn().waitForVisible().tap();

        bala.acceptAndContinueBtn().waitForVisible().tap();
        home.seriesThumbBtn(seriesTitle).waitForVisible();
    }
}
