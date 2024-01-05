package org.aka.gameshop.factory;

import com.microsoft.playwright.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class BroFactory {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;

    BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();

    private static ThreadLocal<Playwright> localPlaywright = new ThreadLocal<>();
    private static ThreadLocal<Browser> localBrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> localBrowserContext = new ThreadLocal<>();
    private static ThreadLocal<Page> localPage = new ThreadLocal<>();

    public static Playwright getPlaywright(){
        return localPlaywright.get();
    }
    public static Browser getBrowser(){
        return localBrowser.get();
    }
    public static BrowserContext getBrowserContext(){
        return localBrowserContext.get();
    }
    public static Page getPage(){
        return localPage.get();
    }


    public Page initBrowser(Properties broproperties) {
        String browserName = broproperties.getProperty("browser").trim();
        boolean headLess =Boolean.parseBoolean(broproperties.getProperty("headless"));
            //playwright = Playwright.create();
            localPlaywright.set(Playwright.create());
            if(!headLess)
                launchOptions.setHeadless(false);
            if (browserName.equalsIgnoreCase("chrome"))
                launchOptions.setChannel("chrome");
            else if (browserName.equalsIgnoreCase("edge"))
                launchOptions.setChannel("edge");

            browser = switch (browserName.toLowerCase()) {
                case "chromium" -> getPlaywright().chromium().launch(launchOptions);
                case "firefox" -> getPlaywright().firefox().launch(launchOptions);
                case "safari" -> getPlaywright().webkit().launch(launchOptions);
                case "chrome" -> getPlaywright().chromium().launch(launchOptions);
                case "edge" -> getPlaywright().chromium().launch(launchOptions);
                default -> throw new IllegalStateException("Unexpected value: " + browser);
            };
            localBrowser.set(browser);
            //browserContext = browser.newContext();
            localBrowserContext.set(getBrowser().newContext());
            //page = browserContext.newPage();
            localPage.set(getBrowserContext().newPage());
            getPage().navigate(broproperties.getProperty("url").trim());
            //page.navigate(properties.getProperty("url").trim());

        return getPage();

    }



    public void tearBrowser() {
        page.context().browser().close();
    }
}
