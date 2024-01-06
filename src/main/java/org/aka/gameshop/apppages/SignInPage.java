package org.aka.gameshop.apppages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.aka.gameshop.appData.AppData;
import org.aka.gameshop.factory.Utils;
import org.testng.Assert;

import static org.aka.gameshop.factory.Utils.printLogs;

public class SignInPage {
    private Page page;
    private String pageTitle = "//div[@id='body']//div[@class='cls-main-heading']";
    private String emailInput = "input#ctl00_ContentPlaceHolder1_txtEmail";
    private String passwordInput = "input#ctl00_ContentPlaceHolder1_txtPassword";
    private String signINPButton = "input#ctl00_ContentPlaceHolder1_btnSubmit";
    private String emailError = "Please fill in E-mail.";
    private String passwordError = "Please fill in Password.";

    private String passwordLengthMSG = "password should be 6 to 20 characters.";
    private String emailNotExistMSG = "Email address is not exist!";
    // Email address is not exist!
    private String loginFailedMSG = "login failed";

    private String loginPopupTable = "#LightBoxTable";
    private String loginPopupOKButton = "OK";

   // private Page.IsVisibleOptions isVisibleOptions = new Page.IsVisibleOptions();



    public SignInPage(Page page){
        this.page= page;
    }

    public boolean isUserONSignInPage(){
        printLogs("Checking Sign In page title..");
        return page.locator(pageTitle).isVisible();
    }

    public String getSignInPageTitle(){
        String title = page.title();
        printLogs("Sign in page title is " + title);
        return title;
    }

    public void clickSignIn(){
        printLogs("Clicking on Sign In button..");
        page.locator(signINPButton).click();
        printLogs("Sign In button clicked.");
    }

    public void clickPopupOK(){
        printLogs("Closing popup dialog message..");
        Locator loginPopupTableL = page.locator(loginPopupTable);
        Locator loginOKButtonL = loginPopupTableL.getByText(loginPopupOKButton);
        loginOKButtonL.click();
        printLogs("Popup dialog closed.");
    }
    public void enterEmail(String email){
        printLogs("Entering Email address..");
        page.locator(emailInput).fill(email);
        printLogs("Email ["+email+"] entered.");
    }


    public void enterPassword(String password){
        printLogs("Entering password..");
        page.locator(passwordInput).fill(password);
        printLogs("Password ["+password+"] entered.");
    }

    public HomeHeaderPage userSignIn(String username, String password){
        printLogs("Entering Email address..");
        enterEmail(username);
        printLogs("Email ["+username+"] entered. Now entering password..");
        enterPassword(password);
        printLogs("Password ["+password+"] entered. Clicking on Sign-In..");
        clickSignIn();
        printLogs("Sign In button clicked.");
        return new HomeHeaderPage(page);
    }
    public boolean isEmailNotExistMSGVisible(){
        printLogs("Checking for 'Email doesn't exist' message..");
        Locator emailNotExistMSGL = page.getByText(emailNotExistMSG);
        return emailNotExistMSGL.isVisible();
    }
    public boolean isLoginFailedMSGVisible(){
        printLogs("Checking for 'Login Failed' message..");
        Locator loginFailedMSGL = page.getByText(loginFailedMSG);
        return loginFailedMSGL.isVisible();
    }
    public boolean isPassLengthMSGVisible(){
        printLogs("Checking for 'Password length' message..");
        Locator passwordLengthMSGL = page.getByText(passwordLengthMSG);
        return passwordLengthMSGL.isVisible();
    }
    public boolean isPassErrorVisible(){
        printLogs("Checking for 'Password required' message..");
        Locator passwordErrorL = page.getByText(passwordError);
        return passwordErrorL.isVisible();
    }
    public boolean isEmailErrorVisible(){
        printLogs("Checking for 'Email required' message..");
        Locator emailErrorL = page.getByText(emailError);
        return emailErrorL.isVisible();
    }
}
