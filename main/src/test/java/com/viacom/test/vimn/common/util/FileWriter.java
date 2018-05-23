package com.viacom.test.vimn.common.util;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.core.util.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter {

    /**
     * Helper method that write xml files for UI debug purposes.
     */
    public static void dumpXml(String desiredFileName) {

        final byte[] xmlContentInBytes = getXMLinBytes();
        final String finalFileName = getFinalFileName(desiredFileName);

        Logger.logMessage("Preparing to write XML file");
        FileOutputStream fop = null;
        File file;

        try {
            String xmlDump = Config.StaticProps.XML_DUMP_FOLDER + finalFileName;

            file = new File(String.valueOf(xmlDump));
            fop = new FileOutputStream(file);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                Logger.logMessage("Creating XML file");
                file.createNewFile();
            }

            // get the content in bytes
            Logger.logMessage("Writing XML file");
            fop.write(xmlContentInBytes);
            fop.flush();
            fop.close();

            Logger.logMessage("Done");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static byte[] getXMLinBytes() {
        byte[] xmlContentInBytes;
        if (TestRun.isAndroid()) {
            xmlContentInBytes = DriverManager.getAndroidDriver().getPageSource().getBytes();
        } else {
            xmlContentInBytes = DriverManager.getAppiumDriver().getPageSource().getBytes();
        }
        return xmlContentInBytes;
    }

    private static String getFinalFileName(String desiredFileName) {
        String finalFileName = "";
        if (TestRun.isAndroid()) {
            finalFileName = desiredFileName + "-Android.xml";
        } else {
            finalFileName = desiredFileName + "-iOS.xml";

        }
        return finalFileName;
    }
}
