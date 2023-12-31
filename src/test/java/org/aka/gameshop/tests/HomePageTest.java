package org.aka.gameshop.tests;

import org.aka.gameshop.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {


    @Test
    public void ahomeTitleTest(){
        String homePageTitle = homePage.getHomePageTitle();
        System.out.printf("Home page title is %s",homePageTitle );
        Assert.assertTrue(homePageTitle.contains("Games Online in India"));
    }

    @Test
    public void bhomeURLTest(){
        String homePageURL = homePage.getHomePageURL();
        System.out.printf("Home page url is %s",homePageURL );
        Assert.assertEquals(homePageURL,"https://www.gamestheshop.com/");
    }

    @Test(dataProvider = "getSearchData" )
    public void chomeSearchTest(String productName){
        String searchResult = homePage.searchForProduct(productName);
        System.out.printf("Searching for product %s",searchResult );
        Assert.assertTrue(searchResult.contains(productName));
    }

    @DataProvider
    public Object[][] getSearchData(){
        return new Object[][] {{"PS5"},{"PS4"},{"Nintendo"}};
    }


}
