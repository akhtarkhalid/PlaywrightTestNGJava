package org.aka.gameshop.tests;

import org.aka.gameshop.appData.AppData;
import org.aka.gameshop.base.BaseTest;
import org.aka.gameshop.factory.CustomAnnotations.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.aka.gameshop.factory.Utils;

public class HomePageTest extends BaseTest {


    @TestName("Verify that correct home page title is displayed")
    @Test(priority = 1)
    public void homeTitleTest() {
        String homePageTitle = homePage.getHomePageTitle();
        System.out.printf("Home page title is %s", homePageTitle);
        Utils.printLogs("Home title printed successfully...");
        Assert.assertTrue(homePageTitle.contains(AppData.HOME_PAGE_TITLE), "Actual Title: " + homePageTitle
                + "\nExpected to contain :" + AppData.HOME_PAGE_TITLE);

    }

    @TestName("Check if the URL of home page is correct")
    @Test(priority = 2)
    public void homeURLTest() {
        String homePageURL = homePage.getHomePageURL();
        System.out.printf("Home page url is %s", homePageURL);
        Assert.fail("Deliberate fail");
        //Assert.assertEquals(homePageURL, prop.getProperty("url"));
    }

    @TestName("Verify that search functionality is working fine")
    @Test(dataProvider = "getSearchData", priority = 3)
    public void homeSearchTest(String productName) {
        String searchResult = homePage.searchForProduct(productName);
        System.out.printf("Searching for product %s", searchResult);
      //  Assert.fail("Test For Screenshot");
        Assert.assertTrue(searchResult.contains(productName), "Search Result: " + searchResult
                             + "\nExpected to contain :" + productName);
    }

    @DataProvider
    public Object[][] getSearchData() {
        return new Object[][]{{"PS5"}, {"PS4"}, {"Nintendo"}};
    }
}
