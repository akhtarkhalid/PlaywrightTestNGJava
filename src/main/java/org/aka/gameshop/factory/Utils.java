package org.aka.gameshop.factory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

public class Utils {
    public static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static void printLogs(String message,Boolean isError){
        if(isError)
            logger.error(message);
        else
            logger.info(message);
    }
    public static void printLogs(String message){
            logger.info(message);
    }

    public static ExtentReports getReporter() {
        Properties properties1 = rtEnvSetup();
        String reportLocation = System.getProperty("user.dir") + properties1.getProperty("reportLocation")+ LocalDate.now()+"/report/"+properties1.getProperty("reportName");
        System.out.println("Reports will be saved at : "+reportLocation);
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportLocation);
        sparkReporter.config().setReportName("Results for "+ LocalDate.now());
        sparkReporter.config().setDocumentTitle("GameTheShop automation test results");

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Executed by: ",System.getProperty("user.name"));
        extentReports.createTest(reportLocation);
        return extentReports;
    }

    public static Properties rtEnvSetup(){
        Properties properties = new Properties();
        try {
            FileInputStream fi = new FileInputStream("./src/test/resources/configuration/runtime.properties");
            properties.load(fi);
        }catch(IOException propLoadExcepion){
            System.out.println(propLoadExcepion.getMessage());
        }
        return properties;
    }
}
