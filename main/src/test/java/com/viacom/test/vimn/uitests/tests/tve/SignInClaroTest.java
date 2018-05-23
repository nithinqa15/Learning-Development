package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.vimn.common.TveBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.exceptions.ProviderException;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SignInClaroTest extends TveBaseTest {

    //Declare data
    Boolean claroIsEnabled;
    String geoCode;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInClaroTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        username = ProviderManager.getProviderUsername(StaticProps.CLARO);
        password = ProviderManager.getProviderPassword(StaticProps.CLARO);
        claroIsEnabled = providerEnabled.isClaroEnabled();
        geoCode = LocaleDataFactory.getIsoGeoCode();

    }

    @TestCaseId("35102")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, ParamProps.CC_INTL_APP, ParamProps.CHILE,
        ParamProps.COLUMBIA, ParamProps.COSTA_RICA, ParamProps.EL_SALVADOR, ParamProps.GUATEMALA,
            ParamProps.HONDURAS, ParamProps.NICARAGUA })
    public void signInClaroAndroidTest() {

        if (claroIsEnabled) {

            splashChain.splashAtRest();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.tapEpisodeLockBtn(episodeTitle);
            selectProvider.claroProviderBtn(geoCode).waitForVisible().tap();
            signIn.claroUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username).closeKeyboard();
            signIn.claroPasswordTxb().waitForVisible().tap().type(password).closeKeyboard();
            signIn.claroSignInBtn().waitForVisible().tap();
            signIn.waitForSigningInText();
            signIn.successTxt().waitForVisible().waitForNotPresent();
            series.waitForEpisodeLoad(episodeTitle);
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        } else {
            String message = "Claro is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
        }

    }

    @TestCaseId("35102")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE  })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, ParamProps.CC_INTL_APP, ParamProps.CHILE,
            ParamProps.COLUMBIA, ParamProps.COSTA_RICA, ParamProps.EL_SALVADOR, ParamProps.GUATEMALA,
                ParamProps.HONDURAS, ParamProps.NICARAGUA })
    public void signInClaroiOSTest() {

        if (claroIsEnabled) {

            splashChain.splashAtRest();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();

            home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
            series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.scrollDownToEpisodeTtl(episodeTitle);
            series.episodeLockBtn(episodeTitle).waitForVisible().tap();
            if (selectProvider.fromFranchiseBackBtn().waitForViewLoad().isVisible() && !selectProvider.claroProviderBtn().isVisible()) {
                selectProvider.fromFranchiseBackBtn().waitForVisible().tap();
            }
            selectProvider.claroProviderBtn().waitForVisible().tap();
            signIn.claroUsernameTxb().waitForVisible().tap().tap().type(username);
            keyboard.goToPasswordField();
            signIn.claroPasswordTxb().waitForVisible().tap().type(password);
            signIn.claroSignInBtn().waitForVisible().tap();
            signIn.successTxt().waitForPresent().waitForNotPresent();
            series.episodePauseBtn(episodeTitle).waitForVisible();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        } else {
            String message = "Claro is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
        }
    }

}
