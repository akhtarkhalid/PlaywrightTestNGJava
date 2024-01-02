package org.aka.gameshop.apppages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import org.aka.gameshop.appData.AppData;

public class HomePage {
    private Page page;


    private String signInButton = "span#ctl00_lblSIGNIN";
    private String searchInput = "input#ctl00_txtSearch";
    private String searchButton = "input#ctl00_ibSearch";
    private String searchPageHeader = "span#searchKeyC";
    private String subscriptionToast = "button#onesignal-slidedown-cancel-button";
    private String signedInText = "//span[@id='ctl00_lblSIGNIN' and contains(text(),'Hi')]";
    public HomePage(Page page){
        this.page=page;
    }

    public String getHomePageTitle(){
        String title = page.title();
        System.out.printf("Home Page Title is - %s",title);
        return title;
    }

    public String getHomePageURL(){
        String url = page.url();
        System.out.printf("Home Page URL is - %s",url);
        return url;
    }

    public String searchForProduct(String product){
        closeSubscriptionToast();
        System.out.printf("Searching for - %s",product);
        page.fill(searchInput,product);
        page.click(searchButton);
        String searchResult = page.textContent(searchPageHeader);
        System.out.printf("Searched results are for - %s",searchResult);
        return searchResult;

    }

    public SignInPage gotoSignInPage(){
        page.click(signInButton);
        page.waitForLoadState(AppData.idleLoadState);
        System.out.println("Clicked on Sign-In button");
        return new SignInPage(page);

    }
    public void closeSubscriptionToast(){
        if(page.locator(subscriptionToast).isVisible())
            page.click(subscriptionToast);
    }

    public boolean isUserSignedIn(){
        return page.isVisible(signedInText);
    }

}
