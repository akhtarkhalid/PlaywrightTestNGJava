package org.aka.gameshop.base;

import com.microsoft.playwright.Page;
import org.aka.gameshop.apppages.SignInPage;
import org.aka.gameshop.factory.BroFactory;
import org.aka.gameshop.apppages.HomePage;
import org.testng.annotations.*;

import java.util.Properties;

public class BaseTest {
    BroFactory playFactory;
    Page page;
    protected Properties prop;
    protected HomePage homePage;
    protected SignInPage signInPage;

    @BeforeTest
    public void setupBrowser(){
        playFactory = new BroFactory();
        prop = playFactory.rtEnvSetup();
        page = playFactory.initBrowser(prop);
        homePage = new HomePage(page);
        signInPage = new SignInPage(page);
    }

    @AfterTest
    public void tearDown(){
        page.context().browser().close();
       // playFactory.tearBrowser();
    }


}
