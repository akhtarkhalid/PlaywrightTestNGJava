package org.aka.gameshop.tests;

import org.aka.gameshop.appData.AppData;
import org.aka.gameshop.base.BaseTest;
import org.aka.gameshop.factory.CustomAnnotations.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.aka.gameshop.factory.Utils;

import static org.aka.gameshop.factory.Utils.printLogs;

public class HomePageTest extends BaseTest {


    @TestName("Verify that correct home page title is displayed")
    @Test(priority = 1)
    public void homeTitleTest() {
        String homePageTitle = homePage.getHomePageTitle();
        printLogs("Home page title is :"+homePageTitle);
        boolean testPass= homePageTitle.contains(AppData.HOME_PAGE_TITLE);

        if(testPass)
            printLogs("Home Page Title " + homePageTitle+" is correct.");
        else
            printLogs("Home Page Title is not correct. Actual Title:" + homePageTitle+
                    "\nExpected to contain :" + AppData.HOME_PAGE_TITLE,true);

        Assert.assertTrue(testPass);
    }

    @TestName("Check if the URL of home page is correct")
    @Test(priority = 2)
    public void homeURLTest() {
        String homePageURL = homePage.getHomePageURL();
        printLogs("Home page url is :"+homePageURL);
        boolean testPass= homePageURL.equalsIgnoreCase(prop.getProperty("url"));
        if(testPass)
            printLogs("Home Page URL " + homePageURL+" is correct.");
        else
            printLogs("Home Page URL is not correct. Actual:"+homePageURL +
                        "\n Expected:"+prop.getProperty("url") ,true);

        Assert.assertTrue(testPass);
    }

    @TestName("Verify that search functionality is working fine")
    @Test(dataProvider = "getSearchData", priority = 3)
    public void homeSearchTest(String productName) {
        String searchResult = homePage.searchForProduct(productName);
        printLogs("Searching for product : "+ searchResult);
        boolean testPass= searchResult.contains(productName);
        if(testPass)
            printLogs("Correct search result displayed for " + productName);
        else
            printLogs("Search Result: " + searchResult
                    + "\nExpected to contain : " + productName,true);

        Assert.assertTrue(testPass);
    }

    @DataProvider
    public Object[][] getSearchData() {
        return new Object[][]{{"PS5"}, {"PS4"}, {"Nintendo"}};
    }
}
