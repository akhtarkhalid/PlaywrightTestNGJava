package org.aka.gameshop.base;

import com.microsoft.playwright.Page;
import org.aka.gameshop.apppages.SignInPage;
import org.aka.gameshop.factory.BroFactory;
import org.aka.gameshop.apppages.HomeHeaderPage;
import org.testng.annotations.*;

import java.util.Properties;

public class BaseTest {
    BroFactory playFactory;
    Page page;
    protected Properties prop;
    protected HomeHeaderPage homePage;
    protected SignInPage signInPage;

    @BeforeTest
    public void setupBrowser(){
        playFactory = new BroFactory();
        prop = playFactory.rtEnvSetup();
        page = playFactory.initBrowser(prop);
        homePage = new HomeHeaderPage(page);
    }

    @AfterTest
    public void tearDown(){
        page.context().browser().close();
       // playFactory.tearBrowser();
    }


}
