package com.viacom.test.vimn.common.proxy;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.core.proxy.ProxyRESTManager;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.ConfigProps;

import io.appium.java_client.AppiumDriver;
import de.sstoehr.harreader.model.HarEntry;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MrssLogUtils {

    static String mrssFeedUrl;
	public static long startTime;
	public static long endTime; 
	public static int waitTime;
    public static boolean IsURLPatternFoundInProxyLog = false;

    @SuppressWarnings("rawtypes")
	public static String getMrssFeedUrl(String episodeId) {
    	startTime = System.currentTimeMillis();
        new FluentWait<AppiumDriver>(DriverManager.getAppiumDriver()).withTimeout(Integer.parseInt(ConfigProps.SERVER_COMMAND_TIMEOUT), TimeUnit.SECONDS)
                .pollingEvery(Config.ConfigProps.POLLING_TIME, TimeUnit.MILLISECONDS).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                Boolean isSuccessful = false;
                mrssFeedUrl = null;
                String urlPattern = Config.StaticProps.MRSS_BASE_URL;
                List<HarEntry> logEntries = ProxyRESTManager.getLogEntries();
                if (!logEntries.isEmpty()) {
                    Iterator<HarEntry> iterator = logEntries.iterator();
                    while (iterator.hasNext()) {
                        HarEntry entry = iterator.next();
                        if (entry.getRequest().getUrl().contains(urlPattern)) {
                        	IsURLPatternFoundInProxyLog = true;
                        }
                        try {
                            if (entry.getRequest().getUrl().contains(urlPattern) && entry.getRequest().getUrl().contains(episodeId)) {
                                mrssFeedUrl = entry.getRequest().getUrl();
                                if (!(mrssFeedUrl==null)) {
                                    isSuccessful = true;
                                    break;
                                }
                            }
                        } catch (NullPointerException e) {
                            // ignore as bmp throws null pointer exception in the event a request/response doesn't
                            // have the piece of data in the har we're checking for... known bmp bug...
                        }
                    }
                	endTime = System.currentTimeMillis();
					waitTime = (int) (endTime-startTime)/1000;
					DriverManager.getAppiumDriver().getDeviceTime(); //To prevent Driver TimeoutException
					//To check url Pattern available
					if (!IsURLPatternFoundInProxyLog && mrssFeedUrl==null && waitTime >= ConfigProps.MAX_WAIT_TIME) { //Condition check before timeout
						Assert.fail(urlPattern + " Not found in proxy log");
					}
					//To check unique identifier available
					if (IsURLPatternFoundInProxyLog && mrssFeedUrl==null && waitTime >= ConfigProps.MAX_WAIT_TIME) { //Condition check before timeout
						Assert.fail(urlPattern + " found in proxy log but " + episodeId + " Not available in log entry");
					} 
                } else {
					//To check proxy setup
					Assert.fail("Proxy Log not found, Local Run? - Check your proxy setup OR MQE Lab Run? - "
							+ "Proxy spin up issue, Please check proxy and bmp log");
                }
                return isSuccessful;
            }
        });
        return mrssFeedUrl;
    }
}
