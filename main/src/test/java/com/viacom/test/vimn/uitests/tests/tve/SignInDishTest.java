package com.viacom.test.vimn.uitests.tests.tve;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.TveBaseTest;
import com.viacom.test.vimn.common.exceptions.ProviderException;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.ConfigProps;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SignInDishTest extends TveBaseTest {

    //Declare data
    Boolean dishIsEnabled;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInDishTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        username = ProviderManager.getProviderUsername(Config.StaticProps.DISH);
        password = ProviderManager.getProviderPassword(Config.StaticProps.DISH);
        dishIsEnabled = providerEnabled.isDishEnabled();

    }

    @TestCaseId("35098")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, ParamProps.CC_INTL_APP, ParamProps.MEXICO })
    public void signInDishAndroidTest() {

        if (dishIsEnabled) {

            splashChain.splashAtRest();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.tapEpisodeLockBtn(episodeTitle);
            selectProvider.dishProviderBtn().waitForVisible().tap();
            signIn.dishUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username).closeKeyboard();
            signIn.dishPasswordTxb().waitForVisible().tap().type(password).closeKeyboard();
            signIn.dishSignInBtn().waitForVisible().tap();
            signIn.waitForSigningInText();
            signIn.successTxt().waitForVisible().waitForNotPresent();
            series.waitForEpisodeLoad(episodeTitle);
            videoPlayer.verifyVideoPlaying(ConfigProps.VIDEO_IMAGE_TIMEOUT);
        } else {
            String message = "Dish is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
        }

    }

    @TestCaseId("35098")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, ParamProps.CC_INTL_APP, ParamProps.MEXICO })
    public void signInDishiOSTest() {

        if (dishIsEnabled) {

            splashChain.splashAtRest();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();

            home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
            series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.scrollDownToEpisodeTtl(episodeTitle);
            series.episodeLockBtn(episodeTitle).waitForVisible().tap();
            if (selectProvider.fromFranchiseBackBtn().waitForViewLoad().isVisible() && !selectProvider.dishProviderBtn().isVisible()) {
                selectProvider.fromFranchiseBackBtn().waitForVisible().tap();
            }
            selectProvider.dishProviderBtn().waitForVisible().tap();
            signIn.dishUsernameTxb().waitForVisible().tap().type(username);
            keyboard.goToPasswordField();
            signIn.dishPasswordTxb().waitForVisible().tap().type(password);
            signIn.dishSignInBtn().waitForVisible().tap();
            //signIn.successTxt().waitForPresent().waitForNotPresent();
            series.episodePauseBtn(episodeTitle).waitForVisible();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        } else {
            String message = "Dish is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
        }
    }

}

