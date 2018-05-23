package com.viacom.test.vimn.uitests.pageobjects;

import java.awt.image.BufferedImage;

import org.testng.Assert;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;
import com.viacom.test.vimn.common.util.TestRun;

import io.appium.java_client.MobileElement;

public class Home {

    private Locator locator;

    // PAGE OBJECT CONSTRUCTOR
    public Home() {
        locator = new Locator();
    }

    // PAGE OBJECT IDENTIFIERS
    public Interact splashImg() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seriesThumbBtn(String title) {
        return new Interact(locator.getLocator(title), locator.locatorData);
    }

    public Interact seriesTtl(String title) {
        return new Interact(locator.getLocator(title), locator.locatorData);
    }

    public Interact seriesDescriptionTxt(String description) {
        return new Interact(locator.getLocator(description), locator.locatorData);
    }

    public Interact newEpisodeBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact newSeriesBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact newSeasonBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact onNowBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact liveTVBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact progressBar(String seriesTitle) {
        return new Interact(locator.getLocator(seriesTitle), locator.locatorData);
    }

    public Interact fullEpisodesBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact watchExtrasBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact trailerBtn() { 
    	return new Interact(locator.getLocator(), locator.locatorData); 
    }

    public Interact resumeWatchingLbl()  {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact posterImage() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact backgroundVideoPlayer() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact chromecastBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact chromecastTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact castToTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact castToDeviceBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact chromecastOKbtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact muteBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact ohNoTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact youLostConnectionTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact retryBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact movieBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact newMovieBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact brandLogoImage() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact specialBtn() {
    	return new Interact(locator.getLocator(),locator.locatorData); 
    }
    
    public Interact newSpecialBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact searchIcon() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact clipsBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    // PAGE OBJECT METHODS
    public void flickToTitle(String title) {
        MobileElement element = seriesThumbBtn(title).waitForScrolledTo().getMobileElement();
        Integer xStart = element.getLocation().x + element.getSize().width;
        if (TestRun.isTablet()) {
            xStart = xStart + 100;
        }
        Integer xEnd = element.getLocation().x;
        Integer yStart = element.getLocation().y - 100;
        Integer yEnd = yStart;

        Integer flickIter = 0;
        while (!seriesTtl(title).isPresent()) {
            if (flickIter > 5) {
                Assert.fail("Series title " + title + " is not present on flick.");
            }
            new Interact(null, null).dragFromTo(xStart, yStart, xEnd, yEnd);
            flickIter++;
        }
    }

    public void enterClipsByTap(String title) {
        MobileElement element = seriesThumbBtn(title).waitForScrolledTo().getMobileElement();
        Integer x = element.getLocation().x + element.getSize().width;
        Integer y = element.getLocation().y + element.getSize().height - 250;
        new Interact(null, null).tap(x, y);

    }

    public void flickRightToSeriesThumb(String seriesTitle, Integer numberOfSwipes) {
        if (!seriesThumbBtn(seriesTitle).isVisible() || !seriesTtl(seriesTitle).isVisible()) {
        	if (!TestRun.isiOS10() && !TestRun.isAndroid()) {
        		seriesThumbBtn(seriesTitle).waitForCarouselFlickedRightTo(numberOfSwipes); //Verify seriesThumb btn
        	} else {
        		seriesTtl(seriesTitle).waitForCarouselFlickedRightTo(numberOfSwipes); 
        	}
        }
        seriesThumbBtn(seriesTitle).isVisible();
    }

    public void flickLeftToSeriesThumb(String seriesTitle, Integer numberOfSwipes) {
    	if (!TestRun.isiOS10() && !TestRun.isAndroid()) {
    		seriesThumbBtn(seriesTitle).waitForCarouselFlickedLeftTo(numberOfSwipes); //Verify seriesThumb btn
    	} else {
    		seriesTtl(seriesTitle).waitForCarouselFlickedLeftTo(numberOfSwipes);
    	}
        seriesThumbBtn(seriesTitle).isVisible();
    }

    public void flickBackToFirstSeriesThumb(String seriesTitle, Integer numberOfSwipes) {
    	if (!TestRun.isiOS10() && !TestRun.isAndroid()) {
    		seriesThumbBtn(seriesTitle).waitForCarouselFlickedLeftTo(numberOfSwipes); //Verify seriesThumb btn
    	} else {
    		seriesTtl(seriesTitle).waitForCarouselFlickedLeftTo(numberOfSwipes);
    	}
        seriesThumbBtn(seriesTitle).isVisible();
    }

    public void enterSeriesView(String seriesTitle, Integer numberOfSwipes) {
        flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
        seriesThumbBtn(seriesTitle).waitForVisible().tap();
    }

    public void enterSeriesWithNewEpisode(String seriesTitle, Integer numberOfSwipes) {
        flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
        if (newEpisodeBtn().isVisible()) {
            seriesThumbBtn(seriesTitle).waitForVisible().tap();
        }
    }

    public void enterSeriesWithNewSeason(String seriesTitle, Integer numberOfSwipes) {
        flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
        if (newSeasonBtn().isVisible()) {
            seriesThumbBtn(seriesTitle).waitForVisible().tap();
        }
    }

    public void enterNewSeries(String seriesTitle, Integer numberOfSwipes) {
        flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
        if (newSeriesBtn().isVisible()) {
            seriesThumbBtn(seriesTitle).waitForVisible().tap();
        }
    }

    public void verifySeriesDescriptionTxtIsVisible(String seriesTitle, String seriesDescription, Integer numberOfSwipes) {
        flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
        if (!TestRun.isiOS10() && !TestRun.isAndroid()) {
        	seriesDescriptionTxt(seriesDescription).waitForPresent(); // iOS 11.0.x had series title & description present but not visible
        } else {
        	seriesDescriptionTxt(seriesDescription).waitForVisible();
        }
    }

    public void enterClipsByTappingOnBackground() {
    	if (TestRun.isIos()) {
    		 posterImage().waitForPresent().tapCenter();
    	} else {
    		 posterImage().waitForVisible().tapCenter();
    	}
    }

    public void enterClipsViewSwipingUp(String seriesTtl, Integer numberOfSwipes) {
        if (!seriesTtl(seriesTtl).isVisible()) {
            flickRightToSeriesThumb(seriesTtl, numberOfSwipes);
        }
        seriesTtl(seriesTtl).swipeUpOnHomeView().waitForNotPresentOrVisible();
    }
    
    public void verifyBackgroundVideoPlaying() {
        Assert.assertTrue(isBackgroundVideoPlaying(), "Background video should be playing");
    }
    
    public void verifyBackgroundVideoNotPlaying() {
        Assert.assertFalse(isBackgroundVideoPlaying(), "Background video should not be playing");
    }
    
    public boolean isBackgroundVideoPlaying() {
        BufferedImage prevImage = backgroundVideoPlayer().waitForVisible().takeScreenshotOfThisInteract();
        posterImage().pause(1000);
        BufferedImage currImage = backgroundVideoPlayer().waitForVisible().takeScreenshotOfThisInteract();
        return !Interact.areScreenshotsEqual(prevImage, currImage);
    }

    public void enterClipsView(String seriesTitle, Integer numberOfSwipes) {
        flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
        watchExtrasBtn().waitForVisible().tap();
    }

    public void enterTrailersView(String moviesTitle, Integer numberOfSwipes) {
        flickRightToSeriesThumb(moviesTitle, numberOfSwipes);
        trailerBtn().waitForVisible().tap();
    }

    public void enterSeriesViewByTappingEpisodesButton(String seriesTitle, Integer numberOfSwipes) {
        flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
        fullEpisodesBtn().waitForVisible().tap();
    }
    
    public void waitUntilHomeScreenDisplay() {
        for (int i=0; i<StaticProps.HOME_SCREEN_LAUNCH_TIME; i++) { 
        	splashImg().pause(StaticProps.SUPER_LARGE_PAUSE);
        	if (TestRun.isBETINTLApp() || TestRun.isCCINTLApp() || TestRun.isMTVINTLApp()) {
        		DriverManager.getAppiumDriver().launchApp(); //Workaround for reporting issue in Intl app
        	}
        	if (splashImg().isPresent()) {
        		splashImg().pause(StaticProps.XLARGE_PAUSE);
        	}
//        	} else if (isAlertPresent()) { 
//        		dismissAlert();
//        		splashImg().pause(StaticProps.XXLARGE_PAUSE); // Allow time between two alert
        	 else if (!splashImg().isPresent() && !isAlertPresent()) {
        		break;
        	}
        }
    }
    
    public void dismissAlert() {
    	if (isAlertPresent()) {
    		if (TestRun.isAndroid()) {
    			DriverManager.getAndroidDriver().switchTo().alert().dismiss();
    		} else {
    			DriverManager.getIOSDriver().switchTo().alert().dismiss();
    		}
    		Logger.logMessage("Dismiss Alert");
    	} 
    }
    
    public boolean isAlertPresent() { 
    	try 
    	{ 
    		if (TestRun.isAndroid()) {
    			DriverManager.getAndroidDriver().switchTo().alert();
    		} else {
    			DriverManager.getIOSDriver().switchTo().alert();
    		}
    		Logger.logMessage("Alert present");
    		return true; 
    	} catch (Exception e) { 
    		Logger.logMessage("Alert not present");
            return false; 
    	}   
    }
}
