package org.aka.gameshop.base;

import com.microsoft.playwright.Page;
import org.aka.gameshop.factory.BroFactory;
import org.aka.gameshop.apppages.HomePage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Properties;

public class BaseTest {
    BroFactory playFactory;
    Page page;
    protected Properties prop;
    protected HomePage homePage;

    @BeforeMethod
    public void setupBrowser(){
        playFactory = new BroFactory();
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
