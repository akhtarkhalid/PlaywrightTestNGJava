package org.aka.gameshop.base;

import com.microsoft.playwright.Page;
import org.aka.gameshop.factory.PlaywrightFactory;
import org.aka.gameshop.factory.pages.HomePage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.util.Properties;

public class BaseTest {
    PlaywrightFactory playFactory;
    Page page;
    protected Properties prop;
    protected HomePage homePage;

    @BeforeMethod
    public void setupBrowser(){
        playFactory = new PlaywrightFactory();
        prop = playFactory.rtEnvSetup();
        page = playFactory.initBrowser(prop);
        homePage = new HomePage(page);
    }

    @AfterMethod
    public void tearDown(){
        page.context().browser().close();
       // playFactory.tearBrowser();
    }


}
