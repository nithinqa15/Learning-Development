package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.exceptions.ContentException;
import com.viacom.test.vimn.common.exceptions.InternetConnectionException;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;


public class Clips {

    private Locator locator;
    private VideoPlayer videoPlayer;

    //PAGE OBJECT CONTSTRUCTOR
    public Clips() {
        locator = new Locator();
        videoPlayer = new VideoPlayer();
    }

    //PAGE OBJECT IDENTIFIERS
    public Interact watchEpisodeBtn(String clipTitle) {
        return new Interact(locator.getLocator(clipTitle), locator.locatorData);
    }

    public Interact clipTitle(String clipTitle) {
        return new Interact(locator.getLocator(clipTitle), locator.locatorData);
    }

    public Interact clipPlayBtn(String clipTitle) {
        return new Interact(locator.getLocator(clipTitle), locator.locatorData);
    }

    public Interact clipCloseBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact backBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact clipDuration(String clipDuration) {
        return new Interact(locator.getLocator(clipDuration), locator.locatorData);
    }

    public Interact noExtrasTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact clipSubtitle(String clipTitle) {
        return new Interact(locator.getLocator(clipTitle), locator.locatorData);
    }

    public Interact clipMetadataCtr() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    //PAGE OBJECT METHODS

    public void verifyClipTitleIsVisible(String clipTitle) {

        if (clipTitle(clipTitle).isPartiallyHidden()) {
            clipTitle(clipTitle).scrollDownOnClipsView(Config.StaticProps.NORMAL_SCROLL).isVisible();
        } else if (!clipTitle(clipTitle).isVisible()) {
            clipTitle(clipTitle).waitForScrolledDownToOnClipsView(1, Config.StaticProps.NORMAL_SCROLL).isVisible();
        }
    }

    public void flickToNextClipsView(String clipTitle) {
        clipTitle(clipTitle).waitForFlickedToNextSeriesFromSeriesView(25);
    }

    public void flickToPreviousClipsView(String clipTitle) {
        clipTitle(clipTitle).waitForFlickedToPreviousSeriesFromSeriesView(25);
    }

    public void scrollDownToClipTitle(String clipTitle, Integer numberOfFlicks) {
        clipTitle(clipTitle).waitForScrolledDownToOnClipsView(numberOfFlicks, Config.StaticProps.NORMAL_SCROLL);
    }

    public void scrollUpToClipTitle(String clipTitle, Integer numberOfFlicks) {
        clipTitle(clipTitle).waitForScrolledUpToOnClipsView(numberOfFlicks);
    }

    public void scrollDownToClipTtl(String clipTitle) {
        clipTitle(clipTitle).waitForScrolledDownTo(30, Config.StaticProps.NORMAL_SCROLL);
    }

    public void tapOnWatchEpisodeButton(String clipTitle, Integer numberOfFlicks) {
        if (!watchEpisodeBtn(clipTitle).isVisible()) {
            scrollDownToClipTitle(clipTitle, numberOfFlicks);
        }
        watchEpisodeBtn(clipTitle).waitForVisible().tap().waitForNotPresentOrVisible();
    }

    public void goBack() {
        backBtn().waitForVisible().tap().waitForNotPresentOrVisible();
    }

    public void waitForClipLoad(String clipTitle) {
        if (videoPlayer.progressSpinner().isVisible()) {
            videoPlayer.progressSpinner().waitForNotPresentOrVisible();
        }
        if (videoPlayer.contentNotAvailableTxt().isVisible()) {
            String message = "The clip " + clipTitle + " was not found or is no longer available. " +
                    "This could be a content issue.";
            throw new ContentException(message);
        } else if (videoPlayer.slowInternetConnectionTxt().isVisible()) {
            String message = "There's something wrong with the internet connection.";
            throw new InternetConnectionException(message);
        }
    }

}
