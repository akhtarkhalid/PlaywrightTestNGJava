package org.aka.gameshop.apppages;

import com.microsoft.playwright.Page;
import org.aka.gameshop.appData.AppData;

import static org.aka.gameshop.factory.Utils.printLogs;

public class HomeHeaderPage {
    private Page page;


    private String signInButton = "span#ctl00_lblSIGNIN";
    private String searchInput = "input#ctl00_txtSearch";
    private String searchButton = "input#ctl00_ibSearch";
    private String searchPageHeader = "span#searchKeyC";
    private String subscriptionToast = "button#onesignal-slidedown-cancel-button";
    private String signedInText = "//span[@id='ctl00_lblSIGNIN' and contains(text(),'Hi')]";
    public HomeHeaderPage(Page page){
        this.page=page;
    }

    public String getHomePageTitle(){
        printLogs("Getting page title..");
        String title = page.title();
        return title;
    }

    public String getHomePageURL(){
        printLogs("Getting home page URL..");
        String url = page.url();
        return url;
    }

    public String searchForProduct(String product){
        closeSubscriptionToast();
        printLogs("Searching for "+ product);
        page.fill(searchInput,product);
        page.click(searchButton);
        String searchResult = page.textContent(searchPageHeader);
        printLogs("Searched results are for "+searchResult);
        return searchResult;

    }

    public SignInPage gotoSignInPage(){
        try {
            printLogs("Navigating to Sign-In page button..");
            page.locator(signInButton).click();
            printLogs("Clicked on Sign-In button..");
            page.waitForSelector("//div[@id='body']//div[@class='cls-main-heading']");
        }catch(Exception e){
            printLogs(e.getMessage(),true);
        }
        return new SignInPage(page);
    }
    public void closeSubscriptionToast(){
        printLogs("Checking for Subscribe now notification..");
        if(page.locator(subscriptionToast).isVisible()) {
            printLogs("Subscribe now notification present. Closing it..");
            page.click(subscriptionToast);
            printLogs("Subscribe now notification closed.");
        }
    }

    public boolean isUserSignedIn(){
        printLogs("Checking is User is logged in..");
        return page.isVisible(signedInText);
    }

}
