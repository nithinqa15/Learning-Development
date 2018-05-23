package com.viacom.test.vimn.common;

import org.testng.annotations.BeforeMethod;

import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

public class BaseTest {

	protected String runParams;
	
	public void setRunParams(String runParams) {
    	this.runParams = runParams;
    }
    
    @BeforeMethod(alwaysRun = true)
    public void initTest() {
        TestRun.init(runParams);
        if (FeedFactory.getAppMainFeedURL() == null) {
            FeedFactory.setFeeds();
        }
    }
}
