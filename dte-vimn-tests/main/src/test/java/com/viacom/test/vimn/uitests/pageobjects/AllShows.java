package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

public class AllShows {

    private Locator locator;

    // PAGE OBJECT CONSTRUCTOR
    public AllShows() {
        locator = new Locator();
    }

    // PAGE OBJECT IDENTIFIERS
    public Interact allShowsBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact allShowsSeriesTitleText(String seriesTitle) {
        return new Interact(locator.getLocator(seriesTitle), locator.locatorData);
    }

    public Interact seriesTile(String seriesTitle) {
        return new Interact(locator.getLocator(seriesTitle), locator.locatorData);
    }

    public Interact allShowsBackBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public AllShows scrollDownToSeriesTile(String seriesTtl) {
        seriesTile(seriesTtl).waitForScrolledDownTo(30, Config.StaticProps.NORMAL_SCROLL);
        return this;
    }

    public AllShows scrollUpToSeriesTile(String seriesTtl) {
        seriesTile(seriesTtl).waitForScrolledUpTo(30, Config.StaticProps.NORMAL_SCROLL);
        return this;
    }
    
    public AllShows scrollDownToSeriesTile(int numberOfScroll, String seriesTtl) {
        seriesTile(seriesTtl).waitForScrolledDownTo(numberOfScroll, Config.StaticProps.NORMAL_SCROLL);
        return this;
    }

    public AllShows scrollUpToSeriesTile(int numberOfScroll, String seriesTtl) {
        seriesTile(seriesTtl).waitForScrolledUpTo(numberOfScroll, Config.StaticProps.NORMAL_SCROLL);
        return this;
    }

    public void enterAllShows() {
        allShowsBtn().waitForVisible().tap().waitForNotPresentOrVisible();
    }

    public void exitAllShows() {
        allShowsBackBtn().waitForVisible().tap().waitForNotPresentOrVisible();
    }

    public AllShows tapSeriesTile(String seriesTitle) {
        seriesTile(seriesTitle).waitForVisible().tap().waitForNotPresentOrVisible();
        return this;
    }

    public void enterSeriesView(String seriesTitle) {
        scrollDownToSeriesTile(seriesTitle);
        tapSeriesTile(seriesTitle);
    }
}
