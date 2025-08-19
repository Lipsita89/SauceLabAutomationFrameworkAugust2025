package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util {
    private static final Logger log = LogManager.getLogger(Util.class.getName());

    public static void sleep(long msec, String info) {
        if (info != null) {
            log.info("Waiting " + (msec * .001) + " seconds :: " + info);
        }
        try {
            Thread.sleep(msec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getScreenshotName(String methodName, String browserName) {
        String localDateTime = getCurrentDateTime();
        StringBuilder name = new StringBuilder().append(browserName)
                .append("_")
                .append(methodName)
                .append("_")
                .append(localDateTime)
                .append(".png");
        return name.toString();
    }


    public static String getCurrentDateTime() {
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(
                "MM/dd/yyyy HH:mm:ss");
        String date = formatter.format(currentDate.getTime()).replace("/", "_");
        date = date.replace(":", "_");
        log.info("Current Date and Time :: " + date);
        return date;
    }

    public static boolean verifyTextMatch(String actualText, String expText) {
        if (actualText.equals(expText)) {
            log.info("Actual Text From Web Application UI   --> : " + actualText);
            log.info("Expected Text From Web Application UI --> : " + expText);
            log.info("### Verification MATCHED !!!");
            return true;
        } else {
            log.error("Actual Text From Web Application UI   --> : " + actualText);
            log.error("Expected Text From Web Application UI --> : " + expText);
            log.error("### Verification DOES NOT MATCH !!!");
            return false;
        }
    }

}