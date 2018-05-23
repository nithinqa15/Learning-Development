package com.viacom.test.vimn.common.proxy;

import com.viacom.test.core.proxy.ProxyRESTManager;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.util.Config;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ProxyUtils {

	public static void enableBandwidthThrottling(Integer latencyInMS, Integer downstreamBandwidthInBytesPerSecond, Integer upstreamBandwidthInBytesPerSecond) {
		Logger.logMessage("Enabling downstream bandwidth throttling to " + downstreamBandwidthInBytesPerSecond +
				" with upstream bandwith throttling " + upstreamBandwidthInBytesPerSecond +
				" Bytes per second with latency of " + latencyInMS + " milliseconds.");
		ProxyRESTManager.setBandwidth(downstreamBandwidthInBytesPerSecond, upstreamBandwidthInBytesPerSecond);
		ProxyRESTManager.setLatency(latencyInMS);
	}

	public static void disableBandwidthThrottling() {
		ProxyRESTManager.setBandwidth(0, 0);
		ProxyRESTManager.setLatency(0);
	}

	public static String addPragmaHeader() {
		Logger.logConsoleMessage("Adding pragma header for akamai.");
		return "request.headers().add('Pragma', 'akamai-x-get-client-ip,akamai-x-cache-on,"
				+ "akamai-x-cache-remote-on,akamai-x-check-cacheable,akamai-x-get-cache-key,"
				+ "akamai-x-get-extracted-values,akamai-x-get-nonces, akamai-x-get-ssl-client-session-id,"
				+ "akamai-x-get-true-cache-key,akamai-x-serial-no, akamai-x-feo-trace, akamai-x-get-request-id');";
	}

	public static String addRegionHeader(String ipAddress) {
		if (ipAddress != null) {
			Logger.logConsoleMessage("Setting region to '" + ipAddress + "'.");
			return "request.headers().add('X-Forwarded-For', '" + ipAddress + "')";
		}
		return null;
	}

    public static String setURLRewrite(String url, String rewriteURL) {
        Logger.logMessage("Rewriting request from " + url + " to " + rewriteURL);
        return "if (request.getUri().toString().contains('" + url + "') " 
        		+ "|| request.getUri().toString().matches('" + url + "')) "
                + "{ request.setUri(request.getUri().toString().replaceAll('" + url + "', '" + rewriteURL + "')); "
                + "request.headers().set('Host', '" + rewriteURL + "');"
                + "messageInfo.getOriginalRequest().setUri(request.getUri().toString().replaceAll('" + url + "', '" + rewriteURL + "')); }";
    }
	
	public static String setURLRewrite(String url, String rewriteURL, String except) {
        Logger.logMessage("Rewriting request from " + url + " to " + rewriteURL);
        return "if (request.getUri().toString().contains('" + url + "') " 
                + "&& !request.getUri().contains('" + except + "')) "
                + "{request.setUri(request.getUri().toString().replaceAll('" + url + "', '" + rewriteURL + "')); "
                + "request.headers().set('Host', '" + rewriteURL + "');"
                + "messageInfo.getOriginalRequest().setUri(request.getUri().toString().replaceAll('" + url + "', '" + rewriteURL + "')); }";
    }

	public static String rewriteCountryCode() {
		String countryCodeRewrite = "{\"countryCode\":\"XXXXXX\"}";
		return "if (messageInfo.getOriginalUrl().contains('" + Config.StaticProps.MTVUNDERTHETHUMB + "') " +
				"|| messageInfo.getOriginalUrl().contains('" + Config.StaticProps.GEOINFO + "')) { " +
				"contents.setTextContents('" + countryCodeRewrite + "');}";
	}

	public static void clearNetworkLogs() {
		Logger.logMessage("Network Logs Cleared");
		ProxyRESTManager.clearLog();
	}

	public static void resetProxyState() {
		Logger.logMessage("Resetting Proxy State");
		ProxyRESTManager.resetProxy();
	}

	public static String disableChromecast() {
		String chromeCastEnabled = "\"chromeCastEnabled\":true,";
		String chromeCastDisabled = "\"chromeCastEnabled\":false,";

		return "if (contents.getTextContents().toString().contains('" + chromeCastEnabled + "')) { " +
		"contents.setTextContents(contents.getTextContents().toString().replace('" + chromeCastEnabled + "', '" + chromeCastDisabled + "')); }";
	}
	
	public static String disableDisplayLiveTVForAllUsers() {
		String displayLiveTVEnabled = "\"displayLiveTVForAllUsers\":true,";
		String displayLiveTVDisabled = "\"displayLiveTVForAllUsers\":false,";

		return "if (contents.getTextContents().toString().contains('" + displayLiveTVEnabled + "')) { " +
		"contents.setTextContents(contents.getTextContents().toString().replace('" + displayLiveTVEnabled + "', '" + displayLiveTVDisabled + "')); }";
	}
	
	public static String enableMarketing() {
		String marketingDisabled = "\"marketingEnabled\":false,";
		String marketingEnabled = "\"marketingEnabled\":true,";

		return "if (contents.getTextContents().toString().contains('" + marketingDisabled + "')) { " +
		"contents.setTextContents(contents.getTextContents().toString().replace('" + marketingDisabled + "', '" + marketingEnabled + "')); }";
	}

	public static String enableBalaNotifier(String balaLatestUpdatedTimeStamp) {
		String balaTimeStamp = "\"balaLatestUpdatedTimeStamp\":" + balaLatestUpdatedTimeStamp + ",";
		String modifyBalaTimeStamp = "\"balaLatestUpdatedTimeStamp\":8909418801,";
		return "if (contents.getTextContents().toString().contains('" + balaTimeStamp + "')) { " +
				"contents.setTextContents(contents.getTextContents().toString().replace('" + balaTimeStamp + "', '" + modifyBalaTimeStamp + "')); }";
	}

	public static void rewriteResponse(String host, String regex, String replacement) {
		Logger.logConsoleMessage("Rewriting: host " + host + ", target: " + regex + ", replace: " + replacement);
		String responseFilterString = "if (messageInfo.getOriginalUrl().contains('" + host + "') || "
				+ "messageInfo.getOriginalUrl().matches('" + host + "')) "
				+ "{ contents.setTextContents(contents.getTextContents().replaceAll('" + regex + "', '"
				+ replacement + "')); }";
		ProxyRESTManager.applyResponseFilters(responseFilterString);
	}

	public static void rewriteShortform(String toggleValue, String toggleRewrite) {
		ProxyUtils.rewriteResponse(Config.StaticProps.REGEX_MAINFEED_URL, "(?<=\\\"shortForm\\\":)" + toggleValue, toggleRewrite);
	}

	public static void rewriteHomeScreenTemplate(String rewrite) {
		ProxyUtils.rewriteResponse(Config.StaticProps.REGEX_MAINFEED_URL, "(\\\"?<=\\\"\\\"homeScreenTemplate\\\":)\\\"PanoramicCards\\\"", rewrite);
	}

	public static void rewriteTSLA(int milliseconds) {
		String freewheelTargetContent = "\\\"freewheelEnabled\\\":\\\\s*\\\\w+";
		String freewheelReplaceContent = "\\\"freewheelEnabled\\\": true";
		String tslaTargetContent = "\\\"timeSinceLastAd\\\":\\\\s*\\\\d+";
		String tslaReplaceContent = String.format("\\\"timeSinceLastAd\\\": %d", milliseconds);
		ProxyUtils.rewriteResponse(Config.StaticProps.PMT_REQUEST_URL, freewheelTargetContent, freewheelReplaceContent);
		ProxyUtils.rewriteResponse(Config.StaticProps.PMT_REQUEST_URL, tslaTargetContent, tslaReplaceContent);
		Logger.logMessage("TSLA has been set to " + milliseconds + " milliseconds.");
	}

	public static void rewritePromoFeedWithAnInternalErrorResponse() {
		String internalErrorJson = "{" + "	\"envelope\": {" + "		\"version\": \"1.0\","
				+ "		\"name\": \"viacom-standard-json\"" + "	}," + "	\"status\": {"
				+ "		\"text\": \"INTERNAL ERROR\"," + "		\"code\": 503" + "	},"
				+ "	\"messages\": {}" + "}";
		ProxyUtils.rewriteResponse(Config.StaticProps.REGEX_PROMOFEED_URL, ".*?", internalErrorJson);
	}

	public static void rewriteContentUnavailableErrorScreen() {
		String url = ".*mediautilssvcs-a.akamaihd.net.*";
		String videoNotFoundXMLResponse = "<package version=\"1.7.1\">"
				+ "<video><item type=\"text\" code=\"not_found\"> Sorry,this video is not found or no longer avaiable due to date or rights restrictions.</item>"
				+ "</video></package>";
		ProxyUtils.rewriteResponse(url, ".*[\\\\w\\\\s\\\\w+].*", videoNotFoundXMLResponse);
	}

	public static void disableAds() {
		Logger.logMessage("Disabling freewheel in PMT");
		// disable Ads
		String freewheelTargetContent = "\\\"freewheelEnabled\\\":\\\\s*\\\\w+";
		String freewheelReplaceContent = "\\\"freewheelEnabled\\\": false";
		String adServerTargetContent = "\\\"adServer\\\":\\\\s*\\\"\\\\w+\\\"";
		String adServerReplaceContent = "\\\"adServer\\\": \\\"\\\"";

		rewriteResponse(Config.StaticProps.PMT_REQUEST_URL, freewheelTargetContent, freewheelReplaceContent);
		rewriteResponse(Config.StaticProps.PMT_REQUEST_URL, adServerTargetContent, adServerReplaceContent);
	}

	public static void disableTve() {
		Logger.logMessage("Disabling Tve locks");
		// disable TVE
		rewriteResponse(Config.StaticProps.FEED_REGEX, "\"authRequired\":true", "\"authRequired\":false");
	}

    public static Integer getSpecificAdDuration(Integer adSlot) {
        Logger.logMessage("Getting ad duration for adslot " + adSlot);
        List<String> adDurations = new ArrayList<>();
        String durationRegex = "renditionDuration=[\\d]+";
        String response = ProxyLogUtils.getResponse(Config.StaticProps.FREEWHEEL_REQUEST_URL);
        Pattern pattern = Pattern.compile(durationRegex);
        Matcher matcher = pattern.matcher(response);
        while (matcher.find()) {
            adDurations.add(matcher.group().replaceAll("\\D+",""));
        }
        return Integer.parseInt(adDurations.get(adSlot));
    }

    public static List<String> getAdsDurations() {
        Logger.logMessage("Getting ad durations");
        List<String> adDurations = new ArrayList<>();
        String durationRegex = "renditionDuration=[\\d]+";
        String response = ProxyLogUtils.getResponse(Config.StaticProps.FREEWHEEL_REQUEST_URL);
        Pattern pattern = Pattern.compile(durationRegex);
        Matcher matcher = pattern.matcher(response);
        while (matcher.find()) {
            adDurations.add(matcher.group().replaceAll("\\D+",""));
        }
        return adDurations;
    }
    
	public static String enableBackgroundServiceVideo() {
		String backgroundServiceVideoDisabled = "\"backgroundServiceVideoEnabled\":false,";
		String backgroundServiceVideoEnabled = "\"backgroundServiceVideoEnabled\":true,";
		return "if (contents.getTextContents().toString().contains('" + backgroundServiceVideoDisabled + "')) { " +
				"contents.setTextContents('" + backgroundServiceVideoDisabled + "', '" + backgroundServiceVideoEnabled + "'); }";
	}
}