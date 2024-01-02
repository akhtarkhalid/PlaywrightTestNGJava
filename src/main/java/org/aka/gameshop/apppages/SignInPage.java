package org.aka.gameshop.apppages;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import org.aka.gameshop.appData.AppData;

import java.security.spec.ECField;

public class SignInPage {
    private Page page;
    private String pageTitle = "//div[contains(text(),'LOGIN TO YOUR')]";
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
            //isVisibleOptions.wait(5000);
    }

    public boolean isSignInPageVisible(){
        return page.title().contains(AppData.SIGN_IN_PAGE_TITLE);
    }
    public String getSignInPageButton(){
        return page.textContent(pageTitle);
    }

    public void clickSignIn(){
        page.locator(signINPButton).click();
        System.out.println("Clicked on Sign-in button");
    }

    public void clickPopupOK(){
        Locator loginPopupTableL = page.locator(loginPopupTable);
        Locator loginOKButtonL = loginPopupTableL.getByText(loginPopupOKButton);
        loginOKButtonL.click();
        System.out.println("Closed the popup message...");
    }
    public void enterEmail(String email){
        page.locator(emailInput).fill(email);
        System.out.printf("Email %s entered in Email text box",email);
    }


    public void enterPassword(String password){
        page.locator(passwordInput).fill(password);
        System.out.printf("Password %s entered in Password text box",password);
    }

    public HomePage userSignIn(String username,String password){
        enterEmail(username);
        enterPassword(password);
        clickSignIn();
        return new HomePage(page);
    }
    public boolean isEmailNotExistMSGVisible(){
        Locator emailNotExistMSGL = page.getByText(emailNotExistMSG);
        return emailNotExistMSGL.isVisible();
    }
    public boolean isLoginFailedMSGVisible(){
        Locator loginFailedMSGL = page.getByText(loginFailedMSG);
        return loginFailedMSGL.isVisible();
    }
    public boolean isPassLengthMSGVisible(){
        Locator passwordLengthMSGL = page.getByText(passwordLengthMSG);
        return passwordLengthMSGL.isVisible();
    }
    public boolean isPassErrorVisible(){
        Locator passwordErrorL = page.getByText(passwordError);
        return passwordErrorL.isVisible();
    }
    public boolean isEmailErrorVisible(){
        Locator emailErrorL = page.getByText(emailError);
        return emailErrorL.isVisible();
    }
}
