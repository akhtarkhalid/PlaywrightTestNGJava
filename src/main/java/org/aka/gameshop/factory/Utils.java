package org.aka.gameshop.factory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.options.ViewportSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

public class Utils {
    private static Properties properties;
    public static final Logger logger = LoggerFactory.getLogger("tests");

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";

    public static void printLogs(String message, Boolean isError) {
        if (isError)
            logger.error(RED+message+RESET);
        else
            logger.info(GREEN+message+RESET);
    }

    public static void printLogs(String message) {
        logger.info(GREEN+message+RESET);
    }


    public static ExtentReports getReporter() {
        ExtentReports extentReports = new ExtentReports();
        ;
        try {
            Properties properties1 = rtEnvSetup();
            String reportLocation = System.getProperty("user.dir") + properties1.getProperty("reportLocation") + LocalDate.now() + "/report/" + properties1.getProperty("reportName");
            printLogs("Reports will be saved at : " + reportLocation);
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportLocation);
            sparkReporter.config().setReportName("Results for " + LocalDate.now());
            sparkReporter.config().setDocumentTitle("GameTheShop automation test results");
            printLogs("Report Name and Title set successfully..");
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("Executed by: ", System.getProperty("user.name"));
            extentReports.createTest(reportLocation);
            printLogs("Report Test created successfully..");

        } catch (Exception exception) {
            printLogs(exception.getMessage(), true);
            printLogs("Error while initializing Extent Report..", true);
        }
        return extentReports;
    }

    public static Properties rtEnvSetup() {

        try {
            if (properties == null) {
                properties = new Properties();
                printLogs("Reading Properties file from [/src/test/resources/configuration/runtime.properties]..");
                FileInputStream fi = new FileInputStream("./src/test/resources/configuration/runtime.properties");
                properties.load(fi);
                printLogs("Properties loaded successfully..");
            }
        } catch (IOException propLoadExcepion) {
            printLogs(propLoadExcepion.getMessage(), true);
            printLogs("Error while loading Properties..", true);
        }
        return properties;
    }
    public static ViewportSize getScreenSize(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();

        return new ViewportSize((int) dimension.getWidth(), (int) dimension.getHeight());
    }
}


