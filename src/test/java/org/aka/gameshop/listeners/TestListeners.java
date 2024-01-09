package org.aka.gameshop.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Page;
import org.aka.gameshop.factory.BroFactory;
import org.aka.gameshop.factory.CustomAnnotations;
import org.aka.gameshop.factory.Utils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Properties;

import static org.aka.gameshop.factory.Utils.getProperties;
import static org.aka.gameshop.factory.Utils.printLogs;

public class TestListeners implements ITestListener {

    ExtentReports extentReports = Utils.getReporter();
    ExtentTest extentTest;
    ThreadLocal<ExtentTest> localTest = new ThreadLocal<>();
    //ThreadLocal<String> localTestName = null;
    @Override
    public void onTestStart(ITestResult result) {
        String testName ="";
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        if (method.isAnnotationPresent(CustomAnnotations.TestName.class)) {
            CustomAnnotations.TestName testNameValue = method.getAnnotation(CustomAnnotations.TestName.class);
            testName = testNameValue.value();

        }else{
            testName="Test name missing for method :"+method.getName();
        }
        printLogs(testName+" Started..");
        extentTest = extentReports.createTest(testName);
        localTest.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName ="";
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        if (method.isAnnotationPresent(CustomAnnotations.TestName.class)) {
            CustomAnnotations.TestName testNameValue = method.getAnnotation(CustomAnnotations.TestName.class);
            testName = testNameValue.value();

        }else{
            testName="Test name missing for method :"+method.getName();
        }
        printLogs(testName+" Completed successrully..");
        localTest.get().log(Status.PASS," - Passed");
//        Page listenerPage;
//        try {
//           listenerPage = (Page)result.getTestClass().getRealClass().getField("Page").get(result.getInstance());
//        } catch (IllegalAccessException | NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(listenerPage.title());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName ="";
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        if (method.isAnnotationPresent(CustomAnnotations.TestName.class)) {
            CustomAnnotations.TestName testNameValue = method.getAnnotation(CustomAnnotations.TestName.class);
            testName = testNameValue.value();
        }else{
            testName="Test name missing for method :"+method.getName();
        }

        printLogs(testName+" Failed.. Adding screenshot to failed report.",true);
        localTest.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(BroFactory.takeScreenshot(testName),result.getMethod().getMethodName()).build());
        printLogs("Screenshot added successfully..");
    }

    // Other methods of ITestListener

    @Override
    public void onFinish(ITestContext context) {

        printLogs("Test ended!.. Generating Report.. ");
        extentReports.flush();
        printLogs("Report Generated successfully.. ");
        localTest.remove();
        printLogs("Test execution finished!..");
        Properties properties = getProperties();
        printLogs("Report saved @ " + System.getProperty("user.dir") + properties.getProperty("reportLocation") + LocalDate.now() + "\\report\\" + properties.getProperty("reportName"));
        printLogs("Logs saved @ " + System.getProperty("user.dir") + "\\PlaywrightTestNGJava\\gameshoplog.log");
    }

}
