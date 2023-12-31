package org.aka.gameshop.factory;

import com.microsoft.playwright.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class PlaywrightFactory {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;
    Properties properties;
    BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();

    public Page initBrowser(Properties properties) {
        String browserName = properties.getProperty("browser").trim();
        boolean headLess =Boolean.parseBoolean(properties.getProperty("headless"));
        if (page == null) {
            playwright = Playwright.create();
            if(!headLess)
                launchOptions.setHeadless(false);
            if (browserName.equalsIgnoreCase("chrome"))
                launchOptions.setChannel("chrome");
            else if (browserName.equalsIgnoreCase("edge"))
                launchOptions.setChannel("edge");

            browser = switch (browserName.toLowerCase()) {
                case "chromium" -> playwright.chromium().launch(launchOptions);
                case "firefox" -> playwright.firefox().launch(launchOptions);
                case "safari" -> playwright.webkit().launch(launchOptions);
                case "chrome" -> playwright.chromium().launch(launchOptions);
                case "edge" -> playwright.chromium().launch(launchOptions);
                default -> throw new IllegalStateException("Unexpected value: " + browser);
            };
            browserContext = browser.newContext();
            page = browserContext.newPage();
            page.navigate(properties.getProperty("url").trim());
        }
        return page;

    }

    public Properties rtEnvSetup(){
        try {
            FileInputStream fi = new FileInputStream("./src/test/resources/configuration/runtime.properties");
            properties = new Properties();
            properties.load(fi);
        }catch(IOException propLoadExcepion){
            System.out.println(propLoadExcepion.getMessage());
        }
        return properties;
    }

    public void tearBrowser() {
        page.context().browser().close();
    }
}
