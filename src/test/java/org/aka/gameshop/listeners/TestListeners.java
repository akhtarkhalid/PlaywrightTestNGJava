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
import java.util.Arrays;

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
        extentTest = extentReports.createTest(testName);
        localTest.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
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

        System.out.println((" failed!"));
        localTest.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(BroFactory.takeScreenshot(testName),result.getMethod().getMethodName()).build());

    }

    // Other methods of ITestListener

    @Override
    public void onFinish(ITestContext context) {

            System.out.println(("Test Suite is ending!"));
        extentReports.flush();
        localTest.remove();
        System.out.println("Test execution finished");
    }

}
