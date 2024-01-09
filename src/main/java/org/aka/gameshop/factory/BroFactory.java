package org.aka.gameshop.factory;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ViewportSize;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Properties;

import static org.aka.gameshop.factory.Utils.*;


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
        //get browser and headless setting from the properties file
        String browserName = broproperties.getProperty("browser").trim();
        boolean headLess = Boolean.parseBoolean(broproperties.getProperty("headless").trim());

        //set launchoptions to headless/headed mode depending on properties file
        localPlaywright.set(Playwright.create());
        if (headLess) {
            launchOptions.setHeadless(true);
            printLogs("Setting " + browserName + " in headless mode");
        }else{
            launchOptions.setHeadless(false);
            printLogs("Setting " + browserName + " in headed mode");
        }

        //setting channel for chomre and edge
        if (browserName.equalsIgnoreCase("chrome"))
            launchOptions.setChannel("chrome");
        else if (browserName.equalsIgnoreCase("edge"))
            launchOptions.setChannel("edge");

        //getting browser depending on peoperties
        browser = switch (browserName.toLowerCase()) {
            case "chromium" -> getPlaywright().chromium().launch(launchOptions);
            case "firefox" -> getPlaywright().firefox().launch(launchOptions);
            case "safari" -> getPlaywright().webkit().launch(launchOptions);
            case "chrome" -> getPlaywright().chromium().launch(launchOptions);
            case "edge" -> getPlaywright().chromium().launch(launchOptions);
            default -> throw new IllegalStateException("Unexpected value: " + browser);
        };
        localBrowser.set(browser);
        printLogs("Browser set successfully..");

        //If browser is set to headless then just get newContext without setting to Maximize
        if (headLess)
            localBrowserContext.set(getBrowser().newContext());
        else
            localBrowserContext.set(getBrowser().newContext(new Browser.NewContextOptions().setViewportSize(getScreenSize())));

        printLogs("Browser Context set successfully..");
        //
        localPage.set(getBrowserContext().newPage());

        //Navigate to URL depending on properties file
        getPage().navigate(broproperties.getProperty("url").trim());
        printLogs("New Page set and navigated to " + broproperties.getProperty("url").trim() + " successfully..");

        return getPage();
    }

    public static String takeScreenshot(String testName) {
        //Getting properties file for screenshot location and saving screenshot there
        Properties properties = getProperties();
        String path = System.getProperty("user.dir") + properties.getProperty("reportLocation")+ LocalDate.now()+"/screenshot/"+testName+System.currentTimeMillis()+ ".png";
        byte[] buffer = getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(false));
        String base64Path = Base64.getEncoder().encodeToString(buffer);

        return base64Path;
    }

    public void tearBrowser() {
        //closing browser and playwright

        localPage.get().close();
        printLogs("Closing Page..");
        localBrowserContext.get().close();
        printLogs("Closing Browser Context..");
        localBrowser.get().close();
        printLogs("Closing Browser..");
        localPlaywright.get().close();
        printLogs("Closing Playwright..");    }
}
