package org.aka.gameshop.tests;

import org.aka.gameshop.appData.AppData;
import org.aka.gameshop.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {


    @Test(priority = 1)
    public void homeURLTest(){
        String homePageURL = homePage.getHomePageURL();
        System.out.printf("Home page url is %s",homePageURL );
        Assert.assertEquals(homePageURL,prop.getProperty("url"));
    }
    @Test(priority = 2)
    public void homeTitleTest(){
        String homePageTitle = homePage.getHomePageTitle();
        System.out.printf("Home page title is %s",homePageTitle );
        Assert.assertTrue(homePageTitle.contains(AppData.HOME_PAGE_TITLE));
    }

    @Test(dataProvider = "getSearchData",priority = 3)
    public void homeSearchTest(String productName){
        String searchResult = homePage.searchForProduct(productName);
        System.out.printf("Searching for product %s",searchResult );
        Assert.assertTrue(searchResult.contains(productName));
    }

    @DataProvider
    public Object[][] getSearchData(){
        return new Object[][] {{"PS5"},{"PS4"},{"Nintendo"}};
    }


}
