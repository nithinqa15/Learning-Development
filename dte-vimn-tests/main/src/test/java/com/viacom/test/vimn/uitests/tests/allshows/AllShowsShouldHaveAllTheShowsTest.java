package com.viacom.test.vimn.uitests.tests.allshows;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;

import com.viacom.test.vimn.common.filters.AllShowsPropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;

import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.AllShows;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class AllShowsShouldHaveAllTheShowsTest extends BaseTest {

	// Declare page objects/actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AllShows allShows;

	// Declare Data
	PropertyResults allSeries;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public AllShowsShouldHaveAllTheShowsTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setUpTest() {
		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		allShows = new AllShows();

		AllShowsPropertyFilter propertyFilter = new AllShowsPropertyFilter(NON_PAGINATED);
		allSeries = propertyFilter.propertyFilter();
	}

	@TestCaseId("10884")
	@Features(GroupProps.ALL_SHOWS)
	@Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP,
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void allShowsShouldHaveAllTheShowsAndroidTest() {

		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();
		allShows.enterAllShows();

		for (PropertyResult series : allSeries) {
			String seriesTitle = series.getPropertyTitle();
			allShows.scrollDownToSeriesTile(seriesTitle);
		}
	}
	
	@TestCaseId("10884")
	@Features(GroupProps.ALL_SHOWS)
	@Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS })
	@Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void allShowsShouldHaveAllTheShowsiOSTest() {

		splashChain.splashAtRest();
		allShows.enterAllShows();

		for (PropertyResult series : allSeries) {
			String seriesTitle = series.getPropertyTitle();
			allShows.scrollDownToSeriesTile(seriesTitle);
		}
	}

}
