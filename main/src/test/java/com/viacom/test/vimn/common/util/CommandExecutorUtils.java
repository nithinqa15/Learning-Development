package com.viacom.test.vimn.common.util;

import com.viacom.test.core.lab.CommandExecutor;
import com.viacom.test.core.lab.GridManager;
import com.viacom.test.core.lab.LabDeviceManager;
import com.viacom.test.core.util.Constants;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandExecutorUtils {

    private static String versionCode;
    private static String deviceDate;

    private static List<String> execCommand(String command) {
        List<String> finalListString = new ArrayList<>();
        Runtime run = Runtime.getRuntime();
        try {
            Process process = run.exec(command);
            process.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = bufferedReader.readLine();
            while (line != null) {
                finalListString.add(line);
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalListString;
    }

    private static void setiOSAppVersionCode() {
        String command;
        String appBundleID = TestRun.getAppPackageID();
        if (Config.ConfigProps.RUN_AS_FACTORY) {
        	String activeMachineIP = GridManager.getRunningSessionIP();
            command = LabDeviceManager.getIDeviceInstallerPath(activeMachineIP) + " -l";
            String[] bufferReader = CommandExecutor.execCommand(command, activeMachineIP, null).split(",");
            for (int i = 0; i < bufferReader.length; i++) {
                if (bufferReader[i].contains(appBundleID)) {
                    versionCode = bufferReader[i+1].split("\"")[1].trim();
                }
            }
        } else {
            command = "ideviceinstaller -l";
            List<String> listOfApps = execCommand(command);
            listOfApps.forEach(app -> {
                if (app.contains(appBundleID)) {
                    versionCode = app.split(",")[1].trim().replace("\"", "");
                }
            });
        }
    }

    private static void setAndroidAppVersionCode() {
        String command;
        String appPackage = TestRun.getAppPackageID();
        if (Config.ConfigProps.RUN_AS_FACTORY) {
            String activeMachineIP = GridManager.getRunningSessionIP();
            command = Constants.ADB_PATH + " shell dumpsys package " + appPackage + " | grep versionCode";
            versionCode = CommandExecutor
                    .execCommand(command, activeMachineIP, null)
                    .split("=")[1]
                    .split(" ")[0];
        } else {
            command = "adb shell dumpsys package " + appPackage + " | grep versionCode";
            versionCode = execCommand(command)
                    .get(0)
                    .split("=")[1]
                    .split(" ")[0];
        }
    }

    public static String getVersionCode() {
        if (versionCode == null) {
            if (TestRun.isAndroid()) {
                setAndroidAppVersionCode();
            } else {
                setiOSAppVersionCode();
            }
        }
        return versionCode;
    }

    public static String getVersionName() {
        String versionName = "";
        String appPackage = new Locator().getAppPackage();
        String command = "adb shell dumpsys package " + appPackage + " | grep versionName";
        if (!Config.ConfigProps.RUN_AS_FACTORY) {
            versionName = execCommand(command).get(0);
        } else {
            String activeMachineIP = GridManager.getRunningSessionIP();
            if (TestRun.isAndroid()) {
                command = Constants.ADB_PATH + " shell dumpsys package " + appPackage + " | grep versionName";
            }
            versionName = CommandExecutor.execCommand(command, activeMachineIP, null);
        }
        return versionName.split("=")[1].trim();
    }

    private static void setAndroidDeviceDate() {
        String command;
        if (Config.ConfigProps.RUN_AS_FACTORY) {
            command = Constants.ADB_PATH + " shell date";
            String activeMachineIP = GridManager.getRunningSessionIP();
            deviceDate = CommandExecutor.execCommand(command, activeMachineIP, null);
        } else {
            command = "adb shell date";
            deviceDate = execCommand(command).get(0);
        }
    }

    private static void setiOSDeviceDate() {
        String command;
        if (Config.ConfigProps.RUN_AS_FACTORY) {
            String activeMachineIP = GridManager.getRunningSessionIP();
            command = LabDeviceManager.getBinaryPathLocation("idevicedate", activeMachineIP);
            deviceDate = CommandExecutor.execCommand(command, activeMachineIP, null);
        } else {
            command = "idevicedate";
            deviceDate = execCommand(command).get(0);
        }
    }

    public static String getDeviceDate() {
        if (TestRun.isAndroid()) {
            setAndroidDeviceDate();
        } else {
            setiOSDeviceDate();
        }
        return deviceDate;
    }
    
    public static String getiOSProductVersion() {
	   String command = "ideviceinfo -s | grep ProductVersion";
	   return execCommand(command).get(0);
    }
}
