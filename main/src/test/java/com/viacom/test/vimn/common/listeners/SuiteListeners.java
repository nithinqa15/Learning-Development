package com.viacom.test.vimn.common.listeners;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.viacom.test.vimn.common.util.*;

import org.testng.ISuite;
import org.testng.ISuiteListener;

import com.viacom.test.core.lab.GlobalInstall;
import com.viacom.test.core.report.AllureManager;
import com.viacom.test.core.report.HueLighting;
import com.viacom.test.core.testrail.TestRailManager;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config.ConfigProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import org.testng.xml.XmlTest;

public class SuiteListeners extends BaseTest implements ISuiteListener {

	private AllureManager allureManager;

	@Override
	public void onStart(ISuite suite) {
		// disabling log4j output
		Logger.disableLog4JConsoleOutput();

		// initiate test run parameters
		TestRun.init("");

		// download the app package
		if (ConfigProps.RUN_AS_FACTORY) {
			GlobalInstall.downloadAppPackage(ConfigProps.APP_URL);
		}

		// set and clean the allure report directory
		allureManager = new AllureManager();
		String reportPath = TestRun.isAndroid() ? ConfigProps.ALLURE_RESULTS_ANDROID_PATH : ConfigProps.ALLURE_RESULTS_IOS_PATH;
		allureManager.setReportDir(reportPath).deleteReportDir();

		// set the hue lighting to indicate a test suite start
		if (ConfigProps.SET_HUE_LIGHTS) {
			String lightID = TestRun.isIos() ? ConfigProps.HUE_IOS_LIGHT_ID : ConfigProps.HUE_ANDROID_LIGHT_ID;
			HueLighting.setTestSuiteStart(lightID);
		}

		if (ConfigProps.SEND_REPORT_CHAT) {
			String includedGroups = "";
			List<XmlTest> tests = suite.getXmlSuite().getTests();
			for (XmlTest test : tests) {
				includedGroups = String.valueOf(test.getIncludedGroups());
				break;
			}
			String version = TestRun.getAppVersion();
			String text = "New test execution has started on: " + "\n" + "App Version: " + version + "\n" + "Test Group: " + includedGroups ;
			String payload = "{\"text\":\"" + text + "\"}";
			//Slack.sendChat(payload);
			HipChatRoom.sendChat(includedGroups);
		}

		// start testrail suite run
		TestRailManager.startNewSuiteRun(StaticProps.TESTRAIL_PROJECT_ID, suite);
	}

	@Override
	public void onFinish(ISuite suite) {

		// generate the allure report
		LinkedHashMap<String, String> environmentProps = new LinkedHashMap<String, String>();
		environmentProps.put("MobileOS", TestRun.getMobileOS().value());
		environmentProps.put("BuildNumber", ConfigProps.BUILD_NUMBER);
		environmentProps.put("AppPackage", ConfigProps.APP_URL);
		environmentProps.put("AppName", ConfigProps.APP_NAME);
		environmentProps.put("APIVersion", ConfigProps.API_VERSION);
		environmentProps.put("AppVersion", TestRun.isIos() ? ConfigProps.IOS_APP_VERSION : ConfigProps.ANDROID_APP_VERSION);
		environmentProps.put("ContentDomain", ConfigProps.CONTENT_DOMAIN);

		SimpleDateFormat attachmentDateTimeFormat = new SimpleDateFormat(StaticProps.ATTACHMENT_DATE_FORMAT);
		String reportDirName = attachmentDateTimeFormat.format(new Date()) + "/allure/";
		String jenkinsReportDir = TestRun.isAndroid() ? ConfigProps.JENKINS_ANDROID_WORKSPACE_FILE_PATH : ConfigProps.JENKINS_IOS_WORKSPACE_FILE_PATH;
		jenkinsReportDir = jenkinsReportDir + reportDirName;
		String jenkinsReportUrl = TestRun.isAndroid() ? ConfigProps.JENKINS_ANDROID_WORKSPACE_URL : ConfigProps.JENKINS_IOS_WORKSPACE_URL;
		jenkinsReportUrl = jenkinsReportUrl + reportDirName + "index.html";

		// check for failures to update on a successful retry
		List<String> failuresToUpdate = new ArrayList<String>();
		for (String failure : TestIDs.getFailedTestIds()) {
			if (TestIDs.getPassedTestIds().contains(failure)) {
				failuresToUpdate.add(failure);
			}
		}

		allureManager.createAllurePropertyFile("environment.properties", environmentProps)
				.removePendingTests()
				.setBrokenTestsAsFailures()
				.setRepeatFailuresAsBroken(failuresToUpdate)
				.setFailedBeforeMethodsBroken()
				.setTestIDsAsTestName(StaticProps.TEST_ID.toLowerCase())
				.setSuiteName(ConfigProps.APPLICATION_TITLE)
				.generateReport();

		String s3ReportUrl = null;
		if (ConfigProps.UPLOAD_REPORT_JENKINS) {
			s3ReportUrl = allureManager.uploadReportToS3(ConfigProps.S3_BUCKET_NAME);
		}

		// get the test counts
		Integer passCount = allureManager.getPassedCount();
		Integer failCount = allureManager.getFailureCount();
		Integer skipCount = allureManager.getSkippedCount();

		// send auto email with report attachment link
		if (ConfigProps.SEND_REPORT_AUTOEMAILS) {
			AppLib.sendResultEmail(s3ReportUrl, passCount, failCount, skipCount);
			Logger.logConsoleMessage("Successfully sent report result email.");
		} else {
			Logger.logConsoleMessage("Report result email not sent per configuration setting.");
		}

		// send chat report
		if (ConfigProps.SEND_REPORT_CHAT) {
			//AppLib.sendResultSlack(s3ReportUrl, passCount, failCount, skipCount);
			AppLib.sendResultHipChat(s3ReportUrl, passCount, failCount, skipCount);
		} else {
			Logger.logConsoleMessage("Report was not sent to chat service per configuration setting.");
		}

		// set hue lighting
		HueLighting.setTestSuiteResult(passCount, failCount);
	}

}