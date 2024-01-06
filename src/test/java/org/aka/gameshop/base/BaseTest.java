package org.aka.gameshop.base;

import com.microsoft.playwright.Page;
import org.aka.gameshop.apppages.SignInPage;
import org.aka.gameshop.factory.BroFactory;
import org.aka.gameshop.apppages.HomeHeaderPage;
import org.aka.gameshop.factory.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.testng.annotations.*;

import java.io.File;
import java.util.Properties;

import static org.aka.gameshop.factory.Utils.printLogs;

public class BaseTest {
    BroFactory playFactory;
    Utils utils;
    Page page;
    protected Properties prop;
    protected HomeHeaderPage homePage;
    protected SignInPage signInPage;

    @BeforeTest
    public void testSetup(){
        try{
        playFactory = new BroFactory();
        prop = Utils.rtEnvSetup();
        page = playFactory.initBrowser(prop);
        homePage = new HomeHeaderPage(page);

        LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
        File file = new File("./src/test/resources/configuration/log4j2.xml");
        loggerContext.setConfigLocation(file.toURI());
        }catch(Exception e){
            printLogs("Error while setting up test..\n"+e.getMessage(),true);
        }
    }

    @AfterTest
    public void tearDown(){
        //page.context().browser().close();
       playFactory.tearBrowser();
    }


}
